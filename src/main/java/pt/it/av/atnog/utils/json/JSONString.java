package pt.it.av.atnog.utils.json;

import pt.it.av.atnog.utils.StringUtils;

import java.io.IOException;
import java.io.Writer;

/**
 * JSON String.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class JSONString implements JSONValue {
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
}
