package pt.it.av.atnog.utils.structures;

import pt.it.av.atnog.utils.Utils;
import pt.it.av.atnog.utils.bla.Vector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KDTree<T extends Vector> {
    private KDNode<T> root;
    private int size;

    public KDTree() {
        this(null, 0);
    }

    private KDTree(KDNode<T> root, int size) {
        this.root = root;
        this.size = size;
    }

    public static <T extends Vector> KDTree<T> build(List<T> points) {
        T[] array = (T[]) new Vector[points.size()];
        array = points.toArray(array);
        return build(array);
    }

    public static <T extends Vector> KDTree<T> build(T[] points) {
        KDTree<T> rv = null;
        if (points == null || points.length == 0)
            rv = new KDTree<T>();
        else
            rv = new KDTree<T>(build(points, 0, points.length - 1, 0, points[0].size()), points.length);
        return rv;
    }

    private static <T extends Vector> KDNode<T> build(T[] points, int left, int right, int cd, int maxDim) {
        KDNode<T> node = null;
        if (left < right) {
            Utils.qselect(points, left, right, left + ((right - left) / 2), (T e1, T e2) -> Double.compare(e1.get(cd), e2.get(cd)));
            int pivot = pivot(points, left, right, cd);
            node = new KDNode<T>(points[pivot]);
            node.left = build(points, left, pivot - 1, (cd + 1) % maxDim, maxDim);
            node.right = build(points, pivot + 1, right, (cd + 1) % maxDim, maxDim);
        } else if (left == right)
            node = new KDNode<T>(points[left]);
        return node;
    }

    private static <T extends Vector> int pivot(T[] points, int left, int right, int cd) {
        int mid = left + ((right - left) / 2), pivot = mid;
        for (; pivot < right && points[pivot + 1].get(cd) == points[mid].get(cd); pivot++) ;
        return pivot;
    }

    public int dim() {
        return root.data.size();
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
            throw new IllegalArgumentException("Already in the kdtree.");
        else if (target.get(cd) <= node.data.get(cd))
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
            } else if (p.get(cd) <= node.data.get(cd))
                node.left = remove(p, node.left, (cd + 1) % dim());
            else
                node.right = remove(p, node.right, (cd + 1) % dim());
        }
        return node;
    }

    public List<T> atMaxDist(T target, double dist) {
        List<T> list = new ArrayList<T>();
        atMaxDist(root, target, dist, list, 0);
        return list;
    }

    private void atMaxDist(KDNode<T> node, T target, double dist, List<T> list,
                           int cd) {
        if (node != null) {
            if (target.euclideanDistance(node.data) < dist)
                list.add(node.data);
            double dp = Math.abs(node.data.get(cd) - target.get(cd));
            if (dp < dist) {
                atMaxDist(node.left, target, dist, list, (cd + 1) % dim());
                atMaxDist(node.right, target, dist, list, (cd + 1) % dim());
            } else if (target.get(cd) <= node.data.get(cd))
                atMaxDist(node.left, target, dist, list, (cd + 1) % dim());
            else
                atMaxDist(node.right, target, dist, list, (cd + 1) % dim());
        }
    }

    public T nearest(T target) {
        T rv = null;
        if (root != null) {
            KDNode<T> nn[] = new KDNode[1];
            nn[0] = parent(target, root, 0);
            nearest(root, nn, target, 0);
            rv = nn[0].data;
        }
        return rv;
    }

    private void nearest(KDNode<T> node, KDNode<T> nn[], T target, int cd) {
        if (node != null) {
            if (target.euclideanDistance(node.data) < target.euclideanDistance(nn[0].data))
                nn[0] = node;
            double dp = Math.abs(node.data.get(cd) - target.get(cd));
            if (dp < target.euclideanDistance(nn[0].data)) {
                nearest(node.left, nn, target, (cd + 1) % dim());
                nearest(node.right, nn, target, (cd + 1) % dim());
            } else {
                if (target.get(cd) <= node.data.get(cd))
                    nearest(node.left, nn, target, (cd + 1) % dim());
                else
                    nearest(node.right, nn, target, (cd + 1) % dim());
            }
        }
    }

    private KDNode<T> parent(T p, KDNode<T> node, int cd) {
        if (p.get(cd) <= node.data.get(cd)) {
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

    public T findMax(int dim) {
        return findMax(root, dim, 0);
    }

    private T findMax(KDNode<T> node, int dim, int cd) {
        T result = null;
        if (node != null) {
            Comparator<T> c = (T a, T b) -> (Double.compare(a.get(dim), b.get(dim)));
            if (cd == dim)
                result = Utils.max(findMax(node.right, dim, (cd + 1) % dim()),
                        node.data, c);
            else
                result = Utils.max(Utils.max(findMax(node.left, dim, (cd + 1) % dim()),
                        findMax(node.right, dim, (cd + 1) % dim()), c), node.data, c);
        }
        return result;
    }

    public T findMin(int dim) {
        return findMin(root, dim, 0);
    }

    private T findMin(KDNode<T> node, int dim, int cd) {
        T result = null;
        if (node != null) {
            Comparator<T> c = (T a, T b) -> (Double.compare(a.get(dim), b.get(dim)));
            if (cd == dim)
                result = Utils.min(findMin(node.left, dim, (cd + 1) % dim()), node.data, c);
            else
                result = Utils.min(Utils.min(findMin(node.left, dim, (cd + 1) % dim()),
                        findMin(node.right, dim, (cd + 1) % dim()), c), node.data, c);
        }
        return result;
    }

    public boolean contains(T target) {
        boolean rv = false;
        if (root != null)
            rv = contains(root, target, 0);
        return rv;
    }

    private boolean contains(KDNode<T> node, T target, int cd) {
        boolean rv = false;
        if (node != null) {
            if (node.data.equals(target))
                rv = true;
            else if (target.get(cd) <= node.data.get(cd))
                rv = contains(node.left, target, (cd + 1) % dim());
            else
                rv = contains(node.right, target, (cd + 1) % dim());
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        print(root, sb, 0);
        return sb.toString();
    }

    private static class KDNode<T extends Vector> {
        public T data;
        public KDNode<T> left, right;

        public KDNode(T data) {
            this.data = data;
            left = null;
            right = null;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }
}
