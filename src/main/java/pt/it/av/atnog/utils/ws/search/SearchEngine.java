package pt.it.av.atnog.utils.ws.search;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mantunes on 3/19/15.
 */
public interface SearchEngine {
    List<String> search(final String q) throws UnsupportedEncodingException;

    List<String> snippets(final String q) throws UnsupportedEncodingException;

    Iterator<String> searchIt(final String q) throws UnsupportedEncodingException;

    Iterator<String> snippetsIt(final String q) throws UnsupportedEncodingException;
}
