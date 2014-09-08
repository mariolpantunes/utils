package pt.ua.it.atnog.utils;

import java.util.Comparator;

public class Utils {

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object o) {
        return (T) o;
    }

    public static void shuffle(int array[]) {
        if (array.length > 1) {
            for (int i = array.length - 1; i > 1; i--) {
                int j = randomBetween(0, i);
                int t = array[j];
                array[j] = array[i];
                array[i] = t;
            }
        }
    }

    public static void shuffle(byte array[]) {
        if (array.length > 1) {
            for (int i = array.length - 1; i > 1; i--) {
                int j = randomBetween(0, i);
                byte t = array[j];
                array[j] = array[i];
                array[i] = t;
            }
        }
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

    public static double fmeasure(double precision, double recall, double beta) {
        double rv = 0;
        if (precision > 0 || recall > 0)
            rv = ((1.0 + Math.pow(beta, 2.0)) * precision * recall)
                    / ((Math.pow(beta, 2.0) * precision) + recall);
        return rv;
    }

    public static double randomBetween(double min, double max) {
        return min + (Math.random() * ((max - min) + 1.0));
    }

    public static int randomBetween(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

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
        System.err.println(v*mf);
        return Math.round(v*mf) / (mf*10);
    }

    public static double round(double v) {
        int ndp = Math.abs((int) (Math.log10(v)) - 1);
        double mf = Math.pow(10, ndp);
        return Math.round(v * mf) / mf;
    }

    public static <T> void swap (T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private static <T> int partition(T[] array, int left, int right, int pivot, Comparator<T> comparator) {
        int rv = left;
        T pivotValue = array[pivot];
        swap(array, pivot, right);
        for(int i = left; i < right; i++) {
            if(comparator.compare(array[i], pivotValue) <= 0) {
                swap(array, rv, i);
                rv++;
            }
        }
        swap(array, right, rv);
        return rv;
    }

    public static <T> T qselect(T[] array, int left, int right, int n, Comparator<T> comparator) {
        T rv = null;
        if(left < right) {
            int pivot = left + (int)Math.floor(Math.random() * (right - left + 1));
            pivot = partition(array, left, right, pivot, comparator);
            if(n == pivot)
                rv = array[n];
            else if(n < pivot)
                rv = qselect(array, left, pivot-1, n, comparator);
            else
                rv = qselect(array, pivot+1, right, n, comparator);

        }
        return rv;
    }

    public static <T> T qselect(T[] array, int n, Comparator<T> comparator) {
        return  qselect(array, 0, array.length-1, n, comparator);
    }

    private static <T extends Comparable<T>> int partition(T[] array, int left, int right, int pivot) {
        int rv = left;
        T pivotValue = array[pivot];
        swap(array, pivot, right);
        for(int i = left; i < right; i++) {
            if(array[i].compareTo(pivotValue) <= 0) {
                swap(array, rv, i);
                rv++;
            }
        }
        swap(array, right, rv);
        return rv;
    }

    public static <T extends Comparable<T>> T qselect(T[] array, int left, int right, int n) {
        T rv = null;
        if(left < right) {
            int pivot = left + (int)Math.floor(Math.random() * (right - left + 1));
            pivot = partition(array, left, right, pivot);
            if(n == pivot)
                rv = array[n];
            else if(n < pivot)
                rv = qselect(array, left, pivot-1, n);
            else
                rv = qselect(array, pivot+1, right, n);

        }
        return rv;
    }

    public static <T extends Comparable<T>> T qselect(T[] array, int n) {
        return  qselect(array, 0, array.length-1, n);
    }

    public static <T> void printArray(T[] array, int left, int right) {
        for(int i = left; i < right+1; i++ ) {
            System.out.print(array[i].toString()+"; ");
        }
        System.out.println();
    }
    public static <T> void printArray(T[] array) {
        printArray(array, 0, array.length-1);
    }
}