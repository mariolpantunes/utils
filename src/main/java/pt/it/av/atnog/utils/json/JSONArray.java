package pt.it.av.atnog.utils.json;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Created by mantunes on 26/03/2015.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 */
public class JSONArray extends JSONValue implements List<JSONValue> {
    private final List<JSONValue> array = new ArrayList<>();

    @Override
    public boolean add(final JSONValue value) {
        return array.add(value);
    }

    @Override
    public void add(int index, final JSONValue value) {
        array.add(index, value);
    }

    public void add(final String s) {
        add(new JSONString(s));
    }

    public void add(double n) {
        add(new JSONNumber(n));
    }

    @Override
    public boolean addAll(Collection<? extends JSONValue> c) {
        return array.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends JSONValue> c) {
        return array.addAll(index, c);
    }

    @Override
    public boolean remove(Object o) {
        return array.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return array.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return array.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return array.retainAll(c);
    }

    @Override
    public JSONValue get(final int idx) {
        return array.get(idx);
    }

    @Override
    public JSONValue set(int index, JSONValue value) {
        return array.set(index, value);
    }

    @Override
    public JSONValue remove(int index) {
        return array.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return array.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return array.indexOf(o);
    }

    @Override
    public ListIterator<JSONValue> listIterator() {
        return new JSONArrayListIterator();
    }

    @Override
    public ListIterator<JSONValue> listIterator(int index) {
        return new JSONArrayListIterator(index);
    }

    @Override
    public List<JSONValue> subList(int fromIndex, int toIndex) {
        return array.subList(fromIndex, toIndex);
    }

    public int size() {
        return array.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public void clear() {
        array.clear();
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

    @Override
    public Iterator<JSONValue> iterator() {
        return new JSONArrayIterator();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    public class JSONArrayIterator implements Iterator<JSONValue> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < array.size();
        }

        @Override
        public JSONValue next() {
            JSONValue rv = null;
            if (index < array.size())
                rv = array.get(index++);
            return rv;
        }
    }

    public class JSONArrayListIterator implements ListIterator<JSONValue> {
        private int index;

        public JSONArrayListIterator() {
            index = 0;
        }

        public JSONArrayListIterator(int index) {
            this.index = index;
        }


        @Override
        public boolean hasNext() {
            return index < array.size();
        }

        @Override
        public JSONValue next() {
            if(index < 0)
                index = 0;

            JSONValue rv = null;

            if (index < array.size())
                rv = array.get(index++);

            return rv;
        }

        @Override
        public boolean hasPrevious() {
            return index >= 0;
        }

        @Override
        public JSONValue previous() {
            if(index >= array.size())
                index = array.size() - 1;

            JSONValue rv = null;

            if (index >= 0)
                rv = array.get(index--);

            return rv;
        }

        @Override
        public int nextIndex() {
            return index + 1;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            array.remove(index);
        }

        @Override
        public void set(JSONValue value) {
            array.set(index, value);
        }

        @Override
        public void add(JSONValue value) {
            array.add(index, value);
        }
    }
}
