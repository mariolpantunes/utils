package pt.it.av.tnav.utils.json;

import java.io.IOException;
import java.io.Writer;

/**
 * JSON Number.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class JSONNumber extends Number implements JSONValue, Comparable<JSONNumber> {
  private static final long serialVersionUID = 1L;
  protected final double n;

  public JSONNumber(double n) {
    this.n = n;
  }

  public double value() {
    return n;
  }

  @Override
  public void write(Writer w) throws IOException {
    if (n % 1 == 0)
      w.append(String.valueOf((long) n));
    else
      w.append(String.valueOf(n));
  }

  @Override
  public boolean equals(Object o) {
    boolean rv = false;
    if (o != null) {
      if (o == this)
        rv = true;
      else if (o instanceof JSONNumber) {
        JSONNumber j = (JSONNumber) o;
        rv = n == j.n ? true : false;
      }
    }
    return rv;
  }

  @Override
  public String toString() {
    String rv = null;

    if (n % 1 == 0)
      rv = String.valueOf((long) n);
    else
      rv = String.valueOf(n);

    return rv;
  }

  @Override
  public int compareTo(JSONNumber o) {
    return Double.compare(n, o.n);
  }

  @Override
  public int intValue() {
    return (int) Math.round(n);
  }

  @Override
  public long longValue() {
    return (long) Math.round(n);
  }

  @Override
  public float floatValue() {
    return (float) n;
  }

  @Override
  public double doubleValue() {
    return n;
  }
}
