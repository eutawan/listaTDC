import models.Deterministic;
import models.FinityAutomate;
import models.State;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class questionFourth {
    public static void main(String[] args) {

        State q0 = new State("primeiro");

        Set<Character> alfabeto = new HashSet<>(Arrays.asList('0', '1'));

        Set<State> estados =new HashSet<>(List.of(q0));

        Set<State> estadoFinal = new HashSet<>();

        FinityAutomate afd = new Deterministic(estados, alfabeto, q0, estadoFinal);

        afd.defineTransaction(q0, '0', q0);
        afd.defineTransaction(q0, '1', q0);

        afd.verifyString("101");
        afd.verifyString("11");


    }
}
