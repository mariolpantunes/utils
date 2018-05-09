package pt.it.av.atnog.utils.structures.bloom;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public interface Hash<T> {
  int hash(T e);
}
