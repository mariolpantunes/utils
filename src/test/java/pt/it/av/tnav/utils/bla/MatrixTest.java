package pt.it.av.tnav.utils.bla;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link Matrix}.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class MatrixTest {
    private static double a[] = {1, 2, 3, 4, 5, 6}, at[] = {1, 3, 5, 2, 4, 6}, b[] = {1, 2, 3, 4, 5, 6},
            apb[] = {2, 4, 6, 8, 10, 12}, aat[] = {5, 11, 17, 11, 25, 39, 17, 39, 61},
            s[] = {1, 2, 3, 4, 5, 6, 7, 8, 9}, st[] = {1, 4, 7, 2, 5, 8, 3, 6, 9};
    private static Matrix A, At, B, AplusB, AAt;

    @BeforeClass
    public static void setup() {
        A = new Matrix(3, 2, a);
        At = new Matrix(2, 3, at);
        B = new Matrix(3, 2, b);
        AplusB = new Matrix(3, 2, apb);
        AAt = new Matrix(3, 3, aat);
    }

    @Test
    public void test_transpose_linear() {
        Matrix T1 = new Matrix(1, 6, a), T2 = new Matrix(6, 1, a);
        assertTrue(T2.equals(T1.transpose()));
    }

    @Test
    public void test_uTranspose_linear() {
        Matrix T1 = new Matrix(1, 6, a), T2 = new Matrix(6, 1, a);
        assertTrue(T2.equals(T1.uTranspose()));
    }

    @Test
    public void test_transpose_small() {
        assertTrue(At.equals(A.transpose()));
    }

    @Test
    public void test_uTranspose_rect_small() {
        Matrix T = new Matrix(A);
        assertTrue(At.equals(T.uTranspose()));
    }

    @Test
    public void test_uTranspose_square_small() {
        Matrix S = new Matrix(3, 3, s), St = new Matrix(3, 3, st);
        assertTrue(St.equals(S.uTranspose()));
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