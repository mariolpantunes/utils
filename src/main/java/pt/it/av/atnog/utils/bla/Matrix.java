package pt.it.av.atnog.utils.bla;

import pt.it.av.atnog.utils.Utils;
import pt.it.av.atnog.utils.parallel.ThreadPool;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Mário Antunes
 */
public class Matrix {
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

    public Matrix sub(double b) {
        Matrix C = new Matrix(rows, columns);
        for (int n = 0, total = data.length; n < total; n++)
            C.data[n] = data[n] - b;
        return C;
    }

    public void uSub(int row, int column, Vector b) {
        for (int i = 0; i < columns - column; i++)
            data[row * columns + i + column] -= b.data[i];
    }

    public Matrix mul(double scalar) {
        Matrix C = new Matrix(rows, columns);
        for (int n = 0, total = data.length; n < total; n++)
            C.data[n] = data[n] * scalar;
        return C;
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
        Matrix C = transpose();
        for (int k = 0; k < rows - 1; k++) {
            //Vector v = C.vector(k, k);
            //double d = v.norm(2);
            Vector v = new Vector(rows - k);
            double d = C.normAndVector(k, k, v);
            if (d > 0) {
                if (v.data[0] > 0)
                    d = -d;
                v.data[0] = v.data[0] - d;
                double f1 = Math.sqrt(-2 * v.data[0] * d);
                v.uDiv(f1);

                // Reflection matrix
                //Matrix H = Matrix.identity(rows-k).sub(v.outerProduct(v).mul(2.0));

                C.zeros(k, k);
                C.data[k * C.columns + k] = d;
                // Apply householder for the remaining columns
                for (int i = k + 1; i < columns; i++) {
                    double f = 2.0 * v.innerProduct(C.vector(i, k));
                    C.uSub(i, k, v.mul(f));
                }
            }
        }
        return C.transpose();
    }

    public Matrix[] QR() {
        Matrix QR[] = new Matrix[2];

        QR[0] = Matrix.identity(rows);
        QR[1] = transpose();

        for (int k = 0; k < rows - 1; k++) {
            Vector v = new Vector(rows - k);
            double d = QR[1].normAndVector(k, k, v);
            if (d > 0) {
                if (v.data[0] > 0)
                    d = -d;
                v.data[0] = v.data[0] - d;
                double f1 = Math.sqrt(-2 * v.data[0] * d);
                v.uDiv(f1);

                // Reflection matrix
                //Matrix H = Matrix.identity(rows-k).sub(v.outerProduct(v).mul(2.0));

                QR[1].zeros(k, k);
                QR[1].data[k * QR[1].columns + k] = d;
                // Apply householder for the remaining columns
                for (int i = k + 1; i < columns; i++)
                    QR[1].uSub(i, k, v.mul(2.0 * v.innerProduct(QR[1].vector(i, k))));

                for (int i = 0; i < rows; i++)
                    QR[0].uSub(i, k, v.mul(2.0 * v.innerProduct(QR[0].vector(i, k))));
            }
        }
        QR[1] = QR[1].transpose();
        return QR;
    }

    private double normAndVector(int row, int column, Vector v) {
        double norm = 0.0;
        for (int i = 0; i < columns - column; i++) {
            v.data[i] = data[row * columns + i + column];
            norm = Utils.norm(norm, data[row * columns + i + column], 2);
        }
        return norm;
    }

    public double det() {
        Matrix B = triangular();
        double rv = 1.0;

        for (int i = 0; i < B.rows; i++)
            rv *= B.data[i * B.columns + i];

        rv *= Math.pow(-1.0, B.rows - 1);

        return rv;
    }

    public Vector vector(int row, int column) {
        return new Vector(data, row * columns + column, columns - column);
    }

    private void zeros(int row, int column) {
        for (int i = column; i < columns; i++)
            data[row * columns + i] = 0.0;
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
