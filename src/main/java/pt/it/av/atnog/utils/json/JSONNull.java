package pt.it.av.atnog.utils.json;

/**
 * Created by mantunes on 5/5/15.
 */
public class JSONNull extends JSONValue {
    @Override
    public void toString(StringBuilder sb) {
        sb.append("null");
    }
}
