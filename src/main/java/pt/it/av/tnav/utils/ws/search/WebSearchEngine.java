package pt.it.av.tnav.utils.ws.search;

import pt.it.av.tnav.utils.CollectionsUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract class that represents a web search engine service.
 * <p>
 * Provides methods to search a specific query.
 * It was designed to provide a efficient iterator mechanism, ideal for pipeline processing.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public abstract class WebSearchEngine implements SearchEngine {
  private static final int DEFAULT_MAX_RESULTS = 200;
  protected final String url;
  protected final int maxResults;

  /**
   * Web Search Engine constructor.
   *
   * @param url web search engine address
   */
  public WebSearchEngine(final String url) {
    this(url, DEFAULT_MAX_RESULTS);
  }

  /**
   * Web Search Engine constructor.
   *
   * @param url web search engine address
   * @param maxResults maximum number of results
   */
  public WebSearchEngine(final String url, final int maxResults) {
    this.url = url;
    this.maxResults = maxResults;
  }

  /**
   * Returns an {@link Iterator} that iterates through the results of the web search engine.
   *
   * @param q web search query
   * @param skip number of results to skip
   * @param pageno page number
   * @return an {@link Iterator} that iterates through the results of the web search engine.
   */
  protected abstract Iterator<Result> resultsIterator(final String q, final int skip, final int pageno);

  @Override
  public List<Result> search(final String q) {
    return CollectionsUtils.iterator2List(searchIt(q));
  }

  @Override
  public Iterator<Result> searchIt(final String q) {
    return new WebSearchEngineIterator(q);
  }

  /**
   * Web Search Engine Iterator.
   * <p>
   *   The result pages are consomed continuously.
   *   Fetch one page of results and iterates over them, before fetching another result's page.
   *   This way the network calls are spread throught time, improving latency to the user.
   * </p>
   *
   * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
   * @version 1.0
   */
  private class WebSearchEngineIterator implements Iterator<Result> {
    private String q;
    private Iterator<Result> it = null;
    private boolean done = false;
    private int skip = 0, pageno = 1;

    /**
     * Web Search Engine Iterator constructor.
     *
     * @param q web search query
     */
    public WebSearchEngineIterator(final String q) {
      try {
        this.q = URLEncoder.encode(q, java.nio.charset.StandardCharsets.UTF_8.toString());
      } catch (UnsupportedEncodingException e) {
        this.q = null;
        done = true;
        e.printStackTrace();
      }
    }

    @Override
    public boolean hasNext() {
      if (!done && it == null) {
        it = resultsIterator(q, skip, pageno);
        if (it == null || !it.hasNext()) {
          done = true;
        } else {
          pageno++;
        }
      }
      return !done;
    }

    @Override
    public Result next() {
      Result rv = it.next();
      skip++;
      if (!it.hasNext()) {
        it = null;
        if (skip >= maxResults)
          done = true;
      }
      return rv;
    }
  }
}
