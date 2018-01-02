package pt.it.av.atnog.utils.structures;

import pt.it.av.atnog.utils.bla.Vector;

/**
 * Three-dimension point.
 * <p>
 * Used to test the clustering algorithms.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Point3D extends Vector {

  /**
   * 3D Point constructor.
   *
   * @param x coordinate of the point.
   * @param y coordinate of the point.
   * @param z coordinate of the point.
   */
  public Point3D(final double x, final double y, final double z) {
    super(3);
    data[0] = x;
    data[1] = y;
    data[2] = z;
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

  /**
   * Returns the z coordinate of this point.
   *
   * @return the z coordinate of this point.
   */
  public double z() {
    return data[2];
  }

  @Override
  public String toString() {
    return "(" + data[0] + "," + data[1] + "," + data[2] + ")";
  }
}