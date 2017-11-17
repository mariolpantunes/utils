package pt.it.av.atnog.utils;

import java.text.BreakIterator;
import java.util.Iterator;
import java.util.Locale;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public final class StringUtils {
  /**
   * Utility class, lets make the constructor private.
   */
  private StringUtils() {
  }

  /**
   * @param s
   * @return
   */
  public static String escape(final String s) {
    StringBuilder sb = new StringBuilder();
    char[] array = s.toCharArray();
    for (int i = 0, t = array.length; i < t; i++) {
      switch (array[i]) {
        case '\"':
          sb.append("\\\"");
          break;
        case '\\':
          sb.append("\\\\");
          break;
        case '\b':
          sb.append("\\b");
          break;
        case '\f':
          sb.append("\\f");
          break;
        case '\n':
          sb.append("\\n");
          break;
        case '\r':
          sb.append("\\r");
          break;
        case '\t':
          sb.append("\\t");
          break;
        default:
          sb.append(array[i]);
      }
    }
    return sb.toString();
  }

  /**
   * @param s
   * @return
   */
  public static String unescape(final String s) {
    StringBuilder sb = new StringBuilder();
    char[] array = s.toCharArray();
    boolean escaped = false;
    for (int i = 0, t = array.length; i < t; i++) {
      if (escaped) {
        switch (array[i]) {
          case '\"':
            sb.append("\"");
            break;
          case '\\':
            sb.append("\\");
            break;
          case '\b':
            sb.append("b");
            break;
          case '\f':
            sb.append("f");
            break;
          case '\n':
            sb.append("n");
            break;
          case '\r':
            sb.append("r");
            break;
          case '\t':
            sb.append("t");
            break;
          default:
            sb.append(array[i]);
        }
        escaped = false;
      } else if (array[i] == '\\') {
        escaped = true;
      } else {
        sb.append(array[i]);
      }
    }
    return sb.toString();
  }

  /**
   * @param a
   * @param b
   * @return
   */
  public static int levenshtein(final String a, final String b) {
    //a = a.toLowerCase();
    //b = b.toLowerCase();
    // i == 0
    int[] costs = new int[b.length() + 1];
    for (int j = 0; j < costs.length; j++) {
      costs[j] = j;
    }
    for (int i = 1; i <= a.length(); i++) {
      // j == 0; nw = lev(i - 1, j)
      costs[0] = i;
      int nw = i - 1;
      for (int j = 1; j <= b.length(); j++) {
        int cj = 0;
        if (a.charAt(i - 1) == b.charAt(j - 1)) {
          cj = nw;
        } else {
          cj = nw + 1;
        }
        cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), cj);
        nw = costs[j];
        costs[j] = cj;
      }
    }
    return costs[b.length()];
  }

  /**
   * @param input
   * @return
   */
  public static Iterator<String> splitSetences(final String input) {
    return splitSetences(input, Locale.getDefault());
  }

  /**
   * @param input
   * @param locale
   * @return
   */
  public static Iterator<String> splitSetences(final String input, final Locale locale) {
    return new SetenceIterator(input, locale);
  }

  /**
   *
   */
  private static class SetenceIterator implements Iterator<String> {
    private final String input;
    private final BreakIterator it;
    private int start, end;

    /**
     * @param input
     * @param locale
     */
    SetenceIterator(final String input, final Locale locale) {
      this.input = input;
      it = BreakIterator.getSentenceInstance(locale);
      it.setText(input);
      start = it.first();
      end = it.next();
    }

    @Override
    public boolean hasNext() {
      return end != BreakIterator.DONE;
    }

    @Override
    public String next() {
      String rv = input.substring(start, end);
      start = end;
      end = it.next();
      return rv;
    }
  }
}
