package pt.it.av.atnog.utils.ws.search;

import java.util.Iterator;
import java.util.List;

/**
 * Created by mantunes on 3/19/15.
 */
public interface SearchEngine {
    List<String> search(final String q);

    List<String> snippets(final String q);

    Iterator<String> searchIt(final String q);

    Iterator<String> snippetsIt(final String q);
}
