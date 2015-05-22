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
}
