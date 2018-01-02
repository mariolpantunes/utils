package pt.it.av.atnog.utils.structures;

import pt.it.av.atnog.utils.bla.Vector;

/**
 * Two-dimension point.
 * <p>
 * Used to test the clustering algorithms.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Point2D extends Vector {
  /**
   * 2D Point constructor.
   *
   * @param x coordinate of the point
   * @param y coordinate of the point
   */
  public Point2D(double x, double y) {
    super(2);
    data[0] = x;
    data[1] = y;
  }

  /**
   * Returns the x coordinate of this point.
   *
   * @return the x coordinate of this point.
   */
  public double x() {
    return data[0];
  }

  /**
   * Returns the y coordinate of this point.
   *
   * @return the y coordinate of this point.
   */
  public double y() {
    return data[1];
  }

  @Override
  public String toString() {
    return "(" + data[0] + "," + data[1] + ")";
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
}