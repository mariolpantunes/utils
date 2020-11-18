package pt.it.av.tnav.utils.csv;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

import pt.it.av.tnav.utils.StringUtils;
import pt.it.av.tnav.utils.structures.CSVify;

/**
 * CSV parser based on RFC4180
 *
 * @see <a href="https://tools.ietf.org/html/rfc4180">RFC4180</a>
 * @author Mário Antunes
 */
public class CSV extends ArrayList<CSVRecord> {
    private static final long serialVersionUID = 1L;
    public static final char CR = 0x000D, LF = 0x000A, COMMA = 0x002C, DQUOTE = 0x0022;
    private boolean hasHeader = false;

    /**
     * Default Constructor
     * 
     * It create an empty CSV structure without a header.
     */
    public CSV() {
        this.hasHeader = false;
    }

    /**
     * CSV constructor
     * 
     * It create an empty CSV structure that may or may not have a header.
     * 
     * @param hasHeader boolean that indicates if the CSV structure should consider
     *                  the first line as a header
     */
    public CSV(final boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    /**
     * CSV constructor
     * 
     * It creates a CSV structure and fills it a {@link List} of {@link CSVRecord}
     * 
     * @param records   a {@link List} of {@link CSVRecord}
     * @param hasHeader boolean that indicates if the CSV structure should consider
     *                  the first line as a header
     */
    public CSV(final List<CSVRecord> records, final boolean hasHeader) {
        super(records);
        this.hasHeader = hasHeader;
    }

    /**
     * Returns true if the CSV structure has a header, false otherwise.
     * 
     * @return true if the CSV structure has a header, false otherwise
     */
    public boolean hasHeader() {
        return hasHeader;
    }

    /**
     * Return a {@link CSVRecord} with the {@link CSVField} from the first line if
     * it has a header, null otherwise.
     * 
     * @return a {@link CSVRecord} with the {@link CSVField} from the first line if
     *         it has a header, null otherwise
     */
    public CSVRecord getHeader() {
        if (hasHeader && this.size() > 0) {
            return this.get(0);
        } else {
            return null;
        }
    }

    /**
     * Inserts a {@link Collection} of objects that implement {@link CSVify}.
     * 
     * @param c a {@link Collection} of objects that implement {@link CSVify}
     */
    public void addLines(Collection<? extends CSVify<?>> c) {
        for (CSVify<?> e : c) {
            this.add(e.csvDump());
        }
    }

    /**
     * Parses the content of a {@link Reader} and creates a {@link CSV} structure.
     * This method assumes that the CSV does not have a header.
     * 
     * @param r {@link Reader} to be parsed
     * @return {@link CSV} structure
     * @throws IOException
     */
    public static CSV read(final Reader r) throws IOException {
        return read(r, false);
    }

    /**
     * Parses the content of a {@link Reader} and creates a {@link CSV} structure.
     * 
     * @param r         {@link Reader} to be parsed
     * @param hasHeader boolean that indicates if the CSV structure should consider
     *                  the first line as a header
     * @return {@link CSV} structure
     * @throws IOException
     */
    public static CSV read(final Reader r, final boolean hasHeader) throws IOException {
        List<CSVRecord> records = new ArrayList<>();
        Deque<STATE> state = new ArrayDeque<>();
        state.push(STATE.END);
        StringBuilder data = new StringBuilder();
        CSVRecord record = null;

        int i = 0;
        char c = ' ';

        try {
            while ((i = r.read()) != -1) {
                c = (char) i;

                switch (state.peek()) {
                    case END: {
                        switch (c) {
                            case CR:
                            case LF:
                            case COMMA:
                                state.push(STATE.ERROR);
                                break;
                            case DQUOTE:
                                state.push(STATE.DQUOTE);
                                record = new CSVRecord();
                                data.setLength(0);
                                break;
                            default:
                                state.push(STATE.TEXTDATA);
                                record = new CSVRecord();
                                data.setLength(0);
                                data.append(c);
                        }
                        break;
                    }
                    case TEXTDATA: {
                        switch (c) {
                            case CR:
                                state.pop();
                                state.push(STATE.CR);
                                record.add(new CSVField(data.toString()));
                                break;

                            case LF:
                                // state.push(STATE.ERROR);
                                state.pop();
                                state.push(STATE.LF);
                                record.add(new CSVField(data.toString()));
                                records.add(record);
                                break;

                            case COMMA:
                                state.pop();
                                state.push(STATE.COMMA);
                                record.add(new CSVField(data.toString()));
                                data.setLength(0);
                                break;

                            default:
                                data.append(c);
                                break;
                        }
                        break;
                    }
                    case CR: {
                        switch (c) {
                            case LF:
                                state.pop();
                                state.push(STATE.LF);
                                records.add(record);
                        }
                        break;
                    }

                    case LF: {
                        switch (c) {
                            case CR:
                            case LF:
                            case COMMA:
                                state.push(STATE.ERROR);
                                break;
                            case DQUOTE:
                                state.pop();
                                state.push(STATE.DQUOTE);
                                record = new CSVRecord();
                                data.setLength(0);
                                break;
                            default:
                                state.pop();
                                state.push(STATE.TEXTDATA);
                                record = new CSVRecord();
                                data.setLength(0);
                                data.append(c);
                        }
                        break;
                    }

                    case COMMA: {
                        switch (c) {
                            case CR:
                            case LF:
                            case COMMA:
                                state.push(STATE.ERROR);
                                break;
                            case DQUOTE:
                                state.pop();
                                state.push(STATE.DQUOTE);
                                data.setLength(0);
                                break;
                            default:
                                state.pop();
                                state.push(STATE.TEXTDATA);
                                data.append(c);
                        }
                        break;
                    }

                    case DQUOTE: {
                        switch (c) {
                            case DQUOTE:
                                state.pop();
                                state.push(STATE.TWODQUOTE);
                                break;

                            default:
                                data.append(c);
                                break;
                        }
                        break;
                    }

                    case TWODQUOTE: {
                        switch (c) {
                            case DQUOTE:
                                state.pop();
                                state.push(STATE.DQUOTE);
                                data.append(c);
                                break;

                            case CR:
                                state.pop();
                                state.push(STATE.CR);
                                record.add(new CSVField(data.toString()));
                                data.setLength(0);
                                break;

                            case LF:
                                // state.push(STATE.ERROR);
                                state.pop();
                                state.push(STATE.LF);
                                record.add(new CSVField(data.toString()));
                                records.add(record);
                                break;

                            case COMMA:
                                state.pop();
                                state.push(STATE.COMMA);
                                record.add(new CSVField(data.toString()));
                                data.setLength(0);
                                break;

                            default:
                                data.append(c);
                                break;
                        }
                        break;
                    }

                    default:
                        state.push(STATE.ERROR);
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("BUFFER: " + c);
            e.printStackTrace();
        }

        if (state.peek() == STATE.TEXTDATA || state.peek() == STATE.TWODQUOTE) {
            state.pop();
            record.add(new CSVField(data.toString()));
            records.add(record);
        } else if (state.peek() == STATE.LF) {
            state.pop();
        }

        if (state.peek() != STATE.END) {
            while (!state.isEmpty()) {
                state.pop();
            }
            throw new IOException();
        }

        return new CSV(records, hasHeader);
    }

    public void write(Writer w) throws IOException {
        int size = this.size();

        for (int i = 0; i < size - 1; i++) {
            CSVRecord r = this.get(i);
            r.write(w);
            w.append(CR);
            w.append(LF);
        }

        if (size > 0) {
            CSVRecord r = this.get(size - 1);
            r.write(w);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = this.size();

        for (int i = 0; i < size - 1; i++) {
            sb.append(this.get(i).toString());
            sb.append(CR);
            sb.append(LF);
        }

        if (size > 0) {
            sb.append(this.get(size - 1).toString());
        }

        return sb.toString();
    }

    /**
     * 
     * @param textData
     * @return
     */
    public static String escape(final String textData) {
        int pidx = -1, idx = textData.indexOf(DQUOTE);
        if (idx >= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(DQUOTE);
            while (idx > 0) {
                sb.append(textData.substring(pidx + 1, idx));
                sb.append(DQUOTE);
                sb.append(DQUOTE);
                pidx = idx;
                idx = textData.indexOf(DQUOTE, pidx + 1);
            }
            sb.append(textData.substring(pidx + 1));
            sb.append(DQUOTE);
            return sb.toString();
        } else {
            if (StringUtils.containsAny(textData, LF, CR, COMMA)) {
                return "\"" + textData + "\"";
            } else {
                return textData;
            }
        }
    }

    private enum STATE {
        END, RECORD, DQUOTE, TEXTDATA, TWODQUOTE, COMMA, CR, LF, ERROR
    }
}