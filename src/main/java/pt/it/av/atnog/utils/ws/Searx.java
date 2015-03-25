package pt.it.av.atnog.utils.ws;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pt.it.av.atnog.utils.HTTP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mantunes on 3/21/15.
 */
public class Searx implements SearchEngine {
    private static final int MAX_PAGE = 5;

    @Override
    public List<String> search(final String q) {
        List<String> rv = new ArrayList<>();
        boolean done = false;
        int page = 1;
        while (!done) {
            try {
                JsonObject json = HTTP.getJSON("https://searx.me/?format=json&pageno=" + page + "&q=" + q);
                if (page >= MAX_PAGE)
                    done = true;
                else
                    page++;
                JsonArray results = json.get("results").asArray();
                for (JsonValue jv : results) {
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
        List<String> rv = new ArrayList<>();
        boolean done = false;
        int page = 1;
        while (!done) {
            try {
                JsonObject json = HTTP.getJSON("https://searx.me/?format=json&pageno=" + page + "&q=" + q);
                if (page >= MAX_PAGE)
                    done = true;
                else
                    page++;
                JsonArray results = json.get("results").asArray();
                for (JsonValue jv : results) {
                    rv.add(jv.asObject().get("content").asString());
                }
            } catch (Exception e) {
                //e.printStackTrace();
                done = true;
            }
        }
        return rv;
    }
}
