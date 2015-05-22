package pt.it.av.atnog.utils.json;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by mantunes on 5/5/15.
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
}
