package pt.it.av.atnog.utils.linearAlgebra;

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

    public Matrix add(Matrix A) {
        Matrix B = new Matrix(rows, columns);
        for (int n = 0, total = data.length; n < total; n++)
            B.data[n] = data[n] + A.data[n];
        return B;
    }

    public Matrix sub(Matrix A) {
        Matrix B = new Matrix(rows, columns);
        for (int n = 0, total = data.length; n < total; n++)
            B.data[n] = data[n] - A.data[n];
        return B;
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
}
