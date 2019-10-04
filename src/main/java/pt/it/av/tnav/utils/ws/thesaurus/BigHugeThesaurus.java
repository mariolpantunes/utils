package pt.it.av.tnav.utils.ws.thesaurus;

import pt.it.av.tnav.utils.Http;
import pt.it.av.tnav.utils.json.JSONArray;
import pt.it.av.tnav.utils.json.JSONObject;
import pt.it.av.tnav.utils.json.JSONValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mantunes on 5/6/15.
 */
public class BigHugeThesaurus implements Thesaurus {
    private static final String VERSION = "2";
    private final String key;

    public BigHugeThesaurus(final String key) {
        this.key = key;
    }

    @Override
    public List<String> synonyms(String s) {
        List<String> rv = new ArrayList<>();
        try {
            JSONObject json = Http.getJson("http://words.bighugelabs.com/api/" + VERSION + "/" + key + "/" + s + "/json");

            if (json.contains("noun")) {
                JSONObject noun = json.get("noun").asObject();
                if (noun.contains("syn")) {
                    JSONArray syn = noun.get("syn").asArray();
                    for (JSONValue j : syn)
                        rv.add(j.asString());
                }
            }

            /*if (json.contains("adjective")) {
                JSONObject adjective = json.get("adjective").asObject();
                if (adjective.contains("syn")) {
                    JSONArray syn = adjective.get("syn").asArray();
                    for (JSONValue j : syn)
                        rv.add(j.asString().value());
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public List<String> antonyms(String s) {
        List<String> rv = new ArrayList<>();
        try {
            JSONObject json = Http.getJson("http://words.bighugelabs.com/api/" + VERSION + "/" + key + "/" + s + "/json");

            if (json.contains("noun")) {
                JSONObject noun = json.get("noun").asObject();
                if (noun.contains("ant")) {
                    JSONArray syn = noun.get("ant").asArray();
                    for (JSONValue j : syn)
                        rv.add(j.asString());
                }
            }

            if (json.contains("adjective")) {
                JSONObject adjective = json.get("adjective").asObject();
                if (adjective.contains("ant")) {
                    JSONArray syn = adjective.get("ant").asArray();
                    for (JSONValue j : syn)
                        rv.add(j.asString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rv;
    }
}
