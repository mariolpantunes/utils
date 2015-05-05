package pt.it.av.atnog.utils.json;

/**
 * Created by mantunes on 3/30/15.
 */
public class JSONNumber extends JSONValue {
    private final double n;

    public JSONNumber(double n) {
        this.n = n;
    }

    public double value() {
        return n;
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append(n);
    }
}
