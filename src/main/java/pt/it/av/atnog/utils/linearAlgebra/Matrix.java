package pt.it.av.atnog.utils.linearAlgebra;

import pt.it.av.atnog.utils.Utils;

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
        Matrix a = new Matrix(rows, columns);
        for (int n = 0, total = data.length; n < total; n++) {
            int r = n / columns;
            int c = n % columns;
            //a.set(c,r,data[n]);
            a.data[c * rows + r] = data[n];
        }
        return a;
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
        Matrix C = new Matrix(rows, B.columns);
        for (int i = 0; i < C.rows; i++)
            for (int k = 0; k < B.rows; k++)
                for (int j = 0; j < C.columns; j++)
                    C.data[i * C.columns + j] += data[i * columns + k] * B.data[k * B.columns + j];
        return C;
    }

    public Matrix block_mul(Matrix B, int bsize) {
        int n = rows;
        Matrix C = new Matrix(n, n);

        int en = bsize * (n / bsize);

        for (int kk = 0; kk < en; kk += bsize) {
            for (int jj = 0; jj < en; jj += bsize) {
                for (int i = 0; i < n; i++) {
                    for (int j = jj; j < jj + bsize; j++) {
                        double sum = C.data[i * n + j];
                        for (int k = kk; k < kk + bsize; k++) {
                            sum += data[i * n + k] * B.data[k * n + j];
                        }
                        C.data[i * n + j] = sum;
                    }
                }
            }
        }

        return C;
    }

    public Matrix parallel_mul(Matrix B) {
        Matrix C = new Matrix(rows, B.columns);

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
                        for (int k = 0; k < K; k++) {
                            for (int j = 0; j < J; j++) {
                                C.data[i * C.columns + j] += data[i * columns + k] * B.data[k * B.columns + j];
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

    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null) {
            if (o == this)
                rv = true;
            else if (o instanceof Matrix) {
                Matrix A = (Matrix) o;
                int rows = A.rows(), columns = A.columns();
                if (rows == A.rows() && columns == A.columns()) {
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

    private void dotProduct(double c[], double a[], double b[], int cCol, int aCol, int bCol, int i, int j, int k, int bSize) {
        System.out.println("Block");
        for (int _i = i; _i < bSize; _i++)
            for (int _j = j; _j < bSize; _j++)
                for (int _k = k; _k < bSize; _k++)
                    c[_i * cCol + _j] += a[_i * aCol + _k] * b[_k * bCol + _j];

    }
}
