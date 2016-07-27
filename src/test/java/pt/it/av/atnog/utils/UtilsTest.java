package pt.it.av.atnog.utils;

import org.junit.Test;
import pt.it.av.atnog.utils.structures.mutableNumber.MutableInteger;
import pt.it.av.atnog.utils.structures.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by mantunes on 21/11/2015.
 */
public class UtilsTest {

    @Test
    public void test_intertwine() {
        List<String> A = new ArrayList<>(), B = new ArrayList<>(), R = new ArrayList<>();
        A.add("A");
        A.add("B");
        A.add("C");

        B.add("A");
        B.add("B");
        B.add("C");

        R.add("A");
        R.add("A");
        R.add("B");
        R.add("B");
        R.add("C");
        R.add("C");

        assertTrue(Utils.intertwine(A, B).equals(R));
    }

    @Test
    public void test_minMax() {
        double array[] = {6.3, 8.4};
        Pair<MutableInteger, MutableInteger> mm = Utils.minMax(array);
        double min = array[mm.a.intValue()], max = array[mm.b.intValue()];

        assertTrue(min == 6.3);
        assertTrue(max == 8.4);
    }
}
