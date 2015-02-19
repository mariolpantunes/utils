package pt.it.av.atnog.utils.bla;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by mantunes on 11/4/14.
 */
public class MatrixTest {
    private static Matrix A, At, B, AplusB, AAt;

    @BeforeClass
    public static void init() {
        A = new Matrix(3, 2);
        A.set(0, 0, 1);
        A.set(0, 1, 2);
        A.set(1, 0, 3);
        A.set(1, 1, 4);
        A.set(2, 0, 5);
        A.set(2, 1, 6);

        At = new Matrix(2, 3);
        At.set(0, 0, 1);
        At.set(0, 1, 3);
        At.set(0, 2, 5);
        At.set(1, 0, 2);
        At.set(1, 1, 4);
        At.set(1, 2, 6);

        B = new Matrix(3, 2);
        B.set(0, 0, 1);
        B.set(0, 1, 2);
        B.set(1, 0, 3);
        B.set(1, 1, 4);
        B.set(2, 0, 5);
        B.set(2, 1, 6);

        AplusB = new Matrix(3, 2);
        AplusB.set(0, 0, 2);
        AplusB.set(0, 1, 4);
        AplusB.set(1, 0, 6);
        AplusB.set(1, 1, 8);
        AplusB.set(2, 0, 10);
        AplusB.set(2, 1, 12);

        AAt = new Matrix(3, 3);
        AAt.set(0, 0, 5);
        AAt.set(0, 1, 11);
        AAt.set(0, 2, 17);
        AAt.set(1, 0, 11);
        AAt.set(1, 1, 25);
        AAt.set(1, 2, 39);
        AAt.set(2, 0, 17);
        AAt.set(2, 1, 39);
        AAt.set(2, 2, 61);
    }

    @Test
    public void test_transpose() {
        assertTrue(At.equals(A.transpose()));
        Matrix T = A.transpose();
        T.utranspose();
        assertTrue(T.equals(A));
    }

    @Test
    public void test_add() {
        assertTrue(AplusB.equals(A.add(B)));
    }

    @Test
    public void test_sub() {
        assertTrue(A.equals(AplusB.sub(B)));
    }

    @Test
    public void test_mul() {
        assertTrue(AAt.equals(A.mul(At)));
    }

    @Test
    public void test_diag() {
        double data[] = {5, 25, 61};
        Vector d = new Vector(data);
        assertTrue(d.equals(AAt.diag()));
    }

    /*@Test
    public void test_parallel_mul() {
        Matrix A = Matrix.rand(512, 256);
        Matrix B = Matrix.rand(256, 512);
        Matrix C = A.mul(B);
        assertTrue(C.equals(A.parallel_mul(B)));
    }*/
}