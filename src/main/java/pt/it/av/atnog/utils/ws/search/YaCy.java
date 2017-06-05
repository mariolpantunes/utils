package pt.it.av.atnog.utils.ws.search;

import pt.it.av.atnog.utils.Http;
import pt.it.av.atnog.utils.json.JSONArray;
import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.json.JSONValue;

import java.util.Iterator;

//TODO: JSON parse fails some times
//TODO: it appears some json are invalid
//TODO: Ease the constrution of the iterator, move the code to hasNext function.
//TODO: If snippet is empty, fill it with title
//TODO: Fix URL usage.

/**
 * YaCy search engine.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class YaCy extends WebSearchEngine {
  private static final String DEFAULT_URL = "http://hrun.hopto.org/yacy/";

  /**
   *
   */
  public YaCy() {
    super(DEFAULT_URL);
  }

  /**
   * @param url
   */
  public YaCy(final String url) {
    super(url);
  }

  /**
   *
   * @param url
   * @param maxResults
   */
  public YaCy(final String url, final int maxResults) {
    super(url, maxResults);
  }

  @Override
  protected Iterator<Result> resultsIterator(final String q, final int skip) {
    return new YacyResultIterator(q, skip);
  }

  private class YacyResultIterator implements Iterator<Result> {
    private final int skip;
    private final String q;
    private Iterator<JSONValue> it = null;
    private boolean done = false;

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
          e.printStackTrace();
          done = true;
        }
      } else {
        done = it.hasNext();
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
