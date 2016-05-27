package pt.it.av.atnog.utils.bla;

import pt.it.av.atnog.utils.MathUtils;
import pt.it.av.atnog.utils.parallel.ThreadPool;
import pt.it.av.atnog.utils.structures.tuple.Quad;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 */
public class Matrix {
    private static int BLK = 64;
    protected double data[];
    protected int rows, cols;

    /**
     * @param rows
     * @param cols
     */
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows * cols];
    }

    /**
     * @param rows
     * @param cols
     * @param data
     */
    public Matrix(int rows, int cols, double data[]) {
        this.rows = rows;
        this.cols = cols;
        this.data = data;
    }

    /**
     * Constructs an square identity matrix.
     *
     * @param size matrix's size
     * @return an square identity matrix
     */
    public static Matrix identity(int size) {
        return identity(size, size);
    }

    public static Matrix identity(int rows, int columns) {
        Matrix C = new Matrix(rows, columns);
        int min = (rows < columns) ? rows : columns;
        for (int i = 0; i < min; i++)
            C.data[i * C.cols + i] = 1.0;
        return C;
    }

    /**
     * @param rows
     * @param columns
     * @param min
     * @param max
     * @return
     */
    public static Matrix random(int rows, int columns, int min, int max) {
        Matrix C = new Matrix(rows, columns);
        for (int n = 0; n < rows * columns; n++)
            C.data[n] = MathUtils.randomBetween(min, max);
        return C;
    }

    public static Matrix random(int rows, int columns) {
        Matrix C = new Matrix(rows, columns);
        for (int n = 0; n < rows * columns; n++)
            C.data[n] = Math.random();
        return C;
    }

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
                stack.push(new Quad(rb, rb + (r / 2), cb, ce));
                stack.push(new Quad(rb + (r / 2), re, cb, ce));
            } else {
                stack.push(new Quad(rb, re, cb, cb + (c / 2)));
                stack.push(new Quad(rb, re, cb + (c / 2), ce));
            }
        }
    }

    /**
     * @param M
     * @param H
     * @param row
     * @param column
     * @return
     */
    private static boolean householder(Matrix M, Matrix H, int row, int column) {
        boolean rv = false;
        Vector v = M.vector(row, column);
        double d = v.norm(2);
        if (d != v.data[v.bIdx]) {
            rv = true;
            if (v.data[v.bIdx] > 0)
                d = -d;
            v.data[v.bIdx] -= d;
            double f1 = Math.sqrt(-2 * v.data[v.bIdx] * d);
            v = v.div(f1);
            for (int i = column; i < M.cols; i++)
                M.data[row * M.cols + i] = 0.0;
            M.data[row * M.cols + column] = d;
            for (int i = row + 1; i < M.rows; i++)
                M.uSubRow(i, column, v.mul(2.0 * v.innerProduct(M.vector(i, column))));
            if (H != null)
                for (int i = 0; i < H.rows; i++)
                    H.uSubRow(i, column, v.mul(2.0 * v.innerProduct(H.vector(i, column))));
        }
        return rv;
    }

    private static double[] rot(double a, double b) {
        double csr[] = new double[3];
        if (b == 0) {
            csr[0] = Math.copySign(1, a);
            csr[1] = 0;
            csr[2] = Math.abs(a);
        } else if (a == 0) {
            csr[0] = 0;
            csr[1] = Math.copySign(1, b);
            csr[2] = Math.abs(b);
        } else if (Math.abs(b) > Math.abs(a)) {
            double t = a / b;
            double u = Math.copySign(Math.sqrt(1 + t * t), b);
            csr[1] = -1 / u;
            csr[0] = -csr[1] * t;
            csr[2] = b * u;
        } else {
            double t = b / a;
            double u = Math.copySign(Math.sqrt(1 + t * t), a);
            csr[0] = 1 / u;
            csr[1] = -csr[0] * t;
            csr[2] = a * u;
        }
        return csr;
    }

    public int rows() {
        return rows;
    }

    public int columns() {
        return cols;
    }

    public void set(int r, int c, double scalar) {
        data[r * cols + c] = scalar;
    }

    public void set(double scalar) {
        for (int i = 0; i < data.length; i++)
            data[i] = scalar;
    }

    public double get(int r, int c) {
        return data[r * cols + c];
    }

    public Matrix transpose() {
        Matrix T = new Matrix(cols, rows);
        transpose(data, T.data, rows, cols);
        return T;
    }

    public Matrix uTranspose() {
        double buffer[] = new double[rows * cols];
        transpose(data, buffer, rows, cols);
        this.data = buffer;
        int t = rows;
        this.rows = cols;
        this.cols = t;
        return this;
    }

    public Matrix add(Matrix B) {
        Matrix C = new Matrix(rows, cols);
        ArraysOps.add(data, 0, B.data, 0, C.data, 0, data.length);
        return C;
    }

    public Matrix uAdd(Matrix B) {
        ArraysOps.add(data, 0, B.data, 0, data, 0, data.length);
        return this;
    }

    public Matrix add(double scalar) {
        Matrix C = new Matrix(rows, cols);
        ArraysOps.add(data, 0, scalar, C.data, 0, data.length);
        return C;
    }

    public Matrix uAdd(double scalar) {
        ArraysOps.add(data, 0, scalar, data, 0, data.length);
        return this;
    }

    public Matrix sub(Matrix B) {
        Matrix C = new Matrix(rows, cols);
        ArraysOps.sub(data, 0, B.data, 0, C.data, 0, data.length);
        return C;
    }

    public Matrix uSub(Matrix B) {
        ArraysOps.sub(data, 0, B.data, 0, data, 0, data.length);
        return this;
    }

    public Matrix sub(double b) {
        Matrix C = new Matrix(rows, cols);
        ArraysOps.sub(this.data, 0, b, C.data, 0, data.length);
        return C;
    }

    public Matrix uSubRow(int row, int column, Vector b) {
        ArraysOps.sub(data, row * cols + column, b.data, b.bIdx, data, row * cols + column, b.data.length);
        return this;
    }

    public Matrix mul(double scalar) {
        Matrix C = new Matrix(rows, cols);
        ArraysOps.mul(data, 0, scalar, C.data, 0, data.length);
        return C;
    }

    public Matrix uMul(double scalar) {
        ArraysOps.mul(data, 0, scalar, this.data, 0, data.length);
        return this;
    }

    public Matrix addColumn(int column, double scalar) {
        Matrix C = new Matrix(rows, cols + 1);
        for (int i = 0, j = 0; i < C.data.length; i++)
            if (column == i % C.cols)
                C.data[i] = scalar;
            else
                C.data[i] = data[j++];
        return C;
    }

    public Vector mul(Vector v) {
        Vector rv = new Vector(rows);
        for (int i = 0; i < rows; i++) {
            double rvi = 0.0;
            for (int j = 0; j < cols; j++)
                rvi += v.data[v.bIdx + j] * data[i * cols + j];
            rv.data[rv.bIdx + i] = rvi;
        }
        return rv;
    }

    //TODO: Check code, improve selection between sequencial and parallel implemenatations
    public Matrix mul(Matrix B) {
        Matrix C = new Matrix(rows, B.cols);
        double bt[] = new double[B.rows * B.cols];
        transpose(B.data, bt, B.rows, B.cols);
        if (C.rows * C.cols < BLK * BLK) {
            for (int i = 0; i < C.rows; i++) {
                int ic = i * cols;
                for (int j = 0; j < C.cols; j++) {
                    int jc = j * B.rows;
                    double cij = 0.0;
                    for (int k = 0; k < B.rows; k++)
                        cij += data[ic + k] * bt[jc + k];
                    C.data[i * C.cols + j] = cij;
                }
            }
        } else {
            int I = C.rows, J = C.cols, K = cols;
            ThreadPool tp = new ThreadPool((Object o, List<Object> l) -> {
                int i = (Integer) o, ic = i * cols;
                for (int j = 0; j < C.cols; j++) {
                    int jc = j * B.rows;
                    double cij = 0.0;
                    for (int k = 0; k < B.rows; k++)
                        cij += data[ic + k] * bt[jc + k];
                    C.data[i * C.cols + j] = cij;
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
        }
        return C;
    }

    public Matrix triangular() {
        Matrix T = transpose();
        for (int k = 0; k < rows - 1; k++)
            householder(T, null, k, k);
        T.uTranspose();
        return T;
    }

    public Matrix[] qr() {
        Matrix QR[] = new Matrix[2];
        QR[0] = Matrix.identity(rows);
        QR[1] = transpose();
        for (int k = 0; k < rows - 1; k++)
            householder(QR[1], QR[0], k, k);
        QR[1].uTranspose();
        return QR;
    }

    public Matrix[] bidiagonal() {
        Matrix UBV[] = new Matrix[3];
        UBV[0] = Matrix.identity(rows);
        UBV[1] = transpose();
        UBV[2] = Matrix.identity(cols);
        for (int k = 0; k < rows - 1; k++) {
            householder(UBV[1], UBV[0], k, k);
            UBV[1].uTranspose();
            householder(UBV[1], UBV[2], k, k + 1);
            UBV[1].uTranspose();
        }
        UBV[1].uTranspose();
        return UBV;
    }

    private double[] diagArray(int n) {
        double d[];
        if (n >= 0) {
            int size = Math.min(rows, cols - n);
            d = new double[size];
            for (int i = 0; i < size; i++)
                d[i] = data[i * cols + (i + n)];
        } else {
            n = Math.abs(n);
            int size = Math.min(rows - n, cols);
            d = new double[size];
            for (int i = 0; i < size; i++)
                d[i] = data[(i + n) * cols + i];
        }
        return d;
    }

    public Vector diag() {
        return new Vector(diagArray(0));
    }

    public Vector diag(int n) {
        return new Vector(diagArray(n));
    }

    public Matrix[] svd() {
        Matrix UDV[] = bidiagonal();

        // TODO: bidiag to diag
        double diag[] = UDV[1].diagArray(0);
        double lambda[] = new double[diag.length],
                mu[] = new double[diag.length];
        //PrintUtils.printArray(diag);
        lambda[diag.length - 1] = Math.abs(diag[diag.length - 1]);

        for (int i = diag.length - 1; i > 0; i--)
            lambda[i] = Math.abs(diag[i]) * lambda[i + 1];


        return UDV;
    }

    public double det() {
        double rv = 1.0;
        if (cols == 1 && rows == 1)
            rv = data[0];
        else if (cols == 2 && rows == 2)
            rv = data[0] * data[3] - data[2] * data[1];
        else if (cols == 3 && rows == 3)
            rv = (data[0] * data[4] * data[8] + data[1] * data[5] * data[6] + data[2] * data[3] * data[7])
                    - (data[2] * data[4] * data[6] + data[1] * data[3] * data[8] + data[0] * data[5] * data[7]);
        else {
            Matrix T = transpose();
            int hh = 0;
            for (int k = 0; k < rows - 1; k++)
                if (householder(T, null, k, k))
                    hh++;
            T.uTranspose();
            rv = 1.0;
            for (int i = 0; i < T.rows; i++)
                rv *= T.data[i * T.cols + i];
            rv *= Math.pow(-1.0, hh);
        }
        return rv;
    }

    public Vector vector(int row, int column) {
        return new Vector(data, row * cols + column, cols - column);
    }

    public Vector vector() {
        return new Vector(data, 0, rows * cols);
    }

    @Override
    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null) {
            if (o == this)
                rv = true;
            else if (o instanceof Matrix) {
                Matrix B = (Matrix) o;
                if (rows == B.rows && cols == B.cols) {
                    rv = true;
                    for (int i = 0; i < data.length && rv == true; i++)
                        if (Double.compare(data[i], B.data[i]) != 0)
                            rv = false;
                }
            }
        }
        return rv;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++)
                sb.append(String.format("%.5f ", data[r * cols + c]));
            sb.append("\n");
        }
        return sb.toString();
    }

    //TODO: optimize this method
    public Matrix[] nmf(int k, int n, double e) {
        Matrix w = Matrix.random(rows, k);
        Matrix h = Matrix.random(k, cols);
        double temp1[] = new double[rows * cols];
        for (int i = 0; i < n; i++) {
            // compute output.
            Matrix wh = w.mul(h);
            double cost = ArraysOps.euclideanDistance(this.data, 0, wh.data, 0, this.data.length);

            // if found solution
            if (cost < e)
                break;

            // update feature matrix.
            Matrix wt = w.transpose();
            Matrix hn = wt.mul(this);
            Matrix hd = wt.mul(wh);
            //h.smultEq(hn.smultEq(hd.sinvEq()));
            ArraysOps.div(hn.data, 0, hd.data, 0, temp1, 0, k * cols);
            ArraysOps.mul(h.data, 0, temp1, 0, h.data, 0, k * cols);

            // update weights matrix
            Matrix ht = h.transpose();
            Matrix wn = mul(ht);
            Matrix wd = w.mul(h).mul(ht);
            //w.smultEq(wn.smultEq(wd.sinvEq()));
            ArraysOps.div(wn.data, 0, wd.data, 0, temp1, 0, rows * k);
            ArraysOps.mul(w.data, 0, temp1, 0, w.data, 0, rows * k);
        }
        return new Matrix[]{w, h};
    }
}
