package pt.it.av.tnav.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CollectionsUtils {

  /**
   *
   * @param l1
   * @param l2
   * @param <T>
   * @return
   */
  public static <T> boolean equals(Collection<T> l1, Collection<T> l2) {
    boolean rv = false;

    if(l1 == null && l2 == null) {
      rv = true;
    } else if (l1.size() != l2.size()) {
      rv = false;
    } else {
      rv = l1.containsAll(l2);
    }

    return rv;
  }

  /**
   *
   * @param a
   * @param b
   * @param <T>
   * @return
   */
  public static <T> List<T> intertwine(List<T> a, List<T> b) {
    List<T> rv = new ArrayList<>();

    Iterator<T> itA = a.iterator(), itB = b.iterator();
    while (itA.hasNext() && itB.hasNext()) {
      rv.add(itA.next());
      rv.add(itB.next());
    }

    while (itA.hasNext())
      rv.add(itA.next());

    while (itB.hasNext())
      rv.add(itB.next());

    return rv;
  }

  /**
   *
   * @param it
   * @param <T>
   * @return
   */
  public static <T> List<T> iterator2List(Iterator<T> it) {
    List<T> rv = new ArrayList<>();
    while (it.hasNext())
      rv.add(it.next());
    return rv;
  }
}
