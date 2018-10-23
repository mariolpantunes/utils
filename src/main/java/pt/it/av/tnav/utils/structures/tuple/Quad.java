package pt.it.av.tnav.utils.structures.tuple;

/**
 *
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
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

  @Override
  public String toString() {
    return "(" + a.toString() + ", " + b.toString() + ", " + c.toString() + ", " + d.toString() + ")";
  }

  @Override
  public int hashCode() {
    int prime = 31, result = 1;
    result = prime * result + ((a == null) ? 0 : a.hashCode());
    result = prime * result + ((b == null) ? 0 : b.hashCode());
    result = prime * result + ((c == null) ? 0 : c.hashCode());
    result = prime * result + ((d == null) ? 0 : d.hashCode());
    return result;
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
