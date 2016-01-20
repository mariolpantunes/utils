package pt.it.av.atnog.utils.ws.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mantunes on 6/16/15.
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
        return new CollectionSearchIterator(q);
    }

    @Override
    public Iterator<String> snippetsIt(final String q) {
        return null;
    }

    private class CollectionSearchIterator implements Iterator<String> {
        private final List<Iterator<String>> seIt;

        public CollectionSearchIterator(final String q) {
            seIt = new ArrayList<>();
            for (SearchEngine s : se)
                seIt.add(s.searchIt(q));
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public String next() {
            return null;
        }
    }

    private class CollectionSnippetIterator implements Iterator<String> {
        private final List<Iterator<String>> seIt;

        public CollectionSnippetIterator(final String q) {
            seIt = new ArrayList<>();
            for (SearchEngine s : se)
                seIt.add(s.snippetsIt(q));
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public String next() {
            return null;
        }
    }
}
