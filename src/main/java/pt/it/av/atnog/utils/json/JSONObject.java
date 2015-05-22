package pt.it.av.atnog.utils.json;

import java.io.*;
import java.util.*;

/**
 * Created by mantunes on 26/03/2015.
 */
public class JSONObject extends JSONValue {
    private static final int LENGTH = 128;
    private final Map<String, JSONValue> map = new HashMap<>();

    public static JSONObject read(String s) throws IOException {
        return read(new BufferedReader(new StringReader(s)));
    }

    // TODO: improve error detection...
    public static JSONObject read(Reader r) throws IOException {
        JSONObject root = new JSONObject();
        char buffer[] = new char[LENGTH];
        Deque<STATE> state = new ArrayDeque<>();
        Deque<JSONObject> objects = new ArrayDeque<>();
        Deque<JSONArray> arrays = new ArrayDeque<>();
        state.push(STATE.BEGIN);
        StringBuilder name = new StringBuilder(), value = new StringBuilder();
        STATE previous = null;
        int t = 0;
        try {
            while ((t = r.read(buffer)) != -1) {
                /*if (state.peek() != previous) {
                    int space = 0;
                    if (state.peek() == STATE.JARRAY)
                        space = arrays.size();
                    else
                        space = objects.size();
                    for (int i = 0; i < space; i++)
                        System.err.print("  ");
                    System.err.println(state.peek().name());
                    previous = state.peek();
                }*/
                for (int i = 0; i < t; i++) {
                    switch (state.peek()) {
                        case BEGIN:
                            switch (buffer[i]) {
                                case '{':
                                    state.pop();
                                    state.push(STATE.END);
                                    state.push(STATE.ROOT);
                                    objects.push(root);
                                    break;
                            }
                            break;
                        case ROOT:
                            switch (buffer[i]) {
                                case '"':
                                    state.push(STATE.KEY);
                                    break;
                                case '}':
                                    objects.pop();
                                    state.pop();
                                    break;
                            }
                            break;
                        case JSON:
                            switch (buffer[i]) {
                                case '"':
                                    state.push(STATE.KEY);
                                    break;
                                case '}':
                                    objects.pop();
                                    state.pop();
                                    state.push(STATE.COMMA);
                                    break;
                            }
                            break;
                        case PRE_KEY:
                            switch (buffer[i]) {
                                case '"':
                                    state.pop();
                                    state.push(STATE.KEY);
                                    break;
                            }
                            break;
                        case KEY:
                            switch (buffer[i]) {
                                case '"':
                                    if (!isEscaped(name)) {
                                        state.pop();
                                        state.push(STATE.COLON);
                                    } else
                                        name.append(buffer[i]);
                                    break;
                                default:
                                    name.append(buffer[i]);
                                    break;
                            }
                            break;
                        case COLON:
                            switch (buffer[i]) {
                                case ':':
                                    state.pop();
                                    state.push(STATE.PRE_VALUE);
                                    break;
                            }
                            break;
                        case PRE_VALUE:
                            if (!Character.isWhitespace(buffer[i])) {
                                switch (buffer[i]) {
                                    case '"':
                                        state.pop();
                                        state.push(STATE.JSTRING);
                                        value.setLength(0);
                                        break;
                                    case '{': {
                                        JSONObject j = new JSONObject();
                                        objects.peek().map.put(name.toString(), j);
                                        objects.push(j);
                                        state.pop();
                                        state.push(STATE.JSON);
                                        name.setLength(0);
                                        break;
                                    }
                                    case '[': {
                                        JSONArray j = new JSONArray();
                                        objects.peek().map.put(name.toString(), j);
                                        arrays.push(j);
                                        state.pop();
                                        state.push(STATE.JARRAY);
                                        name.setLength(0);
                                        value.setLength(0);
                                        break;
                                    }
                                    default:
                                        state.pop();
                                        state.push(STATE.VALUE);
                                        value.append(buffer[i]);
                                        break;
                                }
                            }
                            break;
                        case VALUE:
                            switch (buffer[i]) {
                                case ',':
                                    objects.peek().map.put(name.toString(), factory(value));
                                    state.pop();
                                    if (state.peek() == STATE.ROOT || state.peek() == STATE.JSON)
                                        state.push(STATE.PRE_KEY);
                                    name.setLength(0);
                                    value.setLength(0);
                                    break;
                                case '}': {
                                    JSONValue j = factory(value);
                                    objects.peek().map.put(name.toString(), j);
                                    objects.pop();
                                    state.pop();
                                    state.pop();
                                    if (state.peek() == STATE.JSON || state.peek() == STATE.JARRAY)
                                        state.push(STATE.COMMA);
                                    else
                                        state.pop();
                                    name.setLength(0);
                                    value.setLength(0);
                                    break;
                                }
                                default:
                                    value.append(buffer[i]);
                                    break;
                            }
                            break;
                        case JSTRING:
                            switch (buffer[i]) {
                                case '"':
                                    if (!isEscaped(value)) {
                                        state.pop();
                                        if (state.peek() == STATE.ROOT || state.peek() == STATE.JSON) {
                                            objects.peek().map.put(name.toString(), new JSONString(value.toString()));
                                            name.setLength(0);
                                        } else if (state.peek() == STATE.JARRAY)
                                            arrays.peek().add(new JSONString(value.toString()));
                                        value.setLength(0);
                                        state.push(STATE.COMMA);
                                    } else
                                        value.append(buffer[i]);
                                    break;
                                default:
                                    value.append(buffer[i]);
                                    break;
                            }
                            break;
                        case COMMA:
                            switch (buffer[i]) {
                                case ',':
                                    state.pop();
                                    if (state.peek() == STATE.ROOT || state.peek() == STATE.JSON)
                                        state.push(STATE.PRE_KEY);
                                    break;
                                case '}':
                                    state.pop();
                                    if (state.peek() == STATE.JSON) {
                                        objects.pop();
                                        state.pop();
                                        state.push(STATE.COMMA);
                                    } else if (state.peek() == STATE.ROOT) {
                                        objects.pop();
                                        state.pop();
                                    }
                                    break;
                                case ']':
                                    state.pop();
                                    if (state.peek() == STATE.JARRAY) {
                                        arrays.pop();
                                        state.pop();
                                        state.push(STATE.COMMA);
                                    }
                                    break;
                            }
                            break;
                        case JARRAY:
                            switch (buffer[i]) {
                                case '"':
                                    state.push(STATE.JSTRING);
                                    break;
                                case '[': {
                                    JSONArray j = new JSONArray();
                                    arrays.peek().add(j);
                                    arrays.push(j);
                                    state.push(STATE.JARRAY);
                                    value.setLength(0);
                                    break;
                                }
                                case ']':
                                    if (value.length() > 0) {
                                        System.err.println("Value: " + value.toString());
                                        arrays.peek().add(factory(value));
                                        value.setLength(0);
                                    }
                                    arrays.pop();
                                    state.pop();
                                    state.push(STATE.COMMA);
                                    break;
                                case ',':
                                    if (value.length() > 0) {
                                        arrays.peek().add(factory(value));
                                        value.setLength(0);
                                    }
                                    break;
                                case '{': {
                                    JSONObject j = new JSONObject();
                                    arrays.peek().add(j);
                                    objects.push(j);
                                    state.push(STATE.JSON);
                                    break;
                                }
                            }
                            break;
                        case END:
                            break;
                        case ERROR:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*int space = 0;
        if (state.peek() == STATE.JARRAY)
            space = arrays.size();
        else
            space = objects.size();
        for (int i = 0; i < space; i++)
            System.err.print("  ");
        System.err.println(state.peek().name());*/

        if (state.peek() != STATE.END) {
            root.clear();
            throw new IOException();
        }

        return root;
    }

    private static boolean isEscaped(StringBuilder sb) {
        boolean rv = false;
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '\\')
            rv = true;
        return rv;
    }

    private static JSONValue factory(StringBuilder sb) {
        JSONValue rv = null;
        String s = sb.toString().trim();
        switch (s) {
            case "true":
                rv = new JSONBoolean(true);
                break;
            case "false":
                rv = new JSONBoolean(false);
                break;
            case "null":
                rv = new JSONNull();
                break;
            default:
                rv = new JSONNumber(Double.parseDouble(s));
                break;
        }
        return rv;
    }

    public boolean contains(String name) {
        return map.containsKey(name);
    }

    public JSONValue get(String name) {
        return map.get(name);
    }

    public void add(String name, JSONValue value) {
        map.put(name, value);
    }

    public void add(String name, double value) {
        map.put(name, new JSONNumber(value));
    }

    public void add(String name, String value) {
        map.put(name, new JSONString(value));
    }

    public Set<String> names() {
        return map.keySet();
    }

    public void clear() {
        map.clear();
    }

    //TODO: JSONENCODE the names...
    @Override
    public void write(Writer w) throws IOException {
        w.append("{");
        Set<Map.Entry<String, JSONValue>> s = map.entrySet();
        Iterator<Map.Entry<String, JSONValue>> it = s.iterator();
        int t = s.size() - 1;
        for (int i = 0; i < t; i++) {
            Map.Entry<String, JSONValue> entry = it.next();
            w.append("\"" + entry.getKey() + "\":");
            entry.getValue().write(w);
            w.append(",");
        }
        if (t >= 0) {
            Map.Entry<String, JSONValue> entry = it.next();
            w.append("\"" + entry.getKey() + "\":");
            entry.getValue().write(w);
        }
        w.append("}");
    }

    @Override
    public String toString() {
        StringWriter sw = new StringWriter();
        try {
            write(sw);
        } catch (IOException e) {

        }
        return sw.toString();
    }

    private enum STATE {BEGIN, ROOT, JSON, PRE_KEY, KEY, COLON, COMMA, PRE_VALUE, VALUE, JSTRING, JARRAY, END, ERROR}
}
