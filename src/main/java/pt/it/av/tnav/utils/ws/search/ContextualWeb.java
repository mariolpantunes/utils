package pt.it.av.tnav.utils.ws.search;

import pt.it.av.tnav.utils.Http;
import pt.it.av.tnav.utils.json.JSONArray;
import pt.it.av.tnav.utils.json.JSONObject;
import pt.it.av.tnav.utils.json.JSONValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ContextualWeb search engine.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class ContextualWeb extends WebSearchEngine {
  private static final String DEFAULT_URL = "https://contextualwebsearch-websearch-v1.p.rapidapi.com/api/Search/WebSearchAPI";
  private final Map<String, String> props = new HashMap<>();

  /**
   * Contextual Web constructor.
   *
   * @param rapidAPI_Key web service key
   */
  public ContextualWeb(final String rapidAPI_Key) {
    this(rapidAPI_Key, DEFAULT_URL);
  }

  /**
   * Contextual Web constructor.
   *
   * @param rapidAPI_Key web service key
   * @param url          web service address
   */
  public ContextualWeb(final String rapidAPI_Key, final String url) {
    super(url);
    props.put("X-RapidAPI-Key", rapidAPI_Key);
    //props.put("User-Agent", "Mozilla/5.0 (X11; Linux i686; rv:75.0) Gecko/20100101 Firefox/75.0");
  }

  /**
   * Contextual Web constructor.
   *
   * @param rapidAPI_Key web service key
   * @param url          web service address
   * @param maxResults   maximum number of results
   */
  public ContextualWeb(final String rapidAPI_Key, final String url, final int maxResults) {
    super(url, maxResults);
    props.put("X-RapidAPI-Key", rapidAPI_Key);
    props.put("User-Agent", "Mozilla/5.0 (X11; Linux i686; rv:75.0) Gecko/20100101 Firefox/75.0");
  }

  @Override
  protected Iterator<Result> resultsIterator(final String q, final int skip, final int pageno) {
    return new CWResultIterator(q, skip, pageno);
  }

  /**
   * Contextual Web search iterator.
   * <p>
   * The result pages are consomed continuously. Fetch one page of results and
   * iterates over them, before fetching another result's page. This way the
   * network calls are spread throught time, improving latency to the user.
   * </p>
   *
   * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
   * @version 1.0
   */
  private class CWResultIterator implements Iterator<Result> {
    private final int skip;
    private final Map<String, String> params = new HashMap<>();
    private Iterator<JSONValue> it = null;
    private boolean done = false;

    /**
     * Contextual Web Iteratror constructor.
     *
     * @param q      web search query
     * @param skip   number of results to skip
     * @param pageno page number
     */
    public CWResultIterator(final String q, final int skip, final int pageno) {
      this.skip = skip;
      params.put("q", q);
      params.put("format", "json");
      params.put("pageNumber", Integer.toString(pageno));
      params.put("pageSize", Integer.toString(50));
      params.put("autoCorrect", "False");
      params.put("safeSearch", "False");
    }

    @Override
    public boolean hasNext() {
      if (it == null) {
        try {
          JSONObject json = Http.getJson(baseUrl, props, params);
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
        try {
          String name = json.get("title").asString(), uri = "", description = null;

          if (json.contains("uri")) {
            uri = json.get("url").asString();
          }

          if (json.contains("description")) {
            description = json.get("description").asString();
          }

          rv = new Result(name, description, uri);
        } catch (Exception e) {
          System.err.println(e);
        }
      }
      return rv;
    }
  }
}
