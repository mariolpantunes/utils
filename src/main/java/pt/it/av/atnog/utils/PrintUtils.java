package pt.it.av.atnog.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by mantunes on 5/22/15.
 */
public class PrintUtils {
    public static void printArray(double[] array) {
        printArray(array, 0, array.length - 1);
    }

    public static void printArray(double[] array, int left, int right) {
        System.out.print("[");
        int i = left;
        for (; i < right; i++)
            System.out.print(array[i] + "; ");
        if (right >= left)
            System.out.println(array[i] + "]");
    }


    public static <T> void printArray(T[] a) {
        try {
            PrintWriter w = new PrintWriter(System.out);
            printArray(a, 0, a.length, w);
            w.println();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> void printArray(T[] a, int left, int right) {
        try {
            PrintWriter w = new PrintWriter(System.out);
            printArray(a, left, right, w);
            w.println();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> void printArray(T[] a, Writer w) throws IOException {
        printArray(a, 0, a.length, w);
    }

    public static <T> void printArray(T[] a, int left, int right, Writer w) throws IOException {
        w.append('[');
        int i = left;
        for (; i < right; i++)
            w.append(a[i] + "; ");
        if (right >= left)
            w.append(a[i].toString());
        w.append(']');
    }

    public static <T> void list(List<T> l, Writer w) throws IOException {
        w.append('[');
        int i = 0;
        for (int t = l.size() - 1; i < t; i++)
            w.append(l.get(i).toString() + "; ");
        if (!l.isEmpty())
            w.append(l.get(i).toString());
        w.append(']');
    }

    public static <T> String list(List<T> l) {
        StringWriter w = new StringWriter();
        try {
            list(l, w);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return w.toString();
    }

    public static <K, V> void map(Map<K, V> m, Writer w) throws IOException {
        Iterator<Map.Entry<K, V>> iter = m.entrySet().iterator();
        w.append('{');
        while (iter.hasNext()) {
            Map.Entry<K, V> entry = iter.next();
            w.append(entry.getKey().toString());
            w.append(':');
            w.append(entry.getValue().toString());
            if (iter.hasNext())
                w.append(", ");
        }
        w.append('}');
    }

    public static <K, V> String map(Map<K, V> m) {
        StringWriter w = new StringWriter();
        try {
            map(m, w);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return w.toString();
    }
}
