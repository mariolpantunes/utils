package pt.it.av.tnav.utils.bla;

import org.junit.BeforeClass;
import org.junit.Test;
import pt.it.av.tnav.utils.MathUtils;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link Vector}.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class VectorTest {
  private static Vector A, Aplus1, B, C, D;

  @BeforeClass
  public static void init() {
    A = Vector.ones(5);
    Aplus1 = new Vector(5);
    Aplus1.set(2.0);
    double dataB[] = {1.0, 3.0, 5.0, 5.0, 6.0};
    B = new Vector(dataB);
    double dataC[] = {4.0, 6.0, 10.0, 12.0, 13.0,};
    C = new Vector(dataC);
    double dataD[] = {13.0, 12.0, 10.0, 6.0, 4.0,};
    D = new Vector(dataD);
  }

  @Test
  public void test_add_scalar() {
    Vector C = A.add(1.0);
    assertEquals(Aplus1, C);
  }

  @Test
  public void test_add_vector() {
    Vector C = A.add(A);
    assertEquals(Aplus1, C);
  }

  @Test
  public void test_sub_scalar() {
    Vector C = Aplus1.sub(1.0);
    assertEquals(A, C);
  }

  @Test
  public void test_sub_vector() {
    Vector C = Aplus1.sub(A);
    assertEquals(A, C);
  }

  @Test
  public void test_sub_row() {

  }

  @Test
  public void test_mean() {
    assertEquals(1.0, A.mean(), MathUtils.eps());
    assertEquals(4.0, B.mean(), MathUtils.eps());
    assertEquals(9.0, C.mean(), MathUtils.eps());
    assertEquals(9.0, D.mean(), MathUtils.eps());
  }

  @Test
  public void test_var() {
    assertEquals(0.0, A.var(), MathUtils.eps());
    assertEquals(4.0, B.var(), MathUtils.eps());
    assertEquals(15.0, C.var(), MathUtils.eps());
    assertEquals(15.0, D.var(), MathUtils.eps());
  }

  @Test
  public void test_cov() {
    assertEquals(0.0, A.cov(B), MathUtils.eps());
    assertEquals(0.0, A.cov(C), MathUtils.eps());
    assertEquals(0.0, A.cov(D), MathUtils.eps());
    assertEquals(7.5, B.cov(C), MathUtils.eps());
    assertEquals(-6.75, B.cov(D), MathUtils.eps());
    assertEquals(-14.25, C.cov(D), MathUtils.eps());
  }

  @Test
  public void test_correlation() {
    assertEquals(0.96825, B.corr(C), 0.001);
    assertEquals(-0.87142, B.corr(D), 0.001);
    assertEquals(-0.95, C.corr(D), 0.001);
  }
}
