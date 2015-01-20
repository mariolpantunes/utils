package pt.it.av.atnog.utils.structures.tuple;

public class Pair<A, B> {
    public final A a;
    public final B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
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