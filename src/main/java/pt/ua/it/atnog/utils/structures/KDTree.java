package pt.ua.it.atnog.utils.structures;

import pt.ua.it.atnog.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class KDTree<T extends Point> {
    private int size;
    private KDNode<T> root;

    public KDTree() {
        this(null, 0);
    }

    private KDTree(KDNode<T> root, int size) {
        this.root = root;
        this.size = size;
    }

    public static <T extends Point> KDTree<T> build(List<T> points) {
        T[] array = (T[]) new Point[5];
        array = points.toArray(array);
        return build(array);
    }

    public static <T extends Point> KDTree<T> build(T[] points) {
        KDTree<T> rv = null;
        if (points == null || points.length == 0)
            rv = new KDTree<T>();
        else {
            KDComparator<T> comp = new KDComparator();
            rv = new KDTree<T>(build(points, 0, points.length - 1, 0, points[0].dim(), comp), points.length);
        }
        return rv;
    }

    private static <T extends Point> KDNode<T> build(T[] points, int left, int right, int cd, int maxDim, KDComparator<T> comp) {
        KDNode<T> node = null;
        if (left < right) {
            comp.dim(cd);
            Utils.qselect(points, left, right, left + ((right - left) / 2), comp);
            int pivot = pivot(points, left, right, cd);
            node = new KDNode<T>(points[pivot]);
            node.left = build(points, left, pivot - 1, (cd + 1) % maxDim, maxDim, comp);
            node.right = build(points, pivot + 1, right, (cd + 1) % maxDim, maxDim, comp);
        } else if (left == right)
            node = new KDNode<T>(points[left]);
        return node;
    }

    private static <T extends Point> int pivot(T[] points, int left, int right, int cd) {
        int mid = left + ((right - left) / 2), pivot = mid;
        for (; pivot <= right && points[pivot + 1].coor(cd) == points[mid].coor(cd); pivot++) ;
        return pivot;
    }

    public int dim() {
        return root.data.dim();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public void add(T p) {
        root = add(p, root, 0);
        size++;
    }

    private KDNode<T> add(T target, KDNode<T> node, int cd) {
        if (node == null)
            node = new KDNode<T>(target);
        else if (node.data.equals(target))
            System.err.println("error! duplicate");
        else if (target.coor(cd) <= node.data.coor(cd))
            node.left = add(target, node.left, (cd + 1) % dim());
        else
            node.right = add(target, node.right, (cd + 1) % dim());
        return node;
    }

    public void remove(T p) {
        root = remove(p, root, 0);
        size--;
    }

    private KDNode<T> remove(T p, KDNode<T> node, int cd) {
        if (node != null) {
            if (node.data.equals(p)) {
                if (node.left != null) {
                    node.data = findMax(node.left, cd, (cd + 1) % dim());
                    node.left = remove(node.data, node.left, (cd + 1) % dim());
                } else if (node.right != null) {
                    node.data = findMax(node.right, cd, (cd + 1) % dim());
                    node.left = remove(node.data, node.right, (cd + 1) % dim());
                    node.right = null;
                } else
                    node = null;
            } else if (p.coor(cd) <= node.data.coor(cd))
                node.left = remove(p, node.left, (cd + 1) % dim());
            else
                node.right = remove(p, node.right, (cd + 1) % dim());
        }
        return node;
    }

    private KDNode<T> parent(Point p) {
        if (root != null)
            return parent(p, root, 0);
        else
            return null;
    }

    private KDNode<T> parent(Point p, KDNode<T> node, int cd) {
        if (p.coor(cd) <= node.data.coor(cd)) {
            if (node.left == null)
                return node;
            else
                return parent(p, node.left, (cd + 1) % dim());
        } else {
            if (node.right == null)
                return node;
            else
                return parent(p, node.right, (cd + 1) % dim());
        }
    }

    public List<T> closer(T target, double dist) {
        List<T> list = new ArrayList<T>();
        closer(root, target, dist, list, 0);
        return list;
    }

    private void closer(KDNode<T> node, T target, double dist, List<T> list,
                        int cd) {
        if (node != null) {
            if (target.euclideanDistance(node.data) < dist)
                list.add(node.data);
            double dp = Math.abs(node.data.coor(cd) - target.coor(cd));
            if (dp < dist) {
                closer(node.left, target, dist, list, (cd + 1) % dim());
                closer(node.right, target, dist, list, (cd + 1) % dim());
            } else if (target.coor(cd) <= node.data.coor(cd))
                closer(node.left, target, dist, list, (cd + 1) % dim());
            else
                closer(node.right, target, dist, list, (cd + 1) % dim());
        }
    }

    public T nearest(T target) {
        T rv = null;

        if (root != null) {
            KDNode<T> parent = parent(target);
            double dist[] = {target.euclideanDistance(parent.data)};
            KDNode<T> bestOne = nearest(root, dist, target, 0);
            if (bestOne != null)
                rv = bestOne.data;
            else
                rv = parent.data;
        }

        return rv;
    }

    private KDNode<T> nearest(KDNode<T> node, double[] dist,
                              Point target, int cd) {
        KDNode<T> result = null;
        if (node != null) {
            if (target.euclideanDistance(node.data) < dist[0]) {
                result = node;
                dist[0] = target.euclideanDistance(result.data);
            }
            double dp = Math.abs(node.data.coor(cd) - target.coor(cd));
            if (dp < dist[0]) {
                KDNode<T> temp = null;
                temp = nearest(node.left, dist, target, (cd + 1) % dim());
                if (temp != null) {
                    result = temp;
                    dist[0] = target.euclideanDistance(result.data);
                }
                temp = nearest(node.right, dist, target, (cd + 1) % dim());
                if (temp != null) {
                    result = temp;
                    dist[0] = target.euclideanDistance(result.data);
                }
            } else {
                if (target.coor(cd) <= node.data.coor(cd)) {
                    if (node.left != null)
                        result = nearest(node.left, dist, target, (cd + 1) % dim());
                } else {
                    if (node.right != null)
                        result = nearest(node.right, dist, target, (cd + 1) % dim());
                }
            }
        }
        return result;
    }

    public T findMax(int dim) {
        return findMax(root, dim, 0);
    }

    private T findMax(KDNode<T> node, int dim, int cd) {
        T result = null;
        if (node != null) {
            if (cd == dim) {
                result = Point.max(findMax(node.right, dim, (cd + 1) % dim()),
                        node.data, dim);
            } else {
                result = Point.max(findMax(node.left, dim, (cd + 1) % dim()),
                        findMax(node.right, dim, (cd + 1) % dim()), node.data, dim);
            }
        }
        return result;
    }

    public T findMin(int dim) {
        return findMin(root, dim, 0);
    }

    private T findMin(KDNode<T> node, int dim, int cd) {
        T result = null;
        if (node != null) {
            if (cd == dim) {
                result = Point.min(findMin(node.left, dim, (cd + 1) % dim()),
                        node.data, dim);
            } else {
                result = Point.min(findMin(node.left, dim, (cd + 1) % dim()),
                        findMin(node.right, dim, (cd + 1) % dim()), node.data, dim);
            }
        }
        return result;
    }

    public boolean contains(Point target) {
        boolean rv = false;
        if (root != null)
            rv = contains(root, target, 0);
        return rv;
    }

    private boolean contains(KDNode<T> node, Point target, int cd) {
        boolean rv = false;
        if (node != null) {
            if (node.data.equals(target))
                rv = true;
            else if (target.coor(cd) <= node.data.coor(cd)) {
                rv = contains(node.left, target, (cd + 1) % dim());
            } else {
                rv = contains(node.right, target, (cd + 1) % dim());
            }
        }
        return rv;
    }

    private void print(KDNode<T> node, StringBuilder sb, int prefix) {
        if (node != null) {
            for (int i = 0; i < prefix; i++)
                sb.append(" ");
            String nodeTxt = node.toString();
            sb.append("|--" + nodeTxt + "\n");
            prefix += nodeTxt.length() + 3;
            print(node.left, sb, prefix + 3);
            print(node.right, sb, prefix + 3);
        } else {
            for (int i = 0; i < prefix; i++)
                sb.append(" ");
            sb.append("|--NULL\n");
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        print(root, sb, 0);
        return sb.toString();
    }
}
