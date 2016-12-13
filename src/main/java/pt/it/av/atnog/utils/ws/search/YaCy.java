package pt.it.av.atnog.utils.ws.search;

import pt.it.av.atnog.utils.Http;
import pt.it.av.atnog.utils.json.JSONArray;
import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.json.JSONValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

//TODO: JSON parse fails some times
//TODO: it appears some json are invalid

/**
 * YaCy search engine.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
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

    @Override
    public Iterator<Result> searchIt(final String q) {
        return new YaCySearchIterator(q, maxResults);
    }

    /**
     * Fast YaCy search iterator.
     * <p>The result pages are consomed continuously.
     * Fetch one page of results and iterates over them, before fetching another result's page.
     * This way the network calls are spread throught time, improving latency to the user.</p>
     */
    private class YaCySearchIterator implements Iterator<Result> {
        private boolean lastPage = false, done = false;
        private int skip = 1, maxResults;
        private Iterator<JSONValue> it = null;
        private String q;

        public YaCySearchIterator(final String q, final int maxResults) {
            try {
                this.q = URLEncoder.encode(q, java.nio.charset.StandardCharsets.UTF_8.toString());
                this.maxResults = maxResults;
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
                //System.out.println(json);
                rv = new Result(json.get("title").asString(),
                        json.get("description").asString(),
                        json.get("link").asString());

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
                //String s = HTTP.get("http://search.yacy.net/yacysearch.json?resource=global&contentdom=text&lr=lang_en&startRecord=" + skip + "&query=" + q);
                //System.err.println(s);
                JSONObject json = Http.getJson("http://search.yacy.net/yacysearch.json?resource=global&contentdom=text" +
                        "&lr=lang_en&startRecord=" + skip + "&query=" + q).get("channels").asArray().get(0).asObject();
                JSONArray array = json.get("items").asArray();
                skip += array.size();
                if (skip >= maxResults)
                    lastPage = true;
                it = array.iterator();
                if (!it.hasNext())
                    done = true;
            } catch (Exception e) {
                e.printStackTrace();
                done = true;
            }
        }
    }
}
