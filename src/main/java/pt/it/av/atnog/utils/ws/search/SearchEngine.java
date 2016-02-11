package pt.it.av.atnog.utils.ws.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract class that represents a search engine service.
 *
 * <p>
 *     Provides methods to search a specific query.
 *     It was designed to provide a efficient iterator mechanism, ideal for pipeline processing.
 * </p>
 * @author Mário Antunes
 * @version 1.0
 */
public abstract class SearchEngine {

    /**
     * Searchs a specific query in a search engine service.
     * Returns a list with all the results, ordered by the search engine service.
     * This method is built on top of the iterator search.
     *
     * @param q query used in the search
     * @return list with all the results from the search engine
     */
    public List<Result> search(final String q) {
        List<Result> rv = new ArrayList<>();
        Iterator<Result> it = searchIt(q);
        while (it.hasNext())
            rv.add(it.next());
        return rv;
    }

    /**
     * Searchs a specific query in a search engine service.
     * Returns an iterator containing all the results, ordered by the search engine service.
     *
     * @param q query used in the search
     * @return iterator coitaining all the results
     */
    public abstract Iterator<Result> searchIt(final String q);

    /**
     * Represents one result from the search engine.
     *
     * <p>A single result is composed by a title, snippet and url.</p>
     */
    public class Result {
        public final String title, snippet, url;

        /**
         * Search result constructor
         *
         * @param title title of the webpage
         * @param snippet snippet provided by the search engine
         * @param url url of the webpage
         */
        public Result(final String title, final String snippet, final String url) {
            this.title = title;
            this.snippet = snippet;
            this.url = url;
        }

        @Override
        public String toString() {
            return new String("Title: " + title + " Snippet: " + snippet + " URL: " + url);
        }
    }
}
