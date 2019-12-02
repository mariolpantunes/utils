package pt.it.av.tnav.utils.json;

import java.io.IOException;
import java.io.Writer;

/**
 * JSON Boolean
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class JSONBoolean implements JSONValue {
  protected final boolean b;

  public JSONBoolean(final boolean b) {
    this.b = b;
  }

  @Override
  public void write(Writer w) throws IOException {
    if (b)
      w.append("true");
    else
      w.append("false");
  }

  @Override
  public boolean equals(Object o) {
    boolean rv = false;
    if (o != null) {
      if (o == this)
        rv = true;
      else if (o instanceof JSONBoolean) {
        JSONBoolean j = (JSONBoolean) o;
        rv = b == j.b ? true : false;
      }
    }
    return rv;
  }

  public String toString() {
    String rv = null;

    if (b) {
      rv = "true";
    } else {
      rv = "false";
    }

    return rv;
  }
}
