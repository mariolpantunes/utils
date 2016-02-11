package pt.it.av.atnog.utils.ws.search;

import pt.it.av.atnog.utils.CollectionIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Collection Search Engine.
 * <p>Encapsulates multiple search engines in one interface.</p>
 */
public class CollectionSearchEngine extends SearchEngine {
    private final List<SearchEngine> se;

    public CollectionSearchEngine(final List<SearchEngine> se) {
        this.se = se;
    }

    @Override
    public Iterator<Result> searchIt(final String q) {
        List<Iterator<Result>> its = new ArrayList<>();
        for (SearchEngine s : se)
            its.add(s.searchIt(q));
        return new CollectionIterator<>(its);
    }
}
