package models;

import java.util.*;

public class noDeterministic extends FinityAutomate{
    private final Map<State, Map<Character, Set<State>>> transactionFunction;

    public noDeterministic(Set<State> states, Set<Character> alphabet, State initState, Set<State> finalStates){
        super(states, alphabet, initState, finalStates);
        this.transactionFunction = new HashMap<>();

        for (State state : states) {
            this.transactionFunction.put(state, new HashMap<>());
        }
    }

    @Override
    public void defineTransaction(State firstState, Character element, State nextState) {
        this.transactionFunction
                .computeIfAbsent(firstState, k -> new HashMap<>())
                .computeIfAbsent(element, k -> new HashSet<>())
                .add(nextState);
    }

    @Override
    public void verifyString(String string) {
        boolean accepted = processString(string, initState);
        if (accepted) {
            System.out.println("Accept");
        } else {
            System.out.println("Reject");
        }
    }

    private boolean processString(String input, State currentState) {
        if (input.isEmpty()) {
            return finalStates.contains(currentState);
        }

        char element = input.charAt(0);
        String rest = input.substring(1);

        if (transactionFunction.containsKey(currentState) && transactionFunction.get(currentState).containsKey(element)) {
            for (State nextState : transactionFunction.get(currentState).get(element)) {
                if (processString(rest, nextState)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Set<State> getTransaction(State state, Character symbol) {
        // Verifica se o estado tem uma transição para o símbolo dado
        if (this.transactionFunction.containsKey(state) && this.transactionFunction.get(state).containsKey(symbol)) {
            return this.transactionFunction.get(state).get(symbol);
        }
        return new HashSet<>();  // Retorna um conjunto vazio se não houver transição
    }

    public Set<State> epsilonClosure(Set<State> states) {
        // O fechamento epsilon inicial contém os próprios estados
        Set<State> closure = new HashSet<>(states);

        // Fila para processar os estados, começando pelos estados iniciais
        Queue<State> queue = new LinkedList<>(states);

        // Processa a fila
        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            // Para cada transição epsilon a partir do estado atual
            Set<State> epsilonTransactions = getEpsilonTransactions(currentState);
            for (State epsilonState : epsilonTransactions) {
                // Se o estado ainda não estiver no fechamento epsilon, adicione e processe
                if (!closure.contains(epsilonState)) {
                    closure.add(epsilonState);
                    queue.add(epsilonState);  // Adiciona à fila para processar suas transições epsilon
                }
            }
        }

        return closure;
    }

    public Set<State> getEpsilonTransactions(State state) {
        // Consideramos '\0' como símbolo para transições epsilon
        if (this.transactionFunction.containsKey(state) && this.transactionFunction.get(state).containsKey('\0')) {
            return this.transactionFunction.get(state).get('\0');  // Retorna os estados alcançáveis via epsilon
        }
        return new HashSet<>();  // Retorna conjunto vazio se não houver transição epsilon
    }

    public void printAutomate() {
        System.out.println("\nnoDeterministic Automate:");
        for (State state : this.states) {
            Map<Character, Set<State>> transitions = this.transactionFunction.get(state);
            if (transitions != null) {
                for (Map.Entry<Character, Set<State>> entry : transitions.entrySet()) {
                    char symbol = entry.getKey();
                    Set<State> nextStates = entry.getValue();
                    for (State nextState : nextStates) {
                        System.out.println(state.getName() + " --[" + symbol + "]--> " + nextState.getName());
                    }
                }
            }
        }
    }
}
