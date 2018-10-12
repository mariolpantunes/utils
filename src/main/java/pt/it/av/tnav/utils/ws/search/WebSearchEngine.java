package pt.it.av.tnav.utils.ws.search;

import pt.it.av.tnav.utils.CollectionsUtils;
import pt.it.av.tnav.utils.Utils;

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
  private static final int DEFAULT_MAX_RESULTS = 100;
  protected final String url;
  protected final int maxResults;

  public WebSearchEngine(final String url) {
    this(url, DEFAULT_MAX_RESULTS);
  }

  public WebSearchEngine(final String url, final int maxResults) {
    this.url = url;
    this.maxResults = maxResults;
  }

  /**
   * @param q
   * @param skip
   * @param pageno
   * @return
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

  private class WebSearchEngineIterator implements Iterator<Result> {
    private String q;
    private Iterator<Result> it = null;
    private boolean done = false;
    private int skip = 0, pageno = 1;

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
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
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
