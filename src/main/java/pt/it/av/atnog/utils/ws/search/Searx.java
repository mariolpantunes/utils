package pt.it.av.atnog.utils.ws.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pt.it.av.atnog.utils.HTTP;
import pt.it.av.atnog.utils.json.JSONArray;
import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.json.JSONValue;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mantunes on 3/21/15.
 */
public class Searx implements SearchEngine {
    private final int maxPages;

    public Searx(int maxPages) {
        this.maxPages = maxPages;
    }

    @Override
    public List<String> search(final String q) {
        String qURL = URLEncoder.encode(q);
        List<String> rv = new ArrayList<>();
        boolean done = false;
        int page = 1;
        JSONObject previous = null;
        while (!done) {
            try {
                JSONObject json = HTTP.getJSON("https://searx.me/?format=json&category_general&pageno="
                        + page + "&q=" + qURL);
                if (json.equals(previous) || page >= maxPages)
                    done = true;
                page++;
                previous = json;
                JSONArray results = json.get("results").asArray();
                for (JSONValue jv : results) {
                    //System.out.println(jv.asObject().get("url").asString());
                    try {
                        Document doc = Jsoup.parse(HTTP.get(jv.asObject().get("url").asString()));
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
        String qURL = URLEncoder.encode(q);
        List<String> rv = new ArrayList<>();
        boolean done = false;
        int page = 1;
        JSONObject previous = null;
        while (!done) {
            try {
                JSONObject json = HTTP.getJSON("https://searx.me/?format=json&category_general&pageno="
                        + page + "&q=" + qURL);
                if (json.equals(previous) || page >= maxPages)
                    done = true;
                page++;
                previous = json;
                JSONArray results = json.get("results").asArray();
                for (JSONValue jv : results)
                    rv.add(jv.asObject().get("content").asString());
            } catch (Exception e) {
                //e.printStackTrace();
                done = true;
            }
        }
        return rv;
    }
}
