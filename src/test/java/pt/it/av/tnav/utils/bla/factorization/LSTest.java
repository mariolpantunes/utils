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
public class LSTest {
@Test
  public void test_ls() {
    // 6x5
    double data[] = { 4, 5, 3, 3, 1, 0, 5, 0, 3, 2, 0, 5, 1, 2, 2, 0, 4, 2, 3, 4, 0, 2, 4, 1, 4, 2, 3, 5, 3, 0 };
    Matrix V = new Matrix(6, 5, data);
    //double data[] = { 5, 3, 0, 1, 4, 0, 0, 1, 1, 1, 0, 5, 1, 0, 0, 4, 0, 1, 5, 4 };
    //Matrix V = new Matrix(5, 4, data);
    //double data[] = { 2,5,2, 5,2,5, 0,5,0 };
    //Matrix V = new Matrix(3, 3, data);

    System.out.println(V);

    Matrix PQ[] = V.ls(2);
    Matrix pq = PQ[0].mul(PQ[1]);

    System.out.println(pq);
  }
}