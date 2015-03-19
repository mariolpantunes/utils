package pt.it.av.atnog.utils.ws;

import com.eclipsesource.json.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pt.it.av.atnog.utils.HTTP;

import java.util.ArrayList;
import java.util.List;

public class Wikipedia implements SearchEngine {
    @Override
    public List<String> search(final String q) {
        List<String> rv = new ArrayList<>();
        try {
            JsonObject json = HTTP.getJSON("http://en.wikipedia.org/w/api.php?format=json&action=query&rawcontinue=&" +
                    "titles=" + q).get("query").asObject().get("pages").asObject();
            List<String> names = json.names();
            for (String name : names) {
                long pageid = json.get(name).asObject().get("pageid").asLong();
                try {
                    Document doc = Jsoup.parse(HTTP.get("http://en.wikipedia.org/?curid=" + pageid));
                    rv.add(doc.body().text());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rv;
    }

    //TODO: ADD query continue support
    @Override
    public List<String> snippets(final String q) {
        List<String> rv = new ArrayList<>();
        try {
            JsonObject json = HTTP.getJSON("http://en.wikipedia.org/w/api.php?format=json&action=query&rawcontinue=&" +
                    "titles=" + q).get("query").asObject().get("pages").asObject();
            List<String> names = json.names();
            for (String name : names) {
                long pageid = json.get(name).asObject().get("pageid").asLong();
                try {
                    Document doc = Jsoup.parse(HTTP.get("http://en.wikipedia.org/?curid=" + pageid));
                    rv.add(doc.body().text());
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
