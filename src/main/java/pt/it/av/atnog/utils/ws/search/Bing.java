package pt.it.av.atnog.utils.ws.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pt.it.av.atnog.utils.HTTP;
import pt.it.av.atnog.utils.json.JSONArray;
import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.json.JSONValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
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
        String qURL = null;
        boolean done = false;
        List<String> rv = new ArrayList<>();
        int skip = 0;

        try {
            qURL = URLEncoder.encode("'" + q + "'", java.nio.charset.StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            done = true;
        }


        while (!done) {
            try {
                JSONObject json = HTTP.getJSON("https://api.datamarket.azure.com/Bing/SearchWeb/v1/Web?$format=json" +
                        "&$skip=" + skip + "&Query=" + qURL, "", key).get("d").asObject();
                if (json.get("__next") != null)
                    skip += LENGTH;
                else
                    done = true;
                JSONArray results = json.get("results").asArray();
                for (JSONValue jv : results) {
                    try {
                        Document doc = Jsoup.parse(HTTP.get(jv.asObject().get("Url").asString()));
                        rv.add(doc.body().text());
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return rv;
    }

    @Override
    public List<String> snippets(String q) {
        String qURL = null;
        boolean done = false;
        List<String> rv = new ArrayList<>();
        int skip = 0;

        try {
            qURL = URLEncoder.encode("'" + q + "'", java.nio.charset.StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            done = true;
        }

        while (!done) {
            try {
                JSONObject json = HTTP.getJSON("https://api.datamarket.azure.com/Bing/SearchWeb/v1/Web?$format=json" +
                        "&$skip=" + skip + "&Query=" + qURL, "", key).get("d").asObject();
                if (json.get("__next") != null)
                    skip += LENGTH;
                else
                    done = true;
                JSONArray results = json.get("results").asArray();
                for (JSONValue jv : results)
                    rv.add(jv.asObject().get("Description").asString());
            } catch (Exception e) {
                done = true;
                e.printStackTrace();
            }
        }
        return rv;
    }

    @Override
    public Iterator<String> searchIt(final String q) {
        return null;
    }

    @Override
    public Iterator<String> snippetsIt(final String q) {
        return new BingSnippetIterator(q);
    }

    private class BingSnippetIterator implements Iterator<String> {
        private String q;
        private boolean lastPage = false, done = false;
        private int skip = 0;
        private Iterator<JSONValue> it = null;

        public BingSnippetIterator(final String q) {
            try {
                this.q = URLEncoder.encode("'" + q + "'", java.nio.charset.StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                done = true;
            }

            if (!done)
                nextIterator();
        }

        @Override
        public boolean hasNext() {
            return !done;
        }

        @Override
        public String next() {
            JSONObject json = it.next().asObject();
            String rv = json.get("Description").asString();

            if (!it.hasNext() && !lastPage)
                nextIterator();

            if (!it.hasNext() && lastPage)
                done = true;

            return rv;
        }

        private void nextIterator() {
            try {
                JSONObject json = HTTP.getJSON("https://api.datamarket.azure.com/Bing/SearchWeb/v1/Web?$format=json" +
                        "&$skip=" + skip + "&Query=" + q, "", key).get("d").asObject();
                if (json.get("__next") != null)
                    skip += LENGTH;
                else
                    lastPage = true;
                it = json.get("results").asArray().iterator();
            } catch (Exception e) {
                done = true;
                e.printStackTrace();
            }
        }
    }
}
