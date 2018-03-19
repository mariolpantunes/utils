package pt.it.av.atnog.utils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link ArrayUtils}.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class ArrayUtilsTest {
  private static double inc[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
  private static double small[] = {1.0, 0.0, -1.0},
      large[] = {0.0, -1.0, 100.0, -100.0, 22.0, 55.0, 53.5, 20.1, 84.5, 10.2};
  private static int rankSmall[] = {2, 1, 0},
      rankLarge[] = {2, 1, 9, 0, 5, 7, 6, 4, 8, 3};
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
    int mm[] = ArrayUtils.minMax(small);
    double min = small[mm[0]], max = small[mm[1]];

    assertTrue(min == -1.0);
    assertTrue(max == 1.0);
  }

  @Test
  public void test_minMax_large() {
    int mm[] = ArrayUtils.minMax(large);
    double min = large[mm[0]], max = large[mm[1]];

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

  @Test
  public void test_isoData() {
    double t = Math.round(ArrayUtils.isoData(large));
    assertTrue(t == 33);
  }

  @Test
  public void test_rank_small() {
    int t[] = ArrayUtils.rank(small);
    assertTrue(Arrays.equals(t, rankSmall));
  }

  @Test
  public void test_rank_large() {
    int t[] = ArrayUtils.rank(large);
    assertTrue(Arrays.equals(t, rankLarge));
  }

  @Test
  public void test_mean() {
    double mean = ArrayUtils.mean(inc, 0, inc.length);
    assertTrue(mean == 4.5);
  }

  @Test
  public void test_var() {
    double var = ArrayUtils.var(inc, 0, inc.length);
    assertTrue(MathUtils.equals(var, 9.1667, 0.0001));
  }

  @Test
  public void test_std() {
    double std = ArrayUtils.std(inc, 0, inc.length);
    assertTrue(MathUtils.equals(std, 3.0277, 0.0001));
  }

  @Test
  public void test_mm() {
    double mm[] = {0.5, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 8.5};
    double r[] = new double[inc.length];
    ArrayUtils.mm(inc, 0, r, 0, inc.length, 1);
    assertTrue(Arrays.equals(mm, r));
  }

  @Test
  public void test_lr_slope_positive() {
    double x[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, y[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    double lr[] = ArrayUtils.lr(x, y, 0, 0, x.length);
    assertTrue(lr[0] == 1.0);
    assertTrue(lr[1] == 0.0);
  }

  @Test
  public void test_lr_slope_negative() {
    double x[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        y[] = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
    double lr[] = ArrayUtils.lr(x, y, 0, 0, x.length);
    assertTrue(lr[0] == -1.0);
    assertTrue(lr[1] == 9.0);
  }

  @Test
  public void test_pr_positive() {
    double x[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
        y[] = {2, 8, 18, 32, 50, 72, 98, 128, 162, 200};
    double pr[] = ArrayUtils.pr(x, y, 0, 0, x.length);
    assertTrue(MathUtils.equals(pr[0], 2, 0.01));
    assertTrue(MathUtils.equals(pr[1], 2, 0.01));
  }

  @Test
  public void test_pr_negative() {
    double x[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
        y[] = {2, 0.5, 0.222222222222222, 0.125, 0.08, 0.055555555555556, 0.040816326530612,
            0.03125, 0.024691358024691, 0.02};
    double pr[] = ArrayUtils.pr(x, y, 0, 0, x.length);
    assertTrue(MathUtils.equals(pr[0], 2, 0.01));
    assertTrue(MathUtils.equals(pr[1], -2, 0.01));
  }

  @Test
  public void test_er_positive() {
    double x[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
        y[] = {5.436563657, 14.7781122, 40.17107385, 109.1963001, 296.8263182, 806.857587,
            2193.266317, 5961.915974, 16206.16786, 44052.93159};
    double er[] = ArrayUtils.er(x, y, 0, 0, x.length);
    assertTrue(MathUtils.equals(er[0], 2, 0.01));
    assertTrue(MathUtils.equals(er[1], 1, 0.01));
  }

  @Test
  public void test_er_negative() {
    double x[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
        y[] = {0.735758882, 0.270670566, 0.099574137, 0.036631278, 0.013475894, 0.004957504,
            0.001823764, 0.000670925, 0.00024682, 9.07999E-05};
    double er[] = ArrayUtils.er(x, y, 0, 0, x.length);
    assertTrue(MathUtils.equals(er[0], 2, 0.01));
    assertTrue(MathUtils.equals(er[1], -1, 0.01));
  }

  @Test
  public void test_lnr_positive() {
    double x[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
        y[] = {2, 3.386294361, 4.197224577, 4.772588722, 5.218875825, 5.583518938, 5.891820298,
            6.158883083, 6.394449155, 6.605170186};
    double lnr[] = ArrayUtils.lnr(x, y, 0, 0, x.length);
    assertTrue(MathUtils.equals(lnr[0], 2, 0.01));
    assertTrue(MathUtils.equals(lnr[1], 2, 0.01));
  }

  @Test
  public void test_lnr_negative() {
    double x[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
        y[] = {2, 0.613705639, -0.197224577, -0.772588722, -1.218875825, -1.583518938, -1.891820298,
            -2.158883083, -2.394449155, -2.605170186};
    double lnr[] = ArrayUtils.lnr(x, y, 0, 0, x.length);
    assertTrue(MathUtils.equals(lnr[0], -2, 0.01));
    assertTrue(MathUtils.equals(lnr[1], 2, 0.01));
  }

  @Test
  public void test_reverse_sequential() {
    double in[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, out[] = new double[10], r[] = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    ArrayUtils.reverse(in, 0, out, 0, in.length);
    assertTrue(Arrays.equals(out, r));
  }
}
