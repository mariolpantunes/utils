package pt.it.av.atnog.utils.bla;

import pt.it.av.atnog.utils.structures.tuple.Quad;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Several implementations
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class MatrixTranspose {
  private static int DEFAULT_BLK = 32;

  /**
   * @param data
   * @param tdata
   * @param rows
   * @param columns
   */
  protected static double[] transpose(final double data[], final double tdata[],
                                      final int rows, final int columns) {
    return transpose(data, tdata, rows, columns, DEFAULT_BLK);
  }


  /**
   * @param data
   * @param tdata
   * @param rows
   * @param columns
   * @param blk
   */
  protected static double[] transpose(final double data[], final double tdata[],
                                      final int rows, final int columns, final int blk) {
    double tmp[] = new double[blk * blk];
    Deque<Quad<Integer, Integer, Integer, Integer>> stack = new ArrayDeque<Quad<Integer, Integer, Integer, Integer>>();
    stack.push(new Quad<>(0, rows, 0, columns));
    while (!stack.isEmpty()) {
      Quad<Integer, Integer, Integer, Integer> q = stack.pop();
      int rb = q.a, re = q.b, cb = q.c, ce = q.d, r = q.b - q.a, c = q.d - q.c;
      if (r <= blk && c <= blk) {
        for (int i = rb, tmpc = 0; i < re; i++, tmpc++)
          for (int j = cb, tmpr = 0; j < ce; j++, tmpr++)
            tmp[tmpr * blk + tmpc] = data[i * columns + j];
        for (int j = cb, tmpr = 0; j < ce; j++, tmpr++)
          for (int i = rb, tmpc = 0; i < re; i++, tmpc++)
            tdata[j * rows + i] = tmp[tmpr * blk + tmpc];
      } else if (r >= c) {
        stack.push(new Quad<>(rb + (r / 2), re, cb, ce));
        stack.push(new Quad<>(rb, rb + (r / 2), cb, ce));
      } else {
        stack.push(new Quad<>(rb, re, cb + (c / 2), ce));
        stack.push(new Quad<>(rb, re, cb, cb + (c / 2)));
      }
    }
    return tdata;
  }

  /**
   * @param data
   * @param n
   */
  protected static void inplace_square_transpose(double data[], int n) {
    for (int r = 0; r < n - 1; r++)
      for (int c = r + 1; c < n; c++) {
        double tmp = data[r * n + c];
        data[r * n + c] = data[c * n + r];
        data[c * n + r] = tmp;
      }
  }

  /**
   * @param data
   * @param rows
   * @param columns
   */
  protected static void inplace_follow_cycles(double data[], int rows, int columns) {
    boolean visited[] = new boolean[data.length];
    int q = rows * columns - 1;
    for (int i = 1; i < data.length - 1; i++) {
      if (!visited[i]) {
        int start = i;
        double tmp = data[i];
        boolean done = false;
        while (!done) {
          visited[i] = true;
          int nIdx = i * columns % q;
          if (nIdx == start) {
            data[i] = tmp;
            i = start;
            done = true;
          }
          data[i] = data[nIdx];
          i = nIdx;
        }
      }
    }
  }

  /**
   * A naive implementation of matrix transpose algorithm.
   * This implementation is only intendendet to be used in unit testing.
   * The function returns a Matrix that is a tranpose from the input Matrix.
   *
   * @param M an input matrix
   * @return Matrix that is a tranpose from the input Matrix
   */
  protected static double[] naive_transpose(final double data[], final double tdata[],
                                            final int rows, final int columns) {
    for (int n = 0, total = data.length; n < total; n++) {
      int r = n / columns, c = n % columns;
      tdata[c * rows + r] = data[n];
    }
    return tdata;
  }
}
