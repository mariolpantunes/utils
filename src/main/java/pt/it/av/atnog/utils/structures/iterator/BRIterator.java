package pt.it.av.atnog.utils.structures.iterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * Buffered Reader Iterator.
 * <p>
 * Consumes lines from a {@link java.io.BufferedReader}.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class BRIterator implements Iterator<String>, AutoCloseable {
    private final BufferedReader br;
    private boolean next = true;
    private String line = null;

    public BRIterator(final BufferedReader br) {
        this.br = br;
        try {
            line = br.readLine();
            if (line == null)
                next = false;
        } catch (IOException e) {
            next = false;
        }
    }

    @Override
    public boolean hasNext() {
        return next;
    }

    @Override
    public String next() {
        String rv = line;
        try {
            line = br.readLine();
            if (line == null)
                next = false;
        } catch (IOException e) {
            next = false;
        }
        return rv;
    }

    @Override
    public void close() throws Exception {
        if (br != null)
            br.close();
    }
}
