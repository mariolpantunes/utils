package pt.it.av.atnog.utils.ws.search;

import pt.it.av.atnog.utils.Http;
import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.json.JSONValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

//TODO: standby for gigablast reply...

/**
 * Gigablast search engine.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Gigablast extends SearchEngine {


    @Override
    public Iterator<Result> searchIt(String q) {
        return new GigablastIterator(q);
    }

    private class GigablastIterator implements Iterator<Result> {
        private boolean lastPage = false, done = false;
        private int skip = 1;
        private Iterator<JSONValue> it = null;
        private String q;

        public GigablastIterator(final String q) {
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
            return false;
        }

        @Override
        public Result next() {
            return null;
        }

        /**
         * Fetch a new result's page, process it and returns a iterator with the results.
         * This methods manages network exceptions and the end of the iteration.
         */
        private void nextIterator() {
            try {
                System.out.println("https://www.gigablast.com/search?&format=json&scores=1&sortby=0&s=" + skip + "&q=" + q);
                JSONObject json = Http.getJson("https://www.gigablast.com/search?&format=json&scores=1&sortby=0&s=" + skip + "&q=" + q);
                System.out.println(json);

                /*JSONArray array = json.get("results").asArray();
                skip += array.size();
                if (skip >= json.get("count").asNumber())
                    lastPage = true;
                it = array.iterator();
                if (!it.hasNext())
                    done = true;*/
            } catch (Exception e) {
                done = true;
                e.printStackTrace();
            }
        }
    }
}
