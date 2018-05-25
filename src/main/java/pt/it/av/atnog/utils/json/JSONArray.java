package pt.it.av.atnog.utils.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * JSON Array.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class JSONArray extends ArrayList<JSONValue> implements JSONValue {

  /**
   * @param n
   * @return
   */
  public boolean add(final int n) {
    return add(new JSONNumber(n));
  }

  /**
   * @param n
   * @return
   */
  public boolean add(final double n) {
    return add(new JSONNumber(n));
  }

  /**
   * @param s
   * @return
   */
  public boolean add(final String s) {
    return add(new JSONString(s));
  }

  @Override
  public void write(Writer w) throws IOException {
    w.append("[");
    int i = 0, t = size() - 1;
    for (; i < t; i++) {
      get(i).write(w);
      w.append(",");
    }
    if (t >= 0)
      get(i).write(w);
    w.append("]");
  }

  @Override
  public String toString() {
    StringWriter w = new StringWriter();
    try {
      write(w);
    } catch (IOException e) {
      // should not occur...
    }
    return w.toString();
  }
}
