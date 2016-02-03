package pt.it.av.atnog.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Collection Iterator.
 *
 * Represents a collection of iterators.
 * It allows simultaneous iteration with all of them.
 * Simultaneous iteration, in this context, means the iterators are consomed simultaneously.
 * Each means that preserves a partial order between the elements of the iterators
 *
 * @param <E> generic type for the iterator
 */
public class CollectionIterator<E> implements Iterator<E> {

    /**
     * List of iterators that will be consumed
     */
    private final List<Iterator<E>> its;

    /**
     * Idx of the current iterator
     */
    private int idx = 0;

    /**
     * CollectionIterator contructor.
     * <p>
     * Requires a list of iterators.
     *
     * @param its list of iterators to consume.
     */
    public CollectionIterator(final List<Iterator<E>> its) {
        this.its = its;

        List<Iterator<E>> toRemove = new ArrayList<>();

        for (Iterator<E> it : this.its)
            if (!it.hasNext())
                toRemove.add(it);

        its.removeAll(toRemove);
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
