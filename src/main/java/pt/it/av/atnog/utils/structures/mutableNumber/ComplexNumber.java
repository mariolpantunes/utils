package pt.it.av.atnog.utils.structures.mutableNumber;

/**
 * This class extends {@link MutableNumber}, but does not define methods such as equals,
 * hashCode and compareTo because instances are expected to be mutated,
 * and so are not useful as collection keys.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class ComplexNumber extends MutableNumber<ComplexNumber> {
  protected double r, i;

  /**
   *
   * @param r
   * @param i
   */
  public ComplexNumber(double r, double i) {
    this.r = r;
    this.i = i;
  }

  /**
   *
   */
  public ComplexNumber() {
    this(0.0, 0.0);
  }

  @Override
  public ComplexNumber add(ComplexNumber n) {
    return new ComplexNumber(r + n.r, i + n.i);
  }

  @Override
  public ComplexNumber sub(ComplexNumber n) {
    return new ComplexNumber(r - n.r, i - n.i);
  }

  @Override
  public ComplexNumber mul(ComplexNumber n) {
    return new ComplexNumber(r*n.r - i*n.i, r*n.i + i*n.r);
  }

  @Override
  public ComplexNumber div(ComplexNumber n) {
    double d = Math.pow(n.r, 2.0) + Math.pow(n.i, 2.0);
    return new ComplexNumber((r*n.r + i*n.i)/d, (r*n.i - i*n.r)/d);
  }

  /**
   *
   * @return
   */
  @Override
  public ComplexNumber pow(double n) {
    return new ComplexNumber();
  }

  @Override
  public void increment() {

  }

  /**
   *
   * @return
   */
  public double abs() {
    return Math.sqrt(Math.pow(r, 2.0) + Math.pow(i, 2.0));
  }

  /**
   *
   * @return
   */
  public ComplexNumber conjugate() {
    return new ComplexNumber(r, -i);
  }

  @Override
  public int intValue() {
    return 0;
  }

  @Override
  public long longValue() {
    return 0;
  }

  @Override
  public float floatValue() {
    return 0;
  }

  @Override
  public double doubleValue() {
    return 0;
  }

  @Override
  public boolean isRealNumber() {
    return false;
  }

  @Override
  public String toString() {
    return r+"+"+i+"i";
  }
}
