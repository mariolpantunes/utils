package pt.it.av.atnog.utils.json;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by mantunes on 5/5/15.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 */
public class JSONBoolean extends JSONValue {
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
}
