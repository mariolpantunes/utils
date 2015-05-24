package pt.it.av.atnog.utils.json;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by mantunes on 5/5/15.
 */
public class JSONNull extends JSONValue {
    @Override
    public void write(Writer w) throws IOException {
        w.append("null");
    }

    @Override
    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null) {
            if (o == this)
                rv = true;
            else if (o instanceof JSONNull) {
                rv = true;
            }
        }
        return rv;
    }
}
