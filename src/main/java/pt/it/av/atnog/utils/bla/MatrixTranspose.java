package pt.it.av.atnog.utils.bla;

import pt.it.av.atnog.utils.structures.tuple.Quad;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Low level matrix operations.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class MatrixTranspose {
    private static int BLK = 32;

    /**
     * @param data
     * @param tdata
     * @param rows
     * @param columns
     */
    protected static void transpose(double data[], double tdata[], int rows, int columns) {
        double tmp[] = new double[BLK * BLK];
        Deque<Quad<Integer, Integer, Integer, Integer>> stack = new ArrayDeque<>();
        stack.push(new Quad(0, rows, 0, columns));
        while (!stack.isEmpty()) {
            Quad<Integer, Integer, Integer, Integer> q = stack.pop();
            int rb = q.a, re = q.b, cb = q.c, ce = q.d, r = q.b - q.a, c = q.d - q.c;
            if (r <= BLK && c <= BLK) {
                for (int i = rb, tmpc = 0; i < re; i++, tmpc++)
                    for (int j = cb, tmpr = 0; j < ce; j++, tmpr++)
                        tmp[tmpr * BLK + tmpc] = data[i * columns + j];
                for (int j = cb, tmpr = 0; j < ce; j++, tmpr++)
                    for (int i = rb, tmpc = 0; i < re; i++, tmpc++)
                        tdata[j * rows + i] = tmp[tmpr * BLK + tmpc];
            } else if (r >= c) {
                stack.push(new Quad(rb + (r / 2), re, cb, ce));
                stack.push(new Quad(rb, rb + (r / 2), cb, ce));
            } else {
                stack.push(new Quad(rb, re, cb + (c / 2), ce));
                stack.push(new Quad(rb, re, cb, cb + (c / 2)));
            }
        }
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
    protected static Matrix naive_transpose(Matrix M) {
        Matrix C = new Matrix(M.cols, M.rows);
        for (int n = 0, total = M.data.length; n < total; n++) {
            int r = n / M.cols, c = n % M.cols;
            C.data[c * C.cols + r] = M.data[n];
        }
        return C;
    }
}
