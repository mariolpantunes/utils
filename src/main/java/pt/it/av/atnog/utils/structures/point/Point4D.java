package pt.it.av.atnog.utils.structures.point;

import pt.it.av.atnog.utils.structures.Distance;

/**
 * Four-dimension point.
 * <p>
 * Used to test the clustering algorithms.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Point4D implements Distance<Point4D> {
  private final double x, y, z, w;
  /**
   * 2D Point constructor.
   *
   * @param x coordenate of the point
   * @param y coordenate of the point
   */
  public Point4D(final double x, final double y, final double z, final double w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
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

  /**
   * Returns the z coordinate of this point.
   *
   * @return the z coordinate of this point.
   */
  public double z() {
    return z;
  }

  /**
   * Returns the w coordinate of this point.
   *
   * @return the w coordinate of this point.
   */
  public double w() {
    return w;
  }

  @Override
  public String toString() {
    return "(" + x + "," + y + "," + z + "," + w + ")";
  }

  /**
   * @return
   */
  public boolean isZero() {
    boolean rv = false;
    if (x == 0 && y == 0 && z == 0 && w == 0) {
      rv = true;
    }
    return rv;
  }

  @Override
  public double distanceTo(Point4D d) {
    return Math.sqrt(Math.pow(d.x - x, 2) + Math.pow(d.y - y, 2) + Math.pow(d.z - z, 2) + Math.pow(d.w - w, 2));
  }
}