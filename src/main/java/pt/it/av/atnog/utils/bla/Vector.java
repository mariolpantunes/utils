package pt.it.av.atnog.utils.bla;

import pt.it.av.atnog.utils.MathUtils;

/**
 * General purpose Vector.
 * Implements several functions used in linear algebra and machine learning.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Vector {
    private static int T = 256;
    private static int B = 128;
    protected int bIdx, len;
    protected double data[];

    /**
     * Creates a vector filled with zeros.
     *
     * @param len vector's length
     */
    public Vector(int len) {
        this(new double[len], 0, len);
    }

    /**
     * Creates a vector from a 1D array.
     * The vector only considers the range between [bIdx, bIdx+len[.
     * Does not copy the array's content (shallow copy).
     *
     * @param data 1D array with vector values
     * @param bIdx begin index of the 1D array
     * @param len  the number of elements in the vector
     */
    public Vector(double data[], int bIdx, int len) {
        this.data = data;
        this.bIdx = bIdx;
        this.len = len;
    }

    /**
     * Creates a vector from a 1D array.
     * Does not copy the array's content (shallow copy).
     *
     * @param data
     */
    public Vector(double data[]) {
        this(data, 0, data.length);
    }

    /**
     * Creates a vector from another vector.
     * Implements a copy constructor (deep copy).
     *
     * @param a another vector
     */
    public Vector(Vector a) {
        bIdx = 0;
        len = a.len;
        data = new double[len];
        System.arraycopy(a.data, bIdx, data, bIdx, len);
    }

    /**
     * Returns a vector filled with ones.
     *
     * @param len vector's length
     * @return a vector filled with ones
     */
    public static Vector ones(int len) {
        Vector c = new Vector(len);
        c.set(1.0);
        return c;
    }

    /**
     * Returns a vector filled with random numbers between [0, 1[.
     *
     * @param len vector's length
     * @return a vector filled with random numbers
     */
    public static Vector random(int len) {
        Vector a = new Vector(len);
        for (int n = 0; n < len; n++)
            a.data[n + a.bIdx] = Math.random();
        return a;
    }

    /**
     * Returns a vector filled with random integers between [{@code min}, {@code max}[.
     *
     * @param len vector's length
     * @param min minimal value of the range, inclusive
     * @param max maximal value of the range, exclusive
     * @return a vector filled with random integers
     */
    public static Vector random(int len, int min, int max) {
        Vector a = new Vector(len);
        for (int n = 0; n < len; n++)
            a.data[n] = MathUtils.randomBetween(min, max);
        return a;
    }

    /**
     * Returns the vector's len.
     *
     * @return the vector's len
     */
    public int size() {
        return len;
    }

    /**
     * Set the i-th value.
     *
     * @param i      i-th position
     * @param scalar value
     */
    public void set(int i, double scalar) {
        data[bIdx + i] = scalar;
    }

    /**
     * Set all values to the same scalar.
     *
     * @param scalar scalar
     */
    public void set(double scalar) {
        for (int i = 0; i < len; i++)
            data[bIdx + i] = scalar;
    }

    /**
     * Returns the i-th element.
     *
     * @param i i-th position
     * @return the i-th element
     */
    public double get(int i) {
        return data[bIdx + i];
    }

    /**
     * Returns the sum of all values in the vector.
     *
     * @return the sum of all values in the vector
     */
    public double sum() {
        return ArraysOps.sum(data, bIdx, len);
    }

    public Vector add(double scalar) {
        Vector c = new Vector(len);
        ArraysOps.add(this.data, this.bIdx, scalar, c.data, c.bIdx, len);
        return c;
    }

    public Vector uAdd(double scalar) {
        ArraysOps.add(this.data, this.bIdx, scalar, this.data, this.bIdx, len);
        return this;
    }

    public Vector add(Vector b) {
        Vector c = new Vector(len);
        ArraysOps.add(this.data, this.bIdx, b.data, b.bIdx, c.data, c.bIdx, len);
        return c;
    }

    public Vector uAdd(Vector b) {
        ArraysOps.add(this.data, this.bIdx, b.data, b.bIdx, this.data, this.bIdx, len);
        return this;
    }

    public Vector sub(double scalar) {
        Vector c = new Vector(len);
        ArraysOps.sub(this.data, this.bIdx, scalar, c.data, c.bIdx, len);
        return c;
    }

    public Vector uSub(double scalar) {
        ArraysOps.sub(this.data, this.bIdx, scalar, this.data, this.bIdx, len);
        return this;
    }

    public Vector sub(Vector b) {
        Vector c = new Vector(len);
        ArraysOps.sub(this.data, this.bIdx, b.data, b.bIdx, c.data, c.bIdx, len);
        return c;
    }

    public Vector uSub(Vector b) {
        ArraysOps.sub(this.data, this.bIdx, b.data, b.bIdx, this.data, this.bIdx, len);
        return this;
    }

    public Vector mul(double scalar) {
        Vector c = new Vector(len);
        ArraysOps.mul(this.data, this.bIdx, scalar, c.data, c.bIdx, len);
        return c;
    }

    public Vector uMul(double scalar) {
        ArraysOps.mul(this.data, this.bIdx, scalar, this.data, this.bIdx, len);
        return this;
    }

    public Vector mul(Vector b) {
        Vector c = new Vector(len);
        ArraysOps.mul(this.data, this.bIdx, b.data, b.bIdx, c.data, c.bIdx, len);
        return c;
    }

    public Vector uMul(Vector b) {
        ArraysOps.mul(this.data, this.bIdx, b.data, b.bIdx, this.data, this.bIdx, len);
        return this;
    }

    public Vector div(double scalar) {
        Vector c = new Vector(len);
        ArraysOps.div(this.data, this.bIdx, scalar, c.data, c.bIdx, len);
        return c;
    }

    public Vector uDiv(double scalar) {
        ArraysOps.div(this.data, this.bIdx, scalar, this.data, this.bIdx, len);
        return this;
    }

    public Vector div(Vector b) {
        Vector c = new Vector(len);
        ArraysOps.div(this.data, this.bIdx, b.data, b.bIdx, c.data, c.bIdx, len);
        return c;
    }

    public Vector uDiv(Vector b) {
        ArraysOps.div(this.data, this.bIdx, b.data, b.bIdx, this.data, this.bIdx, len);
        return this;
    }

    public Vector power(double b) {
        Vector c = new Vector(len);
        ArraysOps.pow(this.data, this.bIdx, b, c.data, c.bIdx, len);
        return c;
    }

    public Vector uPower(double b) {
        ArraysOps.pow(this.data, this.bIdx, b, this.data, this.bIdx, len);
        return this;
    }

    public double innerProduct(Vector b) {
        double c = 0.0;
        for (int i = 0; i < len; i++)
            c += data[bIdx + i] * b.data[b.bIdx + i];
        return c;
    }

    public Matrix outerProduct(Vector b) {
        Matrix C = new Matrix(len, b.len);
        int k = 0;
        for (int i = 0; i < len; i++)
            for (int j = 0; j < b.len; j++) {
                C.data[k] = data[bIdx + i] * b.data[b.bIdx + j];
                k++;
            }
        return C;
    }

    public double norm(int p) {
        double norm = 0.0;
        for (int i = 0; i < len; i++)
            norm = MathUtils.norm(norm, data[bIdx + i], p);
        return norm;
    }

    public double minkowskiDistance(Vector b, int p) {
        return ArraysOps.minkowskiDistance(data, bIdx, b.data, b.bIdx, len, p);
    }

    public double euclideanDistance(Vector b) {
        return ArraysOps.euclideanDistance(data, bIdx, b.data, b.bIdx, len);
    }

    public double manhattanDistance(Vector b) {
        return ArraysOps.manhattanDistance(data, bIdx, b.data, b.bIdx, len);
    }

    public double cosine(Vector b) {
        double rv = 0.0, dp = innerProduct(b);
        if (dp > 0)
            rv = dp / (norm(2) * b.norm(2));
        return rv;
    }

    public double kld(Vector b) {
        double rv = 0.0;
        for (int i = 0; i < len; i++) {
            if (b.data[b.bIdx + i] != 0.0 && data[bIdx + i] != 0.0)
                rv += data[bIdx + i] * Math.log(data[bIdx + i] / b.data[b.bIdx + i]);
            else if (b.data[b.bIdx + i] == 0.0)
                rv += data[bIdx + i];
        }
        return rv;
    }

    public int elbow() {
        int min = 0;
        double minDist = Math.sqrt(Math.pow(0.0 - data[bIdx], 2.0) + Math.pow(0.0 - 0.0, 2.0));
        for (int i = 1; i < len; i++) {
            double tmpDist = Math.sqrt(Math.pow(0.0 - data[bIdx + i], 2.0) + Math.pow(0.0 - i, 2.0));
            if (tmpDist < minDist) {
                minDist = tmpDist;
                min = i;
            }
        }
        return min;
    }

    double mean() {
        double m = 0;
        int t = 1;
        for (int i = 0; i < len; i++) {
            m += (data[bIdx + i] - m) / t;
            ++t;
        }
        return m;
    }

    double var() {
        double m = mean(), v = 0.0;
        for (int i = 0; i < len; i++)
            v += Math.pow(data[bIdx + i] - m, 2.0);
        return v / (len - 1);
    }

    public double std() {
        return Math.sqrt(var());
    }

    public double cov(Vector Y) {
        double c = 0.0, mx = 0.0, my = 0.0;
        int t = 1;
        for (int i = 0; i < len; i++) {
            mx += (data[bIdx + i] - mx) / t;
            my += (Y.data[Y.bIdx + i] - my) / t;
            ++t;
        }
        for (int i = 0; i < len; i++)
            c += (data[bIdx + i] - mx) * (Y.data[Y.bIdx + i] - my);
        return c / (len - 1);
    }

    public double corr(Vector Y) {
        double r = 0.0, mx = 0.0, my = 0.0, sx = 0.0, sy = 0.0;
        int t = 1;
        for (int i = 0; i < len; i++) {
            mx += (data[bIdx + i] - mx) / t;
            my += (Y.data[Y.bIdx + i] - my) / t;
            ++t;
        }
        for (int i = 0; i < len; i++) {
            sx += Math.pow(data[bIdx + i] - mx, 2.0);
            sy += Math.pow(Y.data[Y.bIdx + i] - my, 2.0);
        }
        sx = Math.sqrt(sx / (len - 1));
        sy = Math.sqrt(sy / (len - 1));
        for (int i = 0; i < len; i++)
            r += ((data[bIdx + i] - mx) / sx) * ((Y.data[Y.bIdx + i] - my) / sy);
        return r / (len - 1);
    }

    //TODO: Finish this method...

    /**
     * @param b
     * @param eps
     * @return
     */
    public boolean equals(Vector b, double eps) {
        boolean rv = false;
        return rv;
    }

    @Override
    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null) {
            if (o == this)
                rv = true;
            else if (o instanceof Vector) {
                Vector b = (Vector) o;
                if (len == b.len) {
                    rv = true;
                    for (int i = 0; i < len && rv == true; i++)
                        if (Double.compare(data[bIdx + i], b.data[b.bIdx + i]) != 0)
                            rv = false;
                }
            }
        }
        return rv;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < len - 1; i++)
            sb.append(data[bIdx + i] + ", ");
        sb.append(data[bIdx + len - 1] + "]");
        return sb.toString();
    }
}
