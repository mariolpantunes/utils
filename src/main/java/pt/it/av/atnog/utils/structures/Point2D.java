package pt.it.av.atnog.utils.structures;

public class Point2D extends Point {
    public Point2D(int x, int y) {
        super(2);
        coor[0] = x;
        coor[1] = y;
    }

    public double x() {
        return coor[0];
    }

    public double y() {
        return coor[1];
    }
}
