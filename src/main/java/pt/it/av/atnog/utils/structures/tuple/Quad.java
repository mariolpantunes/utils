package pt.it.av.atnog.utils.structures.tuple;

/**
 * Created by mantunes on 1/20/15.
 */
public class Quad<A, B, C, D> {
    public final A a;
    public final B b;
    public final C c;
    public final D d;

    public Quad(A a, B b, C c, D d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public String toString() {
        return "(" + a.toString() + ", " + b.toString() + ", " + c.toString() + ", " + d.toString() + ")";
    }

    @Override
    public int hashCode() {
        return a.hashCode() ^ b.hashCode() ^ c.hashCode() ^ d.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null) {
            if (o == this)
                rv = true;
            else if (o instanceof Quad) {
                Quad q = (Quad) o;
                rv = a.equals(q.a) && b.equals(q.b) && c.equals(q.c) && d.equals(q.d);
            }
        }
        return rv;
    }
}
