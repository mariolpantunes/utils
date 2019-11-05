package pt.it.av.tnav.utils.bla;

import pt.it.av.tnav.utils.ArrayUtils;
import pt.it.av.tnav.utils.bla.factorization.Cholesky;
import pt.it.av.tnav.utils.bla.factorization.NMF;
import pt.it.av.tnav.utils.bla.factorization.QR;
import pt.it.av.tnav.utils.bla.multiplication.Multiplication;
import pt.it.av.tnav.utils.bla.properties.Properties;
import pt.it.av.tnav.utils.bla.transpose.Transpose;
import pt.it.av.tnav.utils.structures.Distance;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * General purpose Matrix. Implements several functions used in linear algebra
 * and machine learning.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 */
public class Matrix implements Distance<Matrix> {
  protected final double[] data;
  protected int rows, cols;

  /**
   * Creates a matrix filled with zeros and with size: Rows x Cols.
   *
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   */
  public Matrix(final int rows, final int cols) {
    this.rows = rows;
    this.cols = cols;
    this.data = new double[rows * cols];
  }

  /**
   * Creates a matrix from a 1D double array with size: Rows x Cols. Does not copy
   * the array's content (shallow copy).
   *
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @param data 1D double array with matrix values
   */
  public Matrix(final int rows, final int cols, final double[] data) {
    this.rows = rows;
    this.cols = cols;
    this.data = data;
  }

  /**
   * Creates a matrix from another matrix. Implements a copy-constructor (deep
   * copy).
   *
   * @param A another matrix
   */
  public Matrix(Matrix A) {
    rows = A.rows;
    cols = A.cols;
    data = new double[rows * cols];
    System.arraycopy(A.data, 0, data, 0, data.length);
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
   * @return an identity matrix with size: Rows x Cols
   */
  public static Matrix identity(final int rows, final int cols) {
    return new Matrix(rows, cols, ArrayUtils.identity(rows, cols));
  }

  public static Matrix zero(final int rows, final int cols) {
    return new Matrix(rows, cols);
  }

  public static Matrix ones(final int rows, final int cols) {
    Matrix RV = new Matrix(rows, cols);
    Arrays.fill(RV.data, 1.0);
    return RV;
  }

  /**
   * Returns a matrix filled with random numbers between [0, 1[.
   *
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @return a matrix filled with random numbers
   */
  public static Matrix random(final int rows, final int cols) {
    Matrix C = new Matrix(rows, cols);
    for (int n = 0, length = rows * cols; n < length; n++)
      C.data[n] = ThreadLocalRandom.current().nextDouble();
    return C;
  }

  /**
   * Returns a matrix filled with random integers between [{@code min},
   * {@code max}[.
   *
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @param min  minimal value of the range, inclusive
   * @param max  maximal value of the range, exclusive
   * @return a matrix filled with random integers
   */
  public static Matrix random(final int rows, final int cols, final int min, final int max) {
    Matrix C = new Matrix(rows, cols);
    for (int n = 0; n < rows * cols; n++) {
      C.data[n] = ThreadLocalRandom.current().nextInt(min, max);
    }
    return C;
  }

  /**
   * Returns a matrix filled with random integers doubles [{@code min},
   * {@code max}[.
   *
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @param min  minimal value of the range, inclusive
   * @param max  maximal value of the range, exclusive
   * @return a matrix filled with random integers
   */
  public static Matrix random(final int rows, final int cols, final double min, final double max) {
    Matrix C = new Matrix(rows, cols);
    for (int n = 0; n < rows * cols; n++) {
      C.data[n] = ThreadLocalRandom.current().nextDouble(min, max);
    }
    return C;
  }

  /**
   * Returns the maximum number in the matrix.
   *
   * @return the maximum number in the matrix.
   */
  public double max() {
    return data[ArrayUtils.max(data)];
  }

  /**
   * Returns the minimum number in the matrix.
   *
   * @return the minimum number in the matrix.
   */
  public double min() {
    return data[ArrayUtils.min(data)];
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

  public int size() {
    return rows * cols;
  }

  /**
   * Set r-row, c-column value.
   *
   * @param r      row
   * @param c      column
   * @param scalar value
   */
  public void set(int r, int c, double scalar) {
    if (r >= 0 && r < this.rows && c >= 0 && c < this.cols) {
      data[r * cols + c] = scalar;
    } else {
      throw new IndexOutOfBoundsException(String.format("Matrix %dx%d -> [%d, %d]", this.rows, this.cols, r, c));
    }
  }

  public void set(final int idx, final double scalar) {
    if (idx >= 0 && idx < data.length) {
      data[idx] = scalar;
    } else {
      throw new IndexOutOfBoundsException(String.format("Matrix %dx%d -> [%d]", this.rows, this.cols, idx));
    }
  }

  /**
   * Set all values to the same scalar.
   *
   * @param scalar scalar
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
  public double get(final int r, final int c) {
    if (r >= 0 && r < this.rows && c >= 0 && c < this.cols) {
      return data[r * cols + c];
    } else {
      throw new IndexOutOfBoundsException(String.format("Matrix %dx%d -> [%d, %d]", this.rows, this.cols, r, c));
    }
  }

  public double get(final int idx) {
    if (idx >= 0 && idx < data.length) {
      return data[idx];
    } else {
      throw new IndexOutOfBoundsException(String.format("Matrix %dx%d -> [%d]", this.rows, this.cols, idx));
    }
  }

  /**
   * Returns <code>true</code> if the matrix is linear; <code>false</code>
   * otherwise.
   * 
   * @return <code>true</code> if the matrix is linear; <code>false</code>
   *         otherwise
   */
  public boolean isLinear() {
    return Properties.isLinear(rows, cols);
  }

  /**
   * Returns <code>true</code> if the matrix is a row matrix; <code>false</code>
   * otherwise.
   * 
   * @return <code>true</code> if the matrix is a row matrix; <code>false</code>
   *         otherwise
   */
  public boolean isRow() {
    return Properties.isRow(rows, cols);
  }

  /**
   * Returns <code>true</code> if the matrix is a column matrix;
   * <code>false</code> otherwise.
   * 
   * @return <code>true</code> if the matrix is a column matrix;
   *         <code>false</code> otherwise
   */
  public boolean isColumn() {
    return Properties.isColumn(rows, cols);
  }

  /**
   * Returns <code>true</code> if the matrix is a 1x1 matrix; <code>false</code>
   * otherwise.
   *
   * @return <code>true</code> if the matrix is a 1x1 matrix; <code>false</code>
   *         otherwise
   */
  public boolean is1x1() {
    return Properties.is1x1(rows, cols);
  }

  /**
   * Returns <code>true</code> if the matrix is square; <code>false</code>
   * otherwise.
   *
   * @return <code>true</code> if the matrix is square; <code>false</code>
   *         otherwise
   */
  public boolean isSquare() {
    return Properties.isSquare(rows, cols);
  }

  /**
   * Returns <code>true</code> if the matrix is zero; <code>false</code>
   * otherwise.
   *
   * @return <code>true</code> if the matrix is zero; <code>false</code> otherwise
   */
  public boolean isZero() {
    return Properties.isZero(data);
  }

  /**
   * Returns <code>true</code> if the matrix is the identity; <code>false</code>
   * otherwise.
   *
   * @return <code>true</code> if the matrix is the identity; <code>false</code>
   *         otherwise
   */
  public boolean isIdentity() {
    return Properties.isIdentity(data, rows, cols);
  }

  /**
   * Returns <code>true</code> if the matrix is a scalar matrix;
   * <code>false</code> otherwise.
   *
   * @return <code>true</code> if the matrix is a scalar matrix;
   *         <code>false</code> otherwise
   */
  public boolean isScalar() {
    return Properties.isScalar(data, rows, cols);
  }

  /**
   * Returns <code>true</code> if the matrix is a diagonal matrix;
   * <code>false</code> otherwise.
   *
   * @return <code>true</code> if the matrix is a diagonal matrix;
   *         <code>false</code> otherwise
   */
  public boolean isDiagonal() {
    return Properties.isDiagonal(data, rows, cols);
  }

  /**
   * Returns <code>true</code> if the matrix is a upper triangular matrix;
   * <code>false</code> otherwise.
   *
   * @return <code>true</code> if the matrix is a upper triangular matrix;
   *         <code>false</code> otherwise.
   */
  public boolean isUpperTriangular() {
    return Properties.isUpperTriangular(data, rows, cols);
  }

  /**
   * Returns <code>true</code> if the matrix is a lower triangular matrix;
   * <code>false</code> otherwise.
   *
   * @return <code>true</code> if the matrix is a lower triangular matrix;
   *         <code>false</code> otherwise.
   */
  public boolean isLowerTriangular() {
    return Properties.isLowerTriangular(data, rows, cols);
  }

  /**
   * Returns the transpose {@link Matrix}.
   *
   * @return the transpose {@link Matrix}
   */
  public Matrix transpose() {
    double[] t = new double[this.data.length];
    return new Matrix(cols, rows, Transpose.transpose(data, t, rows, cols));
  }

  /**
   * Re-organizes the internal representation and returns the transpose matrix.
   * The tranpose is done in-place with minimal auxiliar memory. It is slower than
   * the regular transpose method.
   *
   * @return the transpose matrix
   */
  public Matrix uTranspose() {
    Transpose.uTranspose(data, rows, cols);
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
    ArrayUtils.add(data, 0, B.data, 0, C.data, 0, data.length);
    return C;
  }

  public Matrix uAdd(Matrix B) {
    ArrayUtils.add(data, 0, B.data, 0, data, 0, data.length);
    return this;
  }

  public Matrix add(double scalar) {
    Matrix C = new Matrix(rows, cols);
    ArrayUtils.add(data, 0, scalar, C.data, 0, data.length);
    return C;
  }

  public Matrix uAdd(double scalar) {
    ArrayUtils.add(data, 0, scalar, data, 0, data.length);
    return this;
  }

  public Matrix sub(Matrix B) {
    Matrix C = new Matrix(rows, cols);
    ArrayUtils.sub(data, 0, B.data, 0, C.data, 0, data.length);
    return C;
  }

  public Matrix uSub(Matrix B) {
    ArrayUtils.sub(data, 0, B.data, 0, data, 0, data.length);
    return this;
  }

  public Matrix sub(double b) {
    Matrix C = new Matrix(rows, cols);
    ArrayUtils.sub(this.data, 0, b, C.data, 0, data.length);
    return C;
  }

  public Matrix uSubRow(int row, int column, Vector b) {
    ArrayUtils.sub(data, row * cols + column, b.data, b.bIdx, data, row * cols + column, b.data.length);
    return this;
  }

  /**
   * Return a matrix multiplied by a scalar.
   * 
   * @param scalar
   * @return a matrix multiplied by a scalar
   */
  public Matrix mul(double scalar) {
    Matrix C = new Matrix(rows, cols);
    ArrayUtils.mul(data, 0, scalar, C.data, 0, data.length);
    return C;
  }

  /**
   * Updates the matrix by multiplying all the values by a scalar.
   * 
   * @param scalar scalar to be used
   */
  public Matrix uMul(double scalar) {
    ArrayUtils.mul(data, 0, scalar, this.data, 0, data.length);
    return this;
  }

  /**
   * Updates the matrix by dividing all the values by a scalar.
   * 
   * @param scalar scalar to be used
   */
  public Matrix uDiv(double scalar) {
    ArrayUtils.div(data, 0, scalar, this.data, 0, data.length);
    return this;
  }

  /**
   * Return a matrix where one column was updated by adding a scalar.
   * 
   * @param column the index of the column
   * @param scalar scalar to be used
   * @return a matrix where one column was updated by adding a scalar
   */
  public Matrix addColumn(int column, double scalar) {
    Matrix C = new Matrix(rows, cols + 1);
    for (int i = 0, j = 0; i < C.data.length; i++)
      if (column == i % C.cols)
        C.data[i] = scalar;
      else
        C.data[i] = data[j++];
    return C;
  }

  /**
   *
   * @param v
   * @return
   */
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

  /**
   *
   * @param B
   * @return
   */
  public Matrix mul(Matrix B) {
    Matrix C = new Matrix(rows, B.cols);
    Multiplication.mul(data, B.data, C.data, rows, B.cols, cols);
    return C;
  }

  /**
   * TODO: Finish this with other version of HouseHolder...
   * 
   * @return
   */
  /*
   * public Matrix triangular() { Matrix T = transpose(); for (int k = 0; k < rows
   * - 1; k++) { QR.householder(T.data, T.rows, T.cols, k, k); } return
   * T.uTranspose(); }
   */

  /**
   *
   * @return
   */
  public Matrix[] qr() {
    double[][] qr = QR.qr(data, rows, cols);
    return new Matrix[] { new Matrix(rows, cols, qr[0]), new Matrix(rows, cols, qr[1]) };
  }

  /**
   * TODO: Review this method Need to add urls and other links
   *
   * @return
   */
  public Matrix[] bidiagonal() {
    Matrix U = Matrix.identity(rows), B = transpose(), V = Matrix.identity(cols);

    for (int k = 0; k < rows - 1; k++) {
      QR.householder(B.data, U.data, rows, cols, cols, rows, k, k);
      B.uTranspose();
      // QR.householder(B.data, V.data, rows, cols, k, k + 1);
      QR.householder(B.data, V.data, cols, rows, cols, rows, k, k + 1);
      B.uTranspose();
    }

    return new Matrix[] { U, B.uTranspose(), V };
  }

  private double[] diagArray(int n) {
    double[] d;
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

  /**
   * @return
   */
  public Matrix[] svd() {
    Matrix UDV[] = bidiagonal();

    // TODO: bidiag to diag
    double diag[] = UDV[1].diagArray(0);
    double lambda[] = new double[diag.length], mu[] = new double[diag.length];
    // PrintUtils.printArray(diag);
    lambda[diag.length - 1] = Math.abs(diag[diag.length - 1]);

    for (int i = diag.length - 1; i > 0; i--)
      lambda[i] = Math.abs(diag[i]) * lambda[i + 1];

    return UDV;
  }

  /**
   * Returns the derterminante of a {@link Matrix}.
   * 
   * @return
   */
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
      for (int k = 0; k < rows - 1; k++) {
        // TODO: Fix this...
        /*
         * if (QR.householder(T.data, null, T.rows, T.cols, k, k)) { hh++; }
         */
      }
      T.uTranspose();
      rv = 1.0;
      for (int i = 0; i < T.rows; i++)
        rv *= T.data[i * T.cols + i];
      rv *= Math.pow(-1.0, hh);
    }
    return rv;
  }

  /**
   *
   * @param k
   * @param n
   * @param e
   * @return
   */
  public Matrix[] nmf(final int k, final int n, final double e) {
    double[][] wh = NMF.nmf_mu(data, rows, cols, k, n, e);
    return new Matrix[] { new Matrix(rows, k, wh[0]), new Matrix(k, cols, wh[1]) };
  }

  /**
   *
   * @param k
   * @return
   */
  public Matrix[] nmf(final int k) {
    return nmf(k, 100, 0.01);
  }

  /**
   *
   * @param k
   * @return
   */
  public Matrix[] nmf(final int k, Matrix Mask) {
    double[][] wh = NMF.nmf_mu_imputation(data, Mask.data, rows, cols, k, 1000, 0.01);
    return new Matrix[] { new Matrix(rows, k, wh[0]), new Matrix(k, cols, wh[1]) };
  }

  /**
   * @return
   */
  public Matrix chol() {
    return new Matrix(rows, cols, Cholesky.chol(data, rows, cols));
  }

  /**
   *
   * @param row
   * @param column
   * @return
   */
  public Vector vector(int row, int column) {
    return new Vector(data, row * cols + column, cols - column);
  }

  /**
   *
   * @return
   */
  public Vector vector() {
    return new Vector(data, 0, rows * cols);
  }

  /**
   *
   * @param b
   * @param p
   * @return
   */
  public double minkowskiDistance(Matrix b, int p) {
    return ArrayUtils.minkowskiDistance(data, 0, b.data, 0, data.length, p);
  }

  /**
   *
   * @param b
   * @return
   */
  public double euclideanDistance(Matrix b) {
    return ArrayUtils.euclideanDistance(data, 0, b.data, 0, data.length);
  }

  /**
   * Returns the Manhattan Distance betweem this {@link Matrix} with
   * {@link Matrix} B
   * 
   * @param b
   * @return
   */
  public double manhattanDistance(Matrix b) {
    return ArrayUtils.manhattanDistance(data, 0, b.data, 0, data.length);
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
          rv = Arrays.equals(data, B.data);
        }
      }
    }
    return rv;
  }

  /**
   * @param M
   * @param eps
   * @return
   */
  public boolean equals(Matrix M, double eps) {
    boolean rv = false;

    if (rows == M.rows && cols == M.cols) {
      rv = ArrayUtils.equals(data, M.data, eps);
    }

    return rv;
  }

  public Matrix mask() {
    Matrix RV = new Matrix(this.rows, this.cols);
    for (int i = 0; i < this.data.length; i++) {
      if (data[i] != 0.0) {
        RV.data[i] = 1.0;
      }
    }
    return RV;
  }

  @Override
  public int hashCode() {
    int prime = 31, result = 1;
    result = prime * result + (int) rows;
    result = prime * result + (int) cols;
    result = prime * result + data.hashCode();
    return result;
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

  @Override
  public double distanceTo(Matrix M) {
    return ArrayUtils.euclideanDistance(data, M.data);
  }
}
