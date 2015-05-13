package pt.it.av.atnog.utils.ws.thesaurus;

import pt.it.av.atnog.utils.HTTP;
import pt.it.av.atnog.utils.json.JSONArray;
import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.json.JSONValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mantunes on 5/6/15.
 */
public class Altervista implements Thesaurus {
    private final String key;

    public Altervista(final String key) {
        this.key = key;
    }

    @Override
    public List<String> synonyms(String s) {
        List<String> rv = new ArrayList<>();
        try {
            JSONObject json = HTTP.getJSON("http://thesaurus.altervista.org/thesaurus/v1?language=en_US&key=" + key + "&output=json&word=" + s);
            System.err.println(json);
            if (json.contains("noun")) {
                JSONObject noun = json.get("noun").asObject();
                JSONArray syn = noun.get("syn").asArray();
                for (JSONValue j : syn)
                    rv.add(j.asString().value());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public List<String> antonyms(String s) {
        return null;
    }
}
