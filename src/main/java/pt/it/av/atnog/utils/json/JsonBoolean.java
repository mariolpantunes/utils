package pt.it.av.atnog.utils.json;

/**
 * Created by mantunes on 1/14/15.
 */
public class JsonBoolean extends JsonValue {
    protected boolean value;

    public JsonBoolean(boolean value) {
        this.value = value;
    }

    public void toString(StringBuilder sb) {
        if (value)
            sb.append("true");
        else
            sb.append("false");
    }
}
