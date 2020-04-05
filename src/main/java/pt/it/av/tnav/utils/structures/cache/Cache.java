package pt.it.av.tnav.utils.structures.cache;

/**
 *
 * @author Mário Antunes
 * @version 1.0
 */
public interface Cache<K, V> {
  /**
   * Returns one element from the cache.
   * @param key the identifiers used in the cache
   * @return one element from the cache
   */
  V fetch(K key);

  /**
   * Cleans the cache.
   */
  void clear();
}
