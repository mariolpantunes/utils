package pt.it.av.tnav.utils.bla.determinant;

public class Determinant {
  private Determinant() {
  }

  public static double naive_det(final double m[], final int rows, final int cols) {
    double rv = 0.0;
    if (cols == 1 && rows == 1) {
      rv = m[0];
    } else if (cols == 2 && rows == 2) {
      rv = m[0] * m[3] - m[2] * m[1];
    } else if (cols == 3 && rows == 3) {
      rv = (m[0] * m[4] * m[8] + m[1] * m[5] * m[6] + m[2] * m[3] * m[7]) -
          (m[2] * m[4] * m[6] + m[1] * m[3] * m[8] + m[0] * m[5] * m[7]);
    } else {
      for (int j1 = 0; j1 < rows; j1++) {
        double t[] = new double[(rows - 1) * (cols - 1)];
        for (int i = 1; i < rows; i++) {
          int j2 = 0;
          for (int j = 0; j < rows; j++) {
            if (j == j1)
              continue;
            t[(i - 1) * (cols - 1) + j2] = m[i * cols + j];
            j2++;
          }
        }
        rv += Math.pow(-1.0, 1.0 + j1 + 1.0) * m[0 * cols + j1] * naive_det(t, rows - 1, cols - 1);
      }
    }
    return rv;
  }
}
