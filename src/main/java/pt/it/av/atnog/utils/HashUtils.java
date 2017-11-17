package pt.it.av.atnog.utils;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class HashUtils {
  public static final long FNV_PRIME_32 = 16777619;
  public static final long FNV_OFFSET_32 = 2166136261L;

  /**
   * Utility class, lets make the constructor private.
   */
  private HashUtils() {}

  /**
   * Fowler/Noll/Vo hash 32-bit implementation.
   * Return an 32 unsigned int hash.
   *
   * @param array byte array to hash
   * @return an 32 unsigned int hash
   * @see <a href="http://www.isthe.com/chongo/tech/comp/fnv/">FNV Hash</a>
   */
  public static long fnv1a(byte array[]) {
    long hash = FNV_OFFSET_32;

    for (int i = 0; i < array.length; i++) {
      hash = (hash ^ (array[i])) * FNV_PRIME_32;
    }

    return  hash;
  }

  /**
   * @param array
   * @return
   */
  public static long jenkins(byte array[]) {
    long hash = 0;

    for (int i = 0; i < array.length; i++) {
      hash += array[i];
      hash += hash << 10;
      hash ^= hash >> 6;
    }

    hash += hash << 3;
    hash ^= hash >> 11;
    hash += hash << 15;

    return hash;
  }
}
