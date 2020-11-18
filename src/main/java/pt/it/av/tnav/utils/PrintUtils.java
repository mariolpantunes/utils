package pt.it.av.tnav.utils;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class PrintUtils {
  /**
   * Utility class, lets make the constructor private.
   */
  private PrintUtils() {
  }

  public static String array(int[] a) {
    StringWriter w = new StringWriter();
    try {
      array(a, 0, a.length - 1, w);
      w.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return w.toString();
  }

  public static String array(int[] a, int left, int right) {
    StringWriter w = new StringWriter();
    try {
      array(a, left, right, w);
      w.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return w.toString();
  }

  public static void array(int[] a, Writer w) throws IOException {
    array(a, 0, a.length - 1, w);
  }

  public static void array(int[] a, int left, int right, Writer w) throws IOException {
    w.append('[');
    int i = left;
    for (; i < right; i++)
      w.append(a[i] + ", ");
    if (right >= left)
      w.append(Integer.toString(a[i]));
    w.append(']');
  }

  public static void matrix(double a[], int rows, int cols, Writer w) throws IOException {
    w.append('[');
    for (int r = 0; r < rows; r++) {
      w.append('[');
      for (int c = 0; c < cols - 1; c++) {
        w.append(a[r * cols + c] + ", ");
      }
      w.append(Double.toString(a[r * cols + (cols - 1)]));
      w.append(']');
      w.append(System.getProperty("line.separator"));
    }
    w.append(']');
  }

  public static String matrix(double a[], int rows, int cols) {
    StringWriter w = new StringWriter();
    try {
      matrix(a, rows, cols, w);
      w.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return w.toString();
  }

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
    // DecimalFormat format = new DecimalFormat("0.#");
    w.append('[');
    int i = left;
    for (; i < right; i++)
      w.append(a[i] + ", ");
    if (right >= left) {
      w.append(Double.toString(a[i]));
      // w.append(format.format(a[i]));
    }
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
      w.append(a[i] + ", ");
    if (right >= left)
      w.append(a[i].toString());
    w.append(']');
  }

  public static <T> void list(List<T> l, Writer w) throws IOException {
    w.append('[');
    int i = 0;
    for (int t = l.size() - 1; i < t; i++)
      w.append(l.get(i).toString() + ", ");
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

  /**
   *
   */
  public static String reader(InputStream in) {
    String line = null;
    StringBuilder rslt = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
      while ((line = reader.readLine()) != null) {
        rslt.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return rslt.toString();
  }

  public static String object(final Object object) {
    return PrintUtils.object(object, 0);
  }

  private static String object(final Object object, final int indent) {
    StringBuilder sb;
    if (object instanceof String || object instanceof Number) {
      return object.toString();
    }

    if (object instanceof List) {
      sb = new StringBuilder("[\n");
      List<?> list = (List<?>) object;
      for (int i = 0; i < list.size(); i++) {
        Object element = list.get(i);
        sb.append(StringUtils.repeatString(" ", indent)).append(i).append(":").append(PrintUtils.object(element, indent + 1))
        .append("\n");
      }
      sb.append(StringUtils.repeatString(" ", indent)).append("]");
      return sb.toString();
    }

    if (object.getClass().isArray()) {
      sb = new StringBuilder("[\n");
      for (int i = 0; i < Array.getLength(object); i++) {
        Object element = Array.get(object, i);
        sb.append(StringUtils.repeatString(" ", indent)).append(i).append(":").append(PrintUtils.object(element, indent + 1))
          .append("\n");
      }
      sb.append(StringUtils.repeatString(" ", indent)).append("]");
      return sb.toString();
    }

    if (object instanceof Map) {
      sb = new StringBuilder("{\n");
      Map<?,?> map = (Map<?,?>) object;
      for (Object key : map.keySet()) {
        sb.append(StringUtils.repeatString(" ", indent)).append(key.toString()).append(":")
          .append(PrintUtils.object(map.get(key), indent + 1)).append("\n");
      }
      sb.append(StringUtils.repeatString(" ", indent)).append("}");
      return sb.toString();
    }

    Field[] fields = object.getClass().getDeclaredFields();
    sb = new StringBuilder(object.getClass().getSimpleName() + ":\n" + StringUtils.repeatString(" ", indent) + "{\n");
    for (Field field : fields) {
      field.setAccessible(true);
      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }
      String fieldName = field.getName();
      String value = "";
      try {
        value = PrintUtils.object(field.get(object), indent + 1);
      }
      catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      sb.append(StringUtils.repeatString(" ", indent)).append(fieldName).append(":").append(value).append("\n");
    }
    sb.append(StringUtils.repeatString(" ", indent)).append("}");
    return sb.toString();
  }
}
