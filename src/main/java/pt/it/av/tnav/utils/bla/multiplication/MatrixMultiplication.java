package pt.it.av.tnav.utils.bla.multiplication;

import pt.it.av.tnav.utils.parallel.threadPool.StaticThreadPool;
import pt.it.av.tnav.utils.parallel.threadPool.ThreadPool;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Several implementation of matrix multiplication.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class MatrixMultiplication {

  private MatrixMultiplication() {
  }

  private static final int P_MUL_SIZE = 4096;
  private static final int BLK = 64;

  /**
   * @param a
   * @param b
   * @param c
   * @param m
   * @param n
   * @param p
   */
  public static void ijk(double a[], double b[], double c[], int m, int n, int p) {
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        double v = 0.0;
        for (int k = 0; k < p; k++) {
          v += a[i * p + k] * b[k * n + j];
        }
        c[i * n + j] = v;
      }
    }
  }

  /**
   * @param a
   * @param b
   * @param c
   * @param m
   * @param n
   * @param p
   */
  public static void ikj(double a[], double b[], double c[], int m, int n, int p) {
    for (int i = 0; i < m; i++) {
      int in = i * n;
      for (int k = 0; k < p; k++) {
        int kn = k * n;
        double v = a[i * p + k];
        for (int j = 0; j < n; j++) {
          c[in + j] += v * b[kn + j];
        }
      }
    }
  }

  /**
   * @param a
   * @param bt
   * @param c
   * @param m
   * @param n
   * @param p
   */
  public static double[] ijkt(double a[], double bt[], double c[], int m, int n, int p) {
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        double v = 0.0;
        int jp = j * p, ip = i * p;
        for (int k = 0; k < p; k++) {
          v += a[ip + k] * bt[jp + k];
        }
        c[i * n + j] = v;
      }
    }
    return c;
  }

  public static double[] pmul(double a[], double b[], double c[], int m, int n, int p) {
    ThreadPool<Integer, Object> tp = new StaticThreadPool<Integer, Object>(
        (Integer i, List<Object> l) -> {
          int in = i * n;
          for (int k = 0; k < p; k++) {
            int kn = k * n;
            double v = a[i * p + k];
            for (int j = 0; j < n; j++) {
              c[in + j] += v * b[kn + j];
            }
          }
        });

    BlockingQueue<Object> sink = tp.sink();
    tp.start();

    try {
      for (int i = 0; i < m; i++)
        sink.add(new Integer(i));
      tp.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return c;
  }

  public static double[] pmult(double a[], double bt[], double c[], int m, int n, int p) {
    ThreadPool<Integer, Object> tp = new StaticThreadPool<Integer, Object>(
        (Integer i, List<Object> l) -> {
          int ip = i * p;
          for (int j = 0; j < n; j++) {
            double v = 0.0;
            int jp = j * p;
            for (int k = 0; k < p; k++) {
              v += a[ip + k] * bt[jp + k];
            }
            c[i * n + j] = v;
          }
        });

    BlockingQueue<Object> sink = tp.sink();
    tp.start();

    try {
      for (int i = 0; i < m; i++)
        sink.add(new Integer(i));
      tp.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return c;
  }

  public static void comulr(double a[], double b[], double c[], int a_rb, int a_re,
                            int a_cb, int a_ce, int b_rb, int b_re, int b_cb, int b_ce,
                            int n, int p, int blk) {
    if ((a_re - a_rb) <= blk && (a_ce - a_cb) <= blk) {
      for (int i = a_rb; i < a_re; i++) {
        int in = i * n;
        for (int k = b_rb, bc = 0; k < b_re; k++, bc++) {
          int kn = k * n;
          for (int j = b_cb, br = 0; j < b_ce; j++, br++) {
            double v = a[i * p + k];
            c[in + j] += v * b[kn + j];
          }
        }
      }
    } else if ((a_re - a_rb) >= (a_ce - a_cb)) {
      int a_rm = (a_rb + a_re) / 2, b_cm = (b_cb + b_ce) / 2;
      comulr(a, b, c, a_rb, a_rm, a_cb, a_ce, b_rb, b_re, b_cb, b_cm, n, p, blk);
      comulr(a, b, c, a_rm, a_re, a_cb, a_ce, b_rb, b_re, b_cb, b_cm, n, p, blk);
      comulr(a, b, c, a_rb, a_rm, a_cb, a_ce, b_rb, b_re, b_cm, b_ce, n, p, blk);
      comulr(a, b, c, a_rm, a_re, a_cb, a_ce, b_rb, b_re, b_cm, b_ce, n, p, blk);
    } else {
      int a_cm = (a_cb + a_ce) / 2, b_rm = (b_rb + b_re) / 2;
      comulr(a, b, c, a_rb, a_re, a_cb, a_cm, b_rb, b_rm, b_cb, b_ce, n, p, blk);
      comulr(a, b, c, a_rb, a_re, a_cm, a_ce, b_rm, b_re, b_cb, b_ce, n, p, blk);
    }
  }

  public static double[] comul(double a[], double b[], double c[], int m, int n, int p, int blk) {
    comulr(a, b, c, 0, m, 0, p, 0, p, 0, n, n, p, blk);
    return c;
  }

  public static double[] comul(double a[], double b[], double c[], int m, int n, int p) {
    return comul(a, b, c, m, n, p, BLK);
  }

  private static void comultr(double a[], double b[], double c[], int a_rb, int a_re,
                              int a_cb, int a_ce, int b_rb, int b_re, int b_cb, int b_ce,
                              int n, int p, int blk) {
    if ((a_re - a_rb) <= blk && (a_ce - a_cb) <= blk) {
      for (int i = a_rb; i < a_re; i++) {
        for (int j = b_cb, br = 0; j < b_ce; j++, br++) {
          double v = 0.0;
          int jp = j * p, ip = i * p;
          for (int k = b_rb, bc = 0; k < b_re; k++, bc++) {
            v += a[ip + k] * b[jp + k];
          }
          c[i * n + j] = v;
        }
      }
    } else if ((a_re - a_rb) >= (a_ce - a_cb)) {
      int a_rm = (a_rb + a_re) / 2, b_rm = (b_rb + b_re) / 2;
      comultr(a, b, c, a_rb, a_rm, a_cb, a_ce, b_rb, b_rm, b_cb, b_ce, n, p, blk);
      comultr(a, b, c, a_rm, a_re, a_cb, a_ce, b_rb, b_rm, b_cb, b_ce, n, p, blk);
      comultr(a, b, c, a_rb, a_rm, a_cb, a_ce, b_rm, b_re, b_cb, b_ce, n, p, blk);
      comultr(a, b, c, a_rm, a_re, a_cb, a_ce, b_rm, b_re, b_cb, b_ce, n, p, blk);
    } else {
      int a_cm = (a_cb + a_ce) / 2, b_cm = (b_cb + b_ce) / 2;
      comultr(a, b, c, a_rb, a_re, a_cb, a_cm, b_rb, b_re, b_cb, b_cm, n, p, blk);
      comultr(a, b, c, a_rb, a_re, a_cm, a_ce, b_rb, b_re, b_cb, b_cm, n, p, blk);
    }
  }

  public static double[] comult(double a[], double b[], double c[], int m, int n, int p, int blk) {
    comultr(a, b, c, 0, m, 0, p, 0, p, 0, n, n, p, blk);
    return c;
  }

  public static double[] comult(double a[], double b[], double c[], int m, int n, int p) {
    return comult(a, b, c, m, n, p, BLK);
  }

  /**
   * @param a
   * @param b
   * @param c
   * @param m
   * @param n
   * @param p
   * @return
   */
  public static double[] mul(double a[], double b[], double c[], int m, int n, int p) {
    double rv[] = null;
    if (c.length < P_MUL_SIZE) {
      rv = comul(a, b, c, m, n, p);
    } else {
      rv = pmul(a, b, c, m, n, p);
    }
    return rv;
  }

  /**
   * @param a
   * @param b
   * @param c
   * @param m
   * @param n
   * @param p
   * @return
   */
  public static double[] mult(double a[], double b[], double c[], int m, int n, int p) {
    double rv[] = null;
    if (c.length < P_MUL_SIZE) {
      rv = ijkt(a, b, c, m, n, p);
    } else {
      rv = pmult(a, b, c, m, n, p);
    }
    return rv;
  }
}
