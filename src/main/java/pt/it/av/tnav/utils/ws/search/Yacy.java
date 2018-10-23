package pt.it.av.tnav.utils.ws.search;

import pt.it.av.tnav.utils.Http;
import pt.it.av.tnav.utils.json.JSONArray;
import pt.it.av.tnav.utils.json.JSONObject;
import pt.it.av.tnav.utils.json.JSONValue;

import java.util.Iterator;

//TODO: JSON parse fails some times
//TODO: it appears some json are invalid
//TODO: Ease the constrution of the iterator, move the code to hasNext function.
//TODO: If snippet is empty, fill it with title
//TODO: Fix URL usage.

/**
 * Yacy search engine.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Yacy extends WebSearchEngine {
  private static final String DEFAULT_URL = "http://hrun.hopto.org/yacy/";
  private static final int MAX_RESULTS = 50;

  /**
   * Yacy constructor.
   */
  public Yacy() {
    super(DEFAULT_URL, MAX_RESULTS);
  }

  /**
   * Yacy constructor.
   *
   * @param url web service address
   */
  public Yacy(final String url) {
    super(url, MAX_RESULTS);
  }

  /**
   * Yacy constructor.
   *
   * @param url        web service address
   * @param maxResults maximum number of results
   */
  public Yacy(final String url, final int maxResults) {
    super(url, maxResults);
  }

  @Override
  protected Iterator<Result> resultsIterator(final String q, final int skip, final int pageno) {
    return new YacyResultIterator(q, skip);
  }

  /**
   * Yacy Results Iterator.
   * <p>
   * The result pages are consomed continuously.
   * Fetch one page of results and iterates over them, before fetching another result's page.
   * This way the network calls are spread throught time, improving latency to the user.
   * </p>
   *
   * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
   * @version 1.0
   */
  private class YacyResultIterator implements Iterator<Result> {
    private final int skip;
    private final String q;
    private Iterator<JSONValue> it = null;
    private boolean done = false;

    /**
     * Yacy Iteratror constructor.
     *
     * @param q    web search query
     * @param skip number of results to skip
     */
    public YacyResultIterator(final String q, final int skip) {
      this.q = q;
      this.skip = skip;
    }

    @Override
    public boolean hasNext() {
      if (it == null) {
        try {
          JSONObject json = Http.getJson(url + "/yacysearch.json?resource=global&contentdom=text" +
              "&lr=lang_en&startRecord=" + (skip + 1) + "&query=" + q).get("channels").asArray().get(0).asObject();
          if (json != null) {
            int numberResults = Integer.parseInt(json.get("totalResults").asString());
            if (skip >= numberResults) {
              done = true;
            }
            JSONArray array = json.get("items").asArray();
            it = array.iterator();
            if (!it.hasNext()) {
              done = true;
            }
          } else {
            done = true;
          }
        } catch (Exception e) {
          //e.printStackTrace();
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
        String name = json.get("title").asString(), uri = json.get("link").asString();
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
