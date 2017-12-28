package pt.it.av.atnog.utils.structures;

import pt.it.av.atnog.utils.bla.Vector;

/**
 * Four-dimension point.
 * <p>
 * Used to test the clustering algorithms.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Point4D extends Vector {

  /**
   * 2D Point constructor.
   *
   * @param x coordenate of the point
   * @param y coordenate of the point
   */
  public Point4D(final double x, final double y, final double z, final double w) {
    super(4);
    data[0] = x;
    data[1] = y;
    data[2] = z;
    data[3] = w;
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

  /**
   * Returns the z coordinate of this point.
   *
   * @return the z coordinate of this point.
   */
  public double w() {
    return data[3];
  }

  @Override
  public String toString() {
    return "(" + data[0] + ";" + data[1] + ")";
  }
}