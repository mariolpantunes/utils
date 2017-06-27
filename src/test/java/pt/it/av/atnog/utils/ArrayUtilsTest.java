package pt.it.av.atnog.utils;

import org.junit.Test;
import pt.it.av.atnog.utils.structures.mutableNumber.MutableInteger;
import pt.it.av.atnog.utils.structures.tuple.Pair;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link ArrayUtils}.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class ArrayUtilsTest {
  private static double small[] = {1.0, 0.0, -1.0},
      large[] = {0.0, -1.0, 100.0, -100.0, 22.0, 55.0, 53.5, 20.1, 84.5, 10.2};
  private static double A[] = {0, 1, 2, 3, 4, 5}, B[] = {2, 4, 6, 8, 10, 12};

  @Test
  public void test_min_large() {
    double min = large[ArrayUtils.min(large)];
    assertTrue(min == -100.0);
  }

  @Test
  public void test_max_small() {
    double max = small[ArrayUtils.max(small)];
    assertTrue(max == 1.0);
  }

  @Test
  public void test_max_large() {
    double max = large[ArrayUtils.max(large)];
    assertTrue(max == 100.0);
  }

  @Test
  public void test_minMax_small() {
    Pair<MutableInteger, MutableInteger> mm = ArrayUtils.minMax(small);
    double min = small[mm.a.intValue()], max = small[mm.b.intValue()];

    assertTrue(min == -1.0);
    assertTrue(max == 1.0);
  }

  @Test
  public void test_minMax_large() {
    Pair<MutableInteger, MutableInteger> mm = ArrayUtils.minMax(large);
    double min = large[mm.a.intValue()], max = large[mm.b.intValue()];

    assertTrue(min == -100.0);
    assertTrue(max == 100.0);
  }

  @Test
  public void test_min_small() {
    double min = small[ArrayUtils.min(small)];
    assertTrue(min == -1.0);
  }

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
