package pt.it.av.atnog.utils.json;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Created by mantunes on 26/03/2015.
 */
public class JSONObject extends JSONValue {
    //private static final int LENGTH = 1024;
    private final Map<String, JSONValue> map = new HashMap<>();

    public static JSONObject parse(Reader reader) {
        JSONObject root = new JSONObject();
        //char buffer[] = new char[LENGTH];
        Deque<STATE> state = new ArrayDeque<>();
        Deque<JSONObject> objects = new ArrayDeque<>();
        Deque<JSONArray> arrays = new ArrayDeque<>();
        state.push(STATE.BEGIN);
        StringBuilder name = new StringBuilder(), value = new StringBuilder();
        STATE previous = null;
        int n = 0;
        try {
            while ((n = reader.read()) != -1 && state.peek() != STATE.ERROR) {
                if (state.peek() != previous) {
                    int space = 0;
                    if (state.peek() == STATE.JARRAY)
                        space = arrays.size();
                    else
                        space = objects.size();
                    for (int i = 0; i < space; i++)
                        System.err.print("  ");
                    System.err.println(state.peek().name());
                    previous = state.peek();
                }
                char c = (char) n;
                switch (state.peek()) {
                    case BEGIN:
                        switch (c) {
                            case '{':
                                state.pop();
                                state.push(STATE.END);
                                state.push(STATE.ROOT);
                                objects.push(root);
                                break;
                        }
                        break;
                    case ROOT:
                        switch (c) {
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
                        switch (c) {
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
                        switch (c) {
                            case '"':
                                state.pop();
                                state.push(STATE.KEY);
                                break;
                        }
                        break;
                    case KEY:
                        switch (c) {
                            case '"':
                                if (name.charAt(name.length() - 1) != '\\') {
                                    state.pop();
                                    state.push(STATE.COLON);
                                } else
                                    name.append(c);
                                break;
                            default:
                                name.append(c);
                                break;
                        }
                        break;
                    case COLON:
                        switch (c) {
                            case ':':
                                state.pop();
                                state.push(STATE.VALUE);
                                break;
                        }
                        break;
                    case VALUE:
                        switch (c) {
                            case '"':
                                state.pop();
                                state.push(STATE.JSTRING);
                                break;
                            case ',':
                                objects.peek().map.put(name.toString(), factory(value));
                                state.pop();
                                if (state.peek() == STATE.ROOT || state.peek() == STATE.JSON)
                                    state.push(STATE.PRE_KEY);
                                name.setLength(0);
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
                            case '[': {
                                JSONArray j = new JSONArray();
                                objects.peek().map.put(name.toString(), j);
                                arrays.push(j);
                                state.pop();
                                state.push(STATE.JARRAY);
                                name.setLength(0);
                                break;
                            }
                            default:
                                value.append(c);
                                break;
                        }
                        break;
                    case JSTRING:
                        switch (c) {
                            case '"':
                                if (value.charAt(value.length() - 1) != '\\') {
                                    state.pop();
                                    if (state.peek() == STATE.ROOT || state.peek() == STATE.JSON) {
                                        objects.peek().map.put(name.toString(), factory(value));
                                        name.setLength(0);
                                    } else if (state.peek() == STATE.JARRAY)
                                        arrays.peek().add(factory(value));
                                    value.setLength(0);
                                    state.push(STATE.COMMA);
                                } else
                                    value.append(c);
                                break;
                            default:
                                value.append(c);
                                break;
                        }
                        break;
                    case COMMA:
                        switch (c) {
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
                        switch (c) {
                            case '"':
                                state.push(STATE.JSTRING);
                                break;
                            case '[': {
                                JSONArray j = new JSONArray();
                                arrays.peek().add(j);
                                arrays.push(j);
                                state.push(STATE.JARRAY);
                                break;
                            }
                            case ']':
                                if (value.length() > 0) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root;
    }

    private static JSONValue factory(StringBuilder sb) {
        return new JSONString(sb.toString());
    }

    public JSONValue get(String name) {
        return map.get(name);
    }

    public String getString(String name) {
        return map.get(name).asString().value();
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

    @Override
    public void toString(StringBuilder sb) {
        sb.append("{");
        for (Map.Entry<String, JSONValue> entry : map.entrySet()) {
            sb.append("\"" + entry.getKey() + "\":");
            entry.getValue().toString(sb);
            sb.append(",");
        }
        if (sb.charAt(sb.length() - 1) == ',')
            sb.setCharAt(sb.length() - 1, '}');
        else
            sb.append('}');
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    private enum STATE {BEGIN, ROOT, JSON, PRE_KEY, KEY, COLON, COMMA, VALUE, JSTRING, JARRAY, END, ERROR}
}
