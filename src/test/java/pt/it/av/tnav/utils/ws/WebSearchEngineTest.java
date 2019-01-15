package pt.it.av.tnav.utils.ws;

import org.junit.BeforeClass;
import org.junit.Test;
import pt.it.av.tnav.utils.ws.search.SearchEngine;
import pt.it.av.tnav.utils.ws.search.WebSearchEngine;

import java.util.Iterator;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link WebSearchEngine}.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class WebSearchEngineTest {
  private static MockupSearchEngine dummy;

  @BeforeClass
  public static void setup() {
    dummy = new MockupSearchEngine();
  }

  @Test
  public void test_it_empty() {
    Iterator<SearchEngine.Result> it = dummy.searchIt("");
    assertTrue(!it.hasNext());
  }

  @Test
  public void test_it_zero() {
    Iterator<SearchEngine.Result> it = dummy.searchIt("zero");
    assertTrue(!it.hasNext());
  }

  @Test
  public void test_it_one() {
    Iterator<SearchEngine.Result> it = dummy.searchIt("one");
    assertTrue(it.hasNext());
    int count = 0;
    while (it.hasNext()) {
      count++;
      SearchEngine.Result r = it.next();
      switch (count) {
        case 1:
          assertTrue(r.name.equals("one"));
          break;
        case 2:
          assertTrue(r.name.equals("two"));
          break;
        case 3:
          assertTrue(r.name.equals("three"));
          break;
      }
    }
    assertTrue(count == 1);
  }

  @Test
  public void test_it_two() {
    Iterator<SearchEngine.Result> it = dummy.searchIt("two");
    assertTrue(it.hasNext());
    int count = 0;
    while (it.hasNext()) {
      count++;
      SearchEngine.Result r = it.next();
      switch (count) {
        case 1:
          assertTrue(r.name.equals("one"));
          break;
        case 2:
          assertTrue(r.name.equals("two"));
          break;
        case 3:
          assertTrue(r.name.equals("three"));
          break;
      }
    }
    assertTrue(count == 2);
  }

  @Test
  public void test_it_three() {
    Iterator<SearchEngine.Result> it = dummy.searchIt("three");
    assertTrue(it.hasNext());
    int count = 0;
    while (it.hasNext()) {
      count++;
      SearchEngine.Result r = it.next();
      switch (count) {
        case 1:
          assertTrue(r.name.equals("one"));
          break;
        case 2:
          assertTrue(r.name.equals("two"));
          break;
        case 3:
          assertTrue(r.name.equals("three"));
          break;
      }
    }
    assertTrue(count == 3);
  }
}
