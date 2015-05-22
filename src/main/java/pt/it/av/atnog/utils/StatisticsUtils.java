package pt.it.av.atnog.utils;

/**
 * Created by mantunes on 4/22/15.
 */
public class StatisticsUtils {

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
