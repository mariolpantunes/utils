package pt.ua.it.atnog.utils.structures;

public abstract class Point {
    protected final double[] coor;

    public Point(double[] coor) {
        this.coor = coor;
    }

    public Point(int dim) {
        coor = new double[dim];
    }

    public static <T extends Point> T min(T a, T b, int cd) {
        T rv = a;
        if (a != null && b != null) {
            if (Double.compare(b.coor[cd], rv.coor[cd]) < 0)
                rv = b;
        } else if (b != null)
            rv = b;
        return rv;
    }

    public static <T extends Point> T min(T a, T b, T c, int cd) {
        return min(min(a, b, cd), c, cd);
    }

    public static <T extends Point> T max(T a, T b, int cd) {
        T rv = a;
        if (a != null && b != null) {
            if (Double.compare(b.coor[cd], rv.coor[cd]) > 0)
                rv = b;
        } else if (b != null)
            rv = b;
        return rv;
    }

    public static <T extends Point> T max(T a, T b, T c, int cd) {
        return max(max(a, b, cd), c, cd);
    }

    public int dim() {
        return coor.length;
    }

    public double[] coor() {
        return coor;
    }

    public double coor(int i) {
        return coor[i];
    }

    //TODO add exception
    //throw new IllegalArgumentException(msg);
    public double minkowskiDistance(Point po, int p) {
        double sum = 0.0;
        for (int i = 0; i < coor.length; i++) {
            double absDiff = Math.abs(coor[i] - po.coor[i]);
            sum += java.lang.Math.pow(absDiff, p);
        }
        return java.lang.Math.pow(sum, 1.0 / p);
    }

    public double euclideanDistance(Point p) {
        return minkowskiDistance(p, 2);
    }

    public double manhattanDistance(Point p) {
        return minkowskiDistance(p, 1);
    }

    public boolean equals(Object o) {
        boolean rv = false;
        if (o != null && o instanceof Point) {
            Point e = (Point) o;
            if (coor.length == e.coor.length) {
                rv = true;
                for (int i = 0; i < e.coor.length && rv; i++)
                    if (Double.compare(coor[i], e.coor[i]) != 0)
                        rv = false;
            }
        }
        return rv;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < coor.length - 1; i++)
            sb.append(coor[i] + "; ");
        sb.append(coor[coor.length - 1] + ")");
        return sb.toString();
    }
}
