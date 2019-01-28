package pt.it.av.tnav.utils.bla.factorization;

import org.junit.Test;
import pt.it.av.tnav.utils.bla.Matrix;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link QR}.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class QRTest {

  @Test
  public void test_qr_1() {
    Matrix V = Matrix.identity(5);
    Matrix QR[] = V.qr();
    Matrix qr = QR[0].mul(QR[1]);
    double cost = V.distanceTo(qr);
    assertTrue(cost <= 1.0);
  }

  @Test
  public void test_qr_2() {
    Matrix V = Matrix.random(6, 6);
    Matrix QR[] = V.qr();
    Matrix qr = QR[0].mul(QR[1]);
    double cost = V.distanceTo(qr);
    assertTrue(cost <= 1.0);
  }
}
