package pt.it.av.tnav.utils.bla.factorization;

import org.junit.Test;
import pt.it.av.tnav.utils.bla.Matrix;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link Cholesky}.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class CholeskyTest {
  @Test
  public void test_identity_chol_1() {
    double data[] = {25, 15, -5, 15, 18, 0, -5, 0, 11};
    Matrix V = new Matrix(3, 3, data);
    Matrix L = V.chol();
    Matrix VT = L.mul(L.transpose());
    assertTrue(V.equals(VT, 0.00001));
  }

  @Test
  public void test_identity_chol_2() {
    double data[] = {18, 22, 54, 42, 22, 70, 86, 62, 54, 86, 174, 134, 42, 62, 134, 106};
    Matrix V = new Matrix(4, 4, data);
    Matrix L = V.chol();
    Matrix VT = L.mul(L.transpose());
    assertTrue(V.equals(VT, 0.00001));
  }
}
