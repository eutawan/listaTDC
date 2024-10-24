import models.Deterministic;
import models.FinityAutomate;
import models.State;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class questionThree {
    public static void main(String[] args) {

        State q0 = new State("primeiro");
        State q1 = new State("segundo");
        State q2 = new State("terceiro");

        Set<Character> alfabeto = new HashSet<>(Arrays.asList('0', '1'));

        Set<State> estados = new HashSet<>(Arrays.asList(q0, q1, q2));

        Set<State> estadoFinal = new HashSet<>();
        estadoFinal.add(q1);

        FinityAutomate afd = new Deterministic(estados, alfabeto, q0, estadoFinal);

        afd.defineTransaction(q0, '0', q1);
        afd.defineTransaction(q0, '1', q2);
        afd.defineTransaction(q1, '0', q1);
        afd.defineTransaction(q1, '1', q1);
        afd.defineTransaction(q2, '0', q2);
        afd.defineTransaction(q2, '1', q2);

        afd.verifyString("1000");
        afd.verifyString("00");

    }
}
