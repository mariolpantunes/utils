package pt.it.av.tnav.utils.bla;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 */
public class Naive {
    public static double det(Matrix M) {
        double rv = 0.0;
        if (M.cols == 1 && M.rows == 1)
            rv = M.data[0];
        else if (M.cols == 2 && M.rows == 2)
            rv = M.data[0] * M.data[3] - M.data[2] * M.data[1];
        else if (M.cols == 3 && M.rows == 3)
            rv = (M.data[0] * M.data[4] * M.data[8] +
                    M.data[1] * M.data[5] * M.data[6] +
                    M.data[2] * M.data[3] * M.data[7]) -
                    (M.data[2] * M.data[4] * M.data[6] +
                            M.data[1] * M.data[3] * M.data[8] +
                            M.data[0] * M.data[5] * M.data[7]);
        else {
            for (int j1 = 0; j1 < M.rows; j1++) {
                Matrix T = new Matrix(M.rows - 1, M.cols - 1);
                for (int i = 1; i < M.rows; i++) {
                    int j2 = 0;
                    for (int j = 0; j < M.rows; j++) {
                        if (j == j1)
                            continue;
                        T.data[(i - 1) * T.cols + j2] = M.data[i * M.cols + j];
                        j2++;
                    }
                }
                rv += Math.pow(-1.0, 1.0 + j1 + 1.0) * M.data[0 * M.cols + j1] * det(T);
            }
        }
        return rv;
    }

    /**
     * SVD based on jacobi rotations
     *
     * @param A
     * @return
     */
    public Matrix[] jacobiSVD(Matrix A) {
        Matrix U = Matrix.identity(A.rows, A.cols), V = Matrix.identity(A.cols, A.rows);
        Matrix rv[] = {U, A, V};


        return rv;
    }
}
