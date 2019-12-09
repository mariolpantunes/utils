package pt.it.av.tnav.utils.bla.factorization;

import pt.it.av.tnav.utils.ArrayUtils;
import pt.it.av.tnav.utils.MathUtils;
import pt.it.av.tnav.utils.bla.multiplication.Multiplication;
import pt.it.av.tnav.utils.bla.transpose.Transpose;

import java.util.Arrays;

/**
 * Non negative matrix factorization.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 */
public class NMF {

  /**
   *
   */
  private NMF() {
  }

  /**
   * @param a
   * @param bA
   * @param b
   * @param bB
   * @param c
   * @param bC
   * @param r
   * @param bR
   * @param len
   */
  private static void mulDiv(final double[] a, final int bA, final double[] b, final int bB, final double[] c,
      final int bC, final double[] r, final int bR, final int len) {
    double beta = 0.001;
    for (int i = 0; i < len; i++) {
      r[bR + i] = a[bA + i] * ((b[bB + i] - beta*a[bA + i])  / c[bC + i]);
    }
  }

  /**
   * @param data
   * @param rows
   * @param cols
   * @param k
   * @param n
   * @param e
   * @return
   */
  public static double[][] nmf_mu(final double data[], final int rows, final int cols, final int k, final int n,
      final double e) {
    double max = data[ArrayUtils.max(data)], min = data[ArrayUtils.min(data)], eps = MathUtils.eps();

    double w[] = ArrayUtils.random(rows * k, min, max), h[] = ArrayUtils.random(k * cols, min, max),
        wh[] = new double[data.length], wt[] = new double[w.length], hn[] = new double[k * cols],
        hd[] = new double[k * cols], wn[] = new double[rows * k], hht[] = new double[k * k],
        wd[] = new double[rows * k];

    // compute WH matrix
    Multiplication.mul(w, h, wh, rows, cols, k);
    double cost = ArrayUtils.euclideanDistance(data, 0, wh, 0, data.length);

    for (int i = 0; i < n && cost > e; i++) {
      // update feature matrix.
      Transpose.cotr(w, wt, rows, k);
      Multiplication.mul(wt, data, hn, k, cols, rows);
      
      Multiplication.mul(wt, wh, hd, k, cols, rows);

      //ArrayUtils.add(hd, 0, eps, hd, 0, hd.length);
      ArrayUtils.set_neg(hd, eps);
      mulDiv(h, 0, hn, 0, hd, 0, h, 0, k * cols);

      Arrays.fill(wh, 0.0);
      Arrays.fill(hn, 0.0);
      Arrays.fill(hd, 0.0);

      Multiplication.mult(data, h, wn, rows, k, cols);
      Multiplication.mult(h, h, hht, k, k, cols);
      Multiplication.mul(w, hht, wd, rows, k, k);

      //ArrayUtils.add(wd, 0, eps, wd, 0, wd.length);
      ArrayUtils.set_neg(wd, eps);
      mulDiv(w, 0, wn, 0, wd, 0, w, 0, k * rows);

      Arrays.fill(wd, 0.0);

      // compute WH matrix
      Multiplication.mul(w, h, wh, rows, cols, k);
      cost = ArrayUtils.euclideanDistance(data, 0, wh, 0, data.length);
    }

    return new double[][] { w, h };
  }

  public static double[][] nmf_mu_imputation(final double data[], final double mask[], final int rows, final int cols, final int k,
      final int n, final double e) {
    double max = data[ArrayUtils.max(data)], min = data[ArrayUtils.min(data)], eps = MathUtils.eps();
    double w[] = ArrayUtils.random(rows * k, min, max), h[] = ArrayUtils.random(k * cols, min, max),
    wh[] = new double[data.length], wt[] = new double[w.length], hn[] = new double[k * cols],
    hd[] = new double[k * cols], wn[] = new double[rows * k], hht[] = new double[k * k],
    wd[] = new double[rows * k];
      
    double z[] = new double[data.length];

    // compute WH matrix
    Multiplication.mul(w, h, wh, rows, cols, k);
    
    for(int i = 0; i < mask.length; i++) {
      if(mask[i] == 1) {
        z[i] = data[i];
      } else {
        z[i] = wh[i];
      }
    }

    //System.out.println(PrintUtils.array(data));
    //System.out.println(PrintUtils.array(z));
    double cost = ArrayUtils.euclideanDistance(z, 0, wh, 0, z.length);
    for (int i = 0; i < n && cost > e; i++) {
      // update feature matrix.
      Transpose.cotr(w, wt, rows, k);
      Multiplication.mul(wt, data, hn, k, cols, rows);
      Multiplication.mul(wt, wh, hd, k, cols, rows);

      //ArrayUtils.add(hd, 0, eps, hd, 0, hd.length);
      ArrayUtils.set_neg(hd, eps);
      mulDiv(h, 0, hn, 0, hd, 0, h, 0, k * cols);

      Arrays.fill(wh, 0.0);
      Arrays.fill(hn, 0.0);
      Arrays.fill(hd, 0.0);

      Multiplication.mult(data, h, wn, rows, k, cols);
      Multiplication.mult(h, h, hht, k, k, cols);
      Multiplication.mul(w, hht, wd, rows, k, k);

      //ArrayUtils.add(wd, 0, eps, wd, 0, wd.length);
      ArrayUtils.set_neg(wd, eps);
      mulDiv(w, 0, wn, 0, wd, 0, w, 0, k * rows);
      Arrays.fill(wd, 0.0);

      // compute WH matrix
      Multiplication.mul(w, h, wh, rows, cols, k);
      for(int j = 0; j < mask.length; j++) {
        if(mask[j] == 1) {
          z[j] = data[j];
        } else {
          z[j] = wh[j];
        }
      }
      cost = ArrayUtils.euclideanDistance(data, 0, z, 0, z.length);
      //System.out.println("IT = "+i);
    }
    //System.out.println(PrintUtils.array(data));
    //System.out.println(PrintUtils.array(z));
    return new double[][] { w, h };
  }

  public static double[][] hals(final double m[], final int rows, final int cols, final int k,
  final int n) {
    double alpha = 1, delta = 0.01, relTol = 1e-5;
    double max = m[ArrayUtils.max(m)], min = m[ArrayUtils.min(m)], eps = MathUtils.eps();
    double u[] = ArrayUtils.random(rows * k, min, max), v[] = ArrayUtils.random(k * cols, min, max),
    A[] = {00,00};
    
    
    return new double[][] { u, v };
  }
}
