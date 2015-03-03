package pt.it.av.atnog.utils.bla;

import pt.it.av.atnog.utils.Utils;

/**
 * Created by mantunes on 11/26/14.
 */
public class Vector {
    private static int T = 256;
    private static int B = 128;
    protected int bIdx, length;
    protected double data[];

    public Vector(double data[], int bIdx, int length) {
        this.data = data;
        this.bIdx = bIdx;
        this.length = length;
    }

    public Vector(double data[]) {
        this(data, 0, data.length);
    }

    public Vector(int length) {
        this(new double[length], 0, length);
    }

    public static Vector ones(int length) {
        Vector c = new Vector(length);
        c.set(1.0);
        return c;
    }

    public static Vector rand(int length) {
        Vector b = new Vector(length);
        for (int n = 0; n < length; n++)
            b.data[n + b.bIdx] = Utils.randomBetween(0, 10);
        return b;
    }

    protected static void add(double a[], int bA, double b[], int bB,
                              double c[], int bC, int len) {
        for (int i = 0; i < len; i++)
            c[bC + i] = a[bA + i] + b[bB + i];
    }

    protected static void add(double a[], int bA, double b,
                              double c[], int bC, int len) {
        for (int i = 0; i < len; i++)
            c[bC + i] = a[bA + i] + b;
    }

    protected static void sub(double a[], int bA, double b[], int bB,
                              double c[], int bC, int len) {
        for (int i = 0; i < len; i++)
            c[bC + i] = a[bA + i] - b[bB + i];
    }

    protected static void sub(double a[], int bA, double b,
                              double c[], int bC, int len) {
        for (int i = 0; i < len; i++)
            c[bC + i] = a[bA + i] - b;
    }

    public int size() {
        return length;
    }

    public void set(int i, double scalar) {
        data[bIdx + i] = scalar;
    }

    public void set(double scalar) {
        for (int i = 0; i < length; i++)
            data[bIdx + i] = scalar;
    }

    public double get(int i) {
        return data[bIdx + i];
    }

    public double sum() {
        double rv = 0.0;
        for (int i = 0; i < length; i++)
            rv += data[bIdx + i];
        return rv;
    }

    public Vector add(double scalar) {
        Vector c = new Vector(length);
        for (int i = 0; i < length; i++)
            c.data[c.bIdx + i] = data[bIdx + i] + scalar;
        return c;
    }

    public Vector add(Vector b) {
        Vector c = new Vector(length);
        add(this.data, this.bIdx, b.data, b.bIdx, c.data, c.bIdx, length);
        return c;
    }

    public void uAdd(Vector b) {
        add(this.data, this.bIdx, b.data, b.bIdx, this.data, this.bIdx, length);
    }

    public Vector sub(double scalar) {
        Vector c = new Vector(length);
        for (int i = 0; i < length; i++)
            c.data[c.bIdx + i] = data[bIdx + i] - scalar;
        return c;
    }

    public Vector sub(Vector b) {
        Vector c = new Vector(length);
        for (int i = 0; i < length; i++)
            c.data[c.bIdx + i] = data[bIdx + i] - b.data[b.bIdx + i];
        return c;
    }

    public Vector mul(double scalar) {
        Vector c = new Vector(length);
        for (int i = 0; i < length; i++)
            c.data[c.bIdx + i] = data[bIdx + i] * scalar;
        return c;
    }

    public double innerProduct(Vector b) {
        double c = 0.0;
        for (int i = 0; i < length; i++)
            c += data[bIdx + i] * b.data[b.bIdx + i];
        return c;
    }

    public Matrix outerProduct(Vector b) {
        Matrix C = new Matrix(length, b.length);
        int k = 0;
        for (int i = 0; i < length; i++)
            for (int j = 0; j < b.length; j++) {
                C.data[k] = data[bIdx + i] * b.data[b.bIdx + j];
                k++;
            }
        return C;
    }

    public Vector div(double scalar) {
        Vector c = new Vector(length);
        for (int i = 0; i < length; i++)
            c.data[c.bIdx + i] = data[bIdx + i] / scalar;
        return c;
    }

    public void uDiv(double scalar) {
        for (int i = 0; i < length; i++)
            data[bIdx + i] /= scalar;
    }

    public double norm(int p) {
        double norm = 0.0;
        for (int i = 0; i < length; i++)
            norm = Utils.norm(norm, data[bIdx + i], p);
        return norm;
    }

    // TODO: review this methods
    public double minkowskiDistance(Vector b, int p) {
        double sum = 0.0;
        for (int i = 0; i < data.length; i++) {
            double absDiff = Math.abs(data[bIdx + i] - b.data[b.bIdx + i]);
            sum += Math.pow(absDiff, p);
        }
        return Math.pow(sum, 1.0 / p);
    }

    public double euclideanDistance(Vector p) {
        return minkowskiDistance(p, 2);
    }

    public double manhattanDistance(Vector p) {
        return minkowskiDistance(p, 1);
    }

    public double cosine(Vector b) {
        double rv = 0.0, dp = innerProduct(b);
        if (dp > 0)
            rv = dp / (norm(2) * b.norm(2));
        return rv;
    }

    public double kld(Vector b) {
        double rv = 0.0;
        for (int i = 0; i < length; i++) {
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
        for (int i = 1; i < length; i++) {
            double tmpDist = Math.sqrt(Math.pow(0.0 - data[bIdx + i], 2.0) + Math.pow(0.0 - i, 2.0));
            if (tmpDist < minDist) {
                minDist = tmpDist;
                min = i;
            }
        }
        return min;
    }

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
                if (length == b.length) {
                    rv = true;
                    for (int i = 0; i < length && rv == true; i++)
                        if (Double.compare(data[bIdx + i], b.data[b.bIdx + i]) != 0)
                            rv = false;
                }
            }
        }
        return rv;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < length - 1; i++)
            sb.append(data[bIdx + i] + ", ");
        sb.append(data[bIdx + length - 1] + "]");
        return sb.toString();
    }
}
