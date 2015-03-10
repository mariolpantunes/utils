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
    public void test_transpose_small() {
        assertTrue(At.equals(A.transpose()));
        Matrix T = A.transpose();
        T.uTranspose();
        assertTrue(T.equals(A));
    }

    @Test
    public void test_transpose_large() {
        Matrix A = Matrix.rand(256, 512);
        Matrix T1 = A.transpose(), T2 = Naive.transpose(A);
        assertTrue(T1.equals(T2));
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
    public void test_mul_small() {
        assertTrue(AAt.equals(A.mul(At)));
    }

    @Test
    public void test_mull_large() {
        Matrix A = Matrix.rand(256, 512), B = Matrix.rand(512, 256);
        Matrix R1 = A.mul(B), R2 = Naive.mul(A, B);
        assertTrue(R1.equals(R2));
    }

    @Test
    public void test_diag() {
        double data[] = {1, 4};
        Vector d = new Vector(data);
        assertTrue(d.equals(A.diag()));

        data = new double[]{2};
        d = new Vector(data);
        assertTrue(d.equals(A.diag(1)));

        data = new double[]{3, 6};
        d = new Vector(data);
        assertTrue(d.equals(A.diag(-1)));

        data = new double[]{5};
        d = new Vector(data);
        assertTrue(d.equals(A.diag(-2)));
    }
}