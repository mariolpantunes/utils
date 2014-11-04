package pt.it.av.atnog.utils.structures;

import java.util.Comparator;

public class KDComparator<T extends Point> implements Comparator<T> {
    private int dim;

    public KDComparator() {
        this.dim = 0;
    }

    public void dim(int dim) {
        this.dim = dim;
    }

    public int compare(T e1, T e2) {
        return Double.compare(e1.coor(dim), e2.coor(dim));
    }
}