package pt.it.av.atnog.utils;

import pt.it.av.atnog.utils.structures.mutableNumber.MutableInteger;
import pt.it.av.atnog.utils.structures.tuple.Pair;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Operations over arrays of doubles.
 * These operations are common to Matrix and Vector classes.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public final class ArrayUtils {
  /**
   * Utility class, lets make the constructor private.
   */
  private ArrayUtils() {
  }

  /**
   * Sum two arrays element-wise.
   * The elements from A are added with B and stored in C.
   * <p>
   * $$c = \sum_{i = 0}^{len}$$
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   second vector
   * @param bB  index of the second vector
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void add(final double[] a, final int bA, final double[] b,
                         final int bB, final double[] c, final int bC,
                         final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] + b[bB + i];
    }
  }

  /**
   * Sum a scalar with an array.
   * The scalar B is added with A and stored in C.
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   scalar
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void add(final double[] a, final int bA, final double b,
                         final double[] c, final int bC, final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] + b;
    }
  }

  /**
   * Substratc two arrays element-wise.
   * The elements from B are subtracted from A and stored in C.
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   second vector
   * @param bB  index of the second vector
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void sub(final double[] a, final int bA, final double[] b,
                         final int bB, final double[] c, final int bC,
                         final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] - b[bB + i];
    }
  }

  /**
   * Substratc a scalar from an array.
   * The scalar B are subtracted from A and stored in C.
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   scalar
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void sub(final double[] a, final int bA, final double b,
                         final double[] c, final int bC, final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] - b;
    }
  }

  /**
   * Multiply two arrays element-wise.
   * The elements from A are multiplied with B and stored in C.
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   second vector
   * @param bB  index of the second vector
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void mul(final double[] a, final int bA, final double[] b,
                         final int bB, final double[] c, final int bC,
                         final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] * b[bB + i];
    }
  }

  /**
   * Multiply a scalar with an array.
   * The scalar B are subtracted from A and stored in C.
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   scalar
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void mul(final double[] a, final int bA, final double b,
                         final double[] c, final int bC, final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] * b;
    }
  }

  /**
   * Divide two arrays element-wise.
   * The elements from A are divided by B and stored in C.
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   second vector
   * @param bB  index of the second vector
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void div(final double[] a, final int bA, final double[] b,
                         final int bB, final double[] c,
                         final int bC, final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] / b[bB + i];
    }
  }

  /**
   * Divide an array by a scalar.
   * The elements from A are divided by the scalar b and stored in C.
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   scalar
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void div(final double[] a, final int bA, final double b,
                         final double[] c, final int bC, final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] / b;
    }
  }

  /**
   * @param a
   * @param bA
   * @param b
   * @param bB
   * @param c
   * @param bC
   * @param r
   * @param bR
   * @param len
   */
  public static void mulDiv(final double[] a, final int bA, final double[] b,
                            final int bB, final double[] c, final int bC,
                            final double[] r, final int bR, final int len) {
    for (int i = 0; i < len; i++) {
      r[bR + i] = a[bA + i] * (b[bB + i] / c[bC + i]);
    }
  }

  /**
   * The elements of A are powered by B and stored in C.
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   second vector
   * @param bB  index of the second vector
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void pow(final double[] a, final int bA,
                         final double[] b, final int bB,
                         final double[] c, final int bC, final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = Math.pow(a[bA + i], b[bB + i]);
    }
  }

  /**
   * The elements of A are powered by scalar b and stored in C.
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   scalar
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void pow(final double[] a, final int bA, final double b,
                         final double[] c, final int bC, final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = Math.pow(a[bA + i], b);
    }
  }

  /**
   * @param a
   * @param bA
   * @param b
   * @param bB
   * @param len
   * @param p
   * @return
   */
  public static double minkowskiDistance(final double[] a, final int bA,
                                         final double[] b, final int bB,
                                         final int len, final int p) {
    double sum = 0.0;
    for (int i = 0; i < len; i++) {
      sum += Math.pow(Math.abs(a[bA + i] - b[bB + i]), p);
    }
    return Math.pow(sum, 1.0 / p);
  }

  /**
   * @param a
   * @param bA
   * @param b
   * @param bB
   * @param len
   * @return
   */
  public static double euclideanDistance(final double[] a, final int bA,
                                         final double[] b, final int bB,
                                         final int len) {
    double sum = 0.0;
    for (int i = 0; i < len; i++) {
      sum += Math.pow(a[bA + i] - b[bB + i], 2.0);
    }
    return Math.sqrt(sum);
  }

  /**
   * @param a
   * @param bA
   * @param b
   * @param bB
   * @param len
   * @return
   */
  public static double manhattanDistance(final double[] a, final int bA,
                                         final double[] b, final int bB,
                                         final int len) {
    double sum = 0.0;
    for (int i = 0; i < len; i++) {
      sum += Math.abs(a[bA + i] - b[bB + i]);
    }
    return sum;
  }

  /**
   * @param a
   * @param bA
   * @param len
   * @return
   */
  public static double sum(final double[] a, final int bA, final int len) {
    double sum = 0.0;
    for (int i = 0; i < len; i++) {
      sum += a[i];
    }
    return sum;
  }

  /**
   * @param a
   * @param bA
   * @param b
   * @param bB
   * @param len
   * @return
   */
  public static double dotProduct(final double[] a, final int bA,
                                  final double[] b, final int bB,
                                  final int len) {
    double rv = 0.0;
    for (int i = 0; i < len; i++) {
      rv += a[bA + i] * b[bB + i];
    }
    return rv;
  }


  /**
   * Shuffles the content of an array.
   * Based on Fisher–Yates algorithm.
   *
   * @param array an array of type {@link T}
   * @param <T>   type of elements in the array.
   */
  public static <T> void shuffle(final T array[]) {
    if (array.length > 1) {
      for (int i = array.length - 1; i > 1; i--) {
        int j = ThreadLocalRandom.current().nextInt(0, i);
        T t = array[j];
        array[j] = array[i];
        array[i] = t;
      }
    }
  }

  /**
   * Swaps two elements in the array.
   *
   * @param array an array of type {@link T}
   * @param i     index of the first element.
   * @param j     index of the second element.
   * @param <T>   type of elements in the array.
   */
  public static <T> void swap(final T[] array, final int i, final int j) {
    T tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;
  }

  /**
   * Returns the index of the minimium number in the array.
   *
   * @param array an array of doubles.
   * @return the index of the minimium number in the array.
   */
  public static int min(final double array[]) {
    int rv = 0;
    for (int i = 1; i < array.length; i++)
      if (array[i] < array[rv])
        rv = i;
    return rv;
  }

  /**
   * Returns the index of the maximum number in the array.
   *
   * @param array an array of doubles.
   * @return the index of the maximum number in the array.
   */
  public static int max(final double array[]) {
    int rv = 0;
    for (int i = 1; i < array.length; i++)
      if (array[i] > array[rv])
        rv = i;
    return rv;
  }

  /**
   * Returns the index of the minimum and maximum elements in the array.
   *
   * @param array an array of double.
   * @return the index of the minimum and maximum elements in the array.
   */
  public static Pair<MutableInteger, MutableInteger> minMax(final double array[]) {
    int minIdx = 0, maxIdx = 0, start = 1;
    double min = array[0], max = array[0];

    if (array.length % 2 == 0)
      start = 0;

    for (int i = start; i < array.length; i += 2) {
      if (array[i] < array[i + 1]) {
        if (min > array[i]) {
          min = array[i];
          minIdx = i;
        }
        if (max < array[i + 1]) {
          max = array[i + 1];
          maxIdx = i + 1;
        }
      } else {
        if (min > array[i + 1]) {
          min = array[i + 1];
          minIdx = i + 1;
        }
        if (max < array[i]) {
          max = array[i];
          maxIdx = i;
        }
      }
    }

    return new Pair<>(new MutableInteger(minIdx), new MutableInteger(maxIdx));
  }

  /**
   * Binary array fill.
   *
   * @param a
   * @param v
   */
  public static void fill(final double a[], final double v) {
    int len = a.length;

    if (len > 0) {
      a[0] = v;
    }

    for (int i = 1; i < len; i += i) {
      System.arraycopy(a, 0, a, i, ((len - i) < i) ? (len - i) : i);
    }
  }

  /**
   * Returns a double array filled with random values in the range [0, 1).
   *
   * @param n size of the array
   * @return a double array filled with random values in the range [0, 1)
   */
  public static double[] random(final int n) {
    double rv[] = new double[n];

    for(int i = 0; i < n; i++) {
      rv[i] = ThreadLocalRandom.current().nextDouble();
    }

    return rv;
  }
}