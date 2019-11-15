package pt.it.av.tnav.utils.structures.tuple;

/**
 * @param <A>
 * @param <B>
 */
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
    int prime = 31, result = 1;
    result = prime * result + ((a == null) ? 0 : a.hashCode());
    result = prime * result + ((b == null) ? 0 : b.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object o) {
    boolean rv = false;
    if (o != null) {
      if (o == this)
        rv = true;
      else if (o instanceof Pair<?, ?>) {
        Pair<?, ?> p = (Pair<?, ?>) o;
        rv = this.a.equals(p.a) && this.b.equals(p.b);
      }
    }

    return rv;
  }
}