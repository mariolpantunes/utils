package pt.it.av.atnog.utils.json;

/**
 * Created by mantunes on 1/14/15.
 */
public class JsonNumber extends JsonValue {
    protected double value;

    public JsonNumber(double value) {
        this.value = value;
    }

    public void toString(StringBuilder sb) {
        sb.append(value);
    }
}
