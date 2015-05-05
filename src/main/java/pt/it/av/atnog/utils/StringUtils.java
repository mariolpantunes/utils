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
                case '/':
                    sb.append("\\/");
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
                    case '/':
                        sb.append("/");
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
}
