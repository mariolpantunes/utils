package pt.it.av.atnog.utils;

import java.io.IOException;
import java.io.PrintWriter;
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
            printArray(a, 0, a.length - 1, w);
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

    private static <T> void printArray(T[] a, int left, int right, Writer w) throws IOException {
        w.append("[");
        int i = left;
        for (; i < right; i++)
            w.append(a[i] + "; ");
        if (right >= left)
            w.append(a[i] + "]");
    }

    public static <T> void printList(List<T> l, Writer w) throws IOException {
        w.append("[");
        int i = 0;
        for (int t = l.size() - 1; i < t; i++)
            w.append(l.get(i).toString() + "; ");
        if (!l.isEmpty())
            w.append(l.get(i) + "]");
    }

    public static <T> void printList(List<T> l) {
        try {
            PrintWriter w = new PrintWriter(System.out);
            printList(l, w);
            w.println();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <K, V> void printMap(Map<K, V> m, Writer w) throws IOException {
        Iterator<Map.Entry<K, V>> iter = m.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<K, V> entry = iter.next();
            w.append(entry.getKey().toString());
            w.append('=').append('"');
            w.append(entry.getValue().toString());
            w.append('"');
            if (iter.hasNext())
                w.append(',').append(' ');
        }
    }

    public static <K, V> void printMap(Map<K, V> m) {
        try {
            PrintWriter w = new PrintWriter(System.out);
            printMap(m, w);
            w.println();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
