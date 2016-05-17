package pt.it.av.atnog.utils.structures;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class MutableDouble extends Number implements Comparable<MutableDouble> {
    private double value;

    /**
     *
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

    /**
     *
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
        return (int)value;
    }

    @Override
    public long longValue() {
        return (long)value;
    }

    @Override
    public float floatValue() {
        return (float)value;
    }

    @Override
    public double doubleValue() {
        return value;
    }
}
