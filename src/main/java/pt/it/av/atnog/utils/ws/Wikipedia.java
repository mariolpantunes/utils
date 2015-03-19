package pt.it.av.atnog.utils.ws;

import com.eclipsesource.json.JsonObject;
import pt.it.av.atnog.utils.HTTP;

import java.util.List;

public class Wikipedia implements SearchEngine {
    @Override
    public List<String> search(final String q) {
        List<String> rv = null;
        try {
            JsonObject json = HTTP.getJSON("http://en.wikipedia.org/w/api.php?format=json&action=query&titles=" + q);
            System.out.println(json);
            /*if (start * LENGTH >= json.get("count").asInt())
                done = true;
            else
                start++;
            JsonArray results = json.get("results").asArray();
            for (JsonValue jv : results) {
                System.out.println(jv.asObject().get("url").asString());
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
        return null;
    }
}
