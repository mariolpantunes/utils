package pt.it.av.atnog.utils.structures;

/**
 * @author Mário Antunes
 * @version 1.0
 */
public class MutableInteger extends Number implements Comparable<MutableInteger> {
    private int value;

    /**
     *
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
