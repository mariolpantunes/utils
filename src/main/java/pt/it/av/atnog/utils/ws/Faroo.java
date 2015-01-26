package pt.it.av.atnog.utils.ws;

import pt.it.av.atnog.utils.HTTP;

public class Faroo {
    private final String key;

    public Faroo(final String key) {
        this.key = key;
    }

    public void search(String q) {
        String url = url(q);
        try {
            System.out.println(HTTP.get(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String url(String q) {
        return url(q, 1, 10, 20, Lang.EN, Src.WEB, true, false, Format.JSON);
    }

    private String url(String q, int start, int lenght, int rlength, Lang l,
                       Src src, boolean kwic, boolean i, Format f) {
        StringBuilder sb = new StringBuilder("http://www.faroo.com/api?");
        sb.append("q=" + q + "&");
        sb.append("start=" + start + "&");
        sb.append("length=" + lenght + "&");
        sb.append("rlength=" + rlength + "&");
        sb.append("l=" + enum2Lang(l) + "&");
        sb.append("src=" + enum2Src(src) + "&");
        sb.append("kwic=" + kwic + "&");
        sb.append("i=" + i + "&");
        sb.append("f=" + enum2Format(f) + "&");
        sb.append("key=" + key);

        return sb.toString();
    }

    private String enum2Lang(Lang l) {
        String rv = null;

        switch (l) {
            case EN:
                rv = "en";
                break;
            case DE:
                rv = "de";
                break;
            case ZH:
                rv = "zh";
                break;
        }

        return rv;
    }

    private String enum2Src(Src src) {
        String rv = null;

        switch (src) {
            case WEB:
                rv = "web";
                break;
            case NEWS:
                rv = "news";
                break;
            case TOPICS:
                rv = "topics";
                break;
            case TRENDS:
                rv = "trends";
                break;
            case SUGGEST:
                rv = "suggest";
                break;
        }

        return rv;
    }

    private String enum2Format(Format f) {
        String rv = null;

        switch (f) {
            case JSON:
                rv = "json";
                break;
            case XML:
                rv = "xml";
                break;
            case RSS:
                rv = "rss";
                break;
        }

        return rv;
    }

    public enum Lang {
        EN, DE, ZH
    }

    ;

    public enum Src {
        WEB, NEWS, TOPICS, TRENDS, SUGGEST
    }

    public enum Format {
        JSON, XML, RSS
    }
}
