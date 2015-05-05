package pt.it.av.atnog.utils.json;

/**
 * Created by mantunes on 26/03/2015.
 */
public abstract class JSONValue {
    public abstract void toString(StringBuilder sb);

    public JSONObject asObject() {
        return (JSONObject) this;
    }

    public JSONString asString() {
        return (JSONString) this;
    }

    public JSONArray asArray() {
        return (JSONArray) this;
    }

    public JSONNumber asNumber() {
        return (JSONNumber) this;
    }
}
