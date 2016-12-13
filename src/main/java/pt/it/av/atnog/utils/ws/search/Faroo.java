package pt.it.av.atnog.utils.ws.search;

import pt.it.av.atnog.utils.Http;
import pt.it.av.atnog.utils.json.JSONArray;
import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.json.JSONValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Faroo search engine.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Faroo extends SearchEngine {
    private final String key;

    public Faroo(final String key) {
        this.key = key;
    }

    @Override
    public Iterator<Result> searchIt(String q) {
        return new FarooSearchIterator(q);
    }

    /**
     * Fast Faroo search iterator.
     * <p>The result pages are consomed continuously.
     * Fetch one page of results and iterates over them, before fetching another result's page.
     * This way the network calls are spread throught time, improving latency to the user.</p>
     */
    private class FarooSearchIterator implements Iterator<Result> {
        private boolean lastPage = false, done = false;
        private int skip = 1;
        private Iterator<JSONValue> it = null;
        private String q;

        public FarooSearchIterator(final String q) {
            try {
                this.q = URLEncoder.encode(q, java.nio.charset.StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                done = true;
            }
            nextIterator();
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
                rv = new Result(json.get("title").asString(),
                        json.get("kwic").asString(),
                        json.get("url").asString());
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
                JSONObject json = Http.getJson("http://www.faroo.com/api?key=" + key + "&start=" + skip + "&q=" + q);
                JSONArray array = json.get("results").asArray();
                skip += array.size();
                if (skip >= json.get("count").asNumber())
                    lastPage = true;
                it = array.iterator();
                if (!it.hasNext())
                    done = true;
                Thread.sleep(500);
            } catch (Exception e) {
                done = true;
                //e.printStackTrace();
            }
        }
    }
}
