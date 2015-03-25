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
        return snippets(q);
    }

    @Override
    public List<String> snippets(final String q) {
        String qURL = URLEncoder.encode(q);
        List<String> rv = new ArrayList<>();
        try {
            JsonObject json = HTTP.getJSON("http://api.duckduckgo.com/?format=json&q=" + qURL);
            JsonArray results = json.get("RelatedTopics").asArray();
            String text = json.get("AbstractText").asString();
            if (text != null)
                rv.add(text);
            for (JsonValue jv : results) {
                try {
                    rv.add(jv.asObject().get("Text").asString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rv;
    }
}
