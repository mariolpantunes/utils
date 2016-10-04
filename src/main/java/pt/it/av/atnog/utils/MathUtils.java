package pt.it.av.atnog.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Collection of general-purpose Math utilities.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public final class MathUtils {
    /**
     * Utility class, lets make the constructor private.
     */
    private MathUtils() {
    }
    /**
     * Returns random numbers between [{@code min}, {@code max}[.
     *
     * @param min minimal value of the range, inclusive
     * @param max maximal value of the range, exclusive
     * @return random numbers between [{@code min}, {@code max}[
     */
    public static double randomBetween(final double min, final double max) {
        return min + (Math.random() * ((max - min) + 1.0));
    }

    /**
     * Returns random numbers between [{@code min}, {@code max}[.
     * If performance is a issue consider using Random.nextInt(n).
     *
     * @param min minimal value of the range, inclusive
     * @param max maximal value of the range, exclusive
     * @return random numbers between [{@code min}, {@code max}[
     */
    public static int randomBetween(final int min, final int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    /**
     * Generator of Poisson-distributed random variables (Knuth algorithm).
     * This algorithm is not optimal, if performance is an issue an
     * alternative should be used.
     * Returns number from a possion distribution with mean {@code lambda}.
     *
     * @param lambda mean of the poisson distribution
     * @return number from a possion distribution with mean {@code lambda}
     */
    public static long poisonDist(final long lambda) {
        double l = Math.exp(-lambda), p = 1.0;
        long k = 0;

        do {
            k++;
            p *= Math.random();
        } while (p > l);

        return k - 1;
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
     * Returns the greatest common divisor (gcd) of {@code a} and {@code b}.
     * The gcd between two numbers is the largest positive integer that divides
     * the numbers without a remainder.
     *
     * @param a first number
     * @param b second number
     * @return the greatest common divisor between {@code a} and {@code b}
     */
    public static long gcd(final long a, final long b) {
        long absA = Math.abs(a), absB = Math.abs(b), r, i;
        while (b > 0) {
            r = absA % absB;
            absA = b;
            absB = r;
        }
        return absA;
    }

    /**
     * Returns the greatest common divisor (gcd) of {@code a} and {@code b}.
     * The gcd between two numbers is the largest positive integer that divides
     * the numbers without a remainder.
     *
     * @param a first number
     * @param b second number
     * @return the greatest common divisor between {@code a} and {@code b}
     */
    public static int gcd(final int a, final int b) {
        int absA = Math.abs(a), absB = Math.abs(b), r, i;
        while (b > 0) {
            r = absA % absB;
            absA = b;
            absB = r;
        }
        return absA;
    }

    /**
     * Returns p-norm between two numbers.
     * 2-norm is called the Euclidean norm.
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
     * Returns {@code true} if {@code a} is similar to {@code b} diverging at
     * most by {@code eps}, otherwise returns {@code false}.
     *
     * @param a   first number
     * @param b   second number
     * @param eps small error epsilon
     * @return {@code true} if {@code a} is similar to {@code b} diverging at
     * most by {@code eps}, otherwise returns {@code false}
     */
    public static boolean equals(final double a,
                                 final double b, final double eps) {
        return Math.abs(a - b) < eps;
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
     * Returns the binomial coefficient C({@code n}, {@code k}), this is the
     * number of combinations of n items taken k at a time.
     * Fast implementation that cannot cause an arithmetic overflow unless the
     * final result is too large to be represented.
     * Algorithm based on Lilavati work, a treatise on arithmetic written about
     * 850 years ago in India.
     * The algorithm also appears in the article on "Algebra" from the first
     * edition of the Encyclopaedia Britannica, published in 1768.
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
     * Returns the binomial coefficient C({@code n}, {@code k}), this is the
     * number of combinations of n items taken k at a time.
     * The final result is returned using a BigDecimal class.
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
     * Returns the permutation P({@code n}, {@code k}).
     * The notion of permutation relates to the act of arranging all the members
     * of a set into some sequence or order,
     * or if the set is already ordered, rearranging.
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
     * permutauons.
     * This algorithm is based on binary splitting method.
     *
     * @param a first number
     * @param b second number
     * @return binary split for factorial and permutations
     * @see <a href="http://www.luschny.de/math/factorial/FastFactorialFunctions
     * .htm">FastFactorialFunctions</a>
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
     *
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
    public static double standartDeviation(final double s0,
                                           final double s1, final double s2) {
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
    public static double fmeasure(final double TP, final double FP,
                                  final double FN, final double b) {
        return fmeasure(precision(TP, FP), recall(TP, FN), b);
    }

    /**
     * @param precision
     * @param recall
     * @param b
     * @return
     */
    public static double fmeasure(final double precision,
                                  final double recall, final double b) {
        double rv = 0;
        if (precision > 0 || recall > 0)
            rv = ((1.0 + Math.pow(b, 2.0)) * precision * recall)
                    / ((Math.pow(b, 2.0) * precision) + recall);
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
}
