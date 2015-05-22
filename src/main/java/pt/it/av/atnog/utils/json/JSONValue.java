package pt.it.av.atnog.utils.json;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by mantunes on 26/03/2015.
 */
public abstract class JSONValue {
    public abstract void write(Writer w) throws IOException;

    public JSONObject asObject() {
        return (JSONObject) this;
    }

    public JSONArray asArray() {
        return (JSONArray) this;
    }

    public String asString() {
        return ((JSONString) this).s;
    }

    public double asNumber() {
        return ((JSONNumber) this).n;
    }

    public boolean asBoolean() {
        return ((JSONBoolean) this).b;
    }

    public boolean isObject() {
        return this instanceof JSONObject;
    }

    public boolean isArray() {
        return this instanceof JSONArray;
    }

    public boolean isString() {
        return this instanceof JSONString;
    }
}
