package pt.it.av.tnav.utils.structures.point;

import pt.it.av.tnav.utils.bla.Matrix;
import pt.it.av.tnav.utils.bla.factorization.NMF;
import pt.it.av.tnav.utils.structures.Distance;
import pt.it.av.tnav.utils.structures.Similarity;

import java.util.List;

/**
 * Helper class used to cluster points.
 * <p>
 * The distance and similarities are store in a matrix. This way latent models
 * ({@link NMF}) can be used to improve performance.
 * </p>
 *
 * @param <E> the type of elements in this point
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class MatrixPoint<E> implements Distance<MatrixPoint<E>>, Similarity<MatrixPoint<E>> {
  private final E e;
  private final Matrix matrix;
  private final List<E> map;
  private final int selfIdx;

  /**
   *
   * @param e
   * @param m
   * @param map
   */
  public MatrixPoint(E e, Matrix m, List<E> map) {
    this.e = e;
    this.matrix = m;
    this.map = map;
    this.selfIdx = map.indexOf(e);
  }

  /**
   *
   * @return
   */
  public E point() {
    return e;
  }

  @Override
  public double distanceTo(MatrixPoint<E> point) {
    return 1.0 - similarityTo(point);
  }

  @Override
  public double similarityTo(MatrixPoint<E> point) {
    double rv = 1.0;
    if (!point.e.equals(e)) {
      int p2 = map.indexOf(point.e);
      rv = matrix.get(selfIdx, p2);
    }
    return rv;
  }

  @Override
  public boolean equals(Object o) {
    boolean rv = false;
    if (o != null) {
      if (o == this)
        rv = true;
      else if (o instanceof MatrixPoint) {
        MatrixPoint<?> mp = (MatrixPoint<?>) o;
        rv = e.equals(mp.e);
      }
    }
    return rv;
  }

  @Override
  public int hashCode() {
    int prime = 31, result = 1;
    result = prime * result + ((e == null) ? 0 : e.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return e.toString();
  }
}
