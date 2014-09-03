package pt.ua.it.atnog.utils.structures;

import java.util.Comparator;

public class KDElementComparator implements Comparator<Point> {
    private int dim;
    public KDElementComparator(int dim) {
        this.dim = dim;
    }
    public int compare(Point e1, Point e2) {
        return Double.compare(e1.coor(dim), e2.coor(dim));
    }
}