package pt.it.av.atnog.utils;

import pt.it.av.atnog.utils.structures.mutableNumber.MutableInteger;
import pt.it.av.atnog.utils.structures.tuple.Pair;

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
  public static Pair<MutableInteger, MutableInteger> minMax(final double array[]) {
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
  public static Pair<MutableInteger, MutableInteger> minMax(final double array[], final int bA,
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
   *
   * @param array
   * @return
   */
  public static int[] rank(double array[]) {
    double arraySorted[] = new double[array.length];

    System.arraycopy(array, 0, arraySorted, 0, array.length);
    Arrays.sort(arraySorted);

    int rv[] = new int[array.length];

    for (int i = 0; i < array.length; i++) {
      int rank = Arrays.binarySearch(arraySorted, array[i]);
      rv[i] = rank;
    }

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
    Pair<MutableInteger, MutableInteger> mm = minMax(a, bA, len);
    double min = a[mm.a.intValue()], max = a[mm.b.intValue()], c = (rh - rl) / (max - min);

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
    Pair<MutableInteger, MutableInteger> mm = minMax(a, bA, len);
    double min = a[mm.a.intValue()], max = a[mm.b.intValue()],
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
}