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
    int k = Math.min(V.rows(), V.columns()) - 1;
    Matrix WH[] = V.nmf(k);
    Matrix wh = WH[0].mul(WH[1]);
    double cost = V.distanceTo(wh);
    assertTrue(cost <= 2.0);
  }

  @Test
  public void test_nmf_imputation_1() {
    double data[] = { 1, 0, 5, 0, 0, 3, 1, 3, 5, 3, 3, 4 };
    Matrix V = new Matrix(4, 3, data);
    // System.out.println(V);

    Matrix M = V.mask();
    // System.out.println(M);

    int k = Math.min(V.rows(), V.columns()) - 1;

    Matrix WH[] = V.nmf(k, M);
    Matrix wh = WH[0].mul(WH[1]);
    double cost = V.distanceTo(wh, M);
   
    assertTrue(cost <= 0.1);
  }

  @Test
  public void test_nmf_imputation_2() {
    double data[] = { 5, 3, 0, 1, 4, 0, 0, 1, 1, 1, 0, 5, 1, 0, 0, 4, 0, 1, 5, 4};
    Matrix V = new Matrix(5, 4, data);
    // System.out.println(V);

    Matrix M = V.mask();
    // System.out.println(M);

    int k = Math.min(V.rows(), V.columns()) - 1;

    Matrix WH[] = V.nmf(k, M);
    Matrix wh = WH[0].mul(WH[1]);
    double cost = V.distanceTo(wh, M);
   
    assertTrue(cost <= 0.1);
  }

  

  /*@Test
  public void test_nmf_press() {
    // 6x5
    double data[] = { 4, 5, 3, 3, 1, 0, 5, 0, 3, 2, 0, 5, 1, 2, 2, 0, 4, 2, 3, 4, 0, 2, 4, 1, 4, 2, 3, 5, 3, 0 };
    Matrix V = new Matrix(6, 5, data);
    System.out.println(V);

    int k = Math.min(V.rows(), V.columns()) - 1;
    int bestK = -1;
    double bestPress = Double.MAX_VALUE;

    Matrix Mask = V.mask();
    // Matrix Mask = Matrix.ones(V.rows(), V.columns());
    // Matrix Mask = V.mask();
    //System.out.println(Mask);

    for (int i = 1; i <= k; i++) {
      // System.err.println("K = "+k);
      double press = 0.0;

      for (int j = 0; j < Mask.size(); j++) {
        // System.err.println("Mask = "+j);
        if (Mask.get(j) == 1) {
          Mask.set(j, 0);
          // Several tries
          Matrix WH[] = V.nmf(i, Mask);
          Matrix wh = WH[0].mul(WH[1]);
          double cost = V.distanceTo(wh);
          for (int idx = 0; idx < 100; idx++) {
            // System.err.println("IDX = "+idx);
            Matrix tWH[] = V.nmf(i, Mask);
            wh = tWH[0].mul(tWH[1]);
            double tcost = V.distanceTo(wh);
            if (tcost < cost) {
              cost = tcost;
              WH = tWH;
            }
          }

          wh = WH[0].mul(WH[1]);
          
          press += Math.pow(V.get(j) - wh.get(j), 2.0);
          
          Mask.set(j, 1);
        }
      }

      System.out.println("Best PRESS =" + bestPress);
      System.out.println("PRESS =" + press);
      System.out.println();
      if (bestPress > press) {
        bestPress = press;
        bestK = i;
      }
    }

    System.out.println("Best K = " + bestK);

    Matrix WH[] = V.nmf(bestK, Mask);
    Matrix wh = WH[0].mul(WH[1]);

    double cost = V.distanceTo(wh);

    for (int i = 0; i < 100; i++) {
      Matrix tWH[] = V.nmf(bestK, Mask);
      wh = tWH[0].mul(tWH[1]);
      double tcost = V.distanceTo(wh, Mask);
      if (tcost < cost && Double.isFinite(tcost)) {
        cost = tcost;
        WH = tWH;
      }
    }

    wh = WH[0].mul(WH[1]);

    System.out.println(wh);
    cost = V.distanceTo(wh, Mask);
    
    assertTrue(cost <= 0.1);
  }*/
}
