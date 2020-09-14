package pt.it.av.tnav.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link ArrayUtils}.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class ArrayUtilsTest {
  private static double ones[] = { 1, 1, 1 }, zeros[] = { 0, 0, 0 }, nones[] = { -1, -1, -1 },
      inc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, small[] = { 1.0, 0.0, -1.0 },
      large[] = { 0.0, -1.0, 100.0, -100.0, 22.0, 55.0, 53.5, 20.1, 84.5, 10.2 }, A[] = { 0, 1, 2, 3, 4, 5 },
      B[] = { 2, 4, 6, 8, 10, 12 };
  private static int rankSmall[] = { 2, 1, 0 }, rankLarge[] = { 2, 1, 9, 0, 5, 7, 6, 4, 8, 3 };

  @Test
  public void test_min_large() {
    double min = large[ArrayUtils.min(large)];
    assertEquals(-100.0, min, MathUtils.eps());
  }

  @Test
  public void test_max_small() {
    double max = small[ArrayUtils.max(small)];
    assertEquals(1.0, max, MathUtils.eps());
  }

  @Test
  public void test_max_large() {
    double max = large[ArrayUtils.max(large)];
    assertEquals(100.0, max, MathUtils.eps());
  }

  @Test
  public void test_minMax_small() {
    int mm[] = ArrayUtils.minMax(small);
    double min = small[mm[0]], max = small[mm[1]];
    assertEquals(-1.0, min, MathUtils.eps());
    assertEquals(1.0, max, MathUtils.eps());
  }

  @Test
  public void test_minMax_large() {
    int mm[] = ArrayUtils.minMax(large);
    double min = large[mm[0]], max = large[mm[1]];
    assertEquals(-100.0, min, MathUtils.eps());
    assertEquals(100.0, max, MathUtils.eps());
  }

  @Test
  public void test_min_small() {
    double min = small[ArrayUtils.min(small)];
    assertEquals(-1.0, min, MathUtils.eps());
  }

  @Test
  public void test_add_array() {
    double C[] = new double[3], R[] = { 11, 14, 17 };
    ArrayUtils.add(A, 3, B, 3, C, 0, 3);
    assertArrayEquals(R, C, MathUtils.eps());
  }

  @Test
  public void test_add_scalar() {
    double C[] = new double[3], R[] = { 2, 3, 4 };
    ArrayUtils.add(A, 0, 2, C, 0, 3);
    assertArrayEquals(R, C, MathUtils.eps());
  }

  @Test
  public void test_sub_array() {
    double C[] = new double[3], R[] = { -5, -6, -7 };
    ArrayUtils.sub(A, 3, B, 3, C, 0, 3);
    assertArrayEquals(R, C, MathUtils.eps());
  }

  @Test
  public void test_sub_scalar() {
    double C[] = new double[3], R[] = { -2, -1, 0 };
    ArrayUtils.sub(A, 0, 2, C, 0, 3);
    assertArrayEquals(R, C, MathUtils.eps());
  }

  @Test
  public void test_mul_array() {
    double C[] = new double[3], R[] = { 24, 40, 60 };
    ArrayUtils.mul(A, 3, B, 3, C, 0, 3);
    assertArrayEquals(R, C, MathUtils.eps());
  }

  @Test
  public void test_mul_scalar() {
    double C[] = new double[3], R[] = { 0, 2, 4 };
    ArrayUtils.mul(A, 0, 2, C, 0, 3);
    assertArrayEquals(R, C, MathUtils.eps());
  }

  @Test
  public void test_div_array() {
    double C[] = new double[3], R[] = { 0.375, 0.4, 5.0 / 12.0 };
    ArrayUtils.div(A, 3, B, 3, C, 0, 3);
    assertArrayEquals(R, C, MathUtils.eps());
  }

  @Test
  public void test_div_scalar() {
    double C[] = new double[3], R[] = { 0, 0.5, 1.0 };
    ArrayUtils.div(A, 0, 2, C, 0, 3);
    assertArrayEquals(R, C, MathUtils.eps());
  }

  @Test
  public void test_pow_array() {
    double C[] = new double[3], R[] = { 0.0, 1.0, 64.0 };
    ArrayUtils.pow(A, 0, B, 0, C, 0, 3);
    assertArrayEquals(R, C, MathUtils.eps());
  }

  @Test
  public void test_pow_scalar() {
    double C[] = new double[3], R[] = { Math.sqrt(3.0), Math.sqrt(4.0), Math.sqrt(5.0) };
    ArrayUtils.pow(A, 3, 1.0 / 2.0, C, 0, 3);
    assertArrayEquals(R, C, MathUtils.eps());
  }

  @Test
  public void test_isoData_large() {
    int t = (int) Math.round(ArrayUtils.isoData(large));
    assertEquals(33, t);
  }

  @Test
  public void test_isoData_one() {
    int t = (int) Math.round(ArrayUtils.isoData(new double[] { 1.0 }));
    assertEquals(1, t);
  }

  @Test
  public void test_isoData_two() {
    int t = (int) Math.round(ArrayUtils.isoData(new double[] { 1.0, 3.0 }));
    assertEquals(2, t);
  }

  @Test(timeout = 1000)
  public void test_isoData_equals() {
    double in[] = { 5.0, 5.0, 5.0 };
    int t = (int) Math.round(ArrayUtils.isoData(in));
    assertEquals(5.0, t, MathUtils.eps());
  }

  @Test
  public void test_rank_small() {
    int t[] = ArrayUtils.rank(small);
    assertArrayEquals(rankSmall, t);
  }

  @Test
  public void test_rank_large() {
    int t[] = ArrayUtils.rank(large);
    assertArrayEquals(rankLarge, t);
  }

  @Test
  public void test_mean() {
    double mean = ArrayUtils.mean(inc, 0, inc.length);
    assertEquals(4.5, mean, MathUtils.eps());
  }

  @Test
  public void test_var() {
    double var = ArrayUtils.var(inc, 0, inc.length);
    assertEquals(9.1667, var, 0.0001);
  }

  @Test
  public void test_std() {
    double std = ArrayUtils.std(inc, 0, inc.length);
    assertEquals(3.0277, std, 0.0001);
  }

  @Test
  public void test_sma_last() {
    double values[] = { 0, 2, 4, 6, 8, 10 }, times[] = { 0, 1, 1.2, 2.3, 2.9, 5 };
    double correct[] = { 0.00, 1.80, 2.20, 5.40, 6.6, 9.0 };
    double out[] = new double[values.length];
    ArrayUtils.sma_last(values, times, 2, out);
    assertArrayEquals(correct, out, 0.1);
  }

  @Test
  public void test_sma_next() {
    double values[] = { 0, 2, 4, 6, 8, 10 }, times[] = { 0, 1, 1.2, 2.3, 2.9, 5 };
    double correct[] = { 0.00, 3.00, 3.20, 7.00, 7.60, 10.00 };
    double out[] = new double[values.length];
    ArrayUtils.sma_next(values, times, 2, out);
    assertArrayEquals(correct, out, 0.1);
  }

  @Test
  public void test_sma_linear() {
    double values[] = { 0, 2, 4, 6, 8, 10 }, times[] = { 0, 1, 1.2, 2.3, 2.9, 5 };
    double correct[] = { 0.00, 2.69, 3.23, 6.28, 7.47, 9.76 };
    double out[] = new double[values.length];
    ArrayUtils.sma_linear(values, times, 2, out);
    assertArrayEquals(correct, out, 0.1);
  }

  @Test
  public void test_reverse_sequential() {
    double in[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, out[] = new double[10], r[] = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
    ArrayUtils.reverse(in, 0, out, 0, in.length);
    assertArrayEquals(r, out, MathUtils.eps());
  }

  @Test
  public void test_all_equal_equal() {
    double a[] = { 1.0, 1.0, 1.0, 1.0, 1.0 };
    assertTrue(ArrayUtils.allEqual(a));
  }

  @Test
  public void test_all_equal_different() {
    double a[] = { 1.1, 1.2, 1.3, 1.4, 1.5 };
    assertFalse(ArrayUtils.allEqual(a));
  }

  @Test
  public void test_all_different_equal() {
    double a[] = { 1.1, 1.2, 1.3, 1.0, 1.0 };
    assertFalse(ArrayUtils.allDifferent(a));
  }

  @Test
  public void test_all_different_different() {
    double a[] = { 1.1, 1.2, 1.3, 1.4, 1.5 };
    assertTrue(ArrayUtils.allDifferent(a));
  }

  @Test
  public void test_median_small() {
    double m = ArrayUtils.median(small);
    assertEquals(0.0, m, MathUtils.eps());
  }

  @Test
  public void test_median_large() {
    double m = ArrayUtils.median(large);
    assertEquals(21.05, m, MathUtils.eps());
  }

  @Test
  public void test_minkowski_distance() {
    double d = ArrayUtils.minkowskiDistance(ones, zeros, 3);
    assertEquals(1.442, d, 0.001);
    d = ArrayUtils.minkowskiDistance(nones, zeros, 3);
    assertEquals(1.442, d, 0.001);
  }

  @Test
  public void test_euclidean_distance() {
    double a[] = { 25.0, 15.0, -5.0, 15.0, 18.0, 0.0, -5.0, 0.0, 11.0 }, d = ArrayUtils.euclideanDistance(a, a);
    assertEquals(0.0, d, 0.001);
  }
}
