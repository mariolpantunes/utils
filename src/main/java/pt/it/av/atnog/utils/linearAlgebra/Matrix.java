package pt.it.av.atnog.utils.linearAlgebra;

import pt.it.av.atnog.utils.Utils;
import pt.it.av.atnog.utils.parallel.ThreadPool;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by mantunes on 11/3/14.
 */
public class Matrix {
    protected double data[];
    protected int rows, columns;

    public Matrix(int rows, int columns) {
        this.data = new double[rows * columns];
        this.rows = rows;
        this.columns = columns;
    }

    public static Matrix rand(int rows, int columns) {
        Matrix C = new Matrix(rows, columns);

        for (int n = 0; n < rows * columns; n++)
            C.data[n] = Utils.randomBetween(0, 10);

        return C;
    }

    public int rows() {
        return rows;
    }

    public int columns() {
        return columns;
    }

    public void set(int r, int c, double v) {
        data[r * columns + c] = v;
    }

    public double get(int r, int c) {
        return data[r * columns + c];
    }

    public Matrix transpose() {
        Matrix C = new Matrix(columns, rows);
        for (int n = 0, total = data.length; n < total; n++) {
            int r = n / columns, c = n % columns;
            C.data[c * C.columns + r] = data[n];
        }
        return C;
    }

    public Matrix add(Matrix B) {
        Matrix C = new Matrix(rows, columns);
        for (int n = 0, total = data.length; n < total; n++)
            C.data[n] = data[n] + B.data[n];
        return C;
    }

    public Matrix sub(Matrix B) {
        Matrix C = new Matrix(rows, columns);
        for (int n = 0, total = data.length; n < total; n++)
            C.data[n] = data[n] - B.data[n];
        return C;
    }

    public Matrix mul(Matrix B) {
        Matrix BT = B.transpose(), C = new Matrix(rows, B.columns);
        for (int i = 0; i < C.rows; i++) {
            int ic = i * columns;
            for (int j = 0; j < C.columns; j++) {
                int jc = j * BT.columns;
                double cij = 0.0;
                for (int k = 0; k < B.rows; k++)
                    cij += data[ic + k] * BT.data[jc + k];
                C.data[i * C.columns + j] = cij;
            }
        }
        return C;
    }

    public Matrix parallel_mul(Matrix B) {
        Matrix BT = B.transpose(), C = new Matrix(rows, B.columns);
        int I = C.rows, J = C.columns, K = columns;
        final int nThreads = Runtime.getRuntime().availableProcessors();
        final int blockSize = I / nThreads;
        Thread[] threads = new Thread[nThreads];
        for (int n = 0; n < nThreads; n++) {
            final int finalN = n;
            threads[n] = new Thread() {
                public void run() {
                    final int beginIndex = finalN * blockSize;
                    final int endIndex = (finalN == (nThreads - 1)) ?
                            I : (finalN + 1) * blockSize;
                    for (int i = beginIndex; i < endIndex; i++) {
                        for (int j = 0; j < J; j++) {
                            for (int k = 0; k < K; k++) {
                                C.data[i * C.columns + j] += data[i * columns + k] * BT.data[j * B.columns + k];
                            }
                        }
                    }
                }
            };
            threads[n].start();
        }

        for (int n = 0; n < nThreads; n++) {
            try {
                threads[n].join();
            } catch (InterruptedException e) {
                System.exit(-1);

            }
        }

        return C;
    }

    public Matrix parallel_mul2(Matrix B) {
        Matrix BT = B.transpose(), C = new Matrix(rows, B.columns);
        int I = C.rows, J = C.columns, K = columns;
        ThreadPool tp = new ThreadPool((Object o, List<Object> l) -> {
            int i = (Integer) o, ic = i * columns;
            for (int j = 0; j < C.columns; j++) {
                int jc = j * BT.columns;
                double cij = 0.0;
                for (int k = 0; k < B.rows; k++)
                    cij += data[ic + k] * BT.data[jc + k];
                C.data[i * C.columns + j] = cij;
            }
        });

        BlockingQueue<Object> sink = tp.sink();
        tp.start();

        try {
            for (int i = 0; i < I; i++)
                sink.add(new Integer(i));
            tp.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return C;
    }

    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null) {
            if (o == this)
                rv = true;
            else if (o instanceof Matrix) {
                Matrix A = (Matrix) o;
                if (rows == A.rows && columns == A.columns) {
                    rv = true;
                    for (int i = 0; i < rows && rv == true; i++)
                        for (int j = 0; j < columns && rv == true; j++)
                            if (Double.compare(data[i * columns + j], A.data[i * columns + j]) != 0)
                                rv = false;
                }
            }
        }
        return rv;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                sb.append(String.format("%3.0f ", data[r * columns + c]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
