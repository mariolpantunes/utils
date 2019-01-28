package pt.it.av.tnav.utils.bla.factorization;

import pt.it.av.tnav.utils.ArrayUtils;

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
   * @param h
   * @param rows
   * @param cols
   * @param r
   * @param c
   * @return
   */
  public static boolean householder(final double m[], final double h[], final int rows,
                                    final int cols, final int r, final int c) {
    boolean rv = false;

    //Vector v = M.vector(r, c);

    int bIdx = r * cols + c, len = cols - c;

    //return new Vector(data, , cols - column);
    //double d = v.norm(2);
    double d = ArrayUtils.norm(m, bIdx, len, 2);

    if (d != m[bIdx]) {
      rv = true;
      /*if (m[bIdx] > 0) {
        d = -d;
      }*/
      d = (m[bIdx] > 0) ? -d : d;
      m[bIdx] -= d;
      double f1 = Math.sqrt(-2 * m[bIdx] * d);
      //v = v.div(f1);
      ArrayUtils.div(m, bIdx, f1, m, bIdx, len);
      for (int i = c; i < cols; i++) {
        m[r * cols + i] = 0.0;
      }
      m[r * cols + c] = d;
      double t[] = new double[len];
      for (int i = r + 1; i < rows; i++) {
        //M.uSubRow(i, c, v.mul(2.0 * v.innerProduct(M.vector(i, c))));
        double s = 2.0 * ArrayUtils.dotProduct(m, bIdx, m, i * cols + c, len);
        ArrayUtils.mul(m, bIdx, s, t, 0, len);
        ArrayUtils.sub(m, i * cols + c, t, 0, m, i * cols + c, len);
      }
      if (h != null) {
        //for (int i = 0; i < H.rows; i++) {
        for (int i = 0; i < rows; i++) {
          //H.uSubRow(i, c, v.mul(2.0 * v.innerProduct(H.vector(i, c))));
          double s = 2.0 * ArrayUtils.dotProduct(h, bIdx, h, i * cols + c, len);
          ArrayUtils.mul(m, bIdx, s, t, 0, len);
          ArrayUtils.sub(h, i * cols + c, t, 0, h, i * cols + c, len);
        }
      }
    }

    return rv;
  }
}
