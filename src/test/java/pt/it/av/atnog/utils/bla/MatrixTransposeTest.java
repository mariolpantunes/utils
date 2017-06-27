package pt.it.av.atnog.utils.bla;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link MatrixTranspose}.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class MatrixTransposeTest {
    private static double A[] = {1, 2, 3, 4, 5, 6}, At[] = {1, 3, 5, 2, 4, 6},
            B[] = {0, 5, 10, 1, 6, 11, 2, 7, 12, 3, 8, 13, 4, 9, 14},
            Bt[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    @Test
    public void test_transpose_inplace_follow_cycles_small() {
        MatrixTranspose.inplace_follow_cycles(A, 3, 2);
        assertTrue(Arrays.equals(A, At));
        MatrixTranspose.inplace_follow_cycles(B, 5, 3);
        assertTrue(Arrays.equals(B, Bt));
    }
}
