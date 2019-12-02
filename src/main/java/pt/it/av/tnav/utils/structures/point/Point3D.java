package pt.it.av.tnav.utils.structures.point;

import pt.it.av.tnav.utils.structures.Distance;

/**
 * Three-dimension point.
 * <p>
 * Used to test the clustering algorithms.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Point3D implements Distance<Point3D> {
  private final double x, y, z;
  /**
   * 3D Point constructor.
   *
   * @param x coordinate of the point.
   * @param y coordinate of the point.
   * @param z coordinate of the point.
   */
  public Point3D(final double x, final double y, final double z) {
    this.x = x;
    this.y = y;
    this.z = z;
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

  @Override
  public String toString() {
    return "(" + x + "," + y + "," + z + ")";
  }

  /**
   * @return
   */
  public boolean isZero() {
    boolean rv = false;
    if (x == 0 && y == 0 && z == 0) {
      rv = true;
    }
    return rv;
  }

  @Override
  public double distanceTo(Point3D d) {
    return Math.sqrt(Math.pow(d.x - x, 2) + Math.pow(d.y - y, 2) + Math.pow(d.z - z, 2));
  }
}