package pt.it.av.atnog.utils.bla;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for Arrays library.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class ArraysTest {
    double A[] = {0, 1, 2, 3, 4, 5}, B[] = {2, 4, 6, 8, 10, 12};

    @Test
    public void test_add_array() {
        double C[] = new double[3], R[] = {11, 14, 17};
        Arrays.add(A, 3, B, 3, C, 0, 3);
        assertTrue(java.util.Arrays.equals(C, R));
    }

    @Test
    public void test_add_scalar() {
        double C[] = new double[3], R[] = {2, 3, 4};
        Arrays.add(A, 0, 2, C, 0, 3);
        assertTrue(java.util.Arrays.equals(C, R));
    }

    @Test
    public void test_sub_array() {
        double C[] = new double[3], R[] = {-5, -6, -7};
        Arrays.sub(A, 3, B, 3, C, 0, 3);
        assertTrue(java.util.Arrays.equals(C, R));
    }

    @Test
    public void test_sub_scalar() {
        double C[] = new double[3], R[] = {-2, -1, 0};
        Arrays.sub(A, 0, 2, C, 0, 3);
        assertTrue(java.util.Arrays.equals(C, R));
    }

    @Test
    public void test_mul_array() {
        double C[] = new double[3], R[] = {24, 40, 60};
        Arrays.mul(A, 3, B, 3, C, 0, 3);
        assertTrue(java.util.Arrays.equals(C, R));
    }

    @Test
    public void test_mul_scalar() {
        double C[] = new double[3], R[] = {0, 2, 4};
        Arrays.mul(A, 0, 2, C, 0, 3);
        assertTrue(java.util.Arrays.equals(C, R));
    }

    @Test
    public void test_div_array() {
        double C[] = new double[3], R[] = {0.375, 0.4, 5.0 / 12.0};
        Arrays.div(A, 3, B, 3, C, 0, 3);
        assertTrue(java.util.Arrays.equals(C, R));
    }

    @Test
    public void test_div_scalar() {
        double C[] = new double[3], R[] = {0, 0.5, 1.0};
        Arrays.div(A, 0, 2, C, 0, 3);
        assertTrue(java.util.Arrays.equals(C, R));
    }
}