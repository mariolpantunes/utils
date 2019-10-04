package pt.it.av.tnav.utils.bla.factorization;


/**
 * Cholesky factorization.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 */
public class Cholesky {
  private Cholesky() {
  }

  public static double[] chol(final double data[], final int rows, final int cols) {
    double c[] = new double[data.length];

    for (int i = 0; i < rows; i++) {
      for (int k = 0; k < (i + 1); k++) {
        double sum = 0.0;
        for (int j = 0; j < k; j++) {
          sum += c[i * cols + j] * c[k * cols + j];
        }

        if (i == k) {
          double t = data[i * cols + i] - sum;
          if (t > 0) {
            c[i * cols + k] = Math.sqrt(t);
          } else {
            throw new IllegalArgumentException("The input is not a positive-definite matrix.");
          }
        } else {
          if (c[k * cols + k] != 0) {
            c[i * cols + k] = (data[i * cols + k] - sum) / c[k * cols + k];
          } else {
            throw new IllegalArgumentException("The input is not a positive-definite matrix.");
          }
        }
      }
    }

    return c;
  }
}
