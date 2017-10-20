package pt.it.av.atnog.utils.structures.mutableNumber;

/**
 * This class extends {@link MutableNumber}, but does not define methods such as equals,
 * hashCode and compareTo because instances are expected to be mutated,
 * and so are not useful as collection keys.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class MutableDouble extends MutableNumber<MutableDouble> implements Comparable<MutableDouble> {
  protected double value;

  /**
   * @param value
   */
  public MutableDouble(final double value) {
    this.value = value;
  }

  /**
   *
   */
  public MutableDouble() {
    this(0.0);
  }

  @Override
  public MutableDouble add(MutableDouble n) {
    return new MutableDouble(value + n.value);
  }

  @Override
  public MutableDouble sub(MutableDouble n) {
    return new MutableDouble(value - n.value);
  }

  @Override
  public MutableDouble mul(MutableDouble n) {
    return new MutableDouble(value * n.value);
  }

  @Override
  public MutableDouble div(MutableDouble n) {
    return new MutableDouble(value / n.value);
  }

  @Override
  public MutableDouble pow(double n) {
    return new MutableDouble(Math.pow(value, n));
  }

  /**
   *
   */
  public void increment() {
    value += 1.0;
  }

  /**
   *
   */
  public void decrement() {
    value -= 1.0;
  }

  /**
   * @param value
   */
  public void set(double value) {
    this.value = value;
  }

  @Override
  public int compareTo(MutableDouble o) {
    return Double.compare(value, o.value);
  }

  @Override
  public int intValue() {
    return (int) value;
  }

  @Override
  public long longValue() {
    return (long) value;
  }

  @Override
  public float floatValue() {
    return (float) value;
  }

  @Override
  public double doubleValue() {
    return value;
  }

  @Override
  public boolean isRealNumber() {
    return true;
  }
}
