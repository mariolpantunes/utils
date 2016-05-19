package pt.it.av.atnog.utils.bla;

/**
 * Operations over arrays of doubles.
 * These operations are common to Matrix and Vector classes.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class ArraysOps {

    /**
     * Sum two arrays element-wise.
     * The elements from A are added with B and stored in C.
     *
     * @param a   first vector
     * @param bA  index of the first vector
     * @param b   second vector
     * @param bB  index of the second vector
     * @param c   resulting vector
     * @param bC  index of the resulting vector
     * @param len array's length
     */
    public static void add(double a[], int bA, double b[], int bB, double c[], int bC, int len) {
        for (int i = 0; i < len; i++)
            c[bC + i] = a[bA + i] + b[bB + i];
    }

    /**
     * Sum a scalar with an array.
     * The scalar B is added with A and stored in C.
     *
     * @param a   first vector
     * @param bA  index of the first vector
     * @param b   scalar
     * @param c   resulting vector
     * @param bC  index of the resulting vector
     * @param len array's length
     */
    public static void add(double a[], int bA, double b, double c[], int bC, int len) {
        for (int i = 0; i < len; i++)
            c[bC + i] = a[bA + i] + b;
    }

    /**
     * Substratc two arrays element-wise.
     * The elements from B are subtracted from A and stored in C.
     *
     * @param a   first vector
     * @param bA  index of the first vector
     * @param b   second vector
     * @param bB  index of the second vector
     * @param c   resulting vector
     * @param bC  index of the resulting vector
     * @param len array's length
     */
    public static void sub(double a[], int bA, double b[], int bB, double c[], int bC, int len) {
        for (int i = 0; i < len; i++)
            c[bC + i] = a[bA + i] - b[bB + i];
    }

    /**
     * Substratc a scalar from an array.
     * The scalar B are subtracted from A and stored in C.
     *
     * @param a   first vector
     * @param bA  index of the first vector
     * @param b   scalar
     * @param c   resulting vector
     * @param bC  index of the resulting vector
     * @param len array's length
     */
    public static void sub(double a[], int bA, double b, double c[], int bC, int len) {
        for (int i = 0; i < len; i++)
            c[bC + i] = a[bA + i] - b;
    }

    /**
     * Multiply two arrays element-wise.
     * The elements from A are multiplied with B and stored in C.
     *
     * @param a   first vector
     * @param bA  index of the first vector
     * @param b   second vector
     * @param bB  index of the second vector
     * @param c   resulting vector
     * @param bC  index of the resulting vector
     * @param len array's length
     */
    public static void mul(double a[], int bA, double b[], int bB, double c[], int bC, int len) {
        for (int i = 0; i < len; i++)
            c[bC + i] = a[bA + i] * b[bB + i];
    }

    /**
     * Multiply a scalar with an array.
     * The scalar B are subtracted from A and stored in C.
     *
     * @param a   first vector
     * @param bA  index of the first vector
     * @param b   scalar
     * @param c   resulting vector
     * @param bC  index of the resulting vector
     * @param len array's length
     */
    public static void mul(double a[], int bA, double b, double c[], int bC, int len) {
        for (int i = 0; i < len; i++)
            c[bC + i] = a[bA + i] * b;
    }

    /**
     * Divide two arrays element-wise.
     * The elements from A are divided by B and stored in C.
     *
     * @param a   first vector
     * @param bA  index of the first vector
     * @param b   second vector
     * @param bB  index of the second vector
     * @param c   resulting vector
     * @param bC  index of the resulting vector
     * @param len array's length
     */
    public static void div(double a[], int bA, double b[], int bB, double c[], int bC, int len) {
        for (int i = 0; i < len; i++)
            c[bC + i] = a[bA + i] / b[bB + i];
    }

    /**
     * Divide an array by a scalar.
     * The elements from A are divided by the scalar b and stored in C.
     *
     * @param a   first vector
     * @param bA  index of the first vector
     * @param b   scalar
     * @param c   resulting vector
     * @param bC  index of the resulting vector
     * @param len array's length
     */
    public static void div(double a[], int bA, double b, double c[], int bC, int len) {
        for (int i = 0; i < len; i++)
            c[bC + i] = a[bA + i] / b;
    }

    /**
     * The elements of A are powered by B and stored in C.
     *
     * @param a   first vector
     * @param bA  index of the first vector
     * @param b   second vector
     * @param bB  index of the second vector
     * @param c   resulting vector
     * @param bC  index of the resulting vector
     * @param len array's length
     */
    public static void pow(double a[], int bA, double b[], int bB, double c[], int bC, int len) {
        for (int i = 0; i < len; i++)
            c[bC + i] = Math.pow(a[bA + i], b[bB + i]);
    }

    /**
     * The elements of A are powered by scalar b and stored in C.
     *
     * @param a   first vector
     * @param bA  index of the first vector
     * @param b   scalar
     * @param c   resulting vector
     * @param bC  index of the resulting vector
     * @param len array's length
     */
    public static void pow(double a[], int bA, double b, double c[], int bC, int len) {
        for (int i = 0; i < len; i++)
            c[bC + i] = Math.pow(a[bA + i], b);
    }

    /**
     * @param a
     * @param bA
     * @param b
     * @param bB
     * @param len
     * @param p
     * @return
     */
    public static double minkowskiDistance(double a[], int bA, double b[], int bB, int len, int p) {
        double sum = 0.0;
        for (int i = 0; i < len; i++)
            sum += Math.pow(Math.abs(a[bA + i] - b[bB + i]), p);
        return Math.pow(sum, 1.0 / p);
    }

    /**
     * @param a
     * @param bA
     * @param b
     * @param bB
     * @param len
     * @return
     */
    public static double euclideanDistance(double a[], int bA, double b[], int bB, int len) {
        double sum = 0.0;
        for (int i = 0; i < len; i++)
            sum += Math.pow(a[bA + i] - b[bB + i], 2.0);
        return Math.sqrt(sum);
    }

    /**
     * @param a
     * @param bA
     * @param b
     * @param bB
     * @param len
     * @return
     */
    public static double manhattanDistance(double a[], int bA, double b[], int bB, int len) {
        double sum = 0.0;
        for (int i = 0; i < len; i++)
            sum += Math.abs(a[bA + i] - b[bB + i]);
        return sum;
    }
}
