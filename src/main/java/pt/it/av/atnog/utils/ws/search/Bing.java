package pt.it.av.atnog.utils.ws.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pt.it.av.atnog.utils.HTTP;
import pt.it.av.atnog.utils.json.JSONArray;
import pt.it.av.atnog.utils.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by mantunes on 3/23/15.
 */
public class Bing extends SearchEngine {
    private final String key;

    public Bing(final String key) {
        this.key = key;
    }

    private static void nextIterator(final String q, final String key, IteratorParameters p) {
        try {
            JSONObject json = HTTP.getJSON("https://api.datamarket.azure.com/Bing/SearchWeb/v1/Web?$format=json" +
                    "&$skip=" + p.skip + "&Query=" + q, "", key).get("d").asObject();
            if (json.get("__next") == null)
                p.lastPage = true;
            JSONArray array = json.get("results").asArray();
            p.skip += array.size();
            p.it = array.iterator();
            if (!p.it.hasNext())
                p.done = true;
        } catch (Exception e) {
            p.done = true;
            e.printStackTrace();
        }
    }

    @Override
    public Iterator<String> searchIt(final String q) {
        return new BingSearchIterator(q);
    }

    @Override
    public Iterator<String> snippetsIt(final String q) {
        return new BingSnippetIterator(q);
    }

    private class BingSnippetIterator implements Iterator<String> {
        private IteratorParameters p = new IteratorParameters(0);
        private String q;

        public BingSnippetIterator(final String q) {
            try {
                this.q = URLEncoder.encode("'" + q + "'", java.nio.charset.StandardCharsets.UTF_8.toString());
                nextIterator(this.q, key, p);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                p.done = true;
            }
        }

        @Override
        public boolean hasNext() {
            return !p.done;
        }

        @Override
        public String next() {
            String rv = null;
            if (!p.done) {
                JSONObject json = p.it.next().asObject();
                rv = json.get("Description").asString();
                if (!p.it.hasNext() && !p.lastPage)
                    nextIterator(q, key, p);
                else if (!p.it.hasNext() && p.lastPage)
                    p.done = true;
            }
            return rv;
        }
    }

    private class BingSearchIterator implements Iterator<String> {
        private String q, page;
        private IteratorParameters p = new IteratorParameters(0);

        public BingSearchIterator(final String q) {
            try {
                this.q = URLEncoder.encode("'" + q + "'", java.nio.charset.StandardCharsets.UTF_8.toString());
                nextIterator(this.q, key, p);
                nextPage();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                p.done = true;
            }
        }

        @Override
        public boolean hasNext() {
            return !p.done;
        }

        @Override
        public String next() {
            String rv = null;
            if (!p.done) {
                rv = page;
                nextPage();
            }
            return page;
        }

        private void nextPage() {
            boolean newPage = false;
            while (!newPage) {
                newPage = true;
                try {
                    String url = p.it.next().asObject().get("Url").asString();
                    if (!p.it.hasNext() && !p.lastPage)
                        nextIterator(q, key, p);
                    else if (!p.it.hasNext() && p.lastPage)
                        p.done = true;
                    Document doc = Jsoup.parse(HTTP.get(url));
                    page = doc.body().text();
                } catch (Exception e) {
                    newPage = false;
                }
            }
        }
    }
}
