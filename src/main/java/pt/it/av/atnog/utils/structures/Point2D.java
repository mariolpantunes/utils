package pt.it.av.atnog.utils.structures;

import pt.it.av.atnog.utils.bla.Vector;

/**
 * Two-dimension point.
 * <p>
 *     Used to test the clustering algorithms.
 * </p>
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class Point2D extends Vector implements Distance<Point2D> {

    /**
     * 2D Point constructor.
     * @param x coordenate of the point
     * @param y coordenate of the point
     */
    public Point2D(double x, double y) {
        super(2);
        data[0] = x;
        data[1] = y;
    }

    /**
     *
     * @return
     */
    public double x() {
        return data[0];
    }

    /**
     *
     * @return
     */
    public double y() {
        return data[1];
    }

    @Override
    public double distanceTo(Point2D d) {
        return euclideanDistance(d);
    }

    @Override
    public String toString() { return "(" + data[0] + ";" + data[1] + ")"; }
}