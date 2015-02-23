package pt.it.av.atnog.utils.bla;

import pt.it.av.atnog.utils.structures.tuple.Quad;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by mantunes on 1/23/15.
 */
public class Naive {
    public static double det(Matrix M) {
        double rv = 0.0;
        if (M.columns == 1 && M.rows == 1)
            rv = M.data[0];
        else if (M.columns == 2 && M.rows == 2)
            rv = M.data[0] * M.data[3] - M.data[2] * M.data[1];
        else if (M.columns == 3 && M.rows == 3)
            rv = (M.data[0] * M.data[4] * M.data[8] + M.data[1] * M.data[5] * M.data[6] + M.data[2] * M.data[3] * M.data[7]) - (M.data[2] * M.data[4] * M.data[6] + M.data[1] * M.data[3] * M.data[8] + M.data[0] * M.data[5] * M.data[7]);
        else {
            for (int j1 = 0; j1 < M.rows; j1++) {
                Matrix T = new Matrix(M.rows - 1, M.columns - 1);
                for (int i = 1; i < M.rows; i++) {
                    int j2 = 0;
                    for (int j = 0; j < M.rows; j++) {
                        if (j == j1)
                            continue;
                        T.data[(i - 1) * T.columns + j2] = M.data[i * M.columns + j];
                        j2++;
                    }
                }
                rv += Math.pow(-1.0, 1.0 + j1 + 1.0) * M.data[0 * M.columns + j1] * det(T);
            }
        }
        return rv;
    }

    public static Matrix transpose(Matrix M) {
        Matrix C = new Matrix(M.columns, M.rows);
        for (int n = 0, total = M.data.length; n < total; n++) {
            int r = n / M.columns, c = n % M.columns;
            C.data[c * C.columns + r] = M.data[n];
        }
        return C;
    }

    public static Matrix transpose(Matrix M, int blk) {
        Matrix C = new Matrix(M.columns, M.rows);
        Deque<Quad<Integer, Integer, Integer, Integer>> stack = new ArrayDeque<>();
        stack.push(new Quad(0, M.rows, 0, M.columns));
        while (!stack.isEmpty()) {
            Quad<Integer, Integer, Integer, Integer> q = stack.pop();
            int rb = q.a, re = q.b, cb = q.c, ce = q.d, r = q.b - q.a, c = q.d - q.c;
            if (r <= blk && c <= blk) {
                for (int i = rb; i < re; i++)
                    for (int j = cb; j < ce; j++)
                        C.data[j * M.rows + i] = M.data[i * M.columns + j];
            } else if (r >= c) {
                stack.push(new Quad(rb, rb + (r / 2), cb, ce));
                stack.push(new Quad(rb + (r / 2), re, cb, ce));
            } else {
                stack.push(new Quad(rb, re, cb, cb + (c / 2)));
                stack.push(new Quad(rb, re, cb + (c / 2), ce));
            }
        }
        return C;
    }
}
