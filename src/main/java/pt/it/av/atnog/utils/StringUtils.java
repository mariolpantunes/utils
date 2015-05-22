package pt.it.av.atnog.utils;

/**
 * Created by mantunes on 5/4/15.
 */
public class StringUtils {
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
        a = a.toLowerCase();
        b = b.toLowerCase();
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
}
