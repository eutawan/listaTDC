import com.sun.jdi.event.StepEvent;
import models.Deterministic;
import models.Nondeterministic;
import models.State;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class questionEleven {
    public static void main(String[] args) {
        State q0 = new State("primeiro");
        State q1 = new State("segundo");
        State q2 = new State("terceiro");

        Set<State> states = new HashSet<>(Arrays.asList(q0, q1, q2));
        Set<State> finalStates = new HashSet<>(List.of(q0));
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));

        Nondeterministic afnd = new Nondeterministic(states, alphabet, q0, finalStates);

        afnd.defineTransaction(q0, 'b', q1);
        afnd.defineTransaction(q0, '\0', q2);
        afnd.defineTransaction(q1, 'a', q1);
        afnd.defineTransaction(q1, 'a', q2);
        afnd.defineTransaction(q1, 'b', q2);
        afnd.defineTransaction(q2, 'a', q0);

        afnd.printAutomate();

        Deterministic afd = Deterministic.transformAfdToAfnd(afnd);

        afd.printAutomate();

    }
}
