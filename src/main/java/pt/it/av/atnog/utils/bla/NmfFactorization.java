package pt.it.av.atnog.utils.bla;

import pt.it.av.atnog.utils.ArrayUtils;
import pt.it.av.atnog.utils.MathUtils;

import java.util.Arrays;

/**
 * Non negative matrix factorization.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 */
public class NmfFactorization {

  /**
   * @param k
   * @param n
   * @param e
   * @return
   */
  public static Matrix[] nmf_mu(Matrix v, int k, int n, double e) {
    double max = v.max(), eps = MathUtils.eps();
    Matrix w = Matrix.random(v.rows, k, 0.0, max);
    Matrix h = Matrix.random(k, v.cols, 0.0, max);

    // compute WH matrix
    Matrix wh = w.mul(h);
    double cost = ArrayUtils.euclideanDistance(v.data, 0, wh.data, 0, v.data.length);

    for (int i = 0; i < n && cost > e; i++) {
      // update feature matrix.
      Matrix wt = w.transpose();
      //MatrixTranspose.cotr(w.data, wt.data, w.rows, w.cols);

      Matrix hn = wt.mul(v);
      //hn = MatrixMultiplication.mul(wt, v.data, hn, w.cols, v.cols, v.rows);
      Matrix hd = wt.mul(wh);
      //hd = MatrixMultiplication.mul(wt, wh, hd, w.cols, v.cols, v.rows);

      ArrayUtils.add(hd.data, 0, eps, hd.data, 0, hd.data.length);

      //h.smultEq(hn.smultEq(hd.sinvEq()));
      ArrayUtils.mulDiv(h.data, 0, hn.data, 0, hd.data, 0, h.data, 0, k * v.cols);

      // update weights matrix
      Matrix ht = h.transpose();
      //ht = MatrixTranspose.cotr(h.data, ht, h.rows, h.cols);
      Matrix wn = v.mul(ht);
      //wn = MatrixMultiplication.mul(v.data, ht, wn, v.rows, h.rows, v.cols);
      Matrix wd = w.mul(h).mul(ht);
      //hht = MatrixMultiplication.mult(h.data, h.data, hht, h.rows, h.rows, h.cols);
      //wd = MatrixMultiplication.mul(w.data, hht, wd, w.rows, h.rows, w.cols);

      ArrayUtils.add(wd.data, 0, eps, wd.data, 0, wd.data.length);

      //w.smultEq(wn.smultEq(wd.sinvEq()));
      ArrayUtils.mulDiv(w.data, 0, wn.data, 0, wd.data, 0, w.data, 0, k * v.rows);

      // compute WH matrix
      wh = w.mul(h);
      cost = ArrayUtils.euclideanDistance(v.data, 0, wh.data, 0, v.data.length);
    }
    return new Matrix[]{w, h};
  }

  public static Matrix[] nmf_mu2(final Matrix v, final int k, final int n, final double e) {
    double max = v.max(), eps = MathUtils.eps();
    Matrix w = Matrix.random(v.rows, k, 0.0, max);
    Matrix h = Matrix.random(k, v.cols, 0.0, max);

    double wh[] = new double[v.data.length], wt[] = new double[w.data.length],
        hn[] = new double[w.cols * v.cols], hd[] = new double[w.cols * v.cols],
        wn[] = new double[v.rows * h.rows], hht[] = new double[h.rows * h.rows],
        wd[] = new double[w.rows * h.rows];

    // compute WH matrix
    MatrixMultiplication.mul(w.data, h.data, wh, v.rows, v.cols, k);
    double cost = ArrayUtils.euclideanDistance(v.data, 0, wh, 0, v.data.length);

    for (int i = 0; i < n && cost > e; i++) {
      // update feature matrix.
      MatrixTranspose.cotr(w.data, wt, w.rows, w.columns());
      MatrixMultiplication.mul(wt, v.data, hn, w.cols, v.cols, w.rows);
      MatrixMultiplication.mul(wt, wh, hd, w.cols, v.cols, w.rows);

      ArrayUtils.add(hd, 0, eps, hd, 0, hd.length);
      ArrayUtils.mulDiv(h.data, 0, hn, 0, hd, 0, h.data, 0, k * v.cols);

      Arrays.fill(wh, 0.0);
      Arrays.fill(hn, 0.0);
      Arrays.fill(hd, 0.0);

      MatrixMultiplication.mult(v.data, h.data, wn, v.rows, h.rows, v.cols);
      MatrixMultiplication.mult(h.data, h.data, hht, h.rows, h.rows, h.cols);
      MatrixMultiplication.mul(w.data, hht, wd, w.rows, h.rows, w.cols);

      ArrayUtils.add(wd, 0, eps, wd, 0, wd.length);

      ArrayUtils.mulDiv(w.data, 0, wn, 0, wd, 0, w.data, 0, k * v.rows);

      Arrays.fill(wd, 0.0);

      // compute WH matrix
      MatrixMultiplication.mul(w.data, h.data, wh, v.rows, v.cols, k);
      cost = ArrayUtils.euclideanDistance(v.data, 0, wh, 0, v.data.length);
    }
    return new Matrix[]{w, h};
  }
}
