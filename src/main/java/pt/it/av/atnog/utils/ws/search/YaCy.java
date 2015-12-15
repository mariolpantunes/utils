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
public class YaCy extends SearchEngine {
    private static final int MAX_RESULTS = 30;
    private final int maxResults;

    public YaCy() {
        this(MAX_RESULTS);
    }

    public YaCy(final int maxResults) {
        this.maxResults = maxResults;
    }

    private static void nextIterator(final String q, final IteratorParameters p, final int maxResults) {
        try {
            JSONObject json = HTTP.getJSON("http://search.yacy.de/yacysearch.json?resource=global&contentdom=text" +
                    "&lr=lang_en&startRecord=" + p.skip + "&query=" + q).get("channels").asArray().get(0).asObject();
            JSONArray array = json.get("items").asArray();
            p.skip += array.size();
            if (p.skip >= maxResults)
                p.lastPage = true;
            p.it = array.iterator();
            if (!p.it.hasNext())
                p.done = true;
        } catch (Exception e) {
            e.printStackTrace();
            p.done = true;
        }
    }

    @Override
    public List<String> search(String q) {
        List<String> rv = new ArrayList<>();
        boolean done = false;
        int start = 1;
        while (!done) {
            try {
                JSONObject json = HTTP.getJSON("http://search.yacy.de/yacysearch.json?resource=global&contentdom=text" +
                        "&lr=lang_en&startRecord=" + start + "&query=" + q).get("channels").asArray().get(0).asObject();

                JSONArray results = json.get("items").asArray();
                for (JSONValue jv : results) {
                    try {
                        Document doc = Jsoup.parse(HTTP.get(jv.asObject().get("link").asString()));
                        rv.add(doc.body().text());
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
                done = true;
            }
        }
        return rv;
    }

    @Override
    public Iterator<String> searchIt(String q) {
        return null;
    }

    @Override
    public Iterator<String> snippetsIt(final String q) {
        return new YaCySnippetIterator(q, maxResults);
    }

    private class YaCySnippetIterator implements Iterator<String> {
        private IteratorParameters p = new IteratorParameters(1);
        private String q;
        private int maxResults;

        public YaCySnippetIterator(final String q, final int maxResults) {
            try {
                this.q = URLEncoder.encode(q, java.nio.charset.StandardCharsets.UTF_8.toString());
                this.maxResults = maxResults;
                nextIterator(this.q, p, maxResults);
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
                rv = json.get("description").asString();
                if (!p.it.hasNext() && !p.lastPage)
                    nextIterator(q, p, maxResults);
                else if (!p.it.hasNext() && p.lastPage)
                    p.done = true;
            }
            return rv;
        }
    }

    private class SearxSearchIterator implements Iterator<String> {
        private String q, page;
        private IteratorParameters p = new IteratorParameters(1);
        private int maxResults;

        public SearxSearchIterator(final String q, final int maxResults) {
            try {
                this.q = URLEncoder.encode(q, java.nio.charset.StandardCharsets.UTF_8.toString());
                this.maxResults = maxResults;
                nextIterator(this.q, p, maxResults);
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
                    String url = p.it.next().asObject().get("link").asString();
                    if (!p.it.hasNext() && !p.lastPage)
                        nextIterator(q, p, maxResults);
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
