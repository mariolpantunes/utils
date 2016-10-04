package pt.it.av.atnog.utils.structures.mutableNumber;

/**
 * This class extends {@link Number}, but does not define methods such as equals,
 * hashCode and compareTo because instances are expected to be mutated,
 * and so are not useful as collection keys.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class MutableInteger extends Number implements Comparable<MutableInteger> {
    private int value;

    /**
     * @param value
     */
    public MutableInteger(final int value) {
        this.value = value;
    }

    /**
     *
     */
    public MutableInteger() {
        this(0);
    }

    /**
     *
     */
    public void increment() {
        value++;
    }

    /**
     *
     */
    public void decrement() {
        value--;
    }

    /**
     * @param value
     */
    public void set(int value) {
        this.value = value;
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public int compareTo(MutableInteger n) {
        return Integer.compare(value, n.value);
    }
}
