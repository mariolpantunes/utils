package pt.it.av.atnog.utils.structures;

import pt.it.av.atnog.utils.SortUtils;
import pt.it.av.atnog.utils.Utils;
import pt.it.av.atnog.utils.bla.Vector;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * @param <E> subtype of Vector stored within the KD-Tree
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class KDTree<E extends Vector> implements Collection<E> {
  private KDNode<E> root;
  private int size;

  /**
   *
   */
  public KDTree() {
    this(null, 0);
  }

  /**
   * @param root
   * @param size
   */
  private KDTree(KDNode<E> root, int size) {
    this.root = root;
    this.size = size;
  }

  /**
   * @param points
   * @param <E>
   * @return
   */
  public static <E extends Vector> KDTree<E> build(Collection<E> points) {
    E[] array = Utils.cast(new Vector[points.size()]);
    array = points.toArray(array);
    return build(array);
  }

  /**
   * @param points
   * @param <E>
   * @return
   */
  public static <E extends Vector> KDTree<E> build(E[] points) {
    KDTree<E> rv = null;
    if (points == null || points.length == 0)
      rv = new KDTree<E>();
    else
      rv = new KDTree<E>(build(points, 0, points.length - 1, 0, points[0].size()), points.length);
    return rv;
  }

  /**
   * @param points
   * @param left
   * @param right
   * @param cd
   * @param maxDim
   * @param <E>
   * @return
   */
  private static <E extends Vector> KDNode<E> build(E[] points, int left, int right, int cd, int maxDim) {
    KDNode<E> node = null;
    if (left < right) {
      SortUtils.qselect(points, left, right, left + ((right - left) / 2), (E e1, E e2) -> Double.compare(e1.get(cd), e2.get(cd)));
      int pivot = pivot(points, left, right, cd);
      node = new KDNode<E>(points[pivot]);
      node.left = build(points, left, pivot - 1, (cd + 1) % maxDim, maxDim);
      node.right = build(points, pivot + 1, right, (cd + 1) % maxDim, maxDim);
    } else if (left == right)
      node = new KDNode<E>(points[left]);
    return node;
  }

  /**
   * @param points
   * @param left
   * @param right
   * @param cd
   * @param <E>
   * @return
   */
  private static <E extends Vector> int pivot(E[] points, int left, int right, int cd) {
    int mid = left + ((right - left) / 2), pivot = mid;
    for (; pivot < right && points[pivot + 1].get(cd) == points[mid].get(cd); pivot++) ;
    return pivot;
  }

  /**
   * @return
   */
  public int dim() {
    return root.data.size();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public Iterator<E> iterator() {
    return new KDTreeIterator();
  }

  @Override
  public Object[] toArray() {
    Object a[] = new Object[size()];
    int i = 0;
    for (E e : this)
      a[i++] = e;
    return a;
  }

  @Override
  public <T> T[] toArray(T[] a) {
    int size = size();
    /**
     * If array is too small, allocate the new one with the same component type
     * If array is to large, set the first unassigned element to null
     */
    if (a.length < size)
      a = Utils.cast(Array.newInstance(a.getClass().getComponentType(), size));

    else if (a.length > size)
      a[size] = null;

    /** No need for checked cast
     * ArrayStoreException will be thrown if types are incompatible, just as required
     */
    int i = 0;
    for (E e : this)
      a[i++] = Utils.cast(e);
    return a;
  }

  @Override
  public void clear() {
    root = null;
    size = 0;
  }

  @Override
  public boolean add(E p) {
    boolean rv = true;
    try {
      root = add(p, root, 0);
      size++;
    } catch (IllegalArgumentException e) {
      rv = false;
    }
    return rv;
  }

  /**
   * @param target
   * @param node
   * @param cd
   * @return
   */
  private KDNode<E> add(E target, KDNode<E> node, int cd) {
    if (node == null)
      node = new KDNode<E>(target);
    else if (node.data.equals(target))
      throw new IllegalArgumentException("Already in the kdtree.");
    else if (target.get(cd) <= node.data.get(cd))
      node.left = add(target, node.left, (cd + 1) % dim());
    else
      node.right = add(target, node.right, (cd + 1) % dim());
    return node;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    return false;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean remove(Object o) {
    boolean rv = true;
    if (contains(o)) {
      root = remove(Utils.cast(o), root, 0);
      size--;
    } else
      rv = false;
    return rv;
  }

  /**
   * @param p
   * @param node
   * @param cd
   * @return
   */
  private KDNode<E> remove(E p, KDNode<E> node, int cd) {
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

  /**
   * @param target
   * @param dist
   * @return
   */
  public List<E> atMaxDist(E target, double dist) {
    List<E> list = new ArrayList<E>();
    atMaxDist(root, target, dist, list, 0);
    return list;
  }

  /**
   * @param node
   * @param target
   * @param dist
   * @param list
   * @param cd
   */
  private void atMaxDist(KDNode<E> node, E target, double dist, List<E> list,
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

  /**
   * @param target
   * @return
   */
  public E nearest(E target) {
    E rv = null;
    if (root != null) {
      KDNode<E> nn[] = Utils.cast(new KDNode[1]);
      nn[0] = parent(target, root, 0);
      nearest(root, nn, target, 0);
      rv = nn[0].data;
    }
    return rv;
  }

  /**
   * @param node
   * @param nn
   * @param target
   * @param cd
   */
  private void nearest(KDNode<E> node, KDNode<E> nn[], E target, int cd) {
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

  /**
   * @param p
   * @param node
   * @param cd
   * @return
   */
  private KDNode<E> parent(E p, KDNode<E> node, int cd) {
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

  /**
   * @param dim
   * @return
   */
  public E findMax(int dim) {
    return findMax(root, dim, 0);
  }

  /**
   * @param node
   * @param dim
   * @param cd
   * @return
   */
  private E findMax(KDNode<E> node, int dim, int cd) {
    E result = null;
    if (node != null) {
      Comparator<E> c = (E a, E b) -> (Double.compare(a.get(dim), b.get(dim)));
      if (cd == dim)
        result = Utils.max(findMax(node.right, dim, (cd + 1) % dim()),
            node.data, c);
      else
        result = Utils.max(Utils.max(findMax(node.left, dim, (cd + 1) % dim()),
            findMax(node.right, dim, (cd + 1) % dim()), c), node.data, c);
    }
    return result;
  }

  /**
   * @param dim
   * @return
   */
  public E findMin(int dim) {
    return findMin(root, dim, 0);
  }

  /**
   * @param node
   * @param dim
   * @param cd
   * @return
   */
  private E findMin(KDNode<E> node, int dim, int cd) {
    E result = null;
    if (node != null) {
      Comparator<E> c = (E a, E b) -> (Double.compare(a.get(dim), b.get(dim)));
      if (cd == dim)
        result = Utils.min(findMin(node.left, dim, (cd + 1) % dim()), node.data, c);
      else
        result = Utils.min(Utils.min(findMin(node.left, dim, (cd + 1) % dim()),
            findMin(node.right, dim, (cd + 1) % dim()), c), node.data, c);
    }
    return result;
  }

  @Override
  public boolean contains(Object o) {
    boolean rv = false;
    if (root != null)
      if (o instanceof Vector) {
        rv = contains(root, Utils.cast(o), 0);
      }
    return rv;
  }

  /**
   * @param node
   * @param target
   * @param cd
   * @return
   */
  private boolean contains(KDNode<E> node, E target, int cd) {
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

  /**
   * @param node
   * @param sb
   * @param prefix
   */
  private void print(KDNode<E> node, StringBuilder sb, int prefix) {
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

  /**
   * @param <E>
   */
  private static class KDNode<E extends Vector> {
    public E data;
    public KDNode<E> left, right;

    public KDNode(E data) {
      this.data = data;
      left = null;
      right = null;
    }

    @Override
    public String toString() {
      return data.toString();
    }
  }

  /**
   * @param <E>
   */
  private class KDTreeIterator implements Iterator<E> {
    private final Queue<KDNode<E>> q = new ArrayDeque<>();

    public KDTreeIterator() {
      if (root != null)
        q.add(root);
    }

    @Override
    public boolean hasNext() {
      return !q.isEmpty();
    }

    @Override
    public E next() {
      KDNode<E> node = q.poll();
      q.offer(node.left);
      q.offer(node.right);
      return node.data;
    }
  }
}
