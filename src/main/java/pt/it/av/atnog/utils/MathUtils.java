package pt.it.av.atnog.utils;

/**
 * Created by mantunes on 5/22/15.
 */
public class MathUtils {
    public static double randomBetween(double min, double max) {
        return min + (Math.random() * ((max - min) + 1.0));
    }

    public static int randomBetween(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    //TODO: very poor implementation
    public static long poisonDist(long lambda) {
        double L = Math.exp(-lambda), p = 1.0;
        long k = 0;

        do {
            k++;
            p *= Math.random();
        } while (p > L);

        return k - 1;
    }

    public static double round(double v, int ndp) {
        double mf = Math.pow(10, ndp);
        //System.err.println(v*mf);
        return Math.round(v * mf) / (mf * 10);
    }

    public static double round(double v) {
        int ndp = Math.abs((int) (Math.log10(v)) - 1);
        double mf = Math.pow(10, ndp);
        return Math.round(v * mf) / mf;
    }

    public static int gcd(int a, int b) {
        while (b > 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    public static double norm(double x, double y, int p) {
        return Math.pow(Math.pow(x, p) + Math.pow(y, p), 1.0 / p);
    }

    public static boolean equals(double a, double b, double eps) {
        return Math.abs(a - b) < eps;
    }

    public static double factorial(double n) {
        return product(n, 0);
    }

    public static double combination(double n, double k) {
        return product(n, n - k) / factorial(k);
    }

    public static double permutation(double n, double k) {
        return product(n, n - k);
    }

    private static double product(double a, double b) {
        int d = (int) Math.floor(a - b);
        double m = Math.floor((a + b) / 2.0), rv = 0.0;
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
}
