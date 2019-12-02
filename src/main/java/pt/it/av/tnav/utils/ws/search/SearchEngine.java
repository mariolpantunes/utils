package pt.it.av.tnav.utils.ws.search;

import java.util.Iterator;
import java.util.List;

/**
 * Interface that represents a search engine service.
 * <p>
 * Provides methods to search a specific query.
 * It was designed to provide a efficient iterator mechanism, ideal for pipeline processing.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public interface SearchEngine {

  /**
   * Searchs a specific query in a search engine service.
   * Returns a list with all the results, ordered by the search engine service.
   * This method is built on top of the iterator search.
   *
   * @param q query used in the search
   * @return list with all the results from the search engine
   */
  List<Result> search(final String q);

  /**
   * Searchs a specific query in a search engine service.
   * Returns an iterator containing all the results, ordered by the search engine service.
   *
   * @param q query used in the search
   * @return iterator coitaining all the results
   */
  Iterator<Result> searchIt(final String q);

  /**
   * Represents a single result from the search engine.
   * <p>
   * It is composed by three elements:
   * <ul>
   * <li>{@link String} name of the resource.
   * <li>{@link String} snippet provided by the search engine.
   * <li>{@link String} uri of the resource.
   * </ul>
   * </p>
   */
  class Result {
    public final String name, snippet, uri;

    /**
     * Result contructor.
     *
     * @param name    name of the resource.
     * @param snippet snippet provided by the search engine.
     * @param uri     uri of the resource.
     */
    public Result(final String name, final String snippet, final String uri) {
      this.name = name;
      this.snippet = snippet;
      this.uri = uri;
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Name: ");
      sb.append(name);
      sb.append(System.getProperty("line.separator"));
      sb.append("Snippet: ");
      sb.append(snippet);
      sb.append(System.getProperty("line.separator"));
      sb.append("URI: ");
      sb.append(uri);
      return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
      boolean rv = false;
      if (o != null) {
        if (o == this)
          rv = true;
        else if (o instanceof Result) {
          Result r = (Result) o;
          rv = this.name.equals(r.name) && this.snippet.equals(r.snippet) && this.uri.equals(r.uri);
        }
      }

      return rv;
    }

    @Override
    public int hashCode() {
      int prime = 31, result = 1;
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((snippet == null) ? 0 : snippet.hashCode());
      result = prime * result + ((uri == null) ? 0 : uri.hashCode());
      return result;
    }
  }
}
