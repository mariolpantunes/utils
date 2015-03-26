package pt.it.av.atnog.utils.json;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * Created by mantunes on 26/03/2015.
 */
public class JSONObject extends JSONValue {
    private static final int LENGTH = 1024;
    private Map<String, JSONValue> map;

    public static JSONObject parse(Reader reader) {
        //char buffer[] = new char[LENGTH];
        Deque<STATE> state = new ArrayDeque<>();
        Deque<JSONObject> objects = new ArrayDeque<>();
        Deque<JSONArray> arrays = new ArrayDeque<>();
        state.push(STATE.BEGIN);
        StringBuilder key, value;
        int n = 0;
        try {
            while ((n = reader.read()) != -1) {
                char c = (char) n;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void clear() {
        map.clear();
    }

    private enum STATE {BEGIN, ROOT, JSON, PRE_KEY, KEY, COLON, COMMA, VALUE, JSTRING, JARRAY, END, ERROR}

    ;
}
