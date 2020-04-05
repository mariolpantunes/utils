package pt.it.av.tnav.utils.structures.mutableNumber;

/**
 * This class extends {@link Number}, but does not define methods such as equals,
 * hashCode and compareTo because instances are expected to be mutated,
 * and so are not useful as collection keys.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public abstract class MutableNumber<N> extends Number {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public abstract N add(N n);

  public abstract N sub(N n);

  public abstract N mul(N n);

  public abstract N div(N n);

  public abstract N pow(double n);

  public abstract void increment();

  public abstract boolean isRealNumber();

  public boolean isComplexNumber() {
    return !isRealNumber();
  }
}
