package pt.it.av.tnav.utils.bla.factorization;

import org.junit.Test;
import pt.it.av.tnav.utils.bla.Matrix;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link NMF}.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class ALSTest {
  /*@Test
  public void test_als() {
    double data[] = { 5, 3, 0, 1, 4, 0, 0, 1, 1, 1, 0, 5, 1, 0, 0, 4, 0, 1, 5, 4 };
    Matrix V = new Matrix(5, 4, data);
    int k = Math.min(V.rows(), V.columns()) - 1;
    Matrix PQ[] = V.als(k);
    Matrix pq = PQ[0].mul(PQ[1]);
    double cost = V.distanceTo(pq, V.mask());
    assertTrue(cost <= 0.1);
  }

  @Test
  public void test_als_press() {
    // 6x5
    double data[] = { 4, 5, 3, 3, 1, 0, 5, 0, 3, 2, 0, 5, 1, 2, 2, 0, 4, 2, 3, 4, 0, 2, 4, 1, 4, 2, 3, 5, 3, 0 };

    // double data[] =
    // double data[] = { 2,5,2, 5,2,5, 0,5,5 };
    Matrix V = new Matrix(6, 5, data);
    // Matrix V = Matrix.random(10,10, 0, 5);
    System.out.println(V);

    int k = Math.min(V.rows(), V.columns());
    int bestK = -1;
    double bestPress = Double.MAX_VALUE;

    for (int i = 1; i <= k; i++) {
      double press = 0.0;
      for (int j = 0; j < V.size(); j++) {
        double v = V.get(j);
        if (v > 0) {
          V.set(j, 0);
          Matrix WH[] = V.als(i);
          Matrix wh = WH[0].mul(WH[1]);
          System.out.println(wh);
          // System.out.println("Original Value: "+V.get(j)+" Predicted one: "+wh.get(j)+"
          // E = "+Math.pow(V.get(j)-wh.get(j), 2.0));
          press += Math.pow(v - wh.get(j), 2.0);
          // System.out.println(Mask);
          V.set(j, v);
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

    System.out.println("Best K =" + bestK);

    Matrix WH[] = V.nmf(bestK);
    Matrix wh = WH[0].mul(WH[1]);
    System.out.println(wh);
    double cost = V.distanceTo(wh);
    assertTrue(cost <= 1.0);
  }*/
}