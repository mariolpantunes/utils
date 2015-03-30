package pt.it.av.atnog.utils.json;

/**
 * Created by mantunes on 3/28/15.
 */
public class JSONString implements JSONValue {
    private final String s;

    public JSONString(final String s) {
        this.s = s;
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append("\"" + s + "\"");
    }
}
