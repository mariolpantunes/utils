package pt.it.av.tnav.utils.bla;

import pt.it.av.tnav.utils.structures.tuple.Quad;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Several implementations
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class MatrixTranspose {
  private static int BLK = 64;

  /**
   * Cache-oblivious implemention of matrix tranpose.
   * Uses the default block size.
   * Returns {@link at} array filled with the tranpose matrix.
   *
   * @param a array representing the matrix
   * @param at array representing the transpose matrix
   * @param rows number of rows
   * @param cols number of columns
   * @return {@link at} array filled with the tranpose matrix
   */
  protected static double[] cotr(final double a[], final double at[],
                                 final int rows, final int cols) {
    return cotr(a, at, rows, cols, BLK);
  }


  /**
   * Cache-oblivious implemention of matrix tranpose.
   * Returns {@link at} array filled with the tranpose matrix.
   *
   * @param a array representing the matrix
   * @param at array representing the transpose matrix
   * @param rows number of rows
   * @param cols number of columns
   * @param blk block size threshold to stop division and solve transpose
   * @return {@link at} array filled with the tranpose matrix
   */
  protected static double[] cotr(final double a[], final double at[],
                                 final int rows, final int cols, final int blk) {
    double tmp[] = new double[blk * blk];
    Deque<Quad<Integer, Integer, Integer, Integer>> stack = new ArrayDeque<Quad<Integer, Integer, Integer, Integer>>();
    stack.push(new Quad<>(0, rows, 0, cols));
    while (!stack.isEmpty()) {
      Quad<Integer, Integer, Integer, Integer> q = stack.pop();
      int rb = q.a, re = q.b, cb = q.c, ce = q.d, r = q.b - q.a, c = q.d - q.c;
      if (r <= blk && c <= blk) {
        for (int i = rb, tmpc = 0; i < re; i++, tmpc++)
          for (int j = cb, tmpr = 0; j < ce; j++, tmpr++)
            tmp[tmpr * blk + tmpc] = a[i * cols + j];
        for (int j = cb, tmpr = 0; j < ce; j++, tmpr++)
          for (int i = rb, tmpc = 0; i < re; i++, tmpc++)
            at[j * rows + i] = tmp[tmpr * blk + tmpc];
      } else if (r >= c) {
        stack.push(new Quad<>(rb, rb + (r / 2), cb, ce));
        stack.push(new Quad<>(rb + (r / 2), re, cb, ce));
      } else {
        stack.push(new Quad<>(rb, re, cb, cb + (c / 2)));
        stack.push(new Quad<>(rb, re, cb + (c / 2), ce));
      }
    }
    return at;
  }

  /**
   * In-place implemention of matrix tranpose.
   * Does not require any auxiliar memory, however it only work with square matrixes.
   *
   * @param a array representing the matrix
   * @param n number of rows and columns
   * @return the transpose matrix
   */
  protected static double[] insqtr(double a[], int n) {
    for (int r = 0; r < n - 1; r++)
      for (int c = r + 1; c < n; c++) {
        double tmp = a[r * n + c];
        a[r * n + c] = a[c * n + r];
        a[c * n + r] = tmp;
      }
    return a;
  }

  /**
   * In-place implemention of matrix tranpose.
   * Implementation baes on follow-cycles algorithm.
   * Requires minimal auxilar memory
   *
   * @param a array representing the matrix
   * @param rows number of rows
   * @param cols number of columns
   * @return the transpose matrix
   */
  protected static double[] infotr(double a[], int rows, int cols) {
    boolean visited[] = new boolean[a.length];
    int q = rows * cols - 1;
    for (int i = 1; i < a.length - 1; i++) {
      if (!visited[i]) {
        int start = i;
        double tmp = a[i];
        boolean done = false;
        while (!done) {
          visited[i] = true;
          int nIdx = i * cols % q;
          if (nIdx == start) {
            a[i] = tmp;
            i = start;
            done = true;
          }
          a[i] = a[nIdx];
          i = nIdx;
        }
      }
    }
    return a;
  }

  /**
   * A naive implementation of matrix transpose algorithm.
   * This implementation is only intendendet to be used in unit testing.
   * Returns {@link at} array filled with the tranpose matrix.
   *
   * @param a array with the original matrix
   * @param at array placeholder for the transpose matrix
   * @param rows number of rows
   * @param cols number of columns
   * @return {@link at} array filled with the tranpose matrix
   */
  protected static double[] ntr(final double a[], final double at[],
                                final int rows, final int cols) {
    for (int i = 0; i < a.length; i++) {
      int r = i / cols, c = i % cols;
      at[c*rows+r] = a[i];
    }
    return at;
  }
}
