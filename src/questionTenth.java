import models.Deterministic;
import models.State;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class questionTenth {
    public static void main(String[] args) {

        State q0 = new State("primeiro");
        State q1 = new State("segundo");
        State q2 = new State("terceiro");

        Set<State> states = new HashSet<>(Arrays.asList(q0, q1, q2));

        Set<Character> alphabet = new HashSet<>(Arrays.asList('0', '1'));

        Set<State> finalStates = new HashSet<>();

        Deterministic afd = new Deterministic(states, alphabet, q0, finalStates);

        afd.defineTransaction(q0, '0', q1);
        afd.defineTransaction(q0, '1', q2);
        afd.defineTransaction(q1, '0', q1);
        afd.defineTransaction(q1, '1', q2);
        afd.defineTransaction(q2, '0', q1);
        afd.defineTransaction(q2, '1', q2);

        afd.verifyString2("11000");
        afd.verifyString2("1110");
        afd.verifyString2("00000");
        afd.verifyString2("111");
    }
}
