package pt.it.av.atnog.utils.bla;

import pt.it.av.atnog.utils.ArrayUtils;

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
    double max = v.max(), eps = Math.ulp(1.0);
    Matrix w = Matrix.random(v.rows, k, 0.0, max);
    Matrix h = Matrix.random(k, v.cols, 0.0, max);

    double wh[] = new double[v.data.length], wt[] = new double[w.data.length],
        hn[] = new double[w.cols * v.cols], hd[] = new double[w.cols * v.cols],
        wn[] = new double[v.rows * h.rows], hht[] = new double[h.rows * h.rows],
        wd[] = new double[w.rows * h.rows];

    // compute WH matrix
    //Matrix wh = w.mul(h);
    MatrixMultiplication.fmul(w.data, h.data, wh, v.rows, v.cols, k);
    double cost = ArrayUtils.euclideanDistance(v.data, 0, wh, 0, v.data.length);

    for (int i = 0; i < n && cost > e; i++) {
      // update feature matrix.
      //Matrix wt = w.transpose();
      MatrixTranspose.transpose(w.data, wt, w.rows, w.columns());
      //Matrix hn = wt.mul(v);
      MatrixMultiplication.fmul(wt, v.data, hn, w.cols, v.cols, w.rows);
      //Matrix hd = wt.mul(wh);
      MatrixMultiplication.fmul(wt, wh, hd, w.cols, v.cols, w.rows);

      ArrayUtils.add(hd, 0, eps, hd, 0, hd.length);
      //ArrayUtils.add(hd.data, 0, Double.MIN_VALUE, hd.data, 0, hd.data.length);

      //h.smultEq(hn.smultEq(hd.sinvEq()));
      //ArrayUtils.div(hn.data, 0, hd.data, 0, temp1, 0, k * v.cols);
      //ArrayUtils.mul(h.data, 0, temp1, 0, h.data, 0, k * v.cols);
      ArrayUtils.mulDiv(h.data, 0, hn, 0, hd, 0, h.data, 0, k * v.cols);

      // update weights matrix
      //Matrix ht = h.transpose();
      //Matrix wn = v.mul(ht);
      MatrixMultiplication.fmul_t(v.data, h.data, wn, v.rows, h.rows, v.cols);
      //Matrix wd = w.mul(h).mul(ht);
      //MatrixMultiplication.fmul(w.data, h.data, wh, v.rows, v.cols, k);
      //MatrixMultiplication.fmul_t(wh,h.data,wd,w.rows , h.rows, h.cols);
      MatrixMultiplication.fmul_t(h.data, h.data, hht, h.rows, h.rows, h.cols);
      MatrixMultiplication.fmul(w.data, hht, wd, w.rows, h.rows, w.cols);

      ArrayUtils.add(wd, 0, eps, wd, 0, wd.length);
      //ArrayUtils.add(wd.data, 0, Double.MIN_VALUE, wd.data, 0, wd.data.length);

      //w.smultEq(wn.smultEq(wd.sinvEq()));
      //ArrayUtils.div(wn.data, 0, wd.data, 0, temp1, 0, v.rows * k);
      //ArrayUtils.mul(w.data, 0, temp1, 0, w.data, 0, v.rows * k);
      ArrayUtils.mulDiv(w.data, 0, wn, 0, wd, 0, w.data, 0, k * v.rows);

      // compute WH matrix
      MatrixMultiplication.fmul(w.data, h.data, wh, v.rows, v.cols, k);
      cost = ArrayUtils.euclideanDistance(v.data, 0, wh, 0, v.data.length);
    }
    return new Matrix[]{w, h};
  }
}
