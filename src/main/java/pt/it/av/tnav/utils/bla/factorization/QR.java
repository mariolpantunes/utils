package pt.it.av.tnav.utils.bla.factorization;

import pt.it.av.tnav.utils.ArrayUtils;
import pt.it.av.tnav.utils.bla.transpose.Transpose;

/**
 * QR factorization.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 */
public class QR {
  private QR() {
  }

  /**
   * @param m
   * @param rows
   * @param cols
   * @return
   */
  public static double[][] qr(final double[] m, final int rows, final int cols) {
    double[] q = ArrayUtils.identity(rows, cols), r = new double[rows * cols];
    Transpose.transpose(m, r, rows, cols);

    for (int k = 0; k < rows - 1; k++) {
      QR.householder(r, q, cols, rows, rows, cols, k, k);
    }

    return new double[][]{q, Transpose.uTranspose(r, cols, rows)};
  }

  /**
   * @param m
   * @param h
   * @param rows
   * @param cols
   * @param r
   * @param c
   * @return
   */
  public static void householder(final double[] m, final double[] h, final int mRows,
                                 final int mCols, final int hRows, final int hCols,
                                 final int r, final int c) {
    int bIdx = r * mCols + c, len = mCols - c;
    double d = ArrayUtils.norm(m, bIdx, len, 2);

    if (d != m[bIdx]) {
      d = (m[bIdx] > 0) ? -d : d;
      m[bIdx] -= d;
      double[] v = new double[len];
      double f1 = Math.sqrt(-2 * m[bIdx] * d);
      ArrayUtils.div(m, bIdx, f1, v, 0, len);
      for (int i = c; i < mCols; i++) {
        m[r * mCols + i] = 0.0;
      }
      m[r * mCols + c] = d;

      double[] t = new double[len];

      for (int i = r + 1; i < mRows; i++) {
        double s = 2.0 * ArrayUtils.dotProduct(v, 0, m, i * mCols + c, len);
        ArrayUtils.mul(v, 0, s, t, 0, len);
        ArrayUtils.sub(m, i * mCols + c, t, 0, m, i * mCols + c, len);
      }

      for (int i = 0; i < hRows; i++) {
        double s = 2.0 * ArrayUtils.dotProduct(v, 0, h, i * hCols + c, len);
        ArrayUtils.mul(v, 0, s, t, 0, len);
        ArrayUtils.sub(h, i * hCols + c, t, 0, h, i * hCols + c, len);
      }
    }
  }
}
