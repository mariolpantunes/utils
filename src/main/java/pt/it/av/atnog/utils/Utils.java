package pt.it.av.atnog.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Utils {
  @SuppressWarnings("unchecked")
  public static <T> T cast(Object o) {
    return (T) o;
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

  public static String stackTrace(Exception e) {
    final StringWriter sw = new StringWriter();
    final PrintWriter pw = new PrintWriter(sw, true);
    e.printStackTrace(pw);
    return sw.getBuffer().toString();
  }

  public static <T> List<T> intertwine(List<T> a, List<T> b) {
    List<T> rv = new ArrayList<>();

    Iterator<T> itA = a.iterator(), itB = b.iterator();
    while (itA.hasNext() && itB.hasNext()) {
      rv.add(itA.next());
      rv.add(itB.next());
    }

    while (itA.hasNext())
      rv.add(itA.next());

    while (itB.hasNext())
      rv.add(itB.next());

    return rv;
  }

  public static <T> List<T> iterator2List(Iterator<T> it) {
    List<T> rv = new ArrayList<>();
    while (it.hasNext())
      rv.add(it.next());
    return rv;
  }
}