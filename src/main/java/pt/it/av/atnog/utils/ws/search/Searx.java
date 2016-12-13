package pt.it.av.atnog.utils.ws.search;

import pt.it.av.atnog.utils.Http;
import pt.it.av.atnog.utils.Http;
import pt.it.av.atnog.utils.json.JSONArray;
import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.json.JSONValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Searx search engine.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
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

    @Override
    public Iterator<Result> searchIt(final String q) {
        return new SearxSearchIterator(q, maxPages);
    }

    /**
     * Fast Searx search iterator.
     * <p>The result pages are consomed continuously.
     * Fetch one page of results and iterates over them, before fetching another result's page.
     * This way the network calls are spread throught time, improving latency to the user.</p>
     */
    private class SearxSearchIterator implements Iterator<Result> {
        private boolean lastPage = false, done = false;
        private int skip = 1, maxPages;
        private Iterator<JSONValue> it = null;
        private String q;

        public SearxSearchIterator(final String q, final int maxPages) {
            try {
                this.q = URLEncoder.encode(q, java.nio.charset.StandardCharsets.UTF_8.toString());
                this.maxPages = maxPages;
                nextIterator();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                done = true;
            }
        }

        @Override
        public boolean hasNext() {
            return !done;
        }

        @Override
        public Result next() {
            Result rv = null;
            if (!done) {
                JSONObject json = it.next().asObject();

                String title = json.get("title").asString(), url = json.get("url").asString();

                if (json.contains("content"))
                    rv = new Result(title, json.get("content").asString(), url);
                else
                    rv = new Result(title, title, url);

                if (!it.hasNext() && !lastPage)
                    nextIterator();
                else if (!it.hasNext() && lastPage)
                    done = true;
            }
            return rv;
        }

        /**
         * Fetch a new result's page, process it and returns a iterator with the results.
         * This methods manages network exceptions and the end of the iteration.
         */
        private void nextIterator() {
            try {
                JSONObject json = Http.getJson("https://searx.me/?format=json&category_general&pageno=" + skip + "&q=" + q);
                JSONArray array = json.get("results").asArray();
                if (skip >= maxPages)
                    lastPage = true;
                skip++;
                it = array.iterator();
                if (!it.hasNext())
                    done = true;
            } catch (Exception e) {
                done = true;
            }
        }
    }
}
