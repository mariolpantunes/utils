package pt.it.av.atnog.utils.structures;

import pt.it.av.atnog.utils.bla.Vector;

public class Point2D extends Vector {
    public Point2D(int x, int y) {
        super(2);
        data[0] = x;
        data[1] = y;
    }

    public double x() {
        return data[0];
    }

    public double y() {
        return data[1];
    }
}
