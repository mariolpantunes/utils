package pt.it.av.atnog.utils.ws.search;

import pt.it.av.atnog.utils.Utils;
import pt.it.av.atnog.utils.structures.iterator.CollectionIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Collection Search Engine.
 * <p>
 * Encapsulates multiple search engines in one interface.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class CollectionSearchEngine implements SearchEngine {
  private final List<SearchEngine> se;

  public CollectionSearchEngine(final List<SearchEngine> se) {
    this.se = se;
  }

  @Override
  public List<Result> search(String q) {
    return Utils.iterator2List(searchIt(q));
  }

  @Override
  public Iterator<Result> searchIt(final String q) {
    List<Iterator<Result>> its = new ArrayList<>();
    for (SearchEngine s : se)
      its.add(s.searchIt(q));
    return new CollectionIterator<>(its);
  }
}
