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
 * Created by mantunes on 3/23/15.
 */
public class Bing implements SearchEngine {
    private static final int LENGTH = 50;
    private final String key;

    public Bing(final String key) {
        this.key = key;
    }

    @Override
    public List<String> search(String q) {
        String qURL = URLEncoder.encode("'" + q + "'");
        List<String> rv = new ArrayList<>();
        int skip = 0;
        boolean done = false;
        while (!done) {
            try {
                JSONObject json = HTTP.getJSON("https://api.datamarket.azure.com/Bing/SearchWeb/v1/Web?$format=json" +
                        "&$skip=" + skip + "&Query=" + qURL, "", key).get("d").asObject();
                if (json.get("__next") != null)
                    skip += LENGTH;
                else
                    done = true;
                JSONArray results = json.get("results").asArray();
                for (JSONValue jv : results) {
                    try {
                        Document doc = Jsoup.parse(HTTP.get(jv.asObject().get("Url").asString()));
                        rv.add(doc.body().text());
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rv;
    }

    @Override
    public List<String> snippets(String q) {
        String qURL = URLEncoder.encode("'" + q + "'");
        List<String> rv = new ArrayList<>();
        int skip = 0;
        boolean done = false;
        while (!done) {
            try {
                JSONObject json = HTTP.getJSON("https://api.datamarket.azure.com/Bing/SearchWeb/v1/Web?$format=json" +
                        "&$skip=" + skip + "&Query=" + qURL, "", key).get("d").asObject();
                System.err.println(json);
                if (json.get("__next") != null)
                    skip += LENGTH;
                else
                    done = true;
                JSONArray results = json.get("results").asArray();
                for (JSONValue jv : results)
                    rv.add(jv.asObject().get("Description").asString());

            } catch (Exception e) {
                done = true;
                e.printStackTrace();
            }
        }
        return rv;
    }
}
