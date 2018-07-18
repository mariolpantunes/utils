package pt.it.av.tnav.utils.structures.tuple;

public class Pair<A, B> {
    public final A a;
    public final B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "(" + a.toString() + ", " + b.toString() + ")";
    }

    @Override
    public int hashCode() {
        return a.hashCode() ^ b.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null) {
            if (o == this)
                rv = true;
            else if (o instanceof Pair) {
                Pair p = (Pair) o;
                rv = this.a.equals(p.a) &&
                        this.b.equals(p.b);
            }
        }
        return rv;
    }
}