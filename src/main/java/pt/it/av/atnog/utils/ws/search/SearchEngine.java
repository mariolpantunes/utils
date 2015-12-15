package pt.it.av.atnog.utils.ws.search;

import pt.it.av.atnog.utils.json.JSONValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mantunes on 3/19/15.
 */
public abstract class SearchEngine {

    public List<String> search(String q) {
        List<String> rv = new ArrayList<>();
        Iterator<String> it = searchIt(q);
        while (it.hasNext())
            rv.add(it.next());
        return rv;
    }

    public List<String> snippets(String q) {
        List<String> rv = new ArrayList<>();
        Iterator<String> it = snippetsIt(q);
        while (it.hasNext())
            rv.add(it.next());
        return rv;
    }

    public abstract Iterator<String> searchIt(final String q);

    public abstract Iterator<String> snippetsIt(final String q);

    protected class IteratorParameters {
        protected boolean lastPage = false, done = false;
        protected int skip = 0;
        protected Iterator<JSONValue> it = null;

        public IteratorParameters(int skip) {
            this.skip = skip;
        }
    }
}
