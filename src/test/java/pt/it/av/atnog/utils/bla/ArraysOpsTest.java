package pt.it.av.atnog.utils.bla;

import org.junit.Test;
import pt.it.av.atnog.utils.ArrayUtils;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for ArrayUtils library.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class ArraysOpsTest {
    double A[] = {0, 1, 2, 3, 4, 5}, B[] = {2, 4, 6, 8, 10, 12};

    @Test
    public void test_add_array() {
        double C[] = new double[3], R[] = {11, 14, 17};
        ArrayUtils.add(A, 3, B, 3, C, 0, 3);
        assertTrue(Arrays.equals(C, R));
    }

    @Test
    public void test_add_scalar() {
        double C[] = new double[3], R[] = {2, 3, 4};
        ArrayUtils.add(A, 0, 2, C, 0, 3);
        assertTrue(Arrays.equals(C, R));
    }

    @Test
    public void test_sub_array() {
        double C[] = new double[3], R[] = {-5, -6, -7};
        ArrayUtils.sub(A, 3, B, 3, C, 0, 3);
        assertTrue(Arrays.equals(C, R));
    }

    @Test
    public void test_sub_scalar() {
        double C[] = new double[3], R[] = {-2, -1, 0};
        ArrayUtils.sub(A, 0, 2, C, 0, 3);
        assertTrue(Arrays.equals(C, R));
    }

    @Test
    public void test_mul_array() {
        double C[] = new double[3], R[] = {24, 40, 60};
        ArrayUtils.mul(A, 3, B, 3, C, 0, 3);
        assertTrue(Arrays.equals(C, R));
    }

    @Test
    public void test_mul_scalar() {
        double C[] = new double[3], R[] = {0, 2, 4};
        ArrayUtils.mul(A, 0, 2, C, 0, 3);
        assertTrue(Arrays.equals(C, R));
    }

    @Test
    public void test_div_array() {
        double C[] = new double[3], R[] = {0.375, 0.4, 5.0 / 12.0};
        ArrayUtils.div(A, 3, B, 3, C, 0, 3);
        assertTrue(Arrays.equals(C, R));
    }

    @Test
    public void test_div_scalar() {
        double C[] = new double[3], R[] = {0, 0.5, 1.0};
        ArrayUtils.div(A, 0, 2, C, 0, 3);
        assertTrue(Arrays.equals(C, R));
    }

    @Test
    public void test_pow_array() {
        double C[] = new double[3], R[] = {0.0, 1.0, 64.0};
        ArrayUtils.pow(A, 0, B, 0, C, 0, 3);
        assertTrue(Arrays.equals(C, R));
    }

    @Test
    public void test_pow_scalar() {
        double C[] = new double[3], R[] = {Math.sqrt(3.0), Math.sqrt(4.0), Math.sqrt(5.0)};
        ArrayUtils.pow(A, 3, 1.0 / 2.0, C, 0, 3);
        assertTrue(Arrays.equals(C, R));
    }
}