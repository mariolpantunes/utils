package pt.it.av.tnav.utils.structures.cache;

/**
 *
 * @author Mário Antunes
 * @version 1.0
 */
public interface Cache<K,V> {
  /**
   *
   * @param key
   * @return
   */
  V fetch(K key);
}
