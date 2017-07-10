package pt.it.av.atnog.utils.bla;

import org.junit.Test;
import pt.it.av.atnog.utils.PrintUtils;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link MatrixTranspose}.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class MatrixTransposeTest {
  private static final int N = 128, BLK = 32;
  private static double A[] = {1, 2, 3, 4, 5, 6}, AT[] = {1, 3, 5, 2, 4, 6},
      B[] = {0, 5, 10, 1, 6, 11, 2, 7, 12, 3, 8, 13, 4, 9, 14},
      BT[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

  @Test
  public void test_naive() {
    double at[] = new double[A.length], bt[] = new double[B.length];
    MatrixTranspose.ntr(A, at, 3,2);
    assertTrue(Arrays.equals(at, AT));
    MatrixTranspose.ntr(B, bt, 5,3);
    assertTrue(Arrays.equals(bt, BT));
  }

  @Test
  public void test_transpose_inplace_follow_cycles_small() {
    double a[] = new double[A.length], b[] = new double[B.length];
    System.arraycopy(A,0, a,0, A.length);
    System.arraycopy(B,0, b,0, B.length);
    MatrixTranspose.infotr(a, 3, 2);
    assertTrue(Arrays.equals(a, AT));
    MatrixTranspose.infotr(b, 5, 3);
    assertTrue(Arrays.equals(b, BT));
  }

  @Test
  public void test_transpose_inplace_follow_cycles_large() {
    Matrix M = Matrix.random(N,N);
    Matrix MT = new Matrix(M.cols, M.rows);
    MatrixTranspose.ntr(M.data, MT.data, M.rows, M.cols);
    MatrixTranspose.infotr(M.data, M.rows, M.cols);
    assertTrue(Arrays.equals(M.data, MT.data));
  }

  @Test
  public void test_transpose_cache_oblivious_large() {
    Matrix M = Matrix.random(N,N), MT = new Matrix(M.cols, M.rows);
    MatrixTranspose.ntr(M.data, MT.data, M.rows, M.cols);
    Matrix m = new Matrix(M.cols, M.rows);
    MatrixTranspose.cotr(M.data, m.data, M.rows, M.cols, BLK);
    assertTrue(Arrays.equals(MT.data, m.data));
  }
}
