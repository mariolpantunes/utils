package pt.it.av.atnog.utils.json;

import pt.it.av.atnog.utils.StringUtils;

/**
 * Created by mantunes on 3/28/15.
 */
public class JSONString extends JSONValue {
    private final String s;

    public JSONString(final String s) {
        this.s = StringUtils.unescape(s);
    }

    public final String value() {
        return s;
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append("\"" + StringUtils.escape(s) + "\"");
    }
}
