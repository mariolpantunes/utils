package pt.it.av.atnog.utils.bla;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link MatrixMultiplication}.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class MatrixMultiplicationTest {
  private static double A[] = {0, 1, 2, 3, 4, 5},
      AT[] = {0, 2, 4, 1, 3, 5},
      B[] = {2, 4, 6, 8, 10, 12},
      BT[] = {2, 8, 4, 10, 6, 12},
      AB[] = {8, 10, 12, 28, 38, 48, 48, 66, 84},
      BA[] = {32, 44, 68, 98};

  @Test
  public void test_ijk() {
    double ab[] = new double[9];
    MatrixMultiplication.ijk(A, B, ab, 3, 3, 2);
    assertTrue(Arrays.equals(ab, AB));

    double ba[] = new double[4];
    MatrixMultiplication.ijk(B, A, ba, 2, 2, 3);
    assertTrue(Arrays.equals(ba, BA));
  }

  @Test
  public void test_ikj() {
    double ab[] = new double[9];
    MatrixMultiplication.ikj(A, B, ab, 3, 3, 2);
    assertTrue(Arrays.equals(ab, AB));

    double ba[] = new double[4];
    MatrixMultiplication.ikj(B, A, ba, 2, 2, 3);
    assertTrue(Arrays.equals(ba, BA));
  }

  @Test
  public void test_comul() {
    double ab[] = new double[9];
    MatrixMultiplication.comul(A, B, ab, 3, 3, 2);
    assertTrue(Arrays.equals(ab, AB));

    double ba[] = new double[4];
    MatrixMultiplication.comul(B, A, ba, 2, 2, 3);
    assertTrue(Arrays.equals(ba, BA));
  }

  @Test
  public void test_pmul() {
    double ab[] = new double[9];
    MatrixMultiplication.pmul(A, B, ab, 3, 3, 2);
    assertTrue(Arrays.equals(ab, AB));

    double ba[] = new double[4];
    MatrixMultiplication.pmul(B, A, ba, 2, 2, 3);
    assertTrue(Arrays.equals(ba, BA));
  }

  @Test
  public void test_ijkt() {
    double ab[] = new double[9];
    MatrixMultiplication.ijkt(A, BT, ab, 3, 3, 2);
    assertTrue(Arrays.equals(ab, AB));

    double ba[] = new double[4];
    MatrixMultiplication.ijkt(B, AT, ba, 2, 2, 3);
    assertTrue(Arrays.equals(ba, BA));
  }

  @Test
  public void test_comult() {
    double ab[] = new double[9];
    MatrixMultiplication.comult(A, BT, ab, 3, 3, 2);
    assertTrue(Arrays.equals(ab, AB));

    double ba[] = new double[4];
    MatrixMultiplication.comult(B, AT, ba, 2, 2, 3);
    assertTrue(Arrays.equals(ba, BA));
  }

  @Test
  public void test_pmult() {
    double ab[] = new double[9];
    MatrixMultiplication.pmult(A, BT, ab, 3, 3, 2);
    assertTrue(Arrays.equals(ab, AB));

    double ba[] = new double[4];
    MatrixMultiplication.pmult(B, AT, ba, 2, 2, 3);
    assertTrue(Arrays.equals(ba, BA));
  }
}
