package pt.it.av.atnog.utils.structures.iterator;

import java.util.Iterator;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class LimitIterator<E> implements Iterator<E> {
    private final Iterator<E> it;
    private final int limit;
    private int i = 0;


    public LimitIterator(final Iterator<E> it, final int limit) {
        this.it = it;
        this.limit = limit;
        i = 0;
    }

    @Override
    public boolean hasNext() {
        return it.hasNext() && i < limit;
    }

    @Override
    public E next() {
        E rv = null;
        if (hasNext()) {
            rv = it.next();
            i++;
        }
        return rv;
    }
}
