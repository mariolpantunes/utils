package pt.it.av.atnog.utils.ws;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import pt.it.av.atnog.utils.HTTP;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mantunes on 3/20/15.
 */
public class DuckDuckGo implements SearchEngine {

    @Override
    public List<String> search(final String q) {
        String qURL = URLEncoder.encode(q);
        List<String> rv = new ArrayList<>();
        try {
            System.out.println("http://api.duckduckgo.com/?format=json&q=" + qURL);
            JsonObject json = HTTP.getJSON("http://api.duckduckgo.com/?format=json&q=" + qURL);
            System.out.println(json);
            /*JsonArray results = json.get("results").asArray();
            for (JsonValue jv : results) {
                //System.out.println(jv.asObject().get("url").asString());
                try {
                    Document doc = Jsoup.parse(HTTP.get(jv.asObject().get("url").asString()));
                    rv.add(doc.body().text());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public List<String> snippets(final String q) {
        String qURL = URLEncoder.encode(q);
        List<String> rv = new ArrayList<>();
        try {
            JsonObject json = HTTP.getJSON("http://api.duckduckgo.com/?format=json&q=" + qURL);
            System.out.println(json);
            JsonArray results = json.get("results").asArray();
            for (JsonValue jv : results) {
                rv.add(jv.asObject().get("content").asString());
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return rv;
    }
}
