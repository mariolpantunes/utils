package pt.it.av.atnog.utils.ws.search;

import pt.it.av.atnog.utils.HTTP;
import pt.it.av.atnog.utils.json.JSONArray;
import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.json.JSONValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Bing search engine.
 */
public class Bing extends SearchEngine {
    /**
     * API key used to access the search engine
     */
    private final String key;

    /**
     * Bing seach engine constructor. Requires an valid API key.
     *
     * @param key valid API key
     */
    public Bing(final String key) {
        this.key = key;
    }

    @Override
    public Iterator<Result> searchIt(final String q) {
        return new BingSearchIterator(q);
    }

    /**
     * Fast Bing search iterator.
     * <p>The result pages are consomed continuously.
     * Fetch one page of results and iterates over them, before fetching another result's page.
     * This way the network calls are spread throught time, improving latency to the user.</p>
     */
    private class BingSearchIterator implements Iterator<Result> {
        private boolean lastPage = false, done = false;
        private int skip = 0;
        private Iterator<JSONValue> it = null;
        private String q;

        public BingSearchIterator(final String q) {
            try {
                this.q = URLEncoder.encode("'" + q + "'", java.nio.charset.StandardCharsets.UTF_8.toString());
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

                rv = new Result(json.get("Title").asString(),
                        json.get("Description").asString(),
                        json.get("DisplayUrl").asString());

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
                JSONObject json = HTTP.getJSON("https://api.datamarket.azure.com/Bing/SearchWeb/v1/Web?$format=json" +
                        "&$skip=" + skip + "&Query=" + q, "", key).get("d").asObject();
                if (json.get("__next") == null)
                    lastPage = true;
                JSONArray array = json.get("results").asArray();
                skip += array.size();
                it = array.iterator();
                if (!it.hasNext())
                    done = true;
            } catch (Exception e) {
                done = true;
                e.printStackTrace();
            }
        }
    }
}
