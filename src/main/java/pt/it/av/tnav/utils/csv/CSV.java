package pt.it.av.tnav.utils.csv;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * CSV parser based on RFC 4180
 *
 * @author Mário Antunes
 */
public class CSV {
    public static final char CR = 0x000D, LF = 0x000A, COMMA = 0x002C, DQUOTE = 0x0022;

    private boolean hasHeader = false;
    private List<CSVRecord> records;

    public CSV(final List<CSVRecord> records, final boolean hasHeader) {
        this.records = records;
        this.hasHeader = hasHeader;
    }

    /**
     * 
     * @param r
     * @return
     * @throws IOException
     */
    public static CSV read(final Reader r) throws IOException {
        return read(r, false);
    }

    /**
     * 
     * @param r
     * @param hasHeader
     * @return
     * @throws IOException
     */
    public static CSV read(final Reader r, final boolean hasHeader) throws IOException {
        List<CSVRecord> records = new ArrayList<>();

        Deque<STATE> state = new ArrayDeque<>();
        state.push(STATE.END);
        STATE previous = STATE.END;
        StringBuilder name = new StringBuilder();
        boolean done = false;
        int i = 0;
        char c = ' ';

        try {
            while ((i = r.read()) != -1) {
                c = (char) i;
                System.err.println("C = " + c);
                switch (state.peek()) {
                    case END:
                        state.push(STATE.RECORD);
                        break;
                }

            }
        } catch (Exception e) {
            System.err.println("BUFFER: " + c);
            e.printStackTrace();
        }

        if (state.peek() != STATE.END) {
            while (!state.isEmpty()) {
                System.err.println("STATE -> " + state.peek().name());
                state.pop();
            }
            throw new IOException();
        }

        return new CSV(records, hasHeader);
    }

    public void write(Writer w) throws IOException {
        int size = records.size();
        for (int i = 0; i < size - 1; i++) {
            CSVRecord r = records.get(i);
            r.write(w);
            w.append(CR);
            w.append(LF);
        }

        if (size > 0) {
            CSVRecord r = records.get(size - 1);
            r.write(w);
        }
    }

    @Override
    public String toString() {
        StringWriter w = new StringWriter();
        try {
            write(w);
        } catch (IOException e) {
            // should not occur...
        }
        return w.toString();
    }

    private enum STATE {
        BEGIN, END, RECORD, DQUOTE, TEXTDATA, TWODQUOTE, COMMA, CR, LF, ERROR
    }

    public class CSVField {

    }

    public class CSVRecord {
        private List<CSVField> fields;

        public void write(Writer w) throws IOException {
        }
    }
}
