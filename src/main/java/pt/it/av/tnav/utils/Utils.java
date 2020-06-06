package pt.it.av.tnav.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Comparator;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Utils {
  /**
   * Utility class, lets make the constructor private.
   */
  private Utils() {}

  /**
     * Casts one object type into another suppressing compilation warning.
     * 
     * @param o object to be casted
     * @param <T> the new object type
     * @return the casted object
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object o) {
        return (T) o;
    }

  /**
   * @param a
   * @param b
   * @param <T>
   * @return
   */
  public static <T extends Comparable<T>> T min(T a, T b) {
    T rv = b;
    if (a.compareTo(b) < 0)
      rv = a;
    return rv;
  }

  /**
   *
   * @param a
   * @param b
   * @param c
   * @param <T>
   * @return
   */
  public static <T> T min(T a, T b, Comparator<T> c) {
    T rv = b;
    if (c.compare(a, b) < 0)
      rv = a;
    return rv;
  }

  /**
   *
   * @param a
   * @param b
   * @param <T>
   * @return
   */
  public static <T extends Comparable<T>> T max(T a, T b) {
    T rv = b;
    if (a.compareTo(b) > 0)
      rv = a;
    return rv;
  }

  /**
   *
   * @param a
   * @param b
   * @param c
   * @param <T>
   * @return
   */
  public static <T> T max(T a, T b, Comparator<T> c) {
    T rv = b;
    if (c.compare(a, b) > 0)
      rv = a;
    return rv;
  }

  /**
   *
   * @param e
   * @return
   */
  public static String stackTrace(Exception e) {
    final StringWriter sw = new StringWriter();
    final PrintWriter pw = new PrintWriter(sw, true);
    e.printStackTrace(pw);
    return sw.getBuffer().toString();
  }
}