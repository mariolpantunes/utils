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
     * @param x coordenate of the point
     */
    public Point1D(double x) {
        super(1);
        data[0] = x;
    }

    /**
     * @return
     */
    public double x() {
        return data[0];
    }

    @Override
    public String toString() {
        return "(" + data[0] + ")";
    }
}
