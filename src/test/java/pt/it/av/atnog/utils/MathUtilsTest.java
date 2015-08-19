package pt.it.av.atnog.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by mantunes on 8/18/15.
 */
public class MathUtilsTest {
    @Test
    public void test_factorial() {
        assertTrue(MathUtils.factorial(0) == 1.0);
        assertTrue(MathUtils.factorial(1) == 1.0);
        assertTrue(MathUtils.factorial(5) == 120.0);
        assertTrue(MathUtils.factorial(10) == 3628800.0);
    }

    @Test
    public void test_combination() {
        assertTrue(MathUtils.combination(4, 0) == 1.0);
        assertTrue(MathUtils.combination(4, 1) == 4.0);
        assertTrue(MathUtils.combination(4, 2) == 6.0);
        assertTrue(MathUtils.combination(4, 3) == 4.0);
        assertTrue(MathUtils.combination(4, 4) == 1.0);
        assertTrue(MathUtils.combination(5, 0) == 1.0);
        assertTrue(MathUtils.combination(5, 1) == 5.0);
        assertTrue(MathUtils.combination(5, 2) == 10.0);
        assertTrue(MathUtils.combination(5, 3) == 10.0);
        assertTrue(MathUtils.combination(5, 4) == 5.0);
        assertTrue(MathUtils.combination(5, 5) == 1.0);
        assertTrue(MathUtils.combination(5, 5) == 1.0);
        assertTrue(MathUtils.combination(100, 3) == 161700.0);
        assertTrue(MathUtils.combination(100, 97) == 161700.0);
    }

    @Test
    public void test_permutation() {
        assertTrue(MathUtils.permutation(10, 2) == 90.0);
    }
}
