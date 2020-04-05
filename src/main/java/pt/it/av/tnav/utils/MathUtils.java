package pt.it.av.tnav.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Collection of general-purpose Math utilities.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public final class MathUtils {
  private static final double L9[] = { 0.99999999999980993227684700473478, 676.520368121885098567009190444019,
      -1259.13921672240287047156078755283, 771.3234287776530788486528258894, -176.61502916214059906584551354,
      12.507343278686904814458936853, -0.13857109526572011689554707, 9.984369578019570859563e-6,
      1.50563273514931155834e-7 };

  // log(2*PI)/2
  private static final double doubleEpsilon, LN_SQRT2PI = Math.log(2.0 * Math.PI) / 2.0;
  private static final float floatEpsilon;

  static {
    float feps = 0.5f;
    while (1 + feps > 1) {
      feps /= 2.0f;
    }
    floatEpsilon = feps;

    double deps = 0.5;
    while (1 + deps > 1) {
      deps /= 2.0;
    }
    doubleEpsilon = deps;
  }

  /**
   * Utility class, lets make the constructor private.
   */
  private MathUtils() {
  }

  public static long poisonDist(final long lambda) {
    long rv = 0;
    if (lambda < 20) {
      // e^700
      rv = poisonDistSmall(lambda, 500);
    } else {
      rv = poisonDistLarge(lambda);
    }
    return rv;
  }

  /**
   * Generator of Poisson-distributed random variables (Junhao, based on Knuth).
   * This algorithm is not optimal, if performance is an issue an alternative
   * should be used. Returns number from a possion distribution with mean lambda.
   *
   * @param lambda mean of the poisson distribution
   * @return number from a possion distribution with mean lambda
   * @see <a href="https://en.wikipedia.org/wiki/Poisson_distribution">Poisson
   *      distribution</a>
   */
  public static long poisonDistSmall(final long lambda, final double step) {
    double ll = lambda, p = 1.0;
    long k = 0;
    do {
      k++;
      p *= ThreadLocalRandom.current().nextDouble();
      if (p < Math.E && ll > 0) {
        if (ll > step) {
          p = p * Math.pow(Math.E, step);
          ll = ll - step;
        } else {
          p = p * Math.pow(Math.E, lambda * step);
          ll = -1;
        }
      }
    } while (p > 1);
    return k - 1;
  }

  /**
   * @param lambda
   * @return
   * @see
   */
  private static long poisonDistLarge(final long mean) {
    double x, r, m, sqrt_mean = Math.sqrt(mean), log_mean = Math.log(mean), g_x, f_m;

    do {
      do {
        double u = ThreadLocalRandom.current().nextDouble();
        x = mean + sqrt_mean * Math.tan(Math.PI * (u - 1 / 2.0));
      } while (x < 0);
      g_x = sqrt_mean / (Math.PI * ((x - mean) * (x - mean) + mean));
      m = Math.floor(x);
      f_m = Math.exp(m * log_mean - mean - lgamma(m + 1));
      r = f_m / g_x / 2.4;
    } while (ThreadLocalRandom.current().nextDouble() > r);

    return (long) x;
  }

  /**
   * The \(\Gamma(x)\) function is the extension of the factorial to real numbers
   * \(n! == gamma(n+1)\). \(\Gamma(x)\) grows very quickly (similar to \(x^x\)),
   * that’s why lgamma(), which is the logarithm of the \(\Gamma\) function, is
   * often used instead.
   *
   * @param x
   * @return
   */
  public static double lgamma(double x) {
    double rv = 0;
    if (x <= -1) {
      rv = Double.NaN;
    } else {
      double a = L9[0];

      for (int i = 1; i < 9; ++i) {
        a += L9[i] / (x + i);
      }

      rv = (LN_SQRT2PI + Math.log(a) - 7.) + (x + .5) * Math.log((x + 7.5) / Math.E);
    }

    return rv;
  }

  /**
   * Returns number rounded with specific precision.
   *
   * @param n   number to be rounded
   * @param ndp number of decimal places
   * @return number rounded with specific precision
   */
  public static double round(final double n, final int ndp) {
    if (ndp < 0) {
      throw new IllegalArgumentException();
    } else {
      BigDecimal bd = new BigDecimal(n);
      bd = bd.setScale(ndp, RoundingMode.HALF_UP);
      return bd.doubleValue();
    }
  }

  /**
   * Returns number rounded with specific precision.
   *
   * @param n number to be rounded
   * @return number rounded with specific precision
   */
  public static double round(final double n) {
    int ndp = Math.abs((int) (Math.log10(n)) - 1);
    return round(n, ndp);
  }

  /**
   * Returns the greatest common divisor (gcd) of {@code a} and {@code b}. The gcd
   * between two numbers is the largest positive integer that divides the numbers
   * without a remainder.
   * 
   * The implementation follows the Euclidean algorithm.
   *
   * @param n1 first number
   * @param n2 second number
   * @return the greatest common divisor between {@code a} and {@code b}
   * @see <a href="https://en.wikipedia.org/wiki/Euclidean_algorithm">Euclidean
   *      algorithm</a>
   */
  public static long gcd_euclidean(final long n1, final long n2) {
    if (n1 < 0 || n2 < 0) {
      throw new IllegalArgumentException();
    }

    long a = n1, b = n2;

    // GCD(0, b) == b; GCD(a, 0) == a,
    // GCD(0, 0) == 0
    if (a == 0)
      return b;
    if (b == 0)
      return a;

    while (b > 0) {
      long r = a % b;
      a = b;
      b = r;
    }

    return a;
  }

  /**
   * Returns the greatest common divisor (gcd) of {@code a} and {@code b}. The gcd
   * between two numbers is the largest positive integer that divides the numbers
   * without a remainder.
   * 
   * The implementation follows the Euclidean algorithm.
   *
   * @param n1 first number
   * @param n2 second number
   * @return the greatest common divisor between {@code a} and {@code b}
   * @see <a href="https://en.wikipedia.org/wiki/Euclidean_algorithm">Euclidean
   *      algorithm</a>
   */
  public static int gcd_euclidean(final int n1, final int n2) {
    if (n1 < 0 || n2 < 0) {
      throw new IllegalArgumentException();
    }

    int a = n1, b = n2;

    // GCD(0, b) == b; GCD(a, 0) == a,
    // GCD(0, 0) == 0
    if (a == 0)
      return b;
    if (b == 0)
      return a;

    while (b > 0) {
      int r = a % b;
      a = b;
      b = r;
    }

    return a;
  }

  /**
   * Returns the greatest common divisor (gcd) of {@code a} and {@code b}. The gcd
   * between two numbers is the largest positive integer that divides the numbers
   * without a remainder.
   * 
   * The implementation follows the Binary GCD algorithm.
   *
   * @param n1 first number
   * @param n2 second number
   * @return the greatest common divisor between {@code a} and {@code b}
   * @see <a href="https://en.wikipedia.org/wiki/Binary_GCD_algorithm">Binary GCD
   *      algorithm</a>
   */
  public static int gcd_binary(final int n1, final int n2) {
    if (n1 < 0 || n2 < 0) {
      throw new IllegalArgumentException();
    }

    int a = n1, b = n2;

    // GCD(0, b) == b; GCD(a, 0) == a,
    // GCD(0, 0) == 0
    if (a == 0)
      return b;
    if (b == 0)
      return a;

    // Finding K, where K is the greatest
    // power of 2 that divides both a and b
    int k;
    for (k = 0; ((a | b) & 1) == 0; ++k) {
      a >>= 1;
      b >>= 1;
    }

    // Dividing a by 2 until a becomes odd
    while ((a & 1) == 0)
      a >>= 1;

    // From here on, 'a' is always odd.
    do {
      // If b is even, remove
      // all factor of 2 in b
      while ((b & 1) == 0)
        b >>= 1;

      // Now a and b are both odd. Swap
      // if necessary so a <= b, then set
      // b = b - a (which is even)
      if (a > b) {
        // Swap u and v.
        int temp = a;
        a = b;
        b = temp;
      }

      b = (b - a);
    } while (b != 0);

    // restore common factors of 2
    return a << k;
  }

  /**
   * Returns p-norm between two numbers. 2-norm is called the Euclidean norm.
   *
   * @param x first number
   * @param y second number
   * @param p norm
   * @return p-norm between two numbers
   */
  public static double norm(final double x, final double y, final int p) {
    return Math.pow(Math.pow(x, p) + Math.pow(y, p), 1.0 / p);
  }

  /**
   * Returns {@code true} if {@code a} is similar to {@code b} diverging at most
   * by {@code eps}, otherwise returns {@code false}.
   *
   * @param a   first number
   * @param b   second number
   * @param eps small error epsilon
   * @return {@code true} if {@code a} is similar to {@code b} diverging at most
   *         by {@code eps}, otherwise returns {@code false}
   */
  public static boolean equals(final double a, final double b, final double eps) {
    boolean rv = false;
    if (a == b) {
      rv = true;
    } else {
      rv = Math.abs(a - b) <= eps;
    }
    return rv;
  }

  /**
   * Returns {@code true} if {@code a} is similar to {@code b} diverging at most
   * by a dynamic {@link MathUtils#eps} based on the numbers and the machine
   * precision , otherwise returns {@code false}.
   *
   * @param a first number
   * @param b second number
   * @return {@code true} if {@code a} is similar to {@code b} diverging at most
   *         by a dynamic {@link MathUtils#eps} based on the numbers and the
   *         machine precision , otherwise returns {@code false}.
   */
  public static boolean equals(double a, double b) {
    boolean rv = false;
    if (a == b) {
      rv = true;
    } else {
      rv = Math.abs(a - b) < Math.max(StrictMath.ulp(Math.abs(a)), StrictMath.ulp(Math.abs(b)));
    }
    return rv;
  }

  /**
   * Returns the factorial of {@code n} (n!).
   *
   * @param n value to compute the factorial
   * @return factorial of {@code n} (n!)
   */
  public static double factorial(final double n) {
    return product(n, 0);
  }

  /**
   * Returns the binomial coefficient C({@code n}, {@code k}), this is the number
   * of combinations of n items taken k at a time. Fast implementation that cannot
   * cause an arithmetic overflow unless the final result is too large to be
   * represented. Algorithm based on Lilavati work, a treatise on arithmetic
   * written about 850 years ago in India. The algorithm also appears in the
   * article on "Algebra" from the first edition of the Encyclopaedia Britannica,
   * published in 1768.
   *
   * @param n first number
   * @param k second number
   * @return the binomial coefficient C({@code n}, {@code k})
   */
  public static double binomial(final double n, final double k) {
    double absN = Math.abs(n), absK = Math.abs(k), rv = 1.0;
    if (absK == 0.0 || absN == absK) {
      rv = 1.0;
    } else if (absK == 1.0 || (n - absK) == 1.0) {
      rv = absN;
    } else {
      if (absK > (absN / 2.0)) {
        absK = absN - absK;
      }
      for (int d = 1; d <= absK; d++) {
        rv = rv * (absN-- / d);
      }
    }
    return rv;
  }

  /**
   * Returns the binomial coefficient C({@code n}, {@code k}), this is the number
   * of combinations of n items taken k at a time. The final result is returned
   * using a BigDecimal class.
   *
   * @param n first number
   * @param k second number
   * @return the binomial coefficient C({@code n}, {@code k})
   * @see java.math.BigDecimal
   */
  public static BigDecimal binomialBD(final double n, final double k) {
    double absN = Math.abs(n), absK = Math.abs(k);
    BigDecimal rv = BigDecimal.ONE;
    if (absK == 0.0 || absN == absK) {
      rv = BigDecimal.ONE;
    } else if (absK == 1.0 || (absN - absK) == 1.0) {
      rv = new BigDecimal(absN);
    } else {
      if (absK > (absN / 2.0)) {
        absK = absN - absK;
      }
      for (int d = 1; d <= absK; d++) {
        rv = rv.multiply(new BigDecimal(absN-- / d));
      }
    }
    return rv.setScale(0, RoundingMode.HALF_UP);
  }

  /**
   * Returns the permutation P({@code n}, {@code k}). The notion of permutation
   * relates to the act of arranging all the members of a set into some sequence
   * or order, or if the set is already ordered, rearranging.
   *
   * @param n first number
   * @param k second number
   * @return the permutation P({@code n}, {@code k})
   */
  public static double permutation(final double n, final double k) {
    return product(n, n - k);
  }

  /**
   * Auxiliar funtion, used to speedup the computation of factorial and
   * permutauons. This algorithm is based on binary splitting method.
   *
   * @param a first number
   * @param b second number
   * @return binary split for factorial and permutations
   * @see <a href=
   *      "http://www.luschny.de/math/factorial/FastFactorialFunctions.htm">FastFactorialFunctions</a>
   */
  public static double product(final double a, final double b) {
    int d = (int) Math.floor(a - b);
    double m = Math.floor(a / 2.0 + b / 2.0), rv = 0.0;
    switch (d) {
      case 0:
        rv = 1.0;
        break;
      case 1:
        rv = a;
        break;
      case 2:
        rv = a * (a - 1.0);
        break;
      case 3:
        rv = a * (a - 1.0) * (a - 2.0);
        break;
      default:
        rv = product(a, m) * product(m, b);
    }
    return rv;
  }

  /**
   * @param s0
   * @param s1
   * @return
   */
  public static double average(final double s0, final double s1) {
    return s1 / s0;
  }

  /**
   * @param s0
   * @param s1
   * @param s2
   * @return
   */
  public static double std(final double s0, final double s1, final double s2) {
    return Math.sqrt((s0 * s2 - s1 * s1) / (s0 * (s0 - 1)));
  }

  /**
   * @param TP
   * @param FP
   * @return
   */
  public static double precision(final double TP, final double FP) {
    double rv = 0.0;
    if (TP > 0) {
      rv = TP / (TP + FP);
    }
    return rv;
  }

  /**
   * @param TP
   * @param FN
   * @return
   */
  public static double recall(final double TP, final double FN) {
    double rv = 0.0;
    if (TP > 0) {
      rv = TP / (TP + FN);
    }
    return rv;
  }

  /**
   * @param TP
   * @param FP
   * @param FN
   * @param b
   * @return
   */
  public static double fmeasure(final double TP, final double FP, final double FN, final double b) {
    return fmeasure(precision(TP, FP), recall(TP, FN), b);
  }

  /**
   * @param precision
   * @param recall
   * @param b
   * @return
   */
  public static double fmeasure(final double p, final double r, final double b) {
    double rv = 0;
    if (p > 0 || r > 0) {
      rv = ((1.0 + Math.pow(b, 2.0)) * p * r) / ((Math.pow(b, 2.0) * p) + r);
    }
    return rv;
  }

  /**
   * @param v
   * @return
   */
  public static double averagePrecision(final boolean v[]) {
    return averagePrecision(v, TP(v));
  }

  /**
   * @param v
   * @param TP
   * @return
   */
  public static double averagePrecision(final boolean v[], final double TP) {
    int iTP = 0, iFP = 0;
    double rv = 0.0;

    for (int i = 0; i < v.length; i++)
      if (v[i]) {
        iTP += 1;
        rv += precision(iTP, iFP);
      } else
        iFP += 1;
    return rv / TP;
  }

  /**
   * @param v
   * @return
   */
  private static int TP(boolean v[]) {
    int TP = 0;
    for (int i = 0; i < v.length; i++)
      if (v[i])
        TP += 1;
    return TP;
  }

  /**
   * @return
   */
  public static double eps() {
    return doubleEpsilon;
  }

  /**
   * @return
   */
  public static double epsDouble() {
    return doubleEpsilon;
  }

  /**
   * @return
   */
  public static float epsFloat() {
    return floatEpsilon;
  }

  /**
   * @param n
   * @return
   */
  public static boolean isEven(final int n) {
    return (n % 2 == 0) ? true : false;
  }

  /**
   * @param n
   * @return
   */
  public static boolean isOdd(final int n) {
    return !isEven(n);
  }

  /**
   * Numerically stable nth root.
   *
   * @param base
   * @param n
   * @return
   */
  public static double nthRoot(final double base, final double n) {
    return Math.pow(Math.E, Math.log(base) / n);
  }

  /**
   * 
   */
  public static double gaussian(final double mean, final double std) {
    return ThreadLocalRandom.current().nextGaussian() * std + mean;
  }
}
