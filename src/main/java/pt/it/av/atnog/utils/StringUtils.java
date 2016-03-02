package pt.it.av.atnog.utils;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by mantunes on 5/4/15.
 */
public class StringUtils {

    public static int count(String s, char c) {
        int rv = 0;
        for (int i = 0, t = s.length(); i < t; i++)
            if (s.charAt(i) == c)
                rv += 1;
        return rv;
    }

    public static List<Integer> indexOf(String s, char c) {
        List<Integer> rv = new ArrayList<>();
        for (int i = 0, t = s.length(); i < t; i++)
            if (s.charAt(i) == c)
                rv.add(i);
        return rv;
    }

    public static String escape(String s) {
        StringBuilder sb = new StringBuilder();
        char array[] = s.toCharArray();
        for (int i = 0, t = array.length; i < t; i++) {
            switch (array[i]) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(array[i]);
            }
        }
        return sb.toString();
    }

    public static String unescape(String s) {
        StringBuilder sb = new StringBuilder();
        char array[] = s.toCharArray();
        boolean escaped = false;
        for (int i = 0, t = array.length; i < t; i++) {
            if (escaped) {
                switch (array[i]) {
                    case '\"':
                        sb.append("\"");
                        break;
                    case '\\':
                        sb.append("\\");
                        break;
                    case '\b':
                        sb.append("b");
                        break;
                    case '\f':
                        sb.append("f");
                        break;
                    case '\n':
                        sb.append("n");
                        break;
                    case '\r':
                        sb.append("r");
                        break;
                    case '\t':
                        sb.append("t");
                        break;
                    default:
                        sb.append(array[i]);
                }
                escaped = false;
            } else if (array[i] == '\\')
                escaped = true;
            else
                sb.append(array[i]);
        }
        return sb.toString();
    }

    public static int levenshtein(String a, String b) {
        //a = a.toLowerCase();
        //b = b.toLowerCase();
        // i == 0
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    public static List<String> clauses(String input) {
        return clauses(input, Locale.getDefault());
    }

    public static List<String> clauses(String input, Locale locale) {
        List<String> rv = new ArrayList<>();
        List<Integer> idx = StringUtils.indexOf(input, ',');

        switch (idx.size()) {
            case 1:
                rv.add(input.substring(0, idx.get(0)));
                rv.add(input.substring(idx.get(0) + 1, input.length()).trim());
                break;
            case 2:
                rv.add(input.substring(0, idx.get(0)) + input.substring(idx.get(1) + 1, input.length()));
                rv.add(input.substring(idx.get(0) + 1, idx.get(1)).trim());
                break;
            default:
                rv.add(input);
                break;
        }

        return rv;
    }

    public static Iterator<String> splitSetences(String input) {
        return splitSetences(input, Locale.getDefault());
    }

    public static Iterator<String> splitSetences(String input, Locale locale) {
        return new SetenceIterator(input, locale);
    }

    private static class SetenceIterator implements Iterator<String> {
        private final String input;
        private final BreakIterator it;
        private int start, end;

        public SetenceIterator(String input, Locale locale) {
            this.input = input;
            it = BreakIterator.getSentenceInstance(locale);
            it.setText(input);
            start = it.first();
            end = it.next();
        }

        @Override
        public boolean hasNext() {
            return end != BreakIterator.DONE;
        }

        @Override
        public String next() {
            String rv = input.substring(start, end);
            start = end;
            end = it.next();
            return rv;
        }
    }
}
