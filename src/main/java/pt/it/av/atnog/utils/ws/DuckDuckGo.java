package pt.it.av.atnog.utils.ws;

import pt.it.av.atnog.utils.HTTP;
import pt.it.av.atnog.utils.json.JSONArray;
import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.json.JSONValue;
import pt.it.av.atnog.utils.ws.search.SearchEngine;

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
            JSONObject json = HTTP.getJSON("http://api.duckduckgo.com/?format=json&q=" + qURL);
            JSONArray results = json.get("RelatedTopics").asArray();
            String text = json.get("AbstractText").asString().value();
            if (text != null)
                rv.add(text);
            for (JSONValue jv : results) {
                try {
                    rv.add(jv.asObject().get("Text").asString().value());
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
