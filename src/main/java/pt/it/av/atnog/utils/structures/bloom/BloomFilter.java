package pt.it.av.atnog.utils.structures.bloom;

import java.util.BitSet;
import java.util.function.Predicate;

public class BloomFilter<T> implements Predicate<T> {
  private final BitSet filter;
  private final int k, m;
  private int nElements;
  private Hash<T> h;

  public BloomFilter(final int m, final int k, final Hash<T> h) {
    this.m = m;
    this.k = k;
    this.h = h;
    this.filter = new BitSet(m);
    nElements = 0;
  }

  /**
   * @param n
   * @param m
   * @return
   */
  private static int optimalK(long n, long m) {
    return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
  }

  /**
   * @param n
   * @param p
   * @return
   */
  private static long optimalM(long n, double p) {
    if (p == 0) {
      p = Double.MIN_VALUE;
    }
    return (long) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
  }

  /**
   * @param e
   */
  public void add(T e) {
    int h1 = e.hashCode() & 0x7fffffff,
        h2 = (int) (h.hash(e) & 0x7fffffff);

    for (int i = 0; i < k; i++) {
      filter.set(hash(h1, h2, i, m));
    }

    nElements++;
  }

  public boolean contains(T e) {
    boolean rv = true;
    long h1 = e.hashCode() & 0x7fffffff,
        h2 = (int) (h.hash(e) & 0x7fffffff);

    for (int i = 0; i < k && rv; i++) {
      if (!filter.get(hash(h1, h2, i, m))) {
        rv = false;
      }
    }

    return rv;
  }

  /**
   * @param h1
   * @param h2
   * @param i
   * @param m
   * @return
   */
  private int hash(final long h1, final long h2, final int i, final int m) {
    int rv = Math.toIntExact((h1 + h2 * i) % m);
    return rv;
  }

  public void clear() {
    filter.clear();
    nElements = 0;
  }

  @Override
  public boolean test(T t) {
    return false;
  }
}
