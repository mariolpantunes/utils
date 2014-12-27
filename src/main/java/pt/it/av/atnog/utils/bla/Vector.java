package pt.it.av.atnog.utils.bla;

import pt.it.av.atnog.utils.Utils;

/**
 * Created by mantunes on 11/26/14.
 */
public class Vector {
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

    public Vector add(double scalar) {
        Vector c = new Vector(length);
        for (int i = 0; i < length; i++)
            c.data[c.bIdx + i] = data[bIdx + i] + scalar;
        return c;
    }

    public Vector add(Vector b) {
        Vector c = new Vector(length);
        for (int i = 0; i < length; i++)
            c.data[c.bIdx + i] = data[bIdx + i] + b.data[b.bIdx + i];
        return c;
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
    public double minkowskiDistance(Vector po, int p) {
        double sum = 0.0;
        if (data.length == po.data.length)
            for (int i = 0; i < data.length; i++) {
                double absDiff = Math.abs(data[i] - po.data[i]);
                sum += Math.pow(absDiff, p);
            }
        else
            throw new IllegalArgumentException("The points do not have the same number of dimensions");

        return Math.pow(sum, 1.0 / p);
    }

    public double euclideanDistance(Vector p) {
        return minkowskiDistance(p, 2);
    }

    public double manhattanDistance(Vector p) {
        return minkowskiDistance(p, 1);
    }

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
