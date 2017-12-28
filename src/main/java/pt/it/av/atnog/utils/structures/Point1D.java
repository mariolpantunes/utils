package pt.it.av.atnog.utils.structures;

import pt.it.av.atnog.utils.bla.Vector;

/**
 * One-dimension point.
 * <p>
 * Used to test the clustering algorithms.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Point1D extends Vector {
  /**
   * 1D Point constructor.
   *
   * @param x coordinate of the point
   */
  public Point1D(final double x) {
    super(1);
    data[0] = x;
  }

  /**
   * Returns the x coordinate of this point.
   *
   * @return the x coordinate of this point.
   */
  public double x() {
    return data[0];
  }

  @Override
  public String toString() {
    return "(" + data[0] + ")";
  }
}
