package pt.it.av.atnog.utils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by mantunes on 1/20/16.
 */
public class CollectionIterator<E> implements Iterator<E> {
    private final List<Iterator<E>> its;
    private int idx = 0;

    public CollectionIterator(final List<Iterator<E>> its) {
        this.its = its;
    }

    @Override
    public boolean hasNext() {
        return !its.isEmpty();
    }

    @Override
    public E next() {
        Iterator<E> it = its.get(idx);
        E rv = it.next();
        if (!it.hasNext()) {
            its.remove(idx);
            if (!its.isEmpty())
                idx = idx % its.size();
        } else
            idx = (idx + 1) % its.size();
        return rv;
    }
}
