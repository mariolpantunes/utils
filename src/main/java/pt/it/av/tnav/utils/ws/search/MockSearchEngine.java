package pt.it.av.tnav.utils.ws.search;

import java.util.Iterator;
import java.util.List;

/**
 * A mockup of the {@link SearchEngine} class.
 * Used for unit testing
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class MockSearchEngine implements SearchEngine {
  private final List<SearchEngine.Result> l;

  /**
   * MockSearchEngine constructor.
   */
  public MockSearchEngine(final List<SearchEngine.Result> l) {
    this.l = l;
  }

  @Override
  public List<Result> search(String s) {
    return l;
  }

  @Override
  public Iterator<SearchEngine.Result> searchIt(String s) {
    return l.iterator();
  }
}
