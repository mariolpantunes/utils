package pt.it.av.atnog.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class Utils {
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object o) {
        return (T) o;
    }

    public static <T> void shuffle(T array[]) {
        if (array.length > 1) {
            for (int i = array.length - 1; i > 1; i--) {
                int j = MathUtils.randomBetween(0, i);
                T t = array[j];
                array[j] = array[i];
                array[i] = t;
            }
        }
    }

    public static <T> void swap (T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static <T extends Comparable<T>> T min(T a, T b) {
        T rv = b;
        if (a.compareTo(b) < 0)
            rv = a;
        return rv;
    }

    public static <T> T min(T a, T b, Comparator<T> c) {
        T rv = b;
        if (c.compare(a, b) < 0)
            rv = a;
        return rv;
    }

    public static <T extends Comparable<T>> T max(T a, T b) {
        T rv = b;
        if (a.compareTo(b) > 0)
            rv = a;
        return rv;
    }

    public static <T> T max(T a, T b, Comparator<T> c) {
        T rv = b;
        if (c.compare(a, b) > 0)
            rv = a;
        return rv;
    }

    private static <T> int partition(T[] array, int left, int right, int pivot, Comparator<T> c) {
        int rv = left;
        T pivotValue = array[pivot];
        swap(array, pivot, right);
        for(int i = left; i < right; i++) {
            if (c.compare(array[i], pivotValue) <= 0) {
                swap(array, rv, i);
                rv++;
            }
        }
        swap(array, right, rv);
        return rv;
    }

    public static <T> T qselect(T[] array, int left, int right, int n, Comparator<T> c) {
        T rv = null;
        if(left < right) {
            int pivot = left + (int)Math.floor(Math.random() * (right - left + 1));
            pivot = partition(array, left, right, pivot, c);
            if(n == pivot)
                rv = array[n];
            else if(n < pivot)
                rv = qselect(array, left, pivot - 1, n, c);
            else
                rv = qselect(array, pivot + 1, right, n, c);

        }
        return rv;
    }

    public static <T> T qselect(T[] array, int n, Comparator<T> c) {
        return qselect(array, 0, array.length - 1, n, c);
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

    public static String stackTrace(Exception e) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    // TODO: Use calendar to solve this issue....
    public static Date dateTrim(Date date) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
}