package pt.it.av.tnav.utils.structures.tuple;

/**
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 * @param <E>
 * @param <F>
 * @param <G>
 * @param <H>
 */
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
    int prime = 31, result = 1;
    result = prime * result + ((a == null) ? 0 : a.hashCode());
    result = prime * result + ((b == null) ? 0 : b.hashCode());
    result = prime * result + ((c == null) ? 0 : c.hashCode());
    result = prime * result + ((d == null) ? 0 : d.hashCode());
    result = prime * result + ((e == null) ? 0 : e.hashCode());
    result = prime * result + ((f == null) ? 0 : f.hashCode());
    result = prime * result + ((g == null) ? 0 : g.hashCode());
    result = prime * result + ((h == null) ? 0 : h.hashCode());
    return result;
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