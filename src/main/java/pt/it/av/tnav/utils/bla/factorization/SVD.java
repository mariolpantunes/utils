package pt.it.av.tnav.utils.bla.factorization;

public class SVD {
  /**
   * Utility class, lets make the constructor private.
   */
  private SVD() {
  }

  /**
   * 
   * @param a
   * @param b
   * @return
   */
  private static double[] rot(double a, double b) {
    double csr[] = new double[3];
    if (b == 0) {
      csr[0] = Math.copySign(1, a);
      csr[1] = 0;
      csr[2] = Math.abs(a);
    } else if (a == 0) {
      csr[0] = 0;
      csr[1] = Math.copySign(1, b);
      csr[2] = Math.abs(b);
    } else if (Math.abs(b) > Math.abs(a)) {
      double t = a / b;
      double u = Math.copySign(Math.sqrt(1 + t * t), b);
      csr[1] = -1 / u;
      csr[0] = -csr[1] * t;
      csr[2] = b * u;
    } else {
      double t = b / a;
      double u = Math.copySign(Math.sqrt(1 + t * t), a);
      csr[0] = 1 / u;
      csr[1] = -csr[0] * t;
      csr[2] = a * u;
    }
    return csr;
  }
}
