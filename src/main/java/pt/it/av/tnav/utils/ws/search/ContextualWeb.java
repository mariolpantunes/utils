package pt.it.av.tnav.utils.ws.search;

import pt.it.av.tnav.utils.Http;
import pt.it.av.tnav.utils.json.JSONArray;
import pt.it.av.tnav.utils.json.JSONObject;
import pt.it.av.tnav.utils.json.JSONValue;

import java.util.Iterator;

/**
 * ContextualWeb search engine.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class ContextualWeb extends WebSearchEngine {
  private static final String DEFAULT_URL =
      "https://contextualwebsearch-websearch-v1.p.mashape.com/api/Search/WebSearchAPIWithPagination";
  private final String key;

  /**
   * Contextual Web constructor.
   *
   * @param key web service key
   */
  public ContextualWeb(final String key) {
    super(DEFAULT_URL);
    this.key = key;
  }

  /**
   * Contextual Web constructor.
   *
   * @param key web service key
   * @param url web service address
   */
  public ContextualWeb(final String key, final String url) {
    super(url);
    this.key = key;
  }

  /**
   * Contextual Web constructor.
   *
   * @param key web service key
   * @param url web service address
   * @param maxResults maximum number of results
   */
  public ContextualWeb(final String key, final String url, final int maxResults) {
    super(url, maxResults);
    this.key = key;
  }


  @Override
  protected Iterator<Result> resultsIterator(final String q, final int skip, final int pageno) {
    return new CWResultIterator(q, skip, pageno);
  }

  /**
   * Contextual Web search iterator.
   * <p>
   *   The result pages are consomed continuously.
   *   Fetch one page of results and iterates over them, before fetching another result's page.
   *   This way the network calls are spread throught time, improving latency to the user.
   * </p>
   *
   * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
   * @version 1.0
   */
  private class CWResultIterator implements Iterator<Result> {
    private final int pageno, skip;
    private final String q;
    private Iterator<JSONValue> it = null;
    private boolean done = false;

    /**
     * Contextual Web Iteratror constructor.
     *
     * @param q web search query
     * @param skip number of results to skip
     * @param pageno page number
     */
    public CWResultIterator(final String q, final int skip, final int pageno) {
      this.q = q;
      this.skip= skip;
      this.pageno = pageno;
    }

    @Override
    public boolean hasNext() {
      if (it == null) {
        try {
          JSONObject json = Http.getJson(url + "?q="+q+"&pageNumber="+pageno+"&pageSize=50&autoCorrect=false", key);
          if (json != null) {
            int totalCount = json.get("totalCount").asInt();
            if (skip >= totalCount) {
              done = true;
            }
            JSONArray array = json.get("value").asArray();
            it = array.iterator();
            if (!it.hasNext()) {
              done = true;
            }
          } else {
            done = true;
          }
        } catch (Exception e) {
          e.printStackTrace();
          done = true;
        }
      } else {
        done = !it.hasNext();
      }
      return !done;
    }

    @Override
    public Result next() {
      Result rv = null;
      if (!done) {
        JSONObject json = it.next().asObject();
        String name = json.get("title").asString(), uri = json.get("url").asString();
        if (json.contains("description")) {
          rv = new Result(name, json.get("description").asString(), uri);
        } else {
          rv = new Result(name, name, uri);
        }
      }
      return rv;
    }
  }
}