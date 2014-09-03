package pt.ua.it.atnog.utils.structures;

public abstract class Point {
    private final int maxDim;

    public Point(int maxDim) {
        this.maxDim = maxDim;
    }

    public int maxDim() {
        return maxDim;
    }

    public abstract double coor(int dim);

    public abstract double distance(Point e);

    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null && o instanceof Point) {
            Point e = (Point) o;
            if (maxDim == e.maxDim) {
                rv = true;
                for (int i = 0; i < e.maxDim && rv; i++)
                    if (Double.compare(coor(i),e.coor(i)) != 0)
                        rv = false;
            }
        }
        return rv;
    }
}
