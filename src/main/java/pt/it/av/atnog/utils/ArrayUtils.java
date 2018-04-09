package pt.it.av.atnog.utils;

import java.util.Arrays;
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

  public static void sqrt(final double[] a, final int bA, final double[] c,
                          final int bC, final int len){
    for (int i = 0; i < len; i++) {
      c[bC + i] = Math.sqrt(a[bA + i]);
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
        swap(array, i, j);
      }
    }
  }

  /**
   * Shuffles the content of an array.
   * Based on Fisher–Yates algorithm.
   *
   * @param array an array of integers
   */
  public static <T> void shuffle(final int array[]) {
    if (array.length > 1) {
      for (int i = array.length - 1; i > 1; i--) {
        int j = ThreadLocalRandom.current().nextInt(0, i);
        int t = array[j];
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
   * @param array an array of doubles
   * @param b start index of the array
   * @param l array's length
   * @return the index of the minimium number in the array.
   */
  public static int min(final double array[], final int b, final int l) {
    int rv = 0;
    for (int i = 1; i < l; i++)
      if (array[i + b] < array[rv + b])
        rv = i;
    return rv + b;
  }

  /**
   * Returns the index of the minimium number in the array.
   *
   * @param array an array of doubles.
   * @return the index of the minimium number in the array.
   */
  public static int min(final double array[]) {
    return min(array, 0, array.length);
  }

  /**
   * Returns the index of the maximum number in the array.
   *
   * @param array an array of doubles
   * @param b     start index of the array
   * @param l     array's length
   * @return the index of the maximum number in the array.
   */
  public static int max(final double array[], final int b, final int l) {
    int rv = 0;
    for (int i = 1; i < l; i++)
      if (array[i + b] > array[rv + b])
        rv = i;
    return rv + b;
  }

  /**
   * Returns the index of the maximum number in the array.
   *
   * @param array an array of doubles
   * @return the index of the maximum number in the array.
   */
  public static int max(final double array[]) {
    return max(array, 0, array.length);
  }

  /**
   * Returns the index of the minimum and maximum elements in the array.
   *
   * @param array an array of double.
   * @return the index of the minimum and maximum elements in the array.
   */
  public static int[] minMax(final double array[]) {
    return minMax(array, 0, array.length);
  }

  /**
   * Returns the index of the minimum and maximum elements in the array.
   *
   * @param array   an array of doubles.
   * @param bA  the index that starts the array values.
   * @param len the lenght of the data.
   * @return the index of the minimum and maximum elements in the array.
   */
  public static int[] minMax(final double array[], final int bA,
                             final int len) {
    int minIdx = bA, maxIdx = bA, start = bA + 1;
    double min = array[bA], max = array[bA];

    if (len % 2 == 0)
      start = bA;

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

    int rv[] = {minIdx, maxIdx};
    return rv;
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

    for (int i = 0; i < n; i++) {
      rv[i] = ThreadLocalRandom.current().nextDouble();
    }

    return rv;
  }

  /**
   *
   * @param a
   * @return
   */
  public static double mean(final double a[]) {
    return mean(a, 0, a.length);
  }

  /**
   *
   * @param a
   * @param b
   * @param l
   * @return
   */
  public static double mean(final double a[], final int b, final int l) {
    double rv = 0.0;

    for (int i = 0; i < l; i++) {
      rv += a[b + i];
    }

    return rv / l;
  }

  /**
   * @param array
   * @return
   */
  public static double isoData(final double array[]) {
    return isoData(array, 0, array.length);
  }

  /**
   * @param array
   * @param b
   * @param l
   * @return
   */
  public static double isoData(final double array[], final int b, final int l) {
    double t = mean(array, b, l), ot = 0;

    if(l > 1) {
      while (t != ot) {
        ot = t;
        double mat = 0.0, mbt = 0.0;
        int cat = 0, cbt = 0;
        for (int i = 0; i < l; i++) {
          if (array[b + i] > t) {
            mat += array[b + i];
            cat++;
          } else {
            mbt += array[b + i];
            cbt++;
          }
        }
        mat /= cat;
        mbt /= cbt;
        t = (mat + mbt) / 2.0;
      }
    }

    return t;
  }

  /**
   * @param array
   * @param p
   * @param n
   */
  public static void replace(final int array[], final int p, final int n) {
    for (int i = 0; i < array.length; i++) {
      if (array[i] == p) {
        array[i] = n;
      }
    }
  }

  /**
   * @param array
   * @param p
   * @param n
   */
  public static void replace(final double array[], final double p, final double n) {
    for (int i = 0; i < array.length; i++) {
      if (array[i] == p) {
        array[i] = n;
      }
    }
  }

  /**
   * @param array
   * @param n
   * @return
   */
  public static int indexOf(final double array[], final double n) {
    int rv = -1;
    for (int i = 0; i < array.length; i++) {
      if (array[i] == n) {
        rv = i;
        break;
      }
    }
    return rv;
  }

  /**
   * @param array
   * @param e
   * @param <T>
   * @return
   */
  public static <T> boolean contains(T array[], T e) {
    boolean rv = false;

    for (int i = 0; i < array.length && !rv; i++) {
      if (array[i].equals(e)) {
        rv = true;
      }
    }

    return rv;
  }

  /**
   * Returns the index of the closest element to x.
   * <p>
   *   It assumes the array is not sorted, as such, it searches the complete array.
   * </p>
   *
   * @param x the original value.
   * @param array and array of doubles.
   * @return the index of the closest element to x.
   */
  public static int findClose(double x, double array[]) {
    return findClose(x,array,0,array.length);
  }

  /**
   * Returns the index of the closest element to x.
   * <p>
   *   It assumes the array is not sorted, as such, it searches the complete array.
   * </p>
   *
   * @param x the original value.
   * @param array and array of doubles.
   * @param bA the index where the data starts.
   * @param l the length of the data.
   * @return the index of the closest element to x.
   */
  public static int findClose(double x, double array[], int bA, int l) {
    int rv = 0;
    double err = Math.abs(array[0+bA] - x);

    for (int i = 1; i < l; i++) {
      double t = Math.abs(array[i+bA] - x);
      if (t < err) {
        rv = i;
        err = t;
      }
    }

    return rv;
  }

  /**
   * Returns the index of the closest element to x.
   * <p>
   *   It assumes the array is sorted, as such, it stops whenever the distance increases.
   * </p>
   *
   * @param x the original value.
   * @param array and array of doubles.
   * @return the index of the closest element to x.
   */
  public static int findCloseSorted(double x, double array[]) {
    return findCloseSorted(x,array,0,array.length);
  }

  /**
   * Returns the index of the closest element to x.
   * <p>
   *   It assumes the array is sorted, as such, it stops whenever the distance increases.
   * </p>
   *
   * @param x the original value.
   * @param array and array of doubles.
   * @param bA the index where the data starts.
   * @param l the length of the data.
   * @return the index of the closest element to x.
   */
  public static int findCloseSorted(double x, double array[], int bA, int l) {
    int rv = 0;
    boolean found = false;
    double err = Math.abs(array[0+bA] - x);

    for (int i = 1; i < l && !found; i++) {
      double t = Math.abs(array[i+bA] - x);
      if (t < err) {
        rv = i;
        err = t;
      } else if(t > err) {
        found = true;
      }
    }

    return rv;
  }

  /**
   * Return the rank of an array of elements.
   * @param array an array of elements.
   * @param ranks an array that will be filled with the ranks.
   */
  public static void rank(final double array[], final int ranks[]) {
    double arraySorted[] = new double[array.length];

    System.arraycopy(array, 0, arraySorted, 0, array.length);
    Arrays.sort(arraySorted);

    for (int i = 0; i < array.length; i++) {
      ranks[i] = Arrays.binarySearch(arraySorted, array[i]);
    }
  }

  /**
   * Return an array with the rank of an array of elements.
   *
   * @param array an array of elements.
   * @return an array with the rank of an array of elements.
   */
  public static int[] rank(final double array[]) {
    int rv[] = new int[array.length];
    rank(array, rv);
    return rv;
  }

  /**
   * @param x
   * @param y
   * @return
   */
  public static double spearman(final double x[], final double y[]) {
    int[] rx = rank(x), ry = rank(y);
    return ArrayUtils.pearson(rx, 0, ry, 0, rx.length);
  }

  /**
   * @param x
   * @param y
   * @return
   */
  public static double pearson(final double x[], final double y[]) {
    return pearson(x, 0, y, 0, x.length);
  }

  /**
   * @param x
   * @param y
   * @param bx
   * @param by
   * @param len
   * @return
   */
  public static double pearson(final double x[], final int bx, final double y[], final int by, final int len) {
    double r = 0.0, mx = 0.0, my = 0.0, sx = 0.0, sy = 0.0;
    int t = 1;
    for (int i = 0; i < len; i++) {
      mx += (x[bx + i] - mx) / t;
      my += (y[by + i] - my) / t;
      ++t;
    }
    for (int i = 0; i < len; i++) {
      sx += Math.pow(x[bx + i] - mx, 2.0);
      sy += Math.pow(y[by + i] - my, 2.0);
    }
    sx = Math.sqrt(sx / (len - 1));
    sy = Math.sqrt(sy / (len - 1));
    for (int i = 0; i < len; i++)
      r += ((x[bx + i] - mx) / sx) * ((y[by + i] - my) / sy);
    return r / (len - 1);
  }

  /**
   * @param x
   * @param y
   * @param bx
   * @param by
   * @param len
   * @return
   */
  public static double pearson(final int x[], final int bx, final int y[], final int by, final int len) {
    double r = 0.0, mx = 0.0, my = 0.0, sx = 0.0, sy = 0.0;
    int t = 1;
    for (int i = 0; i < len; i++) {
      mx += (x[bx + i] - mx) / t;
      my += (y[by + i] - my) / t;
      ++t;
    }
    for (int i = 0; i < len; i++) {
      sx += Math.pow(x[bx + i] - mx, 2.0);
      sy += Math.pow(y[by + i] - my, 2.0);
    }
    sx = Math.sqrt(sx / (len - 1));
    sy = Math.sqrt(sy / (len - 1));
    for (int i = 0; i < len; i++)
      r += ((x[bx + i] - mx) / sx) * ((y[by + i] - my) / sy);
    return r / (len - 1);
  }

  /**
   * Feature scaling.
   * The simplest method is rescaling the range of features to scale the range in [rl, rh].
   * https://stats.stackexchange.com/questions/178626/how-to-normalize-data-between-1-and-1
   *
   * @param a   the original data array (input).
   * @param bA  the index where the data starts.
   * @param r   the array that will store the results (output).
   * @param bR  the index where the data can be stored.
   * @param len the number of elements to be processed.
   * @param rl  the lower limit of the range.
   * @param rh  the higher limit of the range.
   */
  public static void rescaling(final double[] a, final int bA, final double[] r, final int bR,
                               final int len, final double rl, final double rh) {
    int mm[] = minMax(a, bA, len);
    double min = a[mm[0]], max = a[mm[1]], c = (rh - rl) / (max - min);

    for (int i = 0; i < len; i++) {
      r[bR + i] = (c * (a[bA + i] - min)) + rl;
    }
  }

  /**
   * Feature scaling.
   * The simplest method is rescaling the range of features to scale the range in [0, 1].
   *
   * @param a   the original data array (input).
   * @param bA  the index where the data starts.
   * @param r   the array that will store the results (output).
   * @param bR  the index where the data can be stored.
   * @param len the number of elements to be processed.
   */
  public static void rescaling(final double[] a, final int bA, final double[] r, final int bR,
                               final int len) {
    rescaling(a, bA, r, bR, len, 0, 1);
  }

  /**
   * Feature scaling.
   * The simplest method is rescaling the range of features to scale the range in [0, 1].
   *
   * @param a the original data array (input).
   * @param r the array that will store the results (output).
   */
  public static void rescaling(final double[] a, final double[] r) {
    rescaling(a, 0, r, 0, a.length, 0, 1);
  }

  /**
   * Feature scaling.
   * Mean normalization.
   *
   * @param a   the original data array (input).
   * @param bA  the index where the data starts.
   * @param r   the array that will store the results (output).
   * @param bR  the index where the data can be stored.
   * @param len the number of elements to be processed.
   */
  public static void meanNormalization(final double[] a, final int bA, final double[] r,
                                       final int bR, final int len) {
    int mm[] = minMax(a, bA, len);
    double min = a[mm[0]], max = a[mm[1]],
        d = max - min, mean = mean(a, bA, len);

    for (int i = 0; i < len; i++) {
      r[bR + i] = (a[bA + i] - mean) / d;
    }
  }

  /**
   * Feature scaling.
   * Standardization (or Z-score normalization), features will be rescaled so that they’ll have the
   * properties of a standard normal distribution with u = 0 and std = 1
   *
   * @param a   the original data array (input).
   * @param bA  the index where the data starts.
   * @param r   the array that will store the results (output).
   * @param bR  the index where the data can be stored.
   * @param len the number of elements to be processed.
   */
  public static void standardization(final double[] a, final int bA, final double[] r,
                                     final int bR, final int len) {
    double mean = mean(a, bA, len), std = std(a, bA, len);

    for (int i = 0; i < len; i++) {
      r[bR + i] = (a[bA + i] - mean) / std;
    }
  }

  /**
   * Feature scaling.
   * Scaling to unit length.
   * Another option that is widely used in machine-learning is to scale the components of a feature
   * vector such that the complete vector has length one.
   * This usually means dividing each component by the Euclidean length of the vector.
   *
   * @param a   the original data array (input).
   * @param bA  the index where the data starts.
   * @param r   the array that will store the results (output).
   * @param bR  the index where the data can be stored.
   * @param len the number of elements to be processed.
   */
  public static void scalingUnitLength(final double[] a, final int bA, final double[] r,
                                       final int bR, final int len) {
    double norm = norm(a, bA, len, 2);
    for (int i = 0; i < len; i++) {
      r[bR + i] = a[bA + i] / norm;
    }
  }

  /**
   * Returns the norm p from a array of data.
   *
   * @param a
   * @param bA
   * @param len
   * @param p
   * @return
   */
  public static double norm(final double a[], final int bA, final int len, final int p) {
    double norm = 0.0;
    for (int i = 0; i < len; i++)
      norm = MathUtils.norm(norm, a[bA + i], p);
    return norm;
  }

  /**
   * Returns the standard deviantion from the data.
   *
   * @param a
   * @param bA
   * @param len
   * @return
   */
  public static double std(final double a[], final int bA, final int len) {
    return Math.sqrt(var(a, bA, len));
  }

  /**
   * Returns the variance from the data.
   *
   * @param a
   * @param bA
   * @param len
   * @return
   */
  public static double var(final double a[], final int bA, final int len) {
    double mean = mean(a, bA, len), v = 0.0;
    for (int i = 0; i < len; i++)
      v += Math.pow(a[bA + i] - mean, 2.0);
    return v / (len - 1);
  }

  /**
   * Moving average.
   * Compute the K centered moving average of a row vector.
   * When there are fewer than K elements in the window at the endpoints, take the average over the
   * elements that are available.
   *
   * @param a   the original data array (input).
   * @param bA  the index where the data starts.
   * @param r   the array that will store the results (output).
   * @param bR  the index where the data can be stored.
   * @param len the number of elements to be processed.
   * @param k   moving average windows size.
   */
  public static void mm(final double a[], final int bA, final double r[], final int bR, final int len, final int k) {
    // Main loop
    for (int i = k; i < len - k; i++) {
      r[i + bR] = mean(a, i + bA - k, k * 2 + 1);
    }
    // Left Extremity
    for (int i = 0; i < k; i++) {
      r[i + bR] = mean(a, bA, k + 1 + i);
    }
    // Right Extremity
    for (int i = len - k; i < len; i++) {
      r[i + bR] = mean(a, bA + i - k, len - i + k);
    }
  }

  /**
   * Moving average.
   * Compute the K centered moving average of a row vector.
   * When there are fewer than K elements in the window at the endpoints, take the average over the
   * elements that are available.
   *
   * @param a the original data array (input).
   * @param r the array that will store the results (output).
   * @param k moving average windows size.
   */
  public static void mm(final double a[], final double r[], final int k) {
    mm(a, 0, r, 0, a.length, k);
  }

  /**
   * Logarithmic regression.
   * <p>
   * It computes the logarithmic equation (\(y = a\lnb{x} + b\) that minimizes the following cost:
   * \(Q=\sum_{i=0}^{n}(y_i-(a \times u_i  + b))^2\)
   * Where \(v = ln(x)\).
   * </p>
   *
   * @param x
   * @param y
   * @return
   */
  public static double[] lnr(final double x[], final double y[]) {
    return lnr(x, y, 0, 0, x.length);
  }

  /**
   * Logarithmic regression.
   * <p>
   * It computes the logarithmic equation (\(y = a\lnb{x} + b\) that minimizes the following cost:
   * \(Q=\sum_{i=0}^{n}(y_i-(a \times u_i  + b))^2\)
   * Where \(v = ln(x)\).
   * </p>
   *
   * @param x
   * @param y
   * @param bX
   * @param bY
   * @param l
   * @return
   */
  public static double[] lnr(final double x[], final double y[], final int bX, final int bY, final int l) {
    double sx = 0.0, sy = 0.0, xy = 0.0, x2 = 0.0;
    double rv[] = {0.0, 0.0, 0.0};

    for (int i = 0; i < l; i++) {
      double u = Math.log(x[i + bY]);
      sx += u;
      sy += y[i + bY];
      xy += u * y[i + bY];
      x2 += Math.pow(u, 2);
    }

    double d = l * x2 - Math.pow(sx, 2.0),
        m = (l * xy - sx * sy) / d,
        b = (sy * x2 - sx * xy) / d,
        my = sy / l, sse = 0.0, sst = 0.0;

    for (int i = 0; i < l; i++) {
      double u = Math.log(x[i + bY]), f = m * u + b;
      sse += Math.pow(y[i + bY] - f, 2);
      sst += Math.pow(y[i + bY] - my, 2);
    }

    rv[0] = m;
    rv[1] = b;
    rv[2] = 1 - (sse / sst);

    return rv;
  }

  /**
   * Exponential regression.
   * <p>
   * It computes the exponential equation (\(y = a \times e^{xb}\) that minimizes the following cost:
   * \(Q=\sum_{i=0}^{n}(v_i-(b \times x_i + k))^2\)
   * Where \(v = ln(y)\) and \(k = ln(a)\).
   * </p>
   *
   * @param x
   * @param y
   * @return
   */
  public static double[] er(final double x[], final double y[]) {
    return er(x, y, 0, 0, x.length);
  }

  /**
   * Exponential regression.
   * <p>
   * It computes the exponential equation (\(y = a \times e^{xb}\) that minimizes the following cost:
   * \(Q=\sum_{i=0}^{n}(v_i-(b \times x_i + k))^2\)
   * Where \(v = ln(y)\) and \(k = ln(a)\).
   * </p>
   *
   * @param x
   * @param y
   * @param bX
   * @param bY
   * @param l
   * @return
   */
  public static double[] er(final double x[], final double y[], final int bX, final int bY, final int l) {
    double sx = 0.0, sy = 0.0, xy = 0.0, x2 = 0.0;
    double rv[] = {0.0, 0.0, 0.0};

    for (int i = 0; i < l; i++) {
      double v = Math.log(y[i + bY]);
      sx += x[i + bX];
      sy += v;
      xy += x[i + bX] * v;
      x2 += Math.pow(x[i + bX], 2);
    }

    double d = l * x2 - Math.pow(sx, 2.0),
        m = (l * xy - sx * sy) / d,
        b = (sy * x2 - sx * xy) / d,
        my = sy / l, sse = 0.0, sst = 0.0;

    for (int i = 0; i < l; i++) {
      double v = Math.log(y[i + bY]), f = m * x[i + bX] + b;
      sse += Math.pow(v - f, 2);
      sst += Math.pow(v - my, 2);
    }

    rv[0] = Math.exp(b);
    rv[1] = m;
    rv[2] = 1 - (sse / sst);

    return rv;
  }

  /**
   * Power regression.
   * <p>
   * It computes the power equation (\(y = a \times x^b\) that minimizes the following cost:
   * \(Q=\sum_{i=0}^{n}(v_i-(b \times u_i + k))^2\)
   * Where \(v = ln(y)\), \(u = ln(x)\) and \(k = ln(a)\).
   * </p>
   *
   * @param x
   * @param y
   * @return
   */
  public static double[] pr(final double x[], final double y[]) {
    return pr(x, y, 0, 0, x.length);
  }

  /**
   * Power regression.
   * <p>
   *   It computes the power equation (\(y = a \times x^b\) that minimizes the following cost:
   *   \(Q=\sum_{i=0}^{n}(v_i-(b \times u_i + k))^2\)
   *   Where \(v = ln(y)\), \(u = ln(x)\) and \(k = ln(a)\).
   * </p>
   *
   * @param x
   * @param y
   * @param bX
   * @param bY
   * @param l
   * @return
   */
  public static double[] pr(final double x[], final double y[], final int bX, final int bY, final int l) {
    double sx = 0.0, sy = 0.0, xy = 0.0, x2 = 0.0;
    double rv[] = {0.0, 0.0, 0.0};

    for (int i = 0; i < l; i++) {
      double u = Math.log(x[i + bX]),
          v = Math.log(y[i + bY]);
      //System.out.print("("+u+"; "+v+") ");
      sx += u;
      sy += v;
      xy += u * v;
      x2 += Math.pow(u, 2);
    }
    //System.out.println();
    //System.out.println(sx+" "+sy+" "+xy+" "+x2);

    double d = l * x2 - Math.pow(sx, 2.0),
        m = (l * xy - sx * sy) / d,
        b = (sy * x2 - sx * xy) / d,
        my = sy / l, sse = 0.0, sst = 0.0;

    for (int i = 0; i < l; i++) {
      double u = Math.log(x[i + bX]),
          v = Math.log(y[i + bY]),
          f = m * u + b;
      sse += Math.pow(v - f, 2);
      sst += Math.pow(v - my, 2);
    }

    rv[0] = Math.exp(b);
    rv[1] = m;
    rv[2] = 1 - (sse / sst);

    return rv;
  }

  /**
   * Linear regression.
   * <p>
   *   It computes the linear equation (\(y = x \times m + b\)) that minimizes the following cost:
   *   \(Q=\sum_{i=0}^{n}(y_i-(x_i \times m + b))^2\)
   * </p>
   *
   * @param x
   * @param y
   * @param bX
   * @param bY
   * @param l
   * @return
   */
  public static double[] lr(final double x[], final double y[], final int bX, final int bY, final int l) {
    double sx = 0.0, sy = 0.0, xy = 0.0, x2 = 0.0;
    double rv[] = {0, 0, 0};

    for (int i = 0; i < l; i++) {
      sx += x[i + bX];
      x2 += Math.pow(x[i + bX], 2);
      xy += x[i + bX] * y[i + bY];
      sy += y[i + bY];
    }

    double d = l * x2 - Math.pow(sx, 2.0),
        m = (l * xy - sx * sy) / d,
        b = (sy * x2 - sx * xy) / d,
        my = sy / l, sse = 0.0, sst = 0.0;

    for (int i = 0; i < l; i++) {
      double f = m * x[i + bX] + b;
      sse += Math.pow(y[i + bY] - f, 2);
      sst += Math.pow(y[i + bY] - my, 2);
    }

    rv[0] = m;
    rv[1] = b;
    rv[2] = 1 - (sse / sst);

    return rv;
  }

  /**
   * Coefficient of determination (\(R^2\)).
   *
   * @param y
   * @param f
   * @return
   */
  public static double r2(final double y[], final double f[]) {
    return r2(y, f, 0, 0, y.length);
  }

  /**
   * Coefficient of determination (\(R^2\)).
   *
   * @param y
   * @param f
   * @param bY
   * @param bF
   * @param l
   * @return
   */
  public static double r2(final double y[], final double f[], final int bY, final int bF, final int l) {
    double sst = 0.0, sse = 0.0;

    double my = ArrayUtils.mean(y, bY, l);
    for (int i = 0; i < l; i++) {
      sst += Math.pow((y[bY + i] - my), 2);
      sse += Math.pow((y[bY + i] - f[bF + i]), 2);
    }
    return 1.0 - (sse / sst);
  }

  /**
   * Returns Mean-Squared Error.
   *
   * @param x
   * @param y
   * @param bX
   * @param bY
   * @param l
   * @return
   */
  public static double mse(final double x[], final double y[], final int bX, final int bY, final int l) {
    double rv = 0.0;

    for (int i = 0; i < l; i++) {
      rv = Math.pow(x[bX + i] - y[bY + i], 2.0);
    }

    return rv / l;
  }

  /**
   * Returns Root Mean Squared Error.
   *
   * @param x
   * @param y
   * @param bX
   * @param bY
   * @param l
   * @return
   */
  public static double rmse(final double x[], final double y[], final int bX, final int bY, final int l) {
    return Math.sqrt(mse(x, y, bX, bY, l));
  }

  /**
   * @param x
   * @param y
   * @param rv
   * @param bX
   * @param bY
   * @param bR
   * @param l
   */
  public static void cfd(final double[] x, final double[] y, final double[] rv,
                         final int bX, final int bY, final int bR, final int l) {
    for (int i = 1; i < l - 1; i++) {
      rv[bR + i - 1] = (y[bY + i + 1] - y[bY + i - 1]) / ((x[bX + i] - x[bX + i - 1]) + (x[bX + i + 1] - x[bX + i]));
    }
  }

  /**
   * @param x
   * @param y
   * @param bX
   * @param bY
   * @param l
   * @return
   */
  public static double[] cfd(final double[] x, final double[] y, final int bX,
                             final int bY, final int l) {
    double rv[] = new double[l - 2];
    cfd(x, y, rv, bX, bY, 0, l);
    return rv;
  }

  /**
   * @param x
   * @param y
   * @return
   */
  public static double[] cfd(final double[] x, final double[] y) {
    double rv[] = new double[y.length - 2];
    cfd(x, y, rv, 0, 0, 0, y.length);
    return rv;
  }

  /**
   * @param x
   * @param y
   * @param rv
   * @param bX
   * @param bY
   * @param bR
   * @param l
   */
  public static void csd(final double[] x, final double[] y, final double[] rv,
                         final int bX, final int bY, final int bR, final int l) {
    for (int i = 1; i < l - 1; i++) {
      rv[bR + i - 1] = (y[bY + i + 1] - 2 * y[bY + i] + y[bY + i]) / ((x[bX + i] - x[bX + i - 1]) * (x[bX + i + 1] - x[bX + i]));
    }
  }

  /**
   * @param x
   * @param y
   * @param bX
   * @param bY
   * @param l
   * @return
   */
  public static double[] csd(final double[] x, final double[] y,
                             final int bX, final int bY, final int l) {
    double rv[] = new double[l - 2];
    csd(x, y, rv, bX, bY, 0, l);
    return rv;
  }

  /**
   * @param x
   * @param y
   * @return
   */
  public static double[] csd(final double[] x, final double[] y) {
    double rv[] = new double[y.length - 2];
    csd(x, y, rv, 0, 0, 0, y.length);
    return rv;
  }

  /**
   * Return an array (out) with the same content that the input (in) array, but in reverse order.
   * <p>
   * This method implements a sequential method to reverse the input array.
   * If the array is large enough, cache misses can degrade the performance.
   * </p>
   *
   * @param in  the input array.
   * @param out the output array.
   * @param bI  the index of the first element of the input array.
   * @param bO  the index of the first element of the output array.
   * @param l   the number of elements to be reversed.
   */
  public static void reverse(final double[] in, final int bI, final double[] out, final int bO, final int l) {
    for (int i = 0; i < l; i++) {
      out[bO + i] = in[bI + l - (i + 1)];
    }
  }

  /**
   * Return an array (out) with the same content that the input (in) array, but in reverse order.
   * <p>
   * This method implements a sequential method to reverse the input array.
   * If the array is large enough, cache misses can degrade the performance.
   * </p>
   *
   * @param in the input array.
   * @return an array (out) with the same content that the input (in) array, but in reverse order.
   */
  public static double[] reverse(final double[] in) {
    double out[] = new double[in.length];
    reverse(in, 0, out, 0, in.length);
    return out;
  }

  /**
   * @param a
   * @param bIdx
   * @param len
   */
  public static void random(final double a[], final int bIdx, final int len) {
    for (int i = 0; i < len; i++) {
      a[i + bIdx] = Math.random();
    }
  }
}