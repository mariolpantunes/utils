package pt.it.av.atnog.utils.structures;

/**
 * Created by mantunes on 11/26/14.
 */

import java.util.Iterator;

public class RingBuffer<E> implements Iterable<E> {
    private int head = 0, size = 0;
    private E data[];

    public RingBuffer() {
        this(10);
    }

    public RingBuffer(int size) {
        this.data = (E[]) new Object[size];
    }

    public void add(E value) {
        int tail = (head + size) % data.length;
        data[tail] = value;
        if (size == data.length)
            head = (head + 1) % data.length;
        else
            size++;
    }

    public boolean isFull() {
        return size == data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }


    public Iterator<E> iterator() {
        return new RingBufferIterator();
    }

    private class RingBufferIterator implements Iterator {
        private int i = head, count = size;

        public boolean hasNext() {
            return count > 0;
        }

        public Object next() {
            E datum = data[i];
            i = (i + 1) % data.length;
            count--;
            return datum;
        }
    }
    //void cbRead(CircularBuffer *cb, ElemType *elem) {
    //    *elem = cb->elems[cb->start];
    //    cb->start = (cb->start + 1) % cb->size;
    //    -- cb->count;
    //}
}
