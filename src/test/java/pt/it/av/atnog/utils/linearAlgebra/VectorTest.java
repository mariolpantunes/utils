package pt.it.av.atnog.utils.linearAlgebra;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by mantunes on 11/26/14.
 */
public class VectorTest {
    private static Vector A, Aplus1;

    @BeforeClass
    public static void init() {
        A = Vector.ones(5);
        Aplus1 = new Vector(5);
        Aplus1.set(2.0);
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
}
