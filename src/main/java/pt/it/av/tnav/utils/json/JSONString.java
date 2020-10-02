package pt.it.av.tnav.utils.json;

import pt.it.av.tnav.utils.StringUtils;

import java.io.IOException;
import java.io.Writer;

/**
 * JSON String.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class JSONString implements JSONValue, CharSequence {
  protected final String s;

  public JSONString(final String s) {
    this.s = StringUtils.unescape(s);
  }

  public final String value() {
    return s;
  }

  @Override
  public void write(Writer w) throws IOException {
    w.append("\"" + StringUtils.escape(s) + "\"");
  }

  @Override
  public boolean equals(Object o) {
    boolean rv = false;
    if (o != null) {
      if (o == this)
        rv = true;
      else if (o instanceof JSONString) {
        JSONString j = (JSONString) o;
        rv = s.equals(j.s);
      }
    }
    return rv;
  }

  @Override
  public String toString() {
    return "\"" + StringUtils.escape(s) + "\"";
  }

  @Override
  public int length() {
    return s.length();
  }

  @Override
  public char charAt(int index) {
    return s.charAt(index);
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    return s.subSequence(start, end);
  }
}
