package pt.it.av.atnog.utils.ws;

import java.util.List;

/**
 * Created by mantunes on 3/19/15.
 */
public interface SearchEngine {
    public List<String> search(final String q);

    public List<String> snippets(final String q);
}
