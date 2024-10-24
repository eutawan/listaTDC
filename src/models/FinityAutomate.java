package models;
import java.util.*;
public abstract class FinityAutomate {
    protected Set<State> states;
    protected Set<Character> alphabet;
    protected State initState;
    protected Set<State> finalStates;

    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public void setFinalStates(Set<State> finalStates) {
        this.finalStates = finalStates;
    }

    public State getInitState() {
        return initState;
    }

    public void setInitState(State initState) {
        this.initState = initState;
    }

    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Set<Character> alphabet) {
        this.alphabet = alphabet;
    }

    public Set<State> getFinalStates() {
        return finalStates;
    }

    public FinityAutomate(Set<State> states, Set<Character> alphabet, State initState, Set<State> finalStates){
        this.states = states;
        this.alphabet = alphabet;
        this.initState = initState;
        this.finalStates = finalStates;
    }

    public abstract void defineTransaction(State firstState, Character element, State nextState);

    public abstract void verifyString (String string);
}
