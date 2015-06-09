package pt.it.av.atnog.utils.structures.tuple;

/**
 * Created by mantunes on 09/06/2015.
 */
public class Triplet<A, B, C> {
    public final A a;
    public final B b;
    public final C c;

    public Triplet(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public String toString() {
        return "(" + a.toString() + ", " + b.toString() + ", " + c.toString() + ")";
    }

    @Override
    public int hashCode() {
        return a.hashCode() ^ b.hashCode() ^ c.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null) {
            if (o == this)
                rv = true;
            else if (o instanceof Triplet) {
                Triplet q = (Triplet) o;
                rv = a.equals(q.a) && b.equals(q.b) && c.equals(q.c);
            }
        }
        return rv;
    }
}
