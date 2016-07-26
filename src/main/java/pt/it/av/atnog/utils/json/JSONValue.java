package pt.it.av.atnog.utils.json;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by mantunes on 26/03/2015.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 */
public abstract class JSONValue {
    /**
     * @param w
     * @throws IOException
     */
    public abstract void write(Writer w) throws IOException;

    /**
     *
     * @return
     */
    public JSONObject asObject() {
        return (JSONObject) this;
    }

    /**
     *
     * @return
     */
    public JSONArray asArray() {
        return (JSONArray) this;
    }

    public String asString() {
        return ((JSONString) this).s;
    }

    public double asNumber() {
        return ((JSONNumber) this).n;
    }

    public int asInt() {
        return (int) ((JSONNumber) this).n;
    }

    public long asLong() {
        return (long) ((JSONNumber) this).n;
    }

    public float asFloat() {
        return (float) ((JSONNumber) this).n;
    }

    public double asDouble() {
        return asNumber();
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
