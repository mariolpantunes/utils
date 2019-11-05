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

  @Test
  public void test_nmf_imputation() {
    //double data[] = { 5, 3, 0, 1, 4, 0, 0, 1, 1, 1, 0, 5, 1, 0, 0, 4, 0, 1, 5, 4 };
    //double data[] = { 2,5,2, 5,2,5, 0,5,5 };
    //Matrix V = new Matrix(3, 3, data);
    Matrix V = Matrix.random(10,10, 0, 5);
    System.out.println(V);
    
    int k = Math.min(V.rows(), V.columns());
    int bestK = -1;
    double bestPress = Double.MAX_VALUE;

    Matrix Mask = Matrix.ones(V.rows(), V.columns());

    System.out.println(Mask);

    for(int i = 1; i <= k; i++) {
      
      double press = 0.0;
      
      for(int j = 0; j < Mask.size(); j++) {
        Mask.set(j, 0);
        Matrix WH[] = V.nmf(i, Mask);
        Matrix wh = WH[0].mul(WH[1]);
        //System.out.println("Original Value: "+V.get(j)+" Predicted one: "+wh.get(j)+" E = "+Math.pow(V.get(j)-wh.get(j), 2.0));
        press += Math.pow(V.get(j)-wh.get(j), 2.0);
        //System.out.println(Mask);
        Mask.set(j, 1);
      }
      
      System.out.println("Best PRESS ="+bestPress);
      System.out.println("PRESS ="+press);
      System.out.println();
      if (bestPress > press) {
        bestPress = press;
        bestK = i;
      } 
    }

    System.out.println("Best K ="+bestK);
    
    Matrix WH[] = V.nmf(bestK);
    Matrix wh = WH[0].mul(WH[1]);
    System.out.println(wh);
    double cost = V.distanceTo(wh);
    assertTrue(cost <= 1.0);
  }
}
