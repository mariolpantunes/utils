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
        double rv = 0.0;

        if (k == 0.0 || n == k)
            rv = 1.0;
        else if (k == 1.0 || (n - k) == 1.0)
            rv = n;
        else if (k > (n / 2.0))
            rv = product(n, k) / factorial(n - k);
        else
            rv = product(n, n - k) / factorial(k);

        return rv;
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

    public static double average(double s0, double s1) {
        return s1 / s0;
    }

    public static double standartDeviation(double s0, double s1, double s2) {
        return Math.sqrt((s0 * s2 - s1 * s1) / (s0 * (s0 - 1)));
    }

    public static double itemsPerSecond(double s0, double s1) {
        return s0 / s1;
    }

    public static double precision(double TP, double FP) {
        double rv = 0.0;
        if (TP > 0)
            rv = TP / (TP + FP);
        return rv;
    }

    public static double recall(double TP, double FN) {
        double rv = 0.0;
        if (TP > 0)
            rv = TP / (TP + FN);
        return rv;
    }

    public static double fmeasure(double TP, double FP, double FN, double b) {
        return fmeasure(precision(TP, FP), recall(TP, FN), b);
    }

    public static double fmeasure(double precision, double recall, double b) {
        double rv = 0;
        if (precision > 0 || recall > 0)
            rv = ((1.0 + Math.pow(b, 2.0)) * precision * recall)
                    / ((Math.pow(b, 2.0) * precision) + recall);
        return rv;
    }

    public static double averagePrecision(boolean v[]) {
        return averagePrecision(v, TP(v));
    }

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

    private static int TP(boolean v[]) {
        int TP = 0;
        for (int i = 0; i < v.length; i++)
            if (v[i])
                TP += 1;
        return TP;
    }
}
