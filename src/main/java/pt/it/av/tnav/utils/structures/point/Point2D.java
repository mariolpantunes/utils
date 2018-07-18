package pt.it.av.tnav.utils.structures.point;

import pt.it.av.tnav.utils.structures.Distance;

/**
 * Two-dimension point.
 * <p>
 * Used to test the clustering algorithms.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Point2D implements Distance<Point2D> {
  private final double x, y;

  /**
   * 2D Point constructor.
   *
   * @param x coordinate of the point
   * @param y coordinate of the point
   */
  public Point2D(double x, double y) {
    this.x = x;
    this.y = y;
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
   * Returns the y coordinate of this point.
   *
   * @return the y coordinate of this point.
   */
  public double y() {
    return y;
  }

  @Override
  public String toString() {
    return "(" + x + "," + y + ")";
  }

  /**
   * @return
   */
  public boolean isZero() {
    boolean rv = false;
    if (x == 0 && y == 0) {
      rv = true;
    }
    return rv;
  }

  /**
   * Return the quadrant of the {@link Point2D}.
   *
   * @return the quadrant of the {@link Point2D}.
   */
  public int quadrant() {
    int rv = 0;

    if (!isZero()) {
      if (x() > 0 && y() > 0) {
        rv = 1;
      } else if (x() < 0 && y() > 0) {
        rv = 2;
      } else if (x() < 0 && y() < 0) {
        rv = 3;
      } else {
        rv = 4;
      }
    }

    return rv;
  }

  @Override
  public double distanceTo(Point2D d) {
    return Math.sqrt(Math.pow(d.x - x, 2) + Math.pow(d.y - y, 2));
  }
}