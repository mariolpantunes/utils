package pt.it.av.tnav.utils.bla.factorization;

import pt.it.av.tnav.utils.bla.Matrix;

/**
 * Cholesky factorization.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 */
public class Cholesky {
  private Cholesky() {
  }

  public static Matrix chol(final double data[], final int rows, final int cols) {
    double c[] = new double[data.length];

    for (int i = 0; i < rows; i++) {
      for (int k = 0; k < (i + 1); k++) {
        double sum = 0.0;
        for (int j = 0; j < k; j++) {
          sum += c[i * cols + j] * c[k * cols + j];
        }
        c[i * cols + k] = (i == k) ? Math.sqrt(data[i * cols + i] - sum) : (1.0 / c[k * cols + k] * (data[i * cols + k] - sum));
      }
    }

    return new Matrix(rows, cols, c);
  }
}
