package pt.it.av.tnav.utils;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link MathUtils}.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class MathUtilsTest {

  @Test
  public void test_gcd_int() {
    assertEquals(17, MathUtils.gcd(17, 34));
  } 

  @Test
  public void test_lgamma() {
    assertEquals(12.801827, MathUtils.lgamma(10), 3);
    assertEquals(359.134205, MathUtils.lgamma(100), 10);
    assertEquals(5905.220423, MathUtils.lgamma(1000), 10);
    assertEquals(82099.717496, MathUtils.lgamma(10000), 10);
    assertEquals(1051287.708974, MathUtils.lgamma(100000), 20);
  }

  @Test
  public void test_factorial() {
    assertEquals(1.0, MathUtils.factorial(0), StrictMath.ulp(1.0));
    assertEquals(1.0, MathUtils.factorial(1), StrictMath.ulp(1.0));
    assertEquals(120.0, MathUtils.factorial(5), StrictMath.ulp(1.0));
    assertEquals(3628800.0, MathUtils.factorial(10), StrictMath.ulp(1.0));
  }

  @Test
  public void test_binomial() {
    assertEquals(1.0, MathUtils.binomial(4, 0), StrictMath.ulp(1.0));
    assertEquals(4.0, MathUtils.binomial(4, 1), StrictMath.ulp(1.0));
    assertEquals(6.0, MathUtils.binomial(4, 2), StrictMath.ulp(1.0));
    assertEquals(4.0, MathUtils.binomial(4, 3), StrictMath.ulp(1.0));
    assertEquals(1.0, MathUtils.binomial(4, 4), StrictMath.ulp(1.0));
    assertEquals(1.0, MathUtils.binomial(5, 0), StrictMath.ulp(1.0));
    assertEquals(5.0, MathUtils.binomial(5, 1), StrictMath.ulp(1.0));
    assertEquals(10.0, MathUtils.binomial(5, 2), StrictMath.ulp(1.0));
    assertEquals(10.0, MathUtils.binomial(5, 3), StrictMath.ulp(1.0));
    assertEquals(5.0, MathUtils.binomial(5, 4), StrictMath.ulp(1.0));
    assertEquals(1.0, MathUtils.binomial(5, 5), StrictMath.ulp(1.0));
    assertEquals(1.0, MathUtils.binomial(5, 5), StrictMath.ulp(1.0));
    assertEquals(161700.0, MathUtils.binomial(100, 3), StrictMath.ulp(1.0));
    assertEquals(161700.0, MathUtils.binomial(100, 97), StrictMath.ulp(1.0));
    assertEquals(304985751.0, MathUtils.binomial(294, 4), StrictMath.ulp(1.0));
  }

  @Test
  public void test_binomialBD() {
    assertEquals(BigDecimal.ONE, MathUtils.binomialBD(4, 0));
    assertEquals(new BigDecimal(4.0), MathUtils.binomialBD(4, 1));
    assertEquals(new BigDecimal(6.0), MathUtils.binomialBD(4, 2));
    assertEquals(new BigDecimal(4.0), MathUtils.binomialBD(4, 3));
    assertEquals(BigDecimal.ONE, MathUtils.binomialBD(4, 4));
    assertEquals(new BigDecimal(161700.0), MathUtils.binomialBD(100, 3));
    assertEquals(new BigDecimal(161700.0), MathUtils.binomialBD(100, 97));
    assertEquals(new BigDecimal(304985751.0), MathUtils.binomialBD(294, 4));
  }

  @Test
  public void test_permutation() {
    assertEquals(90.0, MathUtils.permutation(10, 2), StrictMath.ulp(1.0));
    assertEquals(3360.0, MathUtils.permutation(16, 3), StrictMath.ulp(1.0));
    assertEquals(256032000.0, MathUtils.permutation(128, 4), StrictMath.ulp(1.0));
  }

  @Test
  public void test_averagePrecision() {
    boolean v[] = new boolean[6];
    v[0] = true;
    v[1] = false;
    v[2] = false;
    v[3] = true;
    v[4] = true;
    v[5] = true;
    double ap = MathUtils.averagePrecision(v);
    assertEquals(true, MathUtils.equals(ap, 0.6917, 0.0001));
  }

  @Test
  public void test_equals_00() {
    assertEquals(false, MathUtils.equals(1.1, 1.2));
    assertEquals(false, MathUtils.equals(1.2, 1.3));
    assertEquals(false, MathUtils.equals(1.3, 1.4));
    assertEquals(false, MathUtils.equals(1.4, 1.5));
  }
}
