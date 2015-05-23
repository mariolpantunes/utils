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
        Deque<JSONValue> store = new ArrayDeque<>();
        //Deque<JSONArray> arrays = new ArrayDeque<>();
        state.push(STATE.BEGIN);
        StringBuilder name = new StringBuilder(), value = new StringBuilder();
        int t = 0, i = 0;
        try {
            while ((t = r.read(buffer)) != -1) {
                for (i = 0; i < t; i++) {
                    switch (state.peek()) {
                        case BEGIN:
                            switch (buffer[i]) {
                                case '{':
                                    state.pop();
                                    state.push(STATE.END);
                                    state.push(STATE.ROOT);
                                    store.push(root);
                                    break;
                            }
                            break;
                        case ROOT:
                            switch (buffer[i]) {
                                case '"':
                                    state.push(STATE.KEY);
                                    break;
                                case '}':
                                    store.pop();
                                    state.pop();
                                    break;
                            }
                            break;
                        case OBJECT:
                            switch (buffer[i]) {
                                case '"':
                                    state.push(STATE.KEY);
                                    break;
                                case '}':
                                    store.pop();
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
                                        state.push(STATE.STRING);
                                        value.setLength(0);
                                        break;
                                    case '{': {
                                        state.pop();
                                        JSONObject j = new JSONObject();
                                        if (state.peek() == STATE.ROOT || state.peek() == STATE.OBJECT)
                                            ((JSONObject) store.peek()).add(name.toString(), j);
                                        else if (state.peek() == STATE.ARRAY)
                                            ((JSONArray) store.peek()).add(j);
                                        store.push(j);
                                        state.push(STATE.OBJECT);
                                        name.setLength(0);
                                        break;
                                    }
                                    case '[': {
                                        state.pop();
                                        JSONArray j = new JSONArray();
                                        if (state.peek() == STATE.ROOT || state.peek() == STATE.OBJECT)
                                            ((JSONObject) store.peek()).add(name.toString(), j);
                                        else if (state.peek() == STATE.ARRAY)
                                            ((JSONArray) store.peek()).add(j);
                                        store.push(j);
                                        state.push(STATE.ARRAY);
                                        state.push(STATE.PRE_VALUE);
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
                                    state.pop();
                                    if (state.peek() == STATE.ROOT || state.peek() == STATE.OBJECT) {
                                        ((JSONObject) store.peek()).add(name.toString(), factory(value));
                                        state.push(STATE.PRE_KEY);
                                        name.setLength(0);
                                    } else if (state.peek() == STATE.ARRAY) {
                                        ((JSONArray) store.peek()).add(factory(value));
                                        state.push(STATE.PRE_VALUE);
                                    } else {
                                        System.err.println("MERDA-----");
                                    }
                                    value.setLength(0);
                                    break;
                                case '}': {
                                    state.pop();
                                    ((JSONObject) store.peek()).add(name.toString(), factory(value));
                                    state.pop();
                                    store.pop();
                                    if (state.peek() == STATE.OBJECT || state.peek() == STATE.ARRAY)
                                        state.push(STATE.COMMA);
                                    name.setLength(0);
                                    value.setLength(0);
                                    break;
                                }
                                case ']': {
                                    state.pop();
                                    ((JSONArray) store.peek()).add(factory(value));
                                    state.pop();
                                    store.pop();
                                    state.push(STATE.COMMA);
                                    value.setLength(0);
                                    break;
                                }
                                default:
                                    value.append(buffer[i]);
                                    break;
                            }
                            break;
                        case STRING:
                            switch (buffer[i]) {
                                case '"':
                                    if (!isEscaped(value)) {
                                        state.pop();
                                        if (state.peek() == STATE.ROOT || state.peek() == STATE.OBJECT) {
                                            ((JSONObject) store.peek()).add(name.toString(),
                                                    new JSONString(value.toString()));
                                            name.setLength(0);
                                        } else if (state.peek() == STATE.ARRAY)
                                            ((JSONArray) store.peek()).add(new JSONString(value.toString()));
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
                                    if (state.peek() == STATE.ROOT || state.peek() == STATE.OBJECT)
                                        state.push(STATE.PRE_KEY);
                                    else if (state.peek() == STATE.ARRAY)
                                        state.push(STATE.PRE_VALUE);
                                    break;
                                case '}':
                                    state.pop();
                                    store.pop();
                                    state.pop();
                                    if (state.peek() == STATE.OBJECT)
                                        state.push(STATE.COMMA);
                                    break;
                                case ']':
                                    state.pop();
                                    store.pop();
                                    state.pop();
                                    state.push(STATE.COMMA);
                                    break;
                            }
                            break;
                        case ARRAY:
                            break;
                        case END:
                            break;
                        case ERROR:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("BUFFER: " + new String(buffer));
            System.err.println(root);
            e.printStackTrace();
        }

        if (state.peek() != STATE.END) {
            System.err.println("BUFFER: " + new String(buffer));
            System.err.println(root);
            while (!state.isEmpty()) {
                System.err.println("STATE -> " + state.peek().name());
                state.pop();
            }
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

    private enum STATE {BEGIN, ROOT, OBJECT, PRE_KEY, KEY, COLON, COMMA, PRE_VALUE, VALUE, STRING, ARRAY, END, ERROR}
}
