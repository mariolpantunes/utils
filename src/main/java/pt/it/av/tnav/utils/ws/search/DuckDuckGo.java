package pt.it.av.tnav.utils.ws.search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import pt.it.av.tnav.utils.Http;
import pt.it.av.tnav.utils.json.JSONArray;
import pt.it.av.tnav.utils.json.JSONObject;
import pt.it.av.tnav.utils.json.JSONValue;



/**
 * DuckDuckGo Instant Answers.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class DuckDuckGo extends WebSearchEngine {
  private static final String DEFAULT_URL = "https://api.duckduckgo.com/";

  /**
   * DuckDuckGo Instant Answers constructor.
   */
  public DuckDuckGo() {
    super(DEFAULT_URL);
  }

  /**
   * DuckDuckGo Instant Answers constructor.
   *
   * @param url web service address
   */
  public DuckDuckGo(final String url) {
    super(url);
  }

  /**
   * DuckDuckGo Instant Answers constructor.
   *
   * @param url        web service address
   * @param maxResults maximum number of results
   */
  public DuckDuckGo(final String url, final int maxResults) {
    super(url, maxResults);
  }

  @Override
  protected Iterator<Result> resultsIterator(String q, int skip, int pageno) {
    return new DDGResultIterator(q, pageno);
  }

  private class DDGResultIterator implements Iterator<Result> {
    private final String q;
    private Iterator<JSONValue> it = null;
    private boolean done = false;

    /**
     * Yacy Iteratror constructor.
     *
     * @param q    web search query
     * @param skip number of results to skip
     */
    public DDGResultIterator(final String q, final int pageno) {
      this.q = q;
      if (pageno > 1) {
        done = true;
      }
    }

    @Override
    public boolean hasNext() {
      if (it == null) {
        try {
          JSONObject json = Http.getJson(url + "?format=json&q=" + q);
          if (json != null) {
            // due to the strucuture of the DuckDuckGo reply it is necessary to load all the values
            List<JSONValue> topics = new ArrayList<>();
            Queue<JSONArray> queue = new ArrayDeque<>();
            queue.add(json.get("RelatedTopics").asArray());
            while(!queue.isEmpty()) {
              JSONArray array = queue.poll();
              for(JSONValue jv : array) {
                JSONObject jo = jv.asObject();
                if(jo.contains("Topics")) {
                  queue.offer(jo.get("Topics").asArray());
                } else {
                  topics.add(jv);
                }
              }
            }
            it = topics.iterator();
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
        String uri = json.get("FirstURL").asString(), snippet = json.get("Text").asString();
        rv = new Result("", snippet, uri);
      }
      return rv;
    }
  }
}
