package pt.it.av.tnav.utils.bla.transpose;

import org.junit.Test;
import pt.it.av.tnav.utils.ArrayUtils;
import pt.it.av.tnav.utils.MathUtils;

import static org.junit.Assert.assertArrayEquals;

/**
 * Unit test for {@link Transpose}.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class TransposeTest {
  private static final int N = 128, BLK = 32;
  private static double A[] = {1, 2, 3, 4, 5, 6}, AT[] = {1, 3, 5, 2, 4, 6},
      B[] = {0, 5, 10, 1, 6, 11, 2, 7, 12, 3, 8, 13, 4, 9, 14},
      BT[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

  @Test
  public void test_naive() {
    double at[] = new double[A.length], bt[] = new double[B.length];
    Transpose.ntr(A, at, 3, 2);
    assertArrayEquals(AT, at, MathUtils.eps());
    Transpose.ntr(B, bt, 5, 3);
    assertArrayEquals(BT, bt, MathUtils.eps());
  }

  @Test
  public void test_transpose_inplace_follow_cycles_small() {
    double a[] = new double[A.length], b[] = new double[B.length];
    System.arraycopy(A,0, a,0, A.length);
    System.arraycopy(B,0, b,0, B.length);
    Transpose.infotr(a, 3, 2);
    assertArrayEquals(AT, a, MathUtils.eps());
    Transpose.infotr(b, 5, 3);
    assertArrayEquals(BT, b, MathUtils.eps());
  }

  @Test
  public void test_transpose_inplace_follow_cycles_large() {
    double m[] = ArrayUtils.random(N * N), mt[] = new double[N * N];
    Transpose.ntr(m, mt, N, N);
    Transpose.infotr(m, N, N);
    assertArrayEquals(mt, m, MathUtils.eps());
  }

  @Test
  public void test_transpose_cache_oblivious_large() {
    double m[] = ArrayUtils.random(N * N), mt[] = new double[N * N],
        mt2[] = new double[N * N];
    Transpose.ntr(m, mt, N, N);
    Transpose.cotr(m, mt2, N, N);
    assertArrayEquals(mt, mt2, MathUtils.eps());
  }
}
