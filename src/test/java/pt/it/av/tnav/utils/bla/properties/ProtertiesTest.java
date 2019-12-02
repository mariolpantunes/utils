package pt.it.av.tnav.utils.bla.properties;

import org.junit.Test;
import pt.it.av.tnav.utils.bla.Matrix;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link Properties}.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class ProtertiesTest {

  @Test
  public void test_is_square() {
    Matrix m1 = new Matrix(3, 3);
    assertTrue(m1.isSquare());
    Matrix m2 = new Matrix(3, 4);
    assertTrue(!m2.isSquare());
  }

  @Test
  public void test_is_linear() {
    Matrix m1 = new Matrix(3, 1);
    assertTrue(m1.isLinear());
    Matrix m2 = new Matrix(1, 3);
    assertTrue(m2.isLinear());
    Matrix m3 = new Matrix(3, 3);
    assertTrue(!m3.isLinear());
  }

  @Test
  public void test_is_row() {
    Matrix m1 = new Matrix(3, 1);
    assertTrue(!m1.isRow());
    Matrix m2 = new Matrix(1, 3);
    assertTrue(m2.isRow());
    Matrix m3 = new Matrix(3, 3);
    assertTrue(!m3.isRow());
  }

  @Test
  public void test_is_column() {
    Matrix m1 = new Matrix(3, 1);
    assertTrue(m1.isColumn());
    Matrix m2 = new Matrix(1, 3);
    assertTrue(!m2.isColumn());
    Matrix m3 = new Matrix(3, 3);
    assertTrue(!m3.isColumn());
  }

  @Test
  public void test_is_1x1() {
    Matrix m1 = new Matrix(3, 1);
    assertTrue(!m1.is1x1());
    Matrix m2 = new Matrix(1, 3);
    assertTrue(!m2.is1x1());
    Matrix m3 = new Matrix(3, 3);
    assertTrue(!m3.is1x1());
    Matrix m4 = new Matrix(1, 1);
    assertTrue(m4.is1x1());
  }

  @Test
  public void test_is_zero() {
    Matrix m1 = Matrix.zero(3, 3);
    assertTrue(m1.isZero());
    Matrix m2 = Matrix.random(3, 3);
    assertTrue(!m2.isZero());
  }

  @Test
  public void test_is_identity() {
    Matrix m1 = Matrix.identity(3, 3);
    assertTrue(m1.isIdentity());
    Matrix m2 = Matrix.random(3, 3);
    assertTrue(!m2.isIdentity());
  }

  @Test
  public void test_is_scalar() {
    Matrix m1 = Matrix.identity(3, 3);
    assertTrue(!m1.isScalar());
    Matrix m2 = Matrix.random(3, 3);
    assertTrue(!m2.isScalar());
    Matrix m3 = m1.mul(2.0);
    assertTrue(m3.isScalar());
  }

  @Test
  public void test_is_diagonal() {
    Matrix m1 = Matrix.identity(3, 3);
    assertTrue(m1.isDiagonal());
    Matrix m2 = Matrix.random(3, 3);
    assertTrue(!m2.isDiagonal());
    Matrix m3 = m1.mul(2.0);
    assertTrue(m3.isDiagonal());
    double data[] = {1, 0, 0, 0, 2, 0, 0, 0, 3};
    Matrix m4 = new Matrix(3, 3, data);
    assertTrue(m4.isDiagonal());
  }

  @Test
  public void test_is_upper_triangular() {
    Matrix m1 = Matrix.identity(3, 3);
    assertTrue(m1.isUpperTriangular());
    Matrix m2 = Matrix.random(3, 3);
    assertTrue(!m2.isUpperTriangular());
    Matrix m3 = m1.mul(2.0);
    assertTrue(m3.isUpperTriangular());
    double data[] = {1, 2, 3, 4,
        0, 5, 6, 7,
        0, 0, 8, 9,
        0, 0, 0, 10};
    Matrix m4 = new Matrix(4, 4, data);
    assertTrue(m4.isUpperTriangular());
  }

  @Test
  public void test_is_lower_triangular() {
    Matrix m1 = Matrix.identity(3, 3);
    assertTrue(m1.isLowerTriangular());
    Matrix m2 = Matrix.random(3, 3);
    assertTrue(!m2.isLowerTriangular());
    Matrix m3 = m1.mul(2.0);
    assertTrue(m3.isLowerTriangular());
    double data[] = {10, 0, 0, 0,
        9, 8, 0, 0,
        7, 6, 5, 0,
        4, 3, 2, 1};
    Matrix m4 = new Matrix(4, 4, data);
    assertTrue(m4.isLowerTriangular());
  }
}
