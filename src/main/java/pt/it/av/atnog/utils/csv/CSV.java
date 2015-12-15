package pt.it.av.atnog.utils.csv;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mantunes on 11/24/15.
 */
public class CSV {
    private List<List<String>> data;


    public static Iterator<String> decode(final Reader reader, final char delimiter) {
        return new CSVIterator(reader, delimiter);
    }

    private enum STATE {BEGIN, ROOT, OBJECT, PRE_KEY, KEY, COLON, COMMA, PRE_VALUE, VALUE, STRING, ARRAY, END, ERROR}

    private static class CSVIterator implements Iterator<String> {
        private final Reader reader;
        private final char delimiter;
        private STATE state = STATE.BEGIN;

        public CSVIterator(final Reader reader, final char delimiter) {
            this.reader = reader;
            this.delimiter = delimiter;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public String next() {
            return null;
        }
    }
}
