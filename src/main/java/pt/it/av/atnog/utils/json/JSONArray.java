package pt.it.av.atnog.utils.json;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mantunes on 26/03/2015.
 */
public class JSONArray extends JSONValue implements Iterable<JSONValue> {
    private final List<JSONValue> array = new ArrayList<>();

    public void add(final JSONValue value) {
        array.add(value);
    }

    public void add(final String s) {
        add(new JSONString(s));
    }

    public void add(double n) {
        add(new JSONNumber(n));
    }

    public JSONValue get(final int idx) {
        return array.get(idx);
    }

    public int size() {
        return array.size();
    }

    @Override
    public void write(Writer w) throws IOException {
        w.append("[");
        int i = 0, t = array.size() - 1;
        for (; i < t; i++) {
            array.get(i).write(w);
            w.append(",");
        }
        if (t >= 0)
            array.get(i).write(w);
        w.append("]");
    }

    @Override
    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null) {
            if (o == this)
                rv = true;
            else if (o instanceof JSONArray) {
                JSONArray j = (JSONArray) o;
                if (j.size() == size()) {
                    rv = array.equals(j.array);
                } else
                    rv = false;
            }
        }
        return rv;
    }

    public Iterator<JSONValue> iterator() {
        return new JSONArrayIterator();
    }

    public class JSONArrayIterator implements Iterator<JSONValue> {
        private int idx = 0;

        @Override
        public boolean hasNext() {
            return idx < array.size();
        }

        @Override
        public JSONValue next() {
            JSONValue rv = null;
            if (idx < array.size())
                rv = array.get(idx++);
            return rv;
        }
    }
}
