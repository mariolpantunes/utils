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
      B[] = {2, 4, 6, 8, 10, 12},
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
}
