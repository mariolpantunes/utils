package pt.it.av.atnog.utils.ws.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pt.it.av.atnog.utils.HTTP;
import pt.it.av.atnog.utils.json.JSONArray;
import pt.it.av.atnog.utils.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class Faroo extends SearchEngine {
    //private static final long SLEEP = 1000;
    private final String key;

    public Faroo(final String key) {
        this.key = key;
    }

    private static void nextIterator(final String q, final String key, IteratorParameters p) {
        try {
            JSONObject json = HTTP.getJSON("http://www.faroo.com/api?key=" + key + "&start=" + p.skip + "&q=" + q);
            JSONArray array = json.get("results").asArray();
            p.skip += array.size();
            if (p.skip >= json.get("count").asNumber())
                p.lastPage = true;
            p.it = array.iterator();
            if (!p.it.hasNext())
                p.done = true;
        } catch (Exception e) {
            p.done = true;
        }
    }

    @Override
    public Iterator<String> searchIt(String q) {
        return new FarooSearchIterator(q);
    }

    @Override
    public Iterator<String> snippetsIt(final String q) {
        return new FarooSnippetIterator(q);
    }

    private class FarooSnippetIterator implements Iterator<String> {
        private IteratorParameters p = new IteratorParameters(1);
        private String q;

        public FarooSnippetIterator(final String q) {
            try {
                this.q = URLEncoder.encode(q, java.nio.charset.StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                p.done = true;
            }
            nextIterator(this.q, key, p);
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
                rv = json.get("kwic").asString();
                if (!p.it.hasNext() && !p.lastPage)
                    nextIterator(q, key, p);
                else if (!p.it.hasNext() && p.lastPage)
                    p.done = true;
            }
            return rv;
        }
    }

    //TODO: Test to see if needs \" \" arround the query
    private class FarooSearchIterator implements Iterator<String> {
        private String q, page;
        private IteratorParameters p = new IteratorParameters(1);

        public FarooSearchIterator(final String q) {
            try {
                this.q = URLEncoder.encode(q, java.nio.charset.StandardCharsets.UTF_8.toString());
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
            while (!newPage && !p.done) {
                newPage = true;
                try {
                    String url = p.it.next().asObject().get("url").asString();
                    if (!p.it.hasNext() && !p.lastPage)
                        nextIterator(q, key, p);
                    else if (!p.it.hasNext() && p.lastPage)
                        p.done = true;
                    Document doc = Jsoup.parse(HTTP.get(url));
                    page = doc.body().text();
                } catch (Exception e) {
                    //e.printStackTrace();
                    newPage = false;
                }
            }
        }
    }
}
