package pt.it.av.atnog.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by mantunes on 5/22/15.
 */
public class PrintUtils {

    public static String array(double[] a) {
        StringWriter w = new StringWriter();
        try {
            array(a, 0, a.length - 1, w);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return w.toString();
    }

    public static String array(double[] a, int left, int right) {
        StringWriter w = new StringWriter();
        try {
            array(a, left, right, w);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return w.toString();
    }

    public static void array(double[] a, Writer w) throws IOException {
        array(a, 0, a.length - 1, w);
    }

    public static void array(double[] a, int left, int right, Writer w) throws IOException {
        DecimalFormat format = new DecimalFormat("0.#");
        w.append('[');
        int i = left;
        for (; i < right; i++)
            w.append(a[i] + "; ");
        if (right >= left)
            w.append(format.format(a[i]));
        w.append(']');
    }

    public static <T> String array(T[] a) {
        StringWriter w = new StringWriter();
        try {
            array(a, 0, a.length - 1, w);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return w.toString();
    }

    public static <T> String array(T[] a, int left, int right) {
        StringWriter w = new StringWriter();
        try {
            array(a, left, right, w);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return w.toString();
    }

    public static <T> void array(T[] a, Writer w) throws IOException {
        array(a, 0, a.length - 1, w);
    }

    public static <T> void array(T[] a, int left, int right, Writer w) throws IOException {
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
