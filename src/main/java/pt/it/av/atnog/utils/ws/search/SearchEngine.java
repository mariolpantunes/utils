package pt.it.av.atnog.utils.ws.search;

import pt.it.av.atnog.utils.json.JSONValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract class that represents a search engine.
 *
 * Provides methods to search a specific query.
 * The results can be the complete web page or only the snippets.
 *
 * It was designed to provide a efficient iterator mechanism, ideal for pipeline processing.
 *
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
    public List<String> search(final String q) {
        List<String> rv = new ArrayList<>();
        Iterator<String> it = searchIt(q);
        while (it.hasNext())
            rv.add(it.next());
        return rv;
    }

    /**
     * Searchs a specific query in a search engine service.
     * Returns a list with all the snippets, ordered by the search engine service.
     * This method is built on top of the iterator snippet search.
     *
     * @param q query used in the search
     * @return list with all the results from the search engine
     */
    public List<String> snippets(final String q) {
        List<String> rv = new ArrayList<>();
        Iterator<String> it = snippetsIt(q);
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
    public abstract Iterator<String> searchIt(final String q);

    /**
     * Searchs a specific query in a search engine service.
     * Returns an iterator containing all the snippets, ordered by the search engine service.
     *
     * @param q query used in the search
     * @return iterator coitaining all the snippets
     */
    public abstract Iterator<String> snippetsIt(final String q);

    /**
     * Auxiliar class used to built efficient iterator.
     * Provides a standard way to share common information regarding the iterator properties.
     */
    protected class IteratorParameters {
        protected boolean lastPage = false, done = false;
        protected int skip = 0;
        protected Iterator<JSONValue> it = null;

        /**
         * na
         *
         * @param skip na
         */
        public IteratorParameters(int skip) {
            this.skip = skip;
        }
    }
}
