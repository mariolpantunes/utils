package pt.it.av.atnog.utils.linearAlgebra;

/**
 * Created by mantunes on 11/26/14.
 */
public class Vector {
    protected double data[];

    public Vector(int size) {
        data = new double[size];
    }

    public static Vector ones(int size) {
        Vector c = new Vector(size);
        c.set(1.0);
        return c;
    }

    public int size() {
        return data.length;
    }

    public void set(int idx, double scalar) {
        data[idx] = scalar;
    }

    public void set(double scalar) {
        for (int i = 0; i < data.length; i++)
            data[i] = scalar;
    }

    public Vector add(double scalar) {
        Vector c = new Vector(data.length);
        for (int i = 0; i < data.length; i++)
            c.data[i] = data[i] + scalar;
        return c;
    }

    public Vector add(Vector b) {
        Vector c = new Vector(data.length);
        for (int i = 0; i < data.length; i++)
            c.data[i] = data[i] + b.data[i];
        return c;
    }

    public Vector rem(double scalar) {
        Vector c = new Vector(data.length);
        for (int i = 0; i < data.length; i++)
            c.data[i] = data[i] - scalar;
        return c;
    }

    public Vector rem(Vector b) {
        Vector c = new Vector(data.length);
        for (int i = 0; i < data.length; i++)
            c.data[i] = data[i] - b.data[i];
        return c;
    }

    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null) {
            if (o == this)
                rv = true;
            else if (o instanceof Vector) {
                Vector B = (Vector) o;
                if (data.length == B.data.length) {
                    rv = true;
                    for (int i = 0; i < data.length && rv == true; i++)
                        if (Double.compare(data[i], B.data[i]) != 0)
                            rv = false;
                }
            }
        }
        return rv;
    }
}
