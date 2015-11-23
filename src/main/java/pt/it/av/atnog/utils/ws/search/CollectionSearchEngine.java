package pt.it.av.atnog.utils.ws.search;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mantunes on 6/16/15.
 */
public class CollectionSearchEngine implements SearchEngine {
    private final List<SearchEngine> se;

    public CollectionSearchEngine(final List<SearchEngine> se) {
        this.se = se;
    }

    @Override
    public List<String> search(String q) throws UnsupportedEncodingException {
        List<String> rv = new ArrayList<>();
        for (SearchEngine s : se)
            rv.addAll(s.search(q));
        return rv;
    }

    @Override
    public List<String> snippets(String q) throws UnsupportedEncodingException {
        List<String> rv = new ArrayList<>();
        for (SearchEngine s : se)
            rv.addAll(s.snippets(q));
        return rv;
    }

    @Override
    public Iterator<String> searchIt(String q) throws UnsupportedEncodingException {
        return null;
    }

    @Override
    public Iterator<String> snippetsIt(String q) throws UnsupportedEncodingException {
        return null;
    }
}
