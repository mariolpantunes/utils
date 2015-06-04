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
        int len0 = a.length() + 1;
        int len1 = b.length() + 1;
        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];
        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) cost[i] = i;
        // dynamically computing the array of distances
        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;
            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;
                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }
            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }
        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }
}
