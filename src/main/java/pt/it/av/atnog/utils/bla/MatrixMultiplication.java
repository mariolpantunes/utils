package pt.it.av.atnog.utils.bla;

/**
 * Low level matrix operations.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class MatrixMultiplication {

    /*protected static Matrix ijk(Matrix A, Matrix B) {
        Matrix R = new Matrix(A.rows, B.cols);

        for(int i = 0; i < R.rows; i++) {
            for (int j = 0; j < R.cols; j++) {
                for()
            }
        }
        return R;
    }

    protected static void ijk(int rows) {
        for(int i = 0; i < rows; i++) {

        }
    }



    public static Matrix mul(Matrix A, Matrix B) {
        Matrix C = new Matrix(A.rows, B.cols);
        double bt[] = new double[B.rows * B.cols];
        //transpose(B.data, bt, B.rows, B.cols);

            for (int i = 0; i < C.rows; i++) {
                int ic = i * A.cols;
                for (int j = 0; j < C.cols; j++) {
                    int jc = j * B.rows;
                    double cij = 0.0;
                    for (int k = 0; k < B.rows; k++)
                        cij += data[ic + k] * bt[jc + k];
                    C.data[i * C.cols + j] = cij;
                }
            }
        } else {
            int I = C.rows, J = C.cols, K = cols;
            ThreadPool tp = new ThreadPool((Object o, List<Object> l) -> {
                int i = (Integer) o, ic = i * cols;
                for (int j = 0; j < C.cols; j++) {
                    int jc = j * B.rows;
                    double cij = 0.0;
                    for (int k = 0; k < B.rows; k++)
                        cij += data[ic + k] * bt[jc + k];
                    C.data[i * C.cols + j] = cij;
                }
            });

            BlockingQueue<Object> sink = tp.sink();
            tp.start();

            try {
                for (int i = 0; i < I; i++)
                    sink.add(new Integer(i));
                tp.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return C;
    }*/

}
