package pt.it.av.atnog.utils.structures;

import pt.it.av.atnog.utils.ArrayUtils;
import pt.it.av.atnog.utils.Utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Queue implemented with a circular buffer.
 * <p>
 * This queue has a fixed size.
 * After filling the buffer, any subsequent add operation will overweight the oldest element.<br>
 * Currently this structure is used in two differente scenarios:
 * </p>
 * <ul>
 * <li>gather the gather neighborhoods of a elements and
 * <li>as a buffer to mediate the communication between producers and consumers
 * </ul>
 *
 * @param <E> the type of elements held by the collection
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class CircularPriorityQueue<E> implements Queue<E> {
  private final E data[];
  private final Comparator<E> c;
  private int size;

  public CircularPriorityQueue() {
    this(10, new DefaultComparator());
  }

  public CircularPriorityQueue(int size) {
    this(size, new DefaultComparator());
  }

  public CircularPriorityQueue(int size, Comparator<E> c) {
    this.data = Utils.cast(new Object[size]);
    this.c = c;
    this.size = 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public boolean contains(Object o) {
    return false;
  }

  @Override
  public Iterator<E> iterator() {
    return null;
  }

  @Override
  public Object[] toArray() {
    return new Object[0];
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return null;
  }

  @Override
  public boolean add(E e) {
    if (e == null)
      throw new NullPointerException();

    if (size < data.length) {
      data[size] = e;
      size++;
      for (int i = size - 1; i > 0 && c.compare(data[i], data[i / 2]) < 0.0; i = i / 2) {
        ArrayUtils.swap(data, i / 2, i);
      }
    } else {
      data[0] = e;
      min_heapify(data, 0, size);
    }

    return true;
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
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    for (E e : c) {
      add(e);
    }
    return true;
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
    return false;
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

  void min_heapify(final E data[], int i, int size) {
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
   *
   */
  private static class DefaultComparator implements Comparator {
    public int compare(Object o1, Object o2) {
      return ((Comparable) o1).compareTo(o2);
    }
  }
}
