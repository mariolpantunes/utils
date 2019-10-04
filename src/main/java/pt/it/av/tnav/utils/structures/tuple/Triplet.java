package pt.it.av.tnav.utils.structures.tuple;

/**
 * @param <A>
 * @param <B>
 * @param <C>
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

  @Override
  public String toString() {
    return "(" + a.toString() + ", " + b.toString() + ", " + c.toString() + ")";
  }

  @Override
  public int hashCode() {
    int prime = 31, result = 1;
    result = prime * result + ((a == null) ? 0 : a.hashCode());
    result = prime * result + ((b == null) ? 0 : b.hashCode());
    result = prime * result + ((c == null) ? 0 : c.hashCode());
    return result;
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
