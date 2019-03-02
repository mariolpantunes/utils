package pt.it.av.tnav.utils.bla.properties;

import pt.it.av.tnav.utils.MathUtils;

public class Properties {
  private Properties() {
  }

  /**
   * Returns <code>true</code> if the matrix is square; <code>false</code> otherwise.
   *
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @return <code>true</code> if the matrix is square; <code>false</code> otherwise
   */
  public static boolean isSquare(final int rows, final int cols) {
    return rows == cols;
  }

  /**
   * Returns <code>true</code> if the matrix is a row matrix; <code>false</code> otherwise.
   *
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @return <code>true</code> if the matrix is a row matrix; <code>false</code> otherwise
   */
  public static boolean isRow(final int rows, final int cols) {
    return rows == 1 && cols > 1;
  }

  /**
   * Returns <code>true</code> if the matrix is a column matrix; <code>false</code> otherwise.
   *
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @return <code>true</code> if the matrix is a column matrix; <code>false</code> otherwise
   */
  public static boolean isColumn(final int rows, final int cols) {
    return cols == 1 && rows > 1;
  }

  /**
   * Returns <code>true</code> if the matrix is a 1x1 matrix; <code>false</code> otherwise.
   *
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @return <code>true</code> if the matrix is a 1x1 matrix; <code>false</code> otherwise
   */
  public static boolean is1x1(final int rows, final int cols) {
    return cols == 1 && rows == 1;
  }

  /**
   * Returns <code>true</code> if the matrix is a linear (either column or row);
   * <code>false</code> otherwise.
   *
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @return <code>true</code> if the matrix is a linear (either column or row);
   * <code>false</code> otherwise
   */
  public static boolean isLinear(final int rows, final int cols) {
    return cols == 1 && rows > 1 || rows == 1 && cols > 1;
  }

  /**
   * Returns <code>true</code> if the matrix is zero; <code>false</code> otherwise.
   *
   * @param m array that contains the matrix data
   * @return <code>true</code> if the matrix is zero; <code>false</code> otherwise
   */
  public static boolean isZero(final double[] m) {
    boolean rv = true;

    for (int i = 0; i < m.length && rv; i++) {
      if (!MathUtils.equals(m[i], 0.0)) {
        rv = false;
      }
    }

    return rv;
  }

  /**
   * Returns <code>true</code> if the matrix is the identity; <code>false</code> otherwise.
   *
   * @param m    array that contains the matrix data
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @return <code>true</code> if the matrix is the identity; <code>false</code> otherwise
   */
  public static boolean isIdentity(final double[] m, final int rows, final int cols) {
    boolean rv = isSquare(rows, cols);

    for (int i = 0; i < rows && rv; i++) {
      for (int j = 0; j < cols && rv; j++) {
        if (i == j) {
          if (!MathUtils.equals(m[i * cols + j], 1.0)) {
            rv = false;
          }
        } else {
          if (!MathUtils.equals(m[i * cols + j], 0.0)) {
            rv = false;
          }
        }
      }
    }

    return rv;
  }

  /**
   * Returns <code>true</code> if the matrix is a scalar matrix; <code>false</code>
   * otherwise.
   *
   * @param m    array that contains the matrix data
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @return <code>true</code> if the matrix is a scalar matrix; <code>false</code> otherwise
   */
  public static boolean isScalar(final double[] m, final int rows, final int cols) {
    boolean rv = isSquare(rows, cols);

    double t = m[0];

    if (MathUtils.equals(t, 1.0)) {
      rv = false;
    }

    for (int i = 0; i < rows && rv; i++) {
      for (int j = 0; j < cols && rv; j++) {
        if (i == j) {
          if (!MathUtils.equals(m[i * cols + j], t)) {
            rv = false;
          }
        } else {
          if (!MathUtils.equals(m[i * cols + j], 0.0)) {
            rv = false;
          }
        }
      }
    }

    return rv;
  }

  /**
   * Returns <code>true</code> if the matrix is a diagonal matrix; <code>false</code> otherwise.
   *
   * @param m    array that contains the matrix data
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @return <code>true</code> if the matrix is a diagonal matrix; <code>false</code> otherwise
   */
  public static boolean isDiagonal(final double[] m, final int rows, final int cols) {
    boolean rv = isSquare(rows, cols);
    int countNonZeros = 0;

    for (int i = 0; i < rows && rv; i++) {
      for (int j = 0; j < cols && rv; j++) {
        if (i == j) {
          if (!MathUtils.equals(m[i * cols + j], 0.0)) {
            countNonZeros++;
          }
        } else {
          if (!MathUtils.equals(m[i * cols + j], 0.0)) {
            rv = false;
          }
        }
      }
    }

    if (rv && countNonZeros == 0) {
      rv = false;
    }

    return rv;
  }

  /**
   * Returns <code>true</code> if the matrix is a upper triangular matrix; <code>false</code>
   * otherwise.
   *
   * @param m    array that contains the matrix data
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @return <code>true</code> if the matrix is a upper triangular matrix; <code>false</code>
   * otherwise
   */
  public static boolean isUpperTriangular(final double[] m, final int rows, final int cols) {
    boolean rv = isSquare(rows, cols);
    int countNonZeros = 0;

    for (int i = 0; i < rows && rv; i++) {
      for (int j = 0; j < cols && rv; j++) {
        if (i <= j) {
          if (!MathUtils.equals(m[i * cols + j], 0.0)) {
            countNonZeros++;
          }
        } else {
          if (!MathUtils.equals(m[i * cols + j], 0.0)) {
            rv = false;
          }
        }
      }
    }

    if (rv && countNonZeros == 0) {
      rv = false;
    }

    return rv;
  }

  /**
   * Returns <code>true</code> if the matrix is a lower triangular matrix; <code>false</code>
   * otherwise.
   *
   * @param m    array that contains the matrix data
   * @param rows number of rows in the matrix
   * @param cols number of columns in the matrix
   * @return <code>true</code> if the matrix is a lower triangular matrix; <code>false</code>
   * otherwise
   */
  public static boolean isLowerTriangular(final double[] m, final int rows, final int cols) {
    boolean rv = isSquare(rows, cols);
    int countNonZeros = 0;

    for (int i = 0; i < rows && rv; i++) {
      for (int j = 0; j < cols && rv; j++) {
        if (i >= j) {
          if (!MathUtils.equals(m[i * cols + j], 0.0)) {
            countNonZeros++;
          }
        } else {
          if (!MathUtils.equals(m[i * cols + j], 0.0)) {
            rv = false;
          }
        }
      }
    }

    if (rv && countNonZeros == 0) {
      rv = false;
    }

    return rv;
  }
}
