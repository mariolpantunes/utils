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
    private final List<JSONValue> list = new ArrayList<>();

    public void add(final JSONValue value) {
        list.add(value);
    }

    public void add(final String s) {
        add(new JSONString(s));
    }

    public JSONValue get(final int idx) {
        return list.get(idx);
    }

    public int size() {
        return list.size();
    }

    @Override
    public void write(Writer w) throws IOException {
        w.append("[");
        int i = 0, t = list.size() - 1;
        for (; i < t; i++) {
            list.get(i).write(w);
            w.append(",");
        }
        if (t >= 0)
            list.get(i).write(w);
        w.append("]");
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
