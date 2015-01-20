package pt.it.av.atnog.utils.json;

/**
 * Created by mantunes on 1/14/15.
 */
public abstract class JsonValue {

    public abstract void toString(StringBuilder sb);

    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }
}
