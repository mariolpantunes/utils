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
 * Created by mantunes on 3/21/15.
 */
public class Searx extends SearchEngine {
    private static final int DEFAULT_MAX_PAGES = 3;
    private final int maxPages;

    public Searx() {
        this(DEFAULT_MAX_PAGES);
    }

    public Searx(int maxPages) {
        this.maxPages = maxPages;
    }

    private static void nextIterator(final String q, final IteratorParameters p, final int maxPages) {
        try {
            JSONObject json = HTTP.getJSON("https://searx.me/?format=json&category_general&pageno=" + p.skip + "&q=" + q);
            JSONArray array = json.get("results").asArray();
            if (p.skip >= maxPages)
                p.lastPage = true;
            p.skip++;
            p.it = array.iterator();
            if (!p.it.hasNext())
                p.done = true;
        } catch (Exception e) {
            p.done = true;
        }
    }

    @Override
    public Iterator<String> searchIt(final String q) {
        return new SearxSearchIterator(q, maxPages);
    }

    @Override
    public Iterator<String> snippetsIt(final String q) {
        return new SearxSnippetIterator(q, maxPages);
    }

    private class SearxSnippetIterator implements Iterator<String> {
        private IteratorParameters p = new IteratorParameters(1);
        private int maxPages;
        private String q;

        public SearxSnippetIterator(final String q, final int maxPages) {
            try {
                this.q = URLEncoder.encode(q, java.nio.charset.StandardCharsets.UTF_8.toString());
                this.maxPages = maxPages;
                nextIterator(this.q, p, this.maxPages);
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
                rv = json.get("content").asString();
                if (!p.it.hasNext() && !p.lastPage)
                    nextIterator(q, p, maxPages);
                else if (!p.it.hasNext() && p.lastPage)
                    p.done = true;
            }
            return rv;
        }
    }

    private class SearxSearchIterator implements Iterator<String> {
        private String q, page;
        private IteratorParameters p = new IteratorParameters(1);
        private int maxPages;

        public SearxSearchIterator(final String q, final int maxPages) {
            try {
                this.q = URLEncoder.encode(q, java.nio.charset.StandardCharsets.UTF_8.toString());
                nextIterator(this.q, p, maxPages);
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
                        nextIterator(q, p, maxPages);
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
