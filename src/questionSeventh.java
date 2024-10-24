import models.Deterministic;
import models.FinityAutomate;
import models.State;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class questionSeventh {
    public static void main(String[] args) {
        State q0 = new State("primeiro");
        State q1 = new State("segundo");
        State q2 = new State("terceiro");
        State q3 = new State("quarto");

        Set<Character> alfabeto = new HashSet<>(Arrays.asList('0', '1'));

        Set<State> estados = new HashSet<>(Arrays.asList(q0, q1, q2, q3));

        Set<State> estadoFinal = new HashSet<>();
        estadoFinal.add(q3);

        FinityAutomate afd = new Deterministic(estados, alfabeto, q0, estadoFinal);

        afd.defineTransaction(q0, '0', q1);
        afd.defineTransaction(q0, '1', q0);
        afd.defineTransaction(q1, '0', q1);
        afd.defineTransaction(q1, '1', q2);
        afd.defineTransaction(q2, '0', q3);
        afd.defineTransaction(q2, '1', q0);
        afd.defineTransaction(q3, '0', q3);
        afd.defineTransaction(q3, '1', q3);

        afd.verifyString("1000");
        afd.verifyString("11");
        afd.verifyString("1010");

    }
}
