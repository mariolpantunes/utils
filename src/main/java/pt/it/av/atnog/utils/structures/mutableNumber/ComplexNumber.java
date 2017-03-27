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
    private double r, i;

    public ComplexNumber(double r, double i) {
        this.r = r;
        this.i = i;
    }

    public ComplexNumber() {
        this(0.0, 0.0);
    }

    @Override
    public ComplexNumber add(ComplexNumber n) {
        return new ComplexNumber(this.r + n.r, this.i + n.i);
    }

    @Override
    public void increment() {

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
}
