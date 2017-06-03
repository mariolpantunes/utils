package pt.it.av.atnog.utils.ws.search;

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
   *   It is composed by three elements:
   *   <p><ul>
   *     <li>{@link name} name of the resource.
   *     <li>{@link snippet} snippet provided by the search engine.
   *     <li>{@link uri} uri of the resource.
   *   </ul><p>
   * </p>
   */
  class Result {
    public final String name, snippet, uri;

    /**
     * Result contructor.
     *
     * @param name   name of the resource.
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
  }
}
