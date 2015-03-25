package pt.it.av.atnog.utils.ws;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import pt.it.av.atnog.utils.HTTP;

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
                JsonObject json = HTTP.getJSON("https://api.datamarket.azure.com/Bing/SearchWeb/v1/Web?$format=json" +
                        "&$skip=" + skip + "&Query=" + qURL, "", key).get("d").asObject();
                System.out.println(json);
                System.out.println(json.get("__next"));
                if (json.get("__next") != null)
                    skip += LENGTH;
                else
                    done = true;

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
                JsonObject json = HTTP.getJSON("https://api.datamarket.azure.com/Bing/SearchWeb/v1/Web?$format=json" +
                        "&$skip=" + skip + "&Query=" + qURL, "", key).get("d").asObject();
                System.out.println(json);
                System.out.println(json.get("__next"));
                if (json.get("__next") != null)
                    skip += LENGTH;
                else
                    done = true;
                JsonArray results = json.get("results").asArray();
                for (JsonValue jv : results)
                    rv.add(jv.asObject().get("Description").asString());

            } catch (Exception e) {
                done = true;
                e.printStackTrace();
            }
        }
        return rv;
    }
}
