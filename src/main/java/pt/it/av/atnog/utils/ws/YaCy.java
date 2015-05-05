package pt.it.av.atnog.utils.ws;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pt.it.av.atnog.utils.HTTP;
import pt.it.av.atnog.utils.json.JSONArray;
import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.json.JSONValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mantunes on 3/23/15.
 */
public class YaCy implements SearchEngine {
    private static final int MAX_RECORDS = 30, LENGTH = 10;

    @Override
    public List<String> search(String q) {
        List<String> rv = new ArrayList<>();
        boolean done = false;
        int start = 1;
        while (!done) {
            try {
                JSONObject json = HTTP.getJSON("http://search.yacy.de/yacysearch.json?resource=global&contentdom=text" +
                        "&lr=lang_en&startRecord=" + start + "&query=" + q).get("channels").asArray().get(0).asObject();
                start += LENGTH;
                if (start >= MAX_RECORDS)
                    done = true;
                JSONArray results = json.get("items").asArray();
                for (JSONValue jv : results) {
                    try {
                        Document doc = Jsoup.parse(HTTP.get(jv.asObject().get("link").asString().value()));
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
    public List<String> snippets(String q) {
        List<String> rv = new ArrayList<>();
        boolean done = false;
        int start = 1;
        while (!done) {
            try {
                JSONObject json = HTTP.getJSON("http://search.yacy.de/yacysearch.json?resource=global&contentdom=text" +
                        "&lr=lang_en&startRecord=" + start + "&query=" + q).get("channels").asArray().get(0).asObject();
                start += LENGTH;
                if (start >= MAX_RECORDS)
                    done = true;
                JSONArray results = json.get("items").asArray();
                for (JSONValue jv : results) {
                    rv.add(jv.asObject().get("description").asString().value());
                }
            } catch (Exception e) {
                e.printStackTrace();
                done = true;
            }
        }
        return rv;
    }
}
