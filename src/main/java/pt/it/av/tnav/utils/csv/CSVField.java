package pt.it.av.tnav.utils.csv;

import java.io.IOException;
import java.io.Writer;

public class CSVField implements CharSequence {
    private final String textData;

    public CSVField(final double number) {
        this.textData = Double.toString(number);
    }

    public CSVField(final String textData) {
        this.textData = textData;
    }

    @Override
    public int length() {
        return textData.length();
    }

    @Override
    public char charAt(int index) {
        return textData.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return textData.subSequence(start, end);
    }

    @Override
    public String toString() {
        return textData;
    }

    /**
     * 
     * @param w
     * @throws IOException
     */
    public void write(Writer w) throws IOException {
        w.append(CSV.escape(textData));
    }
}
