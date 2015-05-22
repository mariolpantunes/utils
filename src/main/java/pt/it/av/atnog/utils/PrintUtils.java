package pt.it.av.atnog.utils;

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

    public static void printList(List l) {
        System.out.print("[");
        int i = 0;
        for (int t = l.size() - 1; i < t; i++)
            System.out.print(l.get(i).toString() + "; ");
        if (!l.isEmpty())
            System.out.println(l.get(i) + "]");
    }

    public static <K, V> void printMap(Map<K, V> map) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<K, V>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<K, V> entry = iter.next();
            sb.append(entry.getKey());
            sb.append('=').append('"');
            sb.append(entry.getValue());
            sb.append('"');
            if (iter.hasNext()) {
                sb.append(',').append(' ');
            }
        }
        System.out.println(sb.toString());
    }
}
