package pt.it.av.tnav.utils.structures.point;

import pt.it.av.tnav.utils.structures.Distance;

/**
 * One-dimension point.
 * <p>
 * Used to test the clustering algorithms.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Point1D implements Distance<Point1D> {
  private final double x;
  /**
   * 1D Point constructor.
   *
   * @param x coordinate of the point
   */
  public Point1D(final double x) {
    this.x = x;
  }

  /**
   * Returns the x coordinate of this point.
   *
   * @return the x coordinate of this point.
   */
  public double x() {
    return x;
  }

  /**
   * @return
   */
  public boolean isZero() {
    boolean rv = false;
    if (x == 0) {
      rv = true;
    }
    return rv;
  }

  @Override
  public String toString() {
    return "(" + x + ")";
  }

  @Override
  public double distanceTo(Point1D d) {
    return Math.abs(d.x - x);
  }
}
