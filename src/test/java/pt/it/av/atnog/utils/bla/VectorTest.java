package pt.it.av.atnog.utils.bla;

import org.junit.BeforeClass;
import org.junit.Test;
import pt.it.av.atnog.utils.Utils;

import static org.junit.Assert.assertTrue;

/**
 * Created by mantunes on 11/26/14.
 */
public class VectorTest {
    private static Vector A, Aplus1, B, C;

    @BeforeClass
    public static void init() {
        A = Vector.ones(5);
        Aplus1 = new Vector(5);
        Aplus1.set(2.0);
        double dataB[] = {1.0, 3.0, 5.0, 5.0, 6.0};
        B = new Vector(dataB);
        double dataC[] = {4.0, 6.0, 10.0, 12.0, 13.0,};
        C = new Vector(dataC);
    }

    @Test
    public void test_add_scalar() {
        Vector C = A.add(1.0);
        assertTrue(C.equals(Aplus1));
    }

    @Test
    public void test_add_vector() {
        Vector C = A.add(A);
        assertTrue(C.equals(Aplus1));
    }

    @Test
    public void test_sub_scalar() {
        Vector C = Aplus1.sub(1.0);
        assertTrue(C.equals(A));
    }

    @Test
    public void test_sub_vector() {
        Vector C = Aplus1.sub(A);
        assertTrue(C.equals(A));
    }

    @Test
    public void test_mean() {
        assertTrue(A.mean() == 1.0);
        assertTrue(B.mean() == 4.0);
        assertTrue(C.mean() == 9.0);
    }

    @Test
    public void test_var() {
        assertTrue(A.var() == 0.0);
        assertTrue(B.var() == 4.0);
        assertTrue(C.var() == 15.0);
    }

    @Test
    public void test_cov() {
        assertTrue(A.cov(B) == 0.0);
        assertTrue(A.cov(C) == 0.0);
        assertTrue(B.cov(C) == 7.5);
    }

    @Test
    public void test_correlation() {
        assertTrue(Utils.equals(B.corr(C), 0.96825, 0.001));
    }
}
