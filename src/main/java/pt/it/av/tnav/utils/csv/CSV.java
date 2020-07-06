package pt.it.av.tnav.utils.csv;

import java.io.Reader;
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
    private List<String> header;
    private List<List<String>> records;

    public CSV(final Reader r) {
        this.header = new ArrayList<>();
        this.records = new ArrayList<>();
        read(r);
    }

    ;

    private void read(final Reader r) {
        Deque<STATE> state = new ArrayDeque<>();
        state.push(STATE.BEGIN);
        STATE previous = STATE.BEGIN;
        StringBuilder name = new StringBuilder();
        boolean done = false;
        int i = 0;
        char c = ' ';

        try {
            while ((i = r.read()) != -1) {
                c = (char) i;

            }
        } catch (Exception e) {
            System.err.println("BUFFER: " + c);
            e.printStackTrace();
        }
    }

    private enum STATE {BEGIN, END, HEADER, RECORD, DQUOTE, TEXT, EXCAPED_TEXT, COMMA, CRLF, ERROR}
}
