package pt.it.av.atnog.utils.bla;

import pt.it.av.atnog.utils.MathUtils;
import pt.it.av.atnog.utils.parallel.ThreadPool;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 */
public class Matrix {
    protected double data[];
    protected int rows, cols;

    /**
     * Create a empty matrix with size: Rows x Cols.
     *
     * @param rows number of rows in the matrix
     * @param cols number of columns in the matrix
     */
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows * cols];
    }

    /**
     * Creates a matrix from a 1D double array with size: Rows x Cols.
     *
     * @param rows number of rows in the matrix
     * @param cols number of columns in the matrix
     * @param data 1D double array with matrix values
     */
    public Matrix(int rows, int cols, double data[]) {
        this.rows = rows;
        this.cols = cols;
        this.data = data;
    }

    /**
     * Returns a square identity matrix with size: Size x Size.
     *
     * @param size number of rows and columns.
     * @return a square identity matrix
     */
    public static Matrix identity(int size) {
        return identity(size, size);
    }

    /**
     * Returns an identity matrix with size: Rows x Cols.
     *
     * @param rows number of rows in the matrix
     * @param cols number of columns in the matrix
     * @return an identity matrix
     */
    public static Matrix identity(int rows, int cols) {
        Matrix C = new Matrix(rows, cols);
        int min = (rows < cols) ? rows : cols;
        for (int i = 0; i < min; i++)
            C.data[i * C.cols + i] = 1.0;
        return C;
    }

    /**
     * Returns a matrix filled with random integers between [{@code min}, {@code max}[.
     *
     * @param rows number of rows in the matrix
     * @param cols number of columns in the matrix
     * @param min minimal value of the range, inclusive
     * @param max maximal value of the range, exclusive
     * @return a matrix filled with random integers between [{@code min}, {@code max}[.
     */
    public static Matrix random(int rows, int cols, int min, int max) {
        Matrix C = new Matrix(rows, cols);
        for (int n = 0; n < rows * cols; n++)
            C.data[n] = MathUtils.randomBetween(min, max);
        return C;
    }

    /**
     * Returns a matrix filled with random numbers between [0, 1[.
     *
     * @param rows number of rows in the matrix
     * @param cols number of columns in the matrix
     * @return a matrix filled with random numbers between [0, 1[.
     */
    public static Matrix random(int rows, int cols) {
        Matrix C = new Matrix(rows, cols);
        for (int n = 0; n < rows * cols; n++)
            C.data[n] = Math.random();
        return C;
    }

    /**
     * @param M
     * @param H
     * @param r
     * @param c
     * @return
     */
    private static boolean householder(Matrix M, Matrix H, int r, int c) {
        boolean rv = false;
        Vector v = M.vector(r, c);
        double d = v.norm(2);
        if (d != v.data[v.bIdx]) {
            rv = true;
            if (v.data[v.bIdx] > 0)
                d = -d;
            v.data[v.bIdx] -= d;
            double f1 = Math.sqrt(-2 * v.data[v.bIdx] * d);
            v = v.div(f1);
            for (int i = c; i < M.cols; i++)
                M.data[r * M.cols + i] = 0.0;
            M.data[r * M.cols + c] = d;
            for (int i = r + 1; i < M.rows; i++)
                M.uSubRow(i, c, v.mul(2.0 * v.innerProduct(M.vector(i, c))));
            if (H != null)
                for (int i = 0; i < H.rows; i++)
                    H.uSubRow(i, c, v.mul(2.0 * v.innerProduct(H.vector(i, c))));
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

    /**
     * Returns the number of rows in the matrix.
     *
     * @return the number of rows in the matrix.
     */
    public int rows() {
        return rows;
    }

    /**
     * Returns the number of columns in the matrix.
     *
     * @return the number of columns in the matrix.
     */
    public int columns() {
        return cols;
    }

    /**
     * Set r-row, c-column value.
     *
     * @param r      row
     * @param c      column
     * @param scalar value
     */
    public void set(int r, int c, double scalar) {
        data[r * cols + c] = scalar;
    }

    /**
     * Set all positions to a value.
     *
     * @param scalar value
     */
    public void set(double scalar) {
        for (int i = 0; i < data.length; i++)
            data[i] = scalar;
    }

    /**
     * Retuns the value in r-row, c-column.
     *
     * @param r row
     * @param c column
     * @return the value in r-row, c-column
     */
    public double get(int r, int c) {
        return data[r * cols + c];
    }

    /**
     * @return
     */
    private boolean isLinear() {
        return rows == 1 || cols == 1;
    }

    /**
     * @return
     */
    private boolean isSquare() {
        return rows == cols;
    }

    /**
     * Returns the transpose matrix.
     * This function uses a cache oblivious algorithm to transpose the original matrix to a new one.
     *
     *
     * @return the transpose matrix
     */
    public Matrix transpose() {
        Matrix T = new Matrix(cols, rows);
        if (isLinear())
            System.arraycopy(data, 0, T.data, 0, data.length);
        else
            MatrixTranspose.transpose(data, T.data, rows, cols);
        return T;
    }

    /**
     * Returns the transpose matrix.
     * The tranpose is done in place with none or minimal auxiliary memory.
     *
     * @return the transpose matrix
     */
    public Matrix uTranspose() {
        if (!isLinear()) {
            if (isSquare())
                MatrixTranspose.inplace_follow_cycles(data, rows, cols);
            else
                MatrixTranspose.inplace_square_transpose(data, rows);
        }
        int t = rows;
        this.rows = cols;
        this.cols = t;
        return this;
    }

    /**
     * @param B
     * @return
     */
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
        int BLK = 64;
        Matrix C = new Matrix(rows, B.cols);
        double bt[] = new double[B.rows * B.cols];
        MatrixTranspose.transpose(B.data, bt, B.rows, B.cols);
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
            //ArraysOps.div(hn.data, 0, hd.data, 0, temp1, 0, k * cols);
            //ArraysOps.mul(h.data, 0, temp1, 0, h.data, 0, k * cols);
            ArraysOps.mulDiv(h.data, 0, hn.data, 0, hd.data, 0, h.data, 0, k * cols);

            // update weights matrix
            Matrix ht = h.transpose();
            Matrix wn = mul(ht);
            Matrix wd = w.mul(h).mul(ht);
            //w.smultEq(wn.smultEq(wd.sinvEq()));
            //ArraysOps.div(wn.data, 0, wd.data, 0, temp1, 0, rows * k);
            //ArraysOps.mul(w.data, 0, temp1, 0, w.data, 0, rows * k);
            ArraysOps.mulDiv(w.data, 0, wn.data, 0, wd.data, 0, w.data, 0, k * rows);
        }
        return new Matrix[]{w, h};
    }
}
