package pt.it.av.tnav.utils.csv;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class CSVRecord extends ArrayList<CSVField> {
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param w
     * @throws IOException
     */
    public void write(Writer w) throws IOException {
        int size = this.size();
        for (int i = 0; i < size - 1; i++) {
            get(i).write(w);
            w.append(CSV.COMMA);
        }

        if (size > 0) {
            get(size - 1).write(w);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int size = this.size();
        for (int i = 0; i < size - 1; i++) {
            sb.append(get(i).toString());
            sb.append(CSV.COMMA);
        }

        if (size > 0) {
            sb.append(get(size - 1));
        }

        return sb.toString();
    }
}
