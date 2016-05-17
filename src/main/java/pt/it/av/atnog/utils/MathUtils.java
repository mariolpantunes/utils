package pt.it.av.atnog.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Collection of general-purpose Math utilities.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class MathUtils {
    /**
     * Returns random numbers between [{@code min}, {@code max}[.
     *
     * @param min minimal value of the range, inclusive
     * @param max maximal value of the range, exclusive
     * @return random numbers between [{@code min}, {@code max}[
     */
    public static double randomBetween(double min, double max) {
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
    public static int randomBetween(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    /**
     * Generator of Poisson-distributed random variables (Knuth algorithm).
     * This algorithm is not optimal, if performance is an issue an alternative should be used.
     * Returns number from a possion distribution with mean {@code lambda}.
     *
     * @param lambda mean of the poisson distribution
     * @return number from a possion distribution with mean {@code lambda}
     */
    public static long poisonDist(long lambda) {
        double L = Math.exp(-lambda), p = 1.0;
        long k = 0;

        do {
            k++;
            p *= Math.random();
        } while (p > L);

        return k - 1;
    }

    /**
     * Returns number rounded with specific precision.
     *
     * @param n number to be rounded
     * @param ndp number of decimal places
     * @return number rounded with specific precision
     */
    public static double round(double n, int ndp) {
        if (ndp < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(n);
        bd = bd.setScale(ndp, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Returns number rounded with specific precision.
     *
     * @param n number to be rounded
     * @return number rounded with specific precision
     */
    public static double round(double n) {
        int ndp = Math.abs((int) (Math.log10(n)) - 1);
        return round(n, ndp);
    }

    /**
     * Returns the greatest common divisor (gcd) between {@code a} and {@code b}.
     * The gcd between two numbers is the largest positive integer that divides the numbers without a remainder.
     *
     * @param a first number
     * @param b second number
     * @return the greatest common divisor between {@code a} and {@code b}
     */
    public static int gcd(int a, int b) {
        while (b > 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
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
    public static double norm(double x, double y, int p) {
        return Math.pow(Math.pow(x, p) + Math.pow(y, p), 1.0 / p);
    }

    /**
     * Returns {@code true} if {@code a} is similar to {@code b} diverging at most by {@code eps}, otherwise returns {@code false}.
     *
     * @param a first number
     * @param b second number
     * @param eps small error epsilon
     * @return {@code true} if {@code a} is similar to {@code b} diverging at most by {@code eps}, otherwise returns {@code false}
     */
    public static boolean equals(double a, double b, double eps) {
        return Math.abs(a - b) < eps;
    }

    /**
     * Returns the factorial of {@code n} (n!).
     *
     * @param n value to compute the factorial
     * @return factorial of {@code n} (n!)
     */
    public static double factorial(double n) {
        return product(n, 0);
    }

    /**
     * Returns the binomial coefficient C({@code n}, {@code k}), this is the number of combinations of n items taken k at a time.
     * Fast implementation that cannot cause an arithmetic overflow unless the final result is too large to be represented.
     * Algorithm based on Lilavati work, a treatise on arithmetic written about 850 years ago in India.
     * The algorithm also appears in the article on "Algebra" from the first edition of the Encyclopaedia Britannica, published in 1768.
     *
     * @param n first number
     * @param k second number
     * @return the binomial coefficient C({@code n}, {@code k})
     */
    public static double binomial(double n, double k) {
        double rv = 1.0;
        if (k == 0.0 || n == k)
            rv = 1.0;
        else if (k == 1.0 || (n - k) == 1.0)
            rv = n;
        else {
            if (k > (n / 2.0))
                k = n - k;
            for (int d = 1; d <= k; d++) {
                rv *= n--;
                rv /= d;
            }
        }
        return rv;
    }

    /**
     * Returns the binomial coefficient C({@code n}, {@code k}), this is the number of combinations of n items taken k at a time.
     * The final result is returned using a BigDecimal class.
     * @see java.math.BigDecimal
     *
     * @param n first number
     * @param k second number
     * @return the binomial coefficient C({@code n}, {@code k})
     */
    public static BigDecimal binomialBD(double n, double k) {
        BigDecimal rv = BigDecimal.ONE;
        if (k == 0.0 || n == k)
            rv = BigDecimal.ONE;
        else if (k == 1.0 || (n - k) == 1.0)
            rv = new BigDecimal(n);
        else {
            if (k > (n / 2.0))
                k = n - k;
            for (int d = 1; d <= k; d++)
                rv = rv.multiply(new BigDecimal(n-- / d));
        }
        return rv.setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * Returns the permutation P({@code n}, {@code k}).
     * The notion of permutation relates to the act of arranging all the members of a set into some sequence or order,
     * or if the set is already ordered, rearranging.
     *
     * @param n first number
     * @param k second number
     * @return the permutation P({@code n}, {@code k})
     */
    public static double permutation(double n, double k) {
        return product(n, n - k);
    }

    /**
     * Auxiliar funtion, used to speedup the computation of factorial and permutauons.
     * This algorithm is based on binary splitting method.
     * @see <a href="http://www.luschny.de/math/factorial/FastFactorialFunctions.htm">FastFactorialFunctions</a>
     *
     * @param a first number
     * @param b second number
     * @return binary split for factorial and permutations
     */
    public static double product(double a, double b) {
        int d = (int) Math.floor(a - b);
        double m = Math.floor(a/2.0 + b/2.0), rv = 0.0;
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
     * Returns
     * @param s0
     * @param s1
     * @return
     */
    public static double average(double s0, double s1) {
        return s1 / s0;
    }

    /**
     *
     * @param s0
     * @param s1
     * @param s2
     * @return
     */
    public static double standartDeviation(double s0, double s1, double s2) {
        return Math.sqrt((s0 * s2 - s1 * s1) / (s0 * (s0 - 1)));
    }

    /**
     *
     * @param s0
     * @param s1
     * @return
     */
    public static double itemsPerSecond(double s0, double s1) {
        return s0 / s1;
    }

    /**
     *
     * @param TP
     * @param FP
     * @return
     */
    public static double precision(double TP, double FP) {
        double rv = 0.0;
        if (TP > 0)
            rv = TP / (TP + FP);
        return rv;
    }

    /**
     *
     * @param TP
     * @param FN
     * @return
     */
    public static double recall(double TP, double FN) {
        double rv = 0.0;
        if (TP > 0)
            rv = TP / (TP + FN);
        return rv;
    }

    /**
     *
     * @param TP
     * @param FP
     * @param FN
     * @param b
     * @return
     */
    public static double fmeasure(double TP, double FP, double FN, double b) {
        return fmeasure(precision(TP, FP), recall(TP, FN), b);
    }

    /**
     *
     * @param precision
     * @param recall
     * @param b
     * @return
     */
    public static double fmeasure(double precision, double recall, double b) {
        double rv = 0;
        if (precision > 0 || recall > 0)
            rv = ((1.0 + Math.pow(b, 2.0)) * precision * recall)
                    / ((Math.pow(b, 2.0) * precision) + recall);
        return rv;
    }

    /**
     *
     * @param v
     * @return
     */
    public static double averagePrecision(boolean v[]) {
        return averagePrecision(v, TP(v));
    }

    /**
     *
     * @param v
     * @param TP
     * @return
     */
    public static double averagePrecision(boolean v[], double TP) {
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
     *
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
