package pt.it.av.atnog.utils.json;

/**
 * Created by mantunes on 1/14/15.
 */
public class JsonNull extends JsonValue {
    public void toString(StringBuilder sb) {
        sb.append("null");
    }
}
