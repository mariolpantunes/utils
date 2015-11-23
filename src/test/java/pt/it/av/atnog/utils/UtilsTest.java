package pt.it.av.atnog.utils;

import org.junit.Test;

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
}
