package pt.it.av.atnog.utils.bla;

import org.junit.Test;

/**
 * Unit test for non-negative matrix factorization.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class NmfFactorizationTest {

  @Test
  public void test_identity_nmf() {
    Matrix V = Matrix.identity(10);
    Matrix WH[] = NmfFactorization.nmf_mu(V, 10, 100000, 0.001);
    System.out.println(WH[0]);
    System.out.println(WH[1]);
    Matrix wh = WH[0].mul(WH[1]);
    double cost = ArraysOps.euclideanDistance(V.data, 0, wh.data, 0, V.data.length);
    System.out.println(wh);
    System.out.println(cost);
  }
}
