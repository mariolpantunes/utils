package pt.it.av.atnog.utils.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mantunes on 26/03/2015.
 */
public class JSONArray extends JSONValue implements Iterable<JSONValue> {
    private final List<JSONValue> list = new ArrayList<>();

    public void add(JSONValue value) {
        list.add(value);
    }

    public JSONValue get(int idx) {
        return list.get(idx);
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append("[");
        int i = 0;
        for (int t = list.size() - 1; i < t; i++) {
            list.get(i).toString(sb);
            sb.append(",");
        }
        if (list.size() > 0)
            list.get(i).toString(sb);
        sb.append("]");
    }

    public Iterator<JSONValue> iterator() {
        return new JSONArrayIterator();
    }

    public class JSONArrayIterator implements Iterator<JSONValue> {
        private int idx = 0;

        @Override
        public boolean hasNext() {
            return idx < list.size();
        }

        @Override
        public JSONValue next() {
            return list.get(idx++);
        }
    }
}