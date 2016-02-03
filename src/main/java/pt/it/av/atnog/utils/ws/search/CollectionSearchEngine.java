package pt.it.av.atnog.utils.ws.search;

import pt.it.av.atnog.utils.CollectionIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Collection Search Engine.
 * <P>Executes</P>
 */
public class CollectionSearchEngine extends SearchEngine {
    private final List<SearchEngine> se;

    public CollectionSearchEngine(final List<SearchEngine> se) {
        this.se = se;
    }

    @Override
    public List<String> search(String q) {
        List<String> rv = new ArrayList<>();
        for (SearchEngine s : se)
            rv.addAll(s.search(q));
        return rv;
    }

    @Override
    public List<String> snippets(String q) {
        List<String> rv = new ArrayList<>();
        for (SearchEngine s : se)
            rv.addAll(s.snippets(q));
        return rv;
    }

    @Override
    public Iterator<String> searchIt(final String q) {
        List<Iterator<String>> its = new ArrayList<>();
        for (SearchEngine s : se)
            its.add(s.searchIt(q));
        return new CollectionIterator<>(its);
    }

    @Override
    public Iterator<String> snippetsIt(final String q) {
        List<Iterator<String>> its = new ArrayList<>();
        for (SearchEngine s : se)
            its.add(s.snippetsIt(q));
        return new CollectionIterator<>(its);
    }
}
