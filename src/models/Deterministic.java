package models;

import java.util.*;

public class Deterministic extends FinityAutomate{
    private final Map<State, Map<Character, State>> transactionFunction;

    public Deterministic(Set<State> states, Set<Character> alphabet, State initState, Set<State> finalStates) {
        super(states, alphabet, initState, finalStates);
        this.transactionFunction = new HashMap<>();
    }

    @Override
    public void defineTransaction(State firstState, Character element, State nextState) {
        this.transactionFunction
                .computeIfAbsent(firstState, k -> new HashMap<>())
                .put(element, nextState);
    }

    // Esse metodo foi criado para podermos contar quantos zeros temos numa string, logo é uma prática que
    // não condiz com a máquina de estados finitos, já que seu poder de memória é muito limitado, Quest 10
    public void verifyString2 (String string) {
        State currentState = initState;
        List<String> passStates = new ArrayList<>();
        passStates.add(currentState.name);

        for(char element : string.toCharArray()) {
            if(!this.alphabet.contains(element)) {
                throw new RuntimeException("Invalid element");
            }
            currentState = transactionFunction.get(currentState).get(element);
            passStates.add(currentState.name);
        }

        int stateCountZero = Collections.frequency(passStates, "segundo");
        int stateCountOne = Collections.frequency(passStates, "terceiro");

        if(stateCountZero > stateCountOne) {
            System.out.println("Accept");
        } else {
            System.out.println("Reject");
        }
    }

    @Override
    public void verifyString(String string) {
        State currentState = initState;
        for (char element : string.toCharArray()) {
            if (!this.alphabet.contains(element)) {
                throw new RuntimeException("Invalid element");
            }
            currentState = transactionFunction.get(currentState).get(element);
        }
        if (this.finalStates.contains(currentState)) {
            System.out.println("Accept");
        } else {
            System.out.println("Reject");
        }
    }

    public static Deterministic transformAfdToAfnd(noDeterministic afnd) {
        Set<Character> alphabet = afnd.alphabet;
        State initialState = afnd.initState;

        // Conjuntos de estados para o AFD
        Set<Set<State>> afdStates = new HashSet<>();
        Map<Set<State>, State> stateMapping = new HashMap<>();  // Mapear conjunto de estados para novos estados do AFD

        // Cria o novo estado inicial do AFD
        Set<State> initialAfdStates = afnd.epsilonClosure(Set.of(initialState));
        State afdInitialState = createNewState(initialAfdStates);
        stateMapping.put(initialAfdStates, afdInitialState);

        Deterministic afd = new Deterministic(new HashSet<>(), alphabet, afdInitialState, new HashSet<>());

        // Adiciona o estado inicial no conjunto de estados do AFD
        afd.getStates().add(afdInitialState);

        // Fila para processar novos estados no AFD
        Queue<Set<State>> queue = new LinkedList<>();
        queue.add(initialAfdStates);

        while (!queue.isEmpty()) {
            Set<State> currentSet = queue.poll();
            State afdCurrentState = stateMapping.get(currentSet);

            for (Character symbol : alphabet) {
                Set<State> newSet = new HashSet<>();
                for (State state : currentSet) {
                    Set<State> reachableStates = afnd.getTransaction(state, symbol);
                    newSet.addAll(afnd.epsilonClosure(reachableStates)); // Aplica o fechamento epsilon nos estados alcançáveis
                }

                if (!newSet.isEmpty()) {
                    State newAfdState = stateMapping.get(newSet);
                    if (newAfdState == null) {
                        newAfdState = createNewState(newSet);
                        stateMapping.put(newSet, newAfdState);
                        afd.getStates().add(newAfdState);
                        queue.add(newSet);
                    }
                    afd.defineTransaction(afdCurrentState, symbol, newAfdState);
                }
            }
        }

        // Definir os estados finais do AFD
        for (Set<State> stateSet : stateMapping.keySet()) {
            for (State state : stateSet) {
                if (afnd.getFinalStates().contains(state)) {
                    afd.getFinalStates().add(stateMapping.get(stateSet));
                    break;
                }
            }
        }

        return afd;
    }

    private static State createNewState(Set<State> afndStates) {
        StringBuilder nameBuilder = new StringBuilder();
        for (State state : afndStates) {
            nameBuilder.append(state.getName()).append("_");
        }
        return new State(nameBuilder.toString());
    }

    public void printAutomate() {
        System.out.println("\nDeterministic Automate:");
        for (State state : this.states) {
            Map<Character, State> transitions = this.transactionFunction.get(state);
            if (transitions != null) {
                for (Map.Entry<Character, State> entry : transitions.entrySet()) {
                    char symbol = entry.getKey();
                    State nextState = entry.getValue();
                    System.out.println(state.getName() + " --[" + symbol + "]--> " + nextState.getName());
                }
            }
        }
    }
}
