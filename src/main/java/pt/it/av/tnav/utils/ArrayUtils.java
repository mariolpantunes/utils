package pt.it.av.tnav.utils;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Operations over arrays of doubles.
 * <p>
 * These operations are common to {@link Matrix} and {@link Vector} classes.
 * Ideally all the high-level functionality from the previous mentioned classes
 * should depend on the methods develoed here. The code here must be lean in
 * order to ease the automatic vectorization of the code, if speed becomes an
 * issue, this library can be replaced with JNI compiled code.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 * @see <a href=
 *      "https://docs.oracle.com/en/java/javase/13/docs/specs/jni/intro.html">JNI</a>
 */
public final class ArrayUtils {
  /**
   * Utility class, lets make the constructor private.
   */
  private ArrayUtils() {
  }

  /**
   * Sum two arrays element-wise.
   * <p>
   * The elements from A are added with B and stored in C, acording to the
   * following expression: $$c = \sum_{i = 0}^{len} A_i + B_i $$
   * </p>
   *
   * @param a   first array
   * @param bA  index of the first array
   * @param b   second array
   * @param bB  index of the second array
   * @param c   resulting array
   * @param bC  index of the resulting array
   * @param len array's len
   */
  public static void add(final double[] a, final int bA, final double[] b, final int bB, final double[] c, final int bC,
      final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] + b[bB + i];
    }
  }

  /**
   * Sum a scalar with an array.
   * <p>
   * The elements from A are added with B and stored in C, acording to the
   * following expression: $$c = \sum_{i = 0}^{len} A_i + B_i $$
   * </p>
   *
   * @param a   first array
   * @param bA  index of the first vector
   * @param b   scalar
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void add(final double[] a, final int bA, final double b, final double[] c, final int bC,
      final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] + b;
    }
  }

  /**
   * Substratc two arrays element-wise. The elements from B are subtracted from A
   * and stored in C.
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   second vector
   * @param bB  index of the second vector
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void sub(final double[] a, final int bA, final double[] b, final int bB, final double[] c, final int bC,
      final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] - b[bB + i];
    }
  }

  /**
   * Substratc a scalar from an array. The scalar B are subtracted from A and
   * stored in C.
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   scalar
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void sub(final double[] a, final int bA, final double b, final double[] c, final int bC,
      final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] - b;
    }
  }

  /**
   * Weighted
   * 
   * @param a
   * @param bA
   * @param b
   * @param bB
   * @param c
   * @param bC
   * @param w0
   * @param w1
   * @param len
   */
  public static void wsub(final double[] a, final int bA, final double[] b, final int bB, final double[] c,
      final int bC, final double w0, final double w1, final int len) {
    for (int i = 0; i < len; i++) {
      double v1 = (w0 * a[bA + i]);
      double v2 = (w1 * b[bB + i]);
      c[bC + i] = v1 - v2;
    }
  }

  /**
   * Multiply two arrays element-wise. The elements from A are multiplied with B
   * and stored in C.
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   second vector
   * @param bB  index of the second vector
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void mul(final double[] a, final int bA, final double[] b, final int bB, final double[] c, final int bC,
      final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] * b[bB + i];
    }
  }

  /**
   * Multiply a scalar with an array. The scalar B are subtracted from A and
   * stored in C.
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   scalar
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void mul(final double[] a, final int bA, final double b, final double[] c, final int bC,
      final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] * b;
    }
  }

  /**
   * Divide two arrays element-wise. The elements from A are divided by B and
   * stored in C.
   *
   * @param a   first vector
   * @param bA  index of the first vector
   * @param b   second vector
   * @param bB  index of the second vector
   * @param c   resulting vector
   * @param bC  index of the resulting vector
   * @param len array's len
   */
  public static void div(final double[] a, final int bA, final double[] b, final int bB, final double[] c, final int bC,
      final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] / b[bB + i];
    }
  }

  /**
   * Divide an array by a scalar. The elements from A are divided by the scalar b
   * and stored in C.
   *
   * @param a   first array
   * @param bA  index of the first array
   * @param b   scalar
   * @param c   resulting array
   * @param bC  index of the resulting vector
   * @param len array's length
   */
  public static void div(final double[] a, final int bA, final double b, final double[] c, final int bC,
      final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = a[bA + i] / b;
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
  public static void pow(final double[] a, final int bA, final double[] b, final int bB, final double[] c, final int bC,
      final int len) {
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
   * @param len array's length
   */
  public static void pow(final double[] a, final int bA, final double b, final double[] c, final int bC,
      final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = Math.pow(a[bA + i], b);
    }
  }

  public static void sqrt(final double[] a, final int bA, final double[] c, final int bC, final int len) {
    for (int i = 0; i < len; i++) {
      c[bC + i] = Math.sqrt(a[bA + i]);
    }
  }

  /**
   * Returns the Minkowski distance between two arrays: \(D(a,b) = \left(
   * \sum_{i=0}^{len}|a_{i+bA}-b_{i+bB}|^p \right) ^\frac{1}{p}\)
   * <p>
   * The Minkowski distance is a metric in a normed vector space which can be
   * considered as a generalization of both the Euclidean distance and the
   * Manhattan distance.
   * </p>
   *
   * @param a   the first array
   * @param bA  index of the first array
   * @param b   the second array
   * @param bB  index of the second array
   * @param len array's length
   * @param p   order (p = 0 reprenset infinity)
   * @return the Minkowski distance between two arrays
   */
  public static double minkowskiDistance(final double[] a, final int bA, final double[] b, final int bB, final int len,
      final int p) {
    double rv = 0.0, diff[] = new double[len], max = 0.0;

    max = Math.abs(a[bA] - b[bB]);
    diff[0] = max;
    for (int i = 1; i < len; i++) {
      diff[i] = Math.abs(a[i + bA] - b[i + bB]);
      if (diff[i] > max) {
        max = diff[i];
      }
    }

    if (p == 0) {
      rv = max;
    } else if (p == 1) {
      rv = ArrayUtils.sum(diff, 0, diff.length);
    } else {
      rv = (max > 0) ? norm2(diff, p, max) : 0.0;
    }

    return rv;
  }

  /**
   * Returns the Minkowski distance between two arrays: \(D(a,b) = \left(
   * \sum_{i=0}^{len}|a_{i+bA}-b_{i+bB}|^p \right) ^\frac{1}{p}\)
   * <p>
   * The Minkowski distance is a metric in a normed vector space which can be
   * considered as a generalization of both the Euclidean distance and the
   * Manhattan distance.
   * </p>
   *
   * @param a   the first array
   * @param bA  begin index of the first array
   * @param b   the second array
   * @param bB  begin index of the second array
   * @param len array's length
   * @param m   mask array
   * @param bM  begin index of the mask array
   * @param p   order (p = 0 reprenset infinity)
   * @return the Minkowski distance between two arrays
   */
  public static double minkowskiDistance(final double[] a, final int bA, final double[] b, final int bB,
      final double[] m, final int bM, final int len, final int p) {
    double rv = 0.0, diff[] = new double[len], max = 0.0;

    max = Math.abs(a[bA] - b[bB]);
    diff[0] = max;
    for (int i = 1; i < len; i++) {
      if (m[i + bM] != 0) {
        diff[i] = Math.abs(a[i + bA] - b[i + bB]);
        if (diff[i] > max) {
          max = diff[i];
        }
      }
    }

    if (p == 0) {
      rv = max;
    } else if (p == 1) {
      rv = ArrayUtils.sum(diff, 0, diff.length);
    } else {
      rv = (max > 0) ? norm2(diff, p, max) : 0.0;
    }

    return rv;
  }

  /**
   * Returns the Minkowski distance between two arrays: \(D(a,b) = \left(
   * \sum_{i=0}^{len}|a_{i+bA}-b_{i+bB}|^p \right) ^\frac{1}{p}\)
   * <p>
   * The Minkowski distance is a metric in a normed vector space which can be
   * considered as a generalization of both the Euclidean distance and the
   * Manhattan distance.
   * </p>
   *
   * @param a the first array
   * @param b the second array
   * @param p order (p = 0 reprenset infinity)
   * @return the Minkowski distance between two arrays
   */
  public static double minkowskiDistance(final double[] a, final double[] b, final int p) {
    return minkowskiDistance(a, 0, b, 0, a.length, p);
  }

  /**
   * @param a
   * @param bA
   * @param b
   * @param bB
   * @param len
   * @return
   */
  public static double euclideanDistance(final double[] a, final int bA, final double[] b, final int bB,
      final int len) {
    return minkowskiDistance(a, bA, b, bB, len, 2);
  }

  /**
   * 
   * @param a
   * @param bA
   * @param b
   * @param bB
   * @param m   Mask
   * @param bM  begin of mask array
   * @param len
   * @return
   */
  public static double euclideanDistance(final double[] a, final int bA, final double[] b, final int bB,
      final double[] m, final int bM, final int len) {
    return minkowskiDistance(a, bA, b, bB, m, bM, len, 2);
  }

  /**
   * @param a
   * @param b
   * @return
   */
  public static double euclideanDistance(final double[] a, final double[] b) {
    return euclideanDistance(a, 0, b, 0, a.length);
  }

  public static double euclideanDistance(final double[] a, final double[] b, final double[] m) {
    return euclideanDistance(a, 0, b, 0, m, 0, a.length);
  }

  /**
   * @param a
   * @param bA
   * @param b
   * @param bB
   * @param len
   * @return
   */
  public static double manhattanDistance(final double[] a, final int bA, final double[] b, final int bB,
      final int len) {
    return minkowskiDistance(a, bA, b, bB, len, 1);
  }

  /**
   * Returns the Manhattan distance between two arrays.
   * 
   * @param a the first array
   * @param b the second array
   * @return the Manhattan distance between two arrays
   */
  public static double manhattanDistance(final double[] a, final double[] b) {
    return minkowskiDistance(a, 0, b, 0, a.length, 1);
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
  public static double dotProduct(final double[] a, final int bA, final double[] b, final int bB, final int len) {
    double rv = 0.0;
    for (int i = 0; i < len; i++) {
      rv += a[bA + i] * b[bB + i];
    }
    return rv;
  }

  /**
   * Shuffles the content of an array. Based on Fisher–Yates algorithm.
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
   * Shuffles the content of an array. Based on Fisher–Yates algorithm.
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
   * Swaps two elements in the array.
   *
   * @param array an array of doubles.
   * @param i     index of the first element.
   * @param j     index of the second element.
   */
  public static void swap(final double[] array, final int i, final int j) {
    double tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;
  }

  /**
   * Returns the index of the minimium number in the array.
   *
   * @param array an array of doubles
   * @param b     start index of the array
   * @param l     array's length
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
   * Returns the index of the number with higher value in the array.
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
   * Returns the index of the number with higher value in the array.
   *
   * @param array an array of doubles
   * @return the index of the maximum number in the array.
   */
  public static int max(final double array[]) {
    return max(array, 0, array.length);
  }

  /**
   * Returns the index of the number with higher value (absolute) in the array.
   *
   * @param array an array of doubles
   * @param b     start index of the array
   * @param l     array's length
   * @return the index of the maximum number in the array.
   */
  public static int maxAbs(final double array[], final int b, final int l) {
    int rv = 0;
    for (int i = 1; i < l; i++)
      if (Math.abs(array[i + b]) > Math.abs(array[rv + b]))
        rv = i;
    return rv + b;
  }

  /**
   * Returns the index of the number with higher value (absolute) in the array.
   *
   * @param array an array of doubles
   * @return the index of the maximum number in the array.
   */
  public static int maxAbs(final double array[]) {
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
   * @param array an array of doubles.
   * @param bA    the index that starts the array values.
   * @param len   the lenght of the data.
   * @return the index of the minimum and maximum elements in the array.
   */
  public static int[] minMax(final double array[], final int bA, final int len) {
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

    int rv[] = { minIdx, maxIdx };
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

  public static double[] gaussian(final int n, final double mean, final double std) {
    double rv[] = new double[n];

    for (int i = 0; i < n; i++) {
      rv[i] = MathUtils.gaussian(mean, std);
    }

    return rv;
  }

  /**
   * Returns a double array filled with random values in the range [min, max).
   *
   * @param n   size of the array
   * @param min
   * @param max
   * @return a double array filled with random values in the range [min, max)
   */
  public static double[] random(final int n, final double min, final double max) {
    double rv[] = new double[n];

    for (int i = 0; i < n; i++) {
      rv[i] = ThreadLocalRandom.current().nextDouble(min, max);
    }

    return rv;
  }

  /**
   * @param a
   * @return
   */
  public static double mean(final double a[]) {
    return mean(a, 0, a.length);
  }

  /**
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
   * Returns the ideal threshold to divide the values into two classes.
   * <p>
   * Iterative procedure based on the isodata algorithm of:
   * <ul>
   * <li>Ridler, TW & Calvard, S (1978), "Picture thresholding using an iterative
   * selection method", IEEE Transactions on Systems, Man and Cybernetics 8:
   * 630-632
   * </ul>
   * </p>
   * <p>
   * The procedure divides the values into high and low categories by taking an
   * initial threshold (usually the mean). Then the averages of the values at or
   * below the threshold and above are computed. The averages of those two values
   * are computed, the threshold is incremented and the process is repeated until
   * the threshold is larger than the composite average. That is: $$threshold =
   * \frac{(average\ low + average\ high)}{2}$$
   * </p>
   *
   * @param array array an array of values (scores).
   * @return The ideal threshold to divide the values into two classes.
   */
  public static double isoData(final double array[]) {
    return isoData(array, 0, array.length);
  }

  /**
   * Returns the ideal threshold to divide the values into two classes.
   * <p>
   * Iterative procedure based on the isodata algorithm of:
   * <ul>
   * <li>Ridler, TW & Calvard, S (1978), "Picture thresholding using an iterative
   * selection method", IEEE Transactions on Systems, Man and Cybernetics 8:
   * 630-632
   * </ul>
   * </p>
   * <p>
   * The procedure divides the values into high and low categories by taking an
   * initial threshold (usually the mean). Then the averages of the values at or
   * below the threshold and above are computed. The averages of those two values
   * are computed, the threshold is incremented and the process is repeated until
   * the threshold is larger than the composite average. That is: $$threshold =
   * \frac{(average\ low + average\ high)}{2}$$
   * </p>
   *
   * @param array an array of values (scores).
   * @param bIdx  the index where the values start.
   * @param len   the lenght of array of values.
   * @return The ideal threshold to divide the values into two classes.
   */
  public static double isoData(final double array[], final int bIdx, final int len) {
    double t = array[bIdx];

    if (len > 1) {
      t = mean(array, bIdx, len);
      if (len > 2) {
        double pt = 0;
        while (t != pt) {
          pt = t;
          double mat = 0.0, mbt = 0.0;
          int cat = 0, cbt = 0;
          for (int i = 0; i < len; i++) {
            if (array[bIdx + i] > t) {
              mat += array[bIdx + i];
              cat++;
            } else {
              mbt += array[bIdx + i];
              cbt++;
            }
          }

          mat = (cat > 0) ? mat / cat : pt;
          mbt = (cbt > 0) ? mbt / cbt : pt;
          t = (mat + mbt) / 2.0;
        }
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
   * @param n
   * @return
   */
  public static <T> int indexOf(final T array[], final T e) {
    int rv = -1;
    for (int i = 0; i < array.length; i++) {
      if (array[i].equals(e)) {
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
  public static <T> boolean contains(final T array[], final T e) {
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
   * It assumes the array is not sorted, as such, it searches the complete array.
   * </p>
   *
   * @param x     the original value.
   * @param array and array of doubles.
   * @return the index of the closest element to x.
   */
  public static int findClose(double x, double array[]) {
    return findClose(x, array, 0, array.length);
  }

  /**
   * Returns the index of the closest element to x.
   * <p>
   * It assumes the array is not sorted, as such, it searches the complete array.
   * </p>
   *
   * @param x     the original value.
   * @param array and array of doubles.
   * @param bA    the index where the data starts.
   * @param l     the length of the data.
   * @return the index of the closest element to x.
   */
  public static int findClose(double x, double array[], int bA, int l) {
    int rv = 0;
    double err = Math.abs(array[0 + bA] - x);

    for (int i = 1; i < l; i++) {
      double t = Math.abs(array[i + bA] - x);
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
   * It assumes the array is sorted, as such, it stops whenever the distance
   * increases.
   * </p>
   *
   * @param x     the original value.
   * @param array and array of doubles.
   * @return the index of the closest element to x.
   */
  public static int findCloseSorted(double x, double array[]) {
    return findCloseSorted(x, array, 0, array.length);
  }

  /**
   * Returns the index of the closest element to x.
   * <p>
   * It assumes the array is sorted, as such, it stops whenever the distance
   * increases.
   * </p>
   *
   * @param x     the original value.
   * @param array and array of doubles.
   * @param bA    the index where the data starts.
   * @param l     the length of the data.
   * @return the index of the closest element to x.
   */
  public static int findCloseSorted(double x, double array[], int bA, int l) {
    int rv = 0;
    boolean found = false;
    double err = Math.abs(array[0 + bA] - x);

    for (int i = 1; i < l && !found; i++) {
      double t = Math.abs(array[i + bA] - x);
      if (t < err) {
        rv = i;
        err = t;
      } else if (t > err) {
        found = true;
      }
    }

    return rv;
  }

  /**
   * Return the rank of an array of elements.
   *
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
   * Feature scaling. The simplest method is rescaling the range of features to
   * scale the range in [rl, rh].
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
  public static void rescaling(final double[] a, final int bA, final double[] r, final int bR, final int len,
      final double rl, final double rh) {
    int mm[] = minMax(a, bA, len);
    double min = a[mm[0]], max = a[mm[1]], c = (rh - rl) / (max - min);

    for (int i = 0; i < len; i++) {
      r[bR + i] = (c * (a[bA + i] - min)) + rl;
    }
  }

  /**
   * Feature scaling.
   * <p>
   * The simplest method is rescaling the range of features to scale the range in
   * [0, 1].
   * </p>
   *
   * @param a   the original data array (input).
   * @param bA  the index where the data starts.
   * @param r   the array that will store the results (output).
   * @param bR  the index where the data can be stored.
   * @param len the number of elements to be processed.
   */
  public static void rescaling(final double[] a, final int bA, final double[] r, final int bR, final int len) {
    rescaling(a, bA, r, bR, len, 0, 1);
  }

  /**
   * Feature scaling.
   * <p>
   * The simplest method is rescaling the range of features to scale the range in
   * [0, 1].
   * </p>
   *
   * @param a the original data array (input).
   * @param r the array that will store the results (output).
   */
  public static void rescaling(final double[] a, final double[] r) {
    rescaling(a, 0, r, 0, a.length, 0, 1);
  }

  /**
   * Feature scaling. Mean normalization.
   *
   * @param a   the original data array (input).
   * @param bA  the index where the data starts.
   * @param r   the array that will store the results (output).
   * @param bR  the index where the data can be stored.
   * @param len the number of elements to be processed.
   */
  public static void meanNormalization(final double[] a, final int bA, final double[] r, final int bR, final int len) {
    int mm[] = minMax(a, bA, len);
    double min = a[mm[0]], max = a[mm[1]], d = max - min, mean = mean(a, bA, len);

    for (int i = 0; i < len; i++) {
      r[bR + i] = (a[bA + i] - mean) / d;
    }
  }

  /**
   * Feature scaling. Standardization (or Z-score normalization), features will be
   * rescaled so that they’ll have the properties of a standard normal
   * distribution with u = 0 and std = 1
   *
   * @param a   the original data array (input).
   * @param bA  the index where the data starts.
   * @param r   the array that will store the results (output).
   * @param bR  the index where the data can be stored.
   * @param len the number of elements to be processed.
   */
  public static void standardization(final double[] a, final int bA, final double[] r, final int bR, final int len) {
    double mean = mean(a, bA, len), std = std(a, bA, len);

    for (int i = 0; i < len; i++) {
      r[bR + i] = (a[bA + i] - mean) / std;
    }
  }

  /**
   * Feature scaling. Scaling to unit length. Another option that is widely used
   * in machine-learning is to scale the components of a feature vector such that
   * the complete vector has length one. This usually means dividing each
   * component by the Euclidean length of the vector.
   *
   * @param a   the original data array (input).
   * @param bA  the index where the data starts.
   * @param r   the array that will store the results (output).
   * @param bR  the index where the data can be stored.
   * @param len the number of elements to be processed.
   */
  public static void scalingUnitLength(final double[] a, final int bA, final double[] r, final int bR, final int len) {
    double norm = norm(a, bA, len, 2);
    for (int i = 0; i < len; i++) {
      r[bR + i] = a[bA + i] / norm;
    }
  }

  /**
   *
   * @param a
   * @param bIdx
   * @param len
   * @param p
   * @param max
   * @return
   */
  private static double norm2(final double a[], final int bIdx, final int len, final int p, final double max) {

    double rv = 0.0;
    if (p == 2) {
      for (int i = 0; i < len; i++) {
        rv += Math.pow(a[i + bIdx] / max, 2.0);
      }
      rv = Math.sqrt(rv);
    } else {
      for (int i = 0; i < len; i++) {
        rv += Math.pow(a[i + bIdx] / max, p);
      }
      rv = MathUtils.nthRoot(rv, p);
    }

    rv *= max;

    return rv;
  }

  /**
   * @param a
   * @param p
   * @param max
   * @return
   */
  private static double norm2(final double a[], final int p, final double max) {
    return norm2(a, 0, a.length, p, max);
  }

  /**
   * Returns the norm p from a array of data: \(\left \| x \right \|_p = \left(
   * \sum_{i=0}^{len} |a_{i+bA}|^p \right) ^\frac{1}{p}\)
   * <p>
   * The implementation is numerically stable.
   * </p>
   *
   * @see <a href=
   *      "https://timvieira.github.io/blog/post/2014/11/10/numerically-stable-p-norms/">
   *      Numerically stable p-norms </a>
   *
   * @param a
   * @param bA
   * @param len
   * @param p
   * @return
   */
  public static double norm(final double a[], final int bIdx, final int len, final int p) {
    double norm = 0.0;

    if (p == 1) {
      for (int i = 0; i < len; i++) {
        norm += Math.abs(a[i + bIdx]);
      }
    } else {
      double max = Math.abs(a[ArrayUtils.maxAbs(a, bIdx, len)]);
      norm = (max > 0) ? norm2(a, bIdx, len, p, max) : 0.0;
    }
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
  public static void cfd(final double[] x, final double[] y, final double[] rv, final int bX, final int bY,
      final int bR, final int l) {
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
  public static double[] cfd(final double[] x, final double[] y, final int bX, final int bY, final int l) {
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
  public static void csd(final double[] x, final double[] y, final double[] rv, final int bX, final int bY,
      final int bR, final int l) {
    for (int i = 1; i < l - 1; i++) {
      rv[bR + i - 1] = (y[bY + i + 1] - 2 * y[bY + i] + y[bY + i])
          / ((x[bX + i] - x[bX + i - 1]) * (x[bX + i + 1] - x[bX + i]));
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
  public static double[] csd(final double[] x, final double[] y, final int bX, final int bY, final int l) {
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
   * Return an array (out) with the same content that the input (in) array, but in
   * reverse order.
   * <p>
   * This method implements a sequential method to reverse the input array. If the
   * array is large enough, cache misses can degrade the performance.
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
   * Return an array (out) with the same content that the input (in) array, but in
   * reverse order.
   * <p>
   * This method implements a sequential method to reverse the input array. If the
   * array is large enough, cache misses can degrade the performance.
   * </p>
   *
   * @param in the input array.
   * @return an array (out) with the same content that the input (in) array, but
   *         in reverse order.
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

  /**
   * @param a
   * @return
   */
  public static boolean allEqual(final double a[]) {
    return allEqual(a, 0, a.length);
  }

  /**
   * @param a
   * @param bIdx
   * @param len
   * @return
   */
  public static boolean allEqual(final double a[], final int bIdx, final int len) {
    boolean rv = true;

    for (int i = 0; rv && i < len - 1; i++) {
      rv = MathUtils.equals(a[i], a[i + 1]);
    }

    return rv;
  }

  /**
   * @param a
   * @param n
   * @param eps
   * @return
   */
  public static boolean equalTo(final double a[], final double n, final double eps) {
    return equalTo(a, 0, a.length, n, eps);
  }

  /**
   * @param a
   * @param bIdx
   * @param len
   * @param n
   * @param eps
   * @return
   */
  public static boolean equalTo(final double a[], final int bIdx, final int len, final double n, final double eps) {
    boolean rv = true;
    for (int i = 0; rv && i < len; i++) {
      rv = MathUtils.equals(n, a[bIdx + i], eps);
    }
    return rv;
  }

  public static boolean allDifferent(final double a[]) {
    return allDifferent(a, 0, a.length);
  }

  /**
   * @param a
   * @param bIdx
   * @param len
   * @return
   */
  public static boolean allDifferent(final double a[], final int bIdx, final int len) {
    boolean rv = true;

    double t[] = new double[len];
    System.arraycopy(a, bIdx, t, 0, len);
    Arrays.sort(t);

    for (int i = 0; rv && i < len - 1; i++) {
      rv = !MathUtils.equals(t[i], t[i + 1]);
    }

    return rv;
  }

  /**
   * @param a
   * @param bIdx
   * @param len
   * @return
   */
  public static double median(final double a[], final int bIdx, final int len) {
    double rv = 0.0;

    if (len == 1) {
      rv = a[bIdx];
    } else if (len == 2) {
      rv = (a[bIdx] + a[bIdx + 1]) / 2.0;
    } else if (len == 3) {
      rv = Math.max(Math.min(a[bIdx], a[bIdx + 1]), Math.min(Math.max(a[bIdx], a[bIdx + 1]), a[bIdx + 2]));
    } else {
      double buff[] = new double[len];
      System.arraycopy(a, bIdx, buff, 0, len);
      int middle = len / 2;
      rv = SortUtils.qselect(buff, middle);
      if (len % 2 == 0) {
        double tmp = SortUtils.qselect(buff, middle - 1);
        rv = (rv + tmp) / 2.0;
      }
    }

    return rv;
  }

  /**
   * @param a
   * @return
   */
  public static double median(final double a[]) {
    return median(a, 0, a.length);
  }

  /**
   * @param a
   * @param bidx
   * @param len
   * @return
   */
  public static int subarrayHashCode(final double a[], final int bIdx, final int len) {
    int prime = 31, result = 1;

    for (int i = 0; i < len; i++) {
      result = prime * result + Double.hashCode(a[i + bIdx]);
    }

    return result;
  }

  /**
   * @param a
   * @param aIdx
   * @param b
   * @param bIdx
   * @param len
   * @param eps
   * @return
   */
  public static boolean equals(final double a[], final int aIdx, final double b[], final int bIdx, final int len,
      final double eps) {

    boolean rv = true;

    for (int i = 0; i < len && rv; i++) {
      if (!MathUtils.equals(a[i + aIdx], b[i + bIdx], eps)) {
        rv = false;
      }
    }

    return rv;
  }

  /**
   * @param a
   * @param b
   * @param eps
   * @return
   */
  public static boolean equals(final double a[], final double b[], final double eps) {
    return equals(a, 0, b, 0, a.length, eps);
  }

  /**
   * @param a
   * @param aIdx
   * @param b
   * @param bIdx
   * @param len
   * @return
   */
  public static boolean equals(final double a[], final int aIdx, final double b[], final int bIdx, final int len) {
    boolean rv = true;

    for (int i = 0; i < len && rv; i++) {
      if (a[i + aIdx] != b[i + bIdx]) {
        rv = false;
      }
    }

    return rv;
  }

  /**
   * Returns an identity matrix (an array that represents a matrix in row-major
   * order) with size: Rows x Cols.
   *
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @return an identity matrix (an array that represents a matrix in row-major
   *         order) with size: Rows x Cols
   */
  public static double[] identity(final int rows, final int cols) {
    double m[] = new double[rows * cols];

    int min = (rows < cols) ? rows : cols;
    for (int i = 0; i < min; i++) {
      m[i * cols + i] = 1.0;
    }

    return m;
  }

  public static int count(final double data[], final double scalar, final int idx, final int len) {
    int rv = 0;
    for (int i = 0; i < len; i++) {
      if (data[i + idx] == 0) {
        rv += 1;
      }
    }
    return rv;
  }

  /**
   * 
   * @param data
   * @param scalar
   * @param idx
   * @param len
   */
  public static void set_neg(final double data[], final double scalar, final int idx, final int len) {
    for (int i = 0; i < len; i++) {
      if (data[i + idx] <= 0) {
        data[i + idx] = scalar;
      }
    }
  }

  public static void set_neg(final double data[], final double scalar) {
    set_neg(data, scalar, 0, data.length);
  }

  /**
   * 
   * @param r
   * @param c
   * @param cols
   * @return
   */
  public static int rc2idx(final int r, final int c, final int cols) {
    return (r * cols) + c;
  }

  public static int idx2r(final int idx, final int cols) {
    return idx / cols;
  }

  public static int idx2c(final int idx, final int cols) {
    return idx % cols;
  }

  
}