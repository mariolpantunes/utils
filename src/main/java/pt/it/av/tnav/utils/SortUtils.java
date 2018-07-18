package pt.it.av.tnav.utils;

import java.util.Comparator;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class SortUtils {
  /**
   * Utility class, lets make the constructor private.
   */
  private SortUtils() {}

  /**
   *
   * @param arr
   * @param left
   * @param right
   * @param k
   * @param c
   * @param <T>
   * @return
   */
  public static <T> T qselect(T[] arr, int left, int right, int k, Comparator<T> c) {
    int from = left, to = right;
    // if from == to we reached the kth element
    while (from < to) {
      int r = from, w = to;
      T mid = arr[(r + w) / 2];

      // stop if the reader and writer meets
      while (r < w) {

        if (c.compare(arr[r], mid) >= 0) { // put the large values at the end
          ArrayUtils.swap(arr, w, r);
          w--;
        } else { // the value is smaller than the pivot, skip
          r++;
        }
      }

      // if we stepped up (r++) we need to step one down
      if (c.compare(arr[r], mid) > 0) {
        r--;
      }

      // the r pointer is on the end of the first k elements
      if (k <= r) {
        to = r;
      } else {
        from = r + 1;
      }
    }

    return arr[k];
  }

  /**
   * @param array
   * @param n
   * @param c
   * @param <T>
   * @return
   */
  public static <T> T qselect(T[] array, int n, Comparator<T> c) {
    return qselect(array, 0, array.length - 1, n, c);
  }

  /**
   *
   * @param arr
   * @param left
   * @param right
   * @param k
   * @param <T>
   * @return
   */
  public static <T extends Comparable<T>> T qselect(T[] arr, int left, int right, int k) {
    int from = left, to = right;
    // if from == to we reached the kth element
    while (from < to) {
      int r = from, w = to;
      T mid = arr[(r + w) / 2];

      // stop if the reader and writer meets
      while (r < w) {

        if (arr[r].compareTo(mid) >= 0) { // put the large values at the end
          ArrayUtils.swap(arr, w, r);
          w--;
        } else { // the value is smaller than the pivot, skip
          r++;
        }
      }

      // if we stepped up (r++) we need to step one down
      if (arr[r].compareTo(mid) > 0) {
        r--;
      }

      // the r pointer is on the end of the first k elements
      if (k <= r) {
        to = r;
      } else {
        from = r + 1;
      }
    }

    return arr[k];
  }

  /**
   * @param array
   * @param n
   * @param <T>
   * @return
   */
  public static <T extends Comparable<T>> T qselect(T[] array, int n) {
    return qselect(array, 0, array.length - 1, n);
  }


  /**
   * @param arr
   * @param left
   * @param right
   * @param k
   * @return
   */
  public static double qselect(double[] arr, int left, int right, int k) {
    int from = left, to = right;
    // if from == to we reached the kth element
    while (from < to) {
      int r = from, w = to;
      double mid = arr[(r + w) / 2];

      // stop if the reader and writer meets
      while (r < w) {

        if (arr[r] >= mid) { // put the large values at the end
          ArrayUtils.swap(arr, w, r);
          w--;
        } else { // the value is smaller than the pivot, skip
          r++;
        }
      }

      // if we stepped up (r++) we need to step one down
      if (arr[r] > mid) {
        r--;
      }

      // the r pointer is on the end of the first k elements
      if (k <= r) {
        to = r;
      } else {
        from = r + 1;
      }
    }

    return arr[k];
  }

  /**
   * @param a
   * @param n
   * @return
   */
  public static double qselect(final double a[], int k) {
    return qselect(a, 0, a.length - 1, k);
  }
}
