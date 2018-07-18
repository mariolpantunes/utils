package pt.it.av.tnav.utils.structures.bloom;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public interface Hash<T> {
  long hash(T e);
}
