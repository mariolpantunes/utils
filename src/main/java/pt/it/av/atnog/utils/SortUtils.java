package pt.it.av.atnog.utils;

import java.util.Comparator;

/**
 * Created by mantunes on 6/16/17.
 */
public class SortUtils {


  private static <T> int partition(T[] array, int left, int right, int pivot, Comparator<T> c) {
    int rv = left;
    T pivotValue = array[pivot];
    ArrayUtils.swap(array, pivot, right);
    for (int i = left; i < right; i++) {
      if (c.compare(array[i], pivotValue) <= 0) {
        ArrayUtils.swap(array, rv, i);
        rv++;
      }
    }
    ArrayUtils.swap(array, right, rv);
    return rv;
  }

  public static <T> T qselect(T[] array, int left, int right, int n, Comparator<T> c) {
    T rv = null;
    if (left < right) {
      int pivot = left + (int) Math.floor(Math.random() * (right - left + 1));
      pivot = partition(array, left, right, pivot, c);
      if (n == pivot)
        rv = array[n];
      else if (n < pivot)
        rv = qselect(array, left, pivot - 1, n, c);
      else
        rv = qselect(array, pivot + 1, right, n, c);

    }
    return rv;
  }

  public static <T> T qselect(T[] array, int n, Comparator<T> c) {
    return qselect(array, 0, array.length - 1, n, c);
  }

  /**
   * Quicksort partition algorithm.
   *
   * @param array
   * @param left
   * @param right
   * @param pivot
   * @param <T>
   * @return
   */
  private static <T extends Comparable<T>> int partition(T[] array, int left, int right, int pivot) {
    int rv = left;
    T pivotValue = array[pivot];
    ArrayUtils.swap(array, pivot, right);
    for (int i = left; i < right; i++) {
      if (array[i].compareTo(pivotValue) <= 0) {
        ArrayUtils.swap(array, rv, i);
        rv++;
      }
    }
    ArrayUtils.swap(array, right, rv);
    return rv;
  }

  public static <T extends Comparable<T>> T qselect(T[] array, int left, int right, int n) {
    T rv = null;
    if (left < right) {
      int pivot = left + (int) Math.floor(Math.random() * (right - left + 1));
      pivot = partition(array, left, right, pivot);
      if (n == pivot)
        rv = array[n];
      else if (n < pivot)
        rv = qselect(array, left, pivot - 1, n);
      else
        rv = qselect(array, pivot + 1, right, n);

    }
    return rv;
  }

  public static <T extends Comparable<T>> T qselect(T[] array, int n) {
    return qselect(array, 0, array.length - 1, n);
  }
}
