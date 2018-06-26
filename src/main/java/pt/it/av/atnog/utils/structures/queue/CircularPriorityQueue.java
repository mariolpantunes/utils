package pt.it.av.atnog.utils.structures.queue;

import pt.it.av.atnog.utils.ArrayUtils;
import pt.it.av.atnog.utils.Utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Circular Priority Queue implemented with a min heap.
 * <p>
 * This queue has a fixed size.
 * After filling the buffer, any subsequent add operation will overweight the oldest element.<br>
 * The elements inside de queue are ordered based on natural order, or a {@link Comparator} class.
 * </p>
 *
 * @param <E> the type of elements held by the collection.
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class CircularPriorityQueue<E> implements Queue<E> {
  public static final int DEFAULT_SIZE = 10;
  private final Comparator<E> c;
  private E data[];
  private int size;

  /**
   * Circular Priority Queue constructor.<br>
   * It constructs and queue with size {@link #DEFAULT_SIZE} and natural order {@link DefaultComparator}.
   */
  public CircularPriorityQueue() {
    this(DEFAULT_SIZE, new DefaultComparator<E>());
  }

  public CircularPriorityQueue(int size) {
    this(size, new DefaultComparator<E>());
  }

  public CircularPriorityQueue(int size, Comparator<E> c) {
    this.data = Utils.cast(new Object[size]);
    this.c = c;
    this.size = 0;
  }

  /**
   * Returns the maximum number of elements in the queue.
   *
   * @return the maximum number of elements in the queue
   */
  public int length() {
    return data.length;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Returns true if this collection is full.
   *
   * @return true if this collection is full
   */
  public boolean isFull() {
    return size == data.length;
  }

  @Override
  public boolean contains(Object o) {
    boolean rv = false;

    for (int i = 0; i < size && rv; i++) {
      if (data[i].equals(o)) {
        rv = true;
      }
    }

    return rv;
  }

  @Override
  public Iterator<E> iterator() {
    return new CircularPriorityQueueIterator(data, size);
  }

  @Override
  public Object[] toArray() {
    E rv[] = Utils.cast(new Object[size]);
    int i = 0;
    for (E e : this)
      rv[i++] = e;
    return rv;
  }

  @Override
  public <T> T[] toArray(T[] a) {
    int i = 0;
    for (E e : this)
      a[i++] = Utils.cast(e);
    return a;
  }

  @Override
  public boolean add(E e) {
    if (e == null)
      throw new NullPointerException();

    boolean rv = true;

    if (size < data.length) {
      data[size] = e;
      size++;
      for (int i = size - 1; i > 0 && c.compare(data[i], data[i / 2]) < 0.0; i = i / 2) {
        ArrayUtils.swap(data, i / 2, i);
      }
    } else {
      if (c.compare(data[0], e) < 0.0) {
        data[0] = e;
        min_heapify(data, 0, size);
      } else {
        rv = false;
      }
    }

    return rv;
  }

  @Override
  public boolean remove(Object o) {
    boolean rv = false;
    int idx = -1;

    for (int i = 0; i < size && !rv; i++) {
      if (data[i].equals(o)) {
        idx = i;
        rv = true;
      }
    }

    ArrayUtils.swap(data, idx, size - 1);
    min_heapify(data, idx, size - 1);
    size--;

    return rv;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    boolean rv = true;
    Iterator it = c.iterator();
    while (rv && it.hasNext())
      rv = contains(it.next());
    return rv;
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    boolean rv = false;
    for (E e : c) {
      rv = add(e) || rv;
    }
    return rv;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    int tsize = size();
    for (Object o : c) {
      remove(o);
    }
    return size() != tsize;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    E tdata[] = Utils.cast(new Object[this.data.length]);
    int tsize = 0;
    for (E e : this) {
      if (c.contains(e)) {
        tdata[tsize++] = e;
      }
    }
    boolean rv = tsize != size;
    for (int i = tsize / 2; i >= 1; i--) {
      min_heapify(tdata, i, tsize);
    }
    this.data = tdata;
    this.size = tsize;

    return rv;
  }

  @Override
  public void clear() {
    size = 0;
  }

  @Override
  public boolean offer(E e) {
    return add(e);
  }

  @Override
  public E remove() {
    if (isEmpty())
      throw new NoSuchElementException();

    E rv = data[0];
    ArrayUtils.swap(data, 0, size - 1);
    min_heapify(data, 0, size - 1);
    size--;

    return rv;
  }

  @Override
  public E poll() {
    E rv = null;
    if (!isEmpty()) {
      rv = data[0];
      size--;
    }
    return rv;
  }

  @Override
  public E element() {
    if (isEmpty())
      throw new NoSuchElementException();
    return data[0];
  }

  @Override
  public E peek() {
    E rv = null;
    if (!isEmpty())
      rv = data[0];
    return rv;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    Iterator it = iterator();
    for (int i = 0; i < size - 1; i++) {
      sb.append(data[i]);
      sb.append(", ");
    }

    if (size > 0) {
      sb.append(data[size - 1]);
    }

    sb.append(']');
    return sb.toString();
  }

  /**
   * @param data
   * @param i
   * @param size
   */
  private void min_heapify(final E data[], int i, int size) {
    int left = 2 * i, right = 2 * i + 1, smallest;

    if (left < size && c.compare(data[left], data[i]) < 0.0) {
      smallest = left;
    } else {
      smallest = i;
    }

    if (right < size && c.compare(data[right], data[smallest]) < 0.0) {
      smallest = right;
    }

    if (smallest != i) {
      ArrayUtils.swap(data, i, smallest);
      min_heapify(data, smallest, size);
    }
  }

  /**
   * Default Comparator class.
   * <p>
   * It implements the natural ordering of the elements
   * </p>
   *
   * @param <E> the type of elements held by the collection.
   */
  private static class DefaultComparator<E> implements Comparator<E> {
    @Override
    public int compare(E o1, E o2) {
      Comparable<E> c1 = Utils.cast(o1);
      return (c1.compareTo(o2));
    }
  }

  /**
   * Circular Priority Queue iterator.
   * <p>
   * Implements an interator that is able to transverse a min heap.
   * </p>
   */
  private class CircularPriorityQueueIterator implements Iterator<E> {
    private E data[];
    private int size;

    /**
     * Circular Priority Queue Iterator construtor.
     *
     * @param data the min heap array.
     * @param size the number of elements in the circular queue.
     */
    public CircularPriorityQueueIterator(final E data[], final int size) {
      this.data = Utils.cast(new Object[size]);
      this.size = size;
      System.arraycopy(data, 0, this.data, 0, size);
    }

    @Override
    public boolean hasNext() {
      return size > 0;
    }

    @Override
    public E next() {
      E rv = null;
      if (size > 0) {
        rv = data[0];
        ArrayUtils.swap(data, 0, size - 1);
        min_heapify(data, 0, size - 1);
        size--;
      }
      return rv;
    }
  }
}
