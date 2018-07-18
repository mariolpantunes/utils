package pt.it.av.tnav.utils.ws.search;

import pt.it.av.tnav.utils.Http;
import pt.it.av.tnav.utils.json.JSONArray;
import pt.it.av.tnav.utils.json.JSONObject;
import pt.it.av.tnav.utils.json.JSONValue;

import java.util.Iterator;

/**
 * Faroo search engine.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Faroo extends WebSearchEngine {
  private static final String DEFAULT_URL = "http://www.faroo.com/";
  private final String key;

  /**
   * @param key
   */
  public Faroo(final String key) {
    super(DEFAULT_URL);
    this.key = key;
  }

  @Override
  protected Iterator<Result> resultsIterator(final String q, final int skip, final int pageno) {
    return new FarooResultIterator(q, skip);
  }

  /**
   * Fast Faroo search iterator.
   * <p>The result pages are consomed continuously.
   * Fetch one page of results and iterates over them, before fetching another result's page.
   * This way the network calls are spread throught time, improving latency to the user.</p>
   */
  private class FarooResultIterator implements Iterator<Result> {
    private final int skip;
    private final String q;
    private Iterator<JSONValue> it = null;
    private boolean done = false;

    public FarooResultIterator(final String q, final int skip) {
      this.q = q;
      this.skip = skip;
    }

    @Override
    public boolean hasNext() {
      if (it == null) {
        try {
          JSONObject json = Http.getJson(url + "api?key=" + key +
              "&start=" + (skip + 1) + "&q=" + q);
          if (json != null) {
            int numberResults = json.get("count").asInt();
            if (skip >= numberResults) {
              done = true;
            }
            JSONArray array = json.get("results").asArray();
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
        if (json.contains("kwic")) {
          rv = new Result(name, json.get("kwic").asString(), uri);
        } else {
          rv = new Result(name, name, uri);
        }
      }
      return rv;
    }
  }
}
