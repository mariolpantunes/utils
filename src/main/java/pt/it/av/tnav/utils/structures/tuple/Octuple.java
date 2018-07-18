package pt.it.av.tnav.utils.structures.tuple;

public class Octuple<A, B, C, D, E, F, G, H> {
    public final A a;
    public final B b;
    public final C c;
    public final D d;
    public final E e;
    public final F f;
    public final G g;
    public final H h;

    public Octuple(A a, B b, C c, D d, E e, F f, G g, H h) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.h = h;
    }

    @Override
    public String toString() {
        return "(" + a.toString() + ", " + b.toString() + ", " + c.toString() + ", " + d.toString() +
                e.toString() + ", " + f.toString() + "," + g.toString() + ", " + h.toString() + ")";
    }

    @Override
    public int hashCode() {
        return a.hashCode() ^ b.hashCode() ^ c.hashCode() ^ d.hashCode() ^
                e.hashCode() ^ f.hashCode() ^ g.hashCode() ^ h.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null) {
            if (o == this)
                rv = true;
            else if (o instanceof Octuple) {
                Octuple oc = (Octuple) o;
                rv = a.equals(oc.a) && b.equals(oc.b) && c.equals(oc.c) && d.equals(oc.d) &&
                        e.equals(oc.e) && f.equals(oc.f) && g.equals(oc.g) && h.equals(oc.h);
            }
        }
        return rv;
    }
}