package pt.it.av.atnog.utils.bla;

import pt.it.av.atnog.utils.Utils;
import pt.it.av.atnog.utils.parallel.ThreadPool;
import pt.it.av.atnog.utils.structures.tuple.Quad;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author Mário Antunes
 */
public class Matrix {
    private static int BLK = 64;
    protected double data[];
    protected int rows, columns;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.data = new double[rows * columns];
    }

    public Matrix(int rows, int columns, double data[]) {
        this.rows = rows;
        this.columns = columns;
        this.data = data;
    }

    public static Matrix identity(int size) {
        Matrix C = new Matrix(size, size);
        for (int i = 0; i < size; i++)
            C.data[i * C.columns + i] = 1.0;
        return C;
    }

    public static Matrix rand(int rows, int columns) {
        Matrix C = new Matrix(rows, columns);
        for (int n = 0; n < rows * columns; n++)
            C.data[n] = Utils.randomBetween(0, 10);
        return C;
    }

    protected static void transpose(double data[], double tdata[], int rows, int columns, int blk) {
        double tmp[] = new double[blk * blk];
        Deque<Quad<Integer, Integer, Integer, Integer>> stack = new ArrayDeque<>();
        stack.push(new Quad(0, rows, 0, columns));
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
                stack.push(new Quad(rb, rb + (r / 2), cb, ce));
                stack.push(new Quad(rb + (r / 2), re, cb, ce));
            } else {
                stack.push(new Quad(rb, re, cb, cb + (c / 2)));
                stack.push(new Quad(rb, re, cb + (c / 2), ce));
            }
        }
    }


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
            for (int i = column; i < M.columns; i++)
                M.data[row * M.columns + i] = 0.0;
            M.data[row * M.columns + column] = d;
            for (int i = row + 1; i < M.rows; i++)
                M.uSubRow(i, column, v.mul(2.0 * v.innerProduct(M.vector(i, column))));
            if (H != null)
                for (int i = 0; i < H.rows; i++)
                    H.uSubRow(i, column, v.mul(2.0 * v.innerProduct(H.vector(i, column))));
        }
        return rv;
    }

    public int rows() {
        return rows;
    }

    public int columns() {
        return columns;
    }

    public void set(int r, int c, double scalar) {
        data[r * columns + c] = scalar;
    }

    public void set(double scalar) {
        for (int i = 0; i < data.length; i++)
            data[i] = scalar;
    }

    public double get(int r, int c) {
        return data[r * columns + c];
    }

    public Matrix transpose() {
        Matrix T = new Matrix(columns, rows);
        transpose(data, T.data, rows, columns, BLK);
        return T;
    }

    public void uTranspose() {
        double buffer[] = new double[rows * columns];
        transpose(data, buffer, rows, columns, BLK);
        this.data = buffer;
        int t = rows;
        this.rows = columns;
        this.columns = t;
    }

    public Matrix add(Matrix B) {
        Matrix C = new Matrix(rows, columns);
        Vector.add(data, 0, B.data, 0, C.data, 0, data.length);
        return C;
    }

    public void uAdd(Matrix B) {
        Vector.add(data, 0, B.data, 0, data, 0, data.length);
    }

    public Matrix add(double scalar) {
        Matrix C = new Matrix(rows, columns);
        Vector.add(data, 0, scalar, C.data, 0, data.length);
        return C;
    }

    public void uAdd(double scalar) {
        Vector.add(data, 0, scalar, data, 0, data.length);
    }

    public Matrix sub(Matrix B) {
        Matrix C = new Matrix(rows, columns);
        Vector.sub(data, 0, B.data, 0, C.data, 0, data.length);
        return C;
    }

    public void uSub(Matrix B) {
        Vector.sub(data, 0, B.data, 0, data, 0, data.length);
    }

    public Matrix sub(double b) {
        Matrix C = new Matrix(rows, columns);
        for (int n = 0, total = data.length; n < total; n++)
            C.data[n] = data[n] - b;
        return C;
    }

    public void uSubRow(int row, int column, Vector b) {
        for (int i = 0; i < columns - column; i++)
            data[row * columns + i + column] -= b.data[i];
    }

    public Matrix mul(double scalar) {
        Matrix C = new Matrix(rows, columns);
        for (int n = 0, total = data.length; n < total; n++)
            C.data[n] = data[n] * scalar;
        return C;
    }

    //TODO: optimize this function
    public Vector mul(Vector v) {
        Vector rv = new Vector(v.length);
        for (int i = 0; i < rows; i++) {
            double rvi = 0.0;
            for (int j = 0; j < columns; j++) {
                rvi += v.data[v.bIdx + j] * data[i * columns + j];
            }
            rv.data[rv.bIdx + i] = rvi;
        }
        return rv;
    }

    public Matrix mul(Matrix B) {
        return mul_seq(B);
    }

    public Matrix mul_seq(Matrix B) {
        Matrix C = new Matrix(rows, B.columns), BT = B.transpose();
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

    public Matrix mul_par(Matrix B) {
        Matrix C = new Matrix(rows, B.columns), BT = B.transpose();
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

    /**
     * @return
     */
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
        UBV[2] = Matrix.identity(columns);
        for (int k = 0; k < rows - 1; k++) {
            householder(UBV[1], UBV[0], k, k);
            UBV[1].uTranspose();
            householder(UBV[1], UBV[2], k, k + 1);
            UBV[1].uTranspose();
        }
        UBV[1].uTranspose();
        return UBV;
    }

    private double[] rot(double a, double b) {
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

    private double[] diagArray(int n) {
        double d[];
        if (n >= 0) {
            int size = Math.min(rows, columns - n);
            d = new double[size];
            for (int i = 0; i < size; i++)
                d[i] = data[i * columns + (i + n)];
        } else {
            n = Math.abs(n);
            int size = Math.min(rows - n, columns);
            d = new double[size];
            for (int i = 0; i < size; i++)
                d[i] = data[(i + n) * columns + i];
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
        Utils.printArray(diag);
        lambda[diag.length - 1] = Math.abs(diag[diag.length - 1]);

        for (int i = diag.length - 1; i > 0; i--)
            lambda[i] = Math.abs(diag[i]) * lambda[i + 1];


        return UDV;
    }

    public double det() {
        double rv = 1.0;
        if (columns == 1 && rows == 1)
            rv = data[0];
        else if (columns == 2 && rows == 2)
            rv = data[0] * data[3] - data[2] * data[1];
        else if (columns == 3 && rows == 3)
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
                rv *= T.data[i * T.columns + i];
            rv *= Math.pow(-1.0, hh);
        }
        return rv;
    }

    public Vector vector(int row, int column) {
        return new Vector(data, row * columns + column, columns - column);
    }

    public Vector vector() {
        return new Vector(data, 0, rows * columns);
    }

    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null) {
            if (o == this)
                rv = true;
            else if (o instanceof Matrix) {
                Matrix B = (Matrix) o;
                if (rows == B.rows && columns == B.columns) {
                    rv = true;
                    for (int i = 0; i < data.length && rv == true; i++)
                        if (Double.compare(data[i], B.data[i]) != 0)
                            rv = false;
                }
            }
        }
        return rv;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++)
                sb.append(String.format("%.5f ", data[r * columns + c]));
            sb.append("\n");
        }
        return sb.toString();
    }
}
