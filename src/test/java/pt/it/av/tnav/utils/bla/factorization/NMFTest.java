package pt.it.av.tnav.utils.bla.factorization;

import org.junit.Test;
import pt.it.av.tnav.utils.bla.Matrix;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link NMF}.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class NMFTest {

  @Test
  public void test_identity_nmf() {
    Matrix V = Matrix.identity(5);
    Matrix WH[] = V.nmf(5);
    Matrix wh = WH[0].mul(WH[1]);
    double cost = V.distanceTo(wh);
    assertTrue(cost <= 1.0);
  }
}
