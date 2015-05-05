package pt.it.av.atnog.utils.ws;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pt.it.av.atnog.utils.HTTP;
import pt.it.av.atnog.utils.json.JSONArray;
import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.json.JSONValue;

import java.util.ArrayList;
import java.util.List;

public class Faroo implements SearchEngine {
    private static final int LENGTH = 10;
    private static final long SLEEP = 100;
    private final String key;

    public Faroo(final String key) {
        this.key = key;
    }

    @Override
    public List<String> search(final String q) {
        List<String> rv = new ArrayList<>();
        boolean done = false;
        int start = 1;
        while (!done) {
            try {
                JSONObject json = HTTP.getJSON(url(q, start));
                if (start * LENGTH >= json.get("count").asNumber().value())
                    done = true;
                else
                    start++;
                JSONArray results = json.get("results").asArray();
                for (JSONValue jv : results) {
                    try {
                        Document doc = Jsoup.parse(HTTP.get(jv.asObject().get("url").asString().value()));
                        rv.add(doc.body().text());
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
                done = true;
            }
        }
        return rv;
    }

    @Override
    public List<String> snippets(final String q) {
        List<String> rv = new ArrayList<>();
        boolean done = false;
        int start = 1;
        while (!done) {
            try {
                JSONObject json = HTTP.getJSON(url(q, start));
                JSONArray results = json.get("results").asArray();
                for (JSONValue jv : results) {
                    rv.add(jv.asObject().get("kwic").asString().value());
                }
                if (start * LENGTH >= json.get("count").asNumber().value())
                    done = true;
                else
                    start++;
                Thread.sleep(SLEEP);
            } catch (Exception e) {
                //e.printStackTrace();
                done = true;
            }
        }
        return rv;
    }

    private String url(String q, int start) {
        return url(q, start, Src.WEB, true, false);
    }

    private String url(String q) {
        return url(q, 1, Src.WEB, true, false);
    }

    private String url(String q, int start,
                       Src src, boolean kwic, boolean i) {
        java.lang.StringBuilder sb = new StringBuilder("http://www.faroo.com/api?");
        sb.append("q=" + q + "&");
        sb.append("start=" + start + "&");
        sb.append("src=" + enum2Src(src) + "&");
        sb.append("kwic=" + kwic + "&");
        sb.append("i=" + i + "&");
        sb.append("length=10&rlength=20&l=en&f=json&key=" + key);
        return sb.toString();
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

    public enum Src {
        WEB, NEWS, TOPICS, TRENDS, SUGGEST
    }
}
