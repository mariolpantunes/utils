package pt.it.av.atnog.utils.structures;

public class Pair<F, S> {
    public final F first;
    public final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F first() {
        return first;
    }

    public S second() {
        return second;
    }

    @Override
    public int hashCode() {
        return first.hashCode() ^ second.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null) {
            if (o == this)
                rv = true;
            else if (o instanceof Pair) {
                Pair p = (Pair) o;
                rv = this.first.equals(p.first) &&
                        this.second.equals(p.second);
            }
        }
        return rv;
    }
}