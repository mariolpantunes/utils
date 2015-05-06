package pt.it.av.atnog.utils.json;

/**
 * Created by mantunes on 5/5/15.
 */
public class JSONBoolean extends JSONValue {
    private final boolean b;

    public JSONBoolean(final boolean b) {
        this.b = b;
    }

    @Override
    public void toString(StringBuilder sb) {
        if (b)
            sb.append("true");
        else
            sb.append("false");
    }
}
