package pt.it.av.atnog.utils.linearAlgebra;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by mantunes on 11/4/14.
 */
public class MatrixTest {
    @Test
    public void test_transpose() {
        Matrix A = new Matrix(3, 2), B = new Matrix(2, 3);

        A.set(0, 0, 1);
        A.set(0, 1, 2);
        A.set(1, 0, 3);
        A.set(1, 1, 4);
        A.set(2, 0, 5);
        A.set(2, 1, 6);

        B.set(0, 0, 1);
        B.set(0, 1, 3);
        B.set(0, 2, 5);
        B.set(1, 0, 2);
        B.set(1, 1, 4);
        B.set(1, 2, 6);

        assertTrue(B.equals(A.transpose()));
    }

    @Test
    public void test_add() {
        Matrix A = new Matrix(3, 2),
                B = new Matrix(3, 2),
                C = new Matrix(3, 2);

        A.set(0, 0, 1);
        A.set(0, 1, 2);
        A.set(1, 0, 3);
        A.set(1, 1, 4);
        A.set(2, 0, 5);
        A.set(2, 1, 6);

        B.set(0, 0, 1);
        B.set(0, 1, 2);
        B.set(1, 0, 3);
        B.set(1, 1, 4);
        B.set(2, 0, 5);
        B.set(2, 1, 6);

        C.set(0, 0, 2);
        C.set(0, 1, 4);
        C.set(1, 0, 6);
        C.set(1, 1, 8);
        C.set(2, 0, 10);
        C.set(2, 1, 12);

        assertTrue(C.equals(A.add(B)));
    }

    @Test
    public void test_sub() {
        Matrix A = new Matrix(3, 2),
                B = new Matrix(3, 2),
                C = new Matrix(3, 2);

        A.set(0, 0, 1);
        A.set(0, 1, 2);
        A.set(1, 0, 3);
        A.set(1, 1, 4);
        A.set(2, 0, 5);
        A.set(2, 1, 6);

        B.set(0, 0, 1);
        B.set(0, 1, 2);
        B.set(1, 0, 3);
        B.set(1, 1, 4);
        B.set(2, 0, 5);
        B.set(2, 1, 6);

        C.set(0, 0, 2);
        C.set(0, 1, 4);
        C.set(1, 0, 6);
        C.set(1, 1, 8);
        C.set(2, 0, 10);
        C.set(2, 1, 12);

        assertTrue(B.equals(C.sub(A)));
    }
}
