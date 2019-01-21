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
        if (i == k) {
          c[i * cols + k] = Math.sqrt(data[i * cols + i] - sum);
        } else {
          c[i * cols + k] = (data[i * cols + k] - sum);
          if (c[k * cols + k] != 0) {
            c[i * cols + k] /= c[k * cols + k];
          }
        }
      }
    }

    return new Matrix(rows, cols, c);
  }
}
