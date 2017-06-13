package pt.it.av.atnog.utils;

import org.junit.Test;
import pt.it.av.atnog.utils.structures.mutableNumber.MutableInteger;
import pt.it.av.atnog.utils.structures.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link Utils}.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class UtilsTest {
  private static double small[] = {1.0, 0.0, -1.0},
      large[] = {0.0, -1.0, 100.0, -100.0, 22.0, 55.0, 53.5, 20.1, 84.5, 10.2};

  @Test
  public void test_intertwine() {
    List<String> A = new ArrayList<>(), B = new ArrayList<>(), R = new ArrayList<>();
    A.add("A");
    A.add("B");
    A.add("C");

    B.add("A");
    B.add("B");
    B.add("C");

    R.add("A");
    R.add("A");
    R.add("B");
    R.add("B");
    R.add("C");
    R.add("C");

    assertTrue(Utils.intertwine(A, B).equals(R));
  }

  @Test
  public void test_minMax_small() {
    Pair<MutableInteger, MutableInteger> mm = Utils.minMax(small);
    double min = small[mm.a.intValue()], max = small[mm.b.intValue()];

    assertTrue(min == -1.0);
    assertTrue(max == 1.0);
  }

  @Test
  public void test_minMax_large() {
    Pair<MutableInteger, MutableInteger> mm = Utils.minMax(large);
    double min = large[mm.a.intValue()], max = large[mm.b.intValue()];

    assertTrue(min == -100.0);
    assertTrue(max == 100.0);
  }

  @Test
  public void test_min_small() {
    double min = small[Utils.min(small)];
    assertTrue(min == -1.0);
  }

  @Test
  public void test_min_large() {
    double min = large[Utils.min(large)];
    assertTrue(min == -100.0);
  }

  @Test
  public void test_max_small() {
    double max = small[Utils.max(small)];
    assertTrue(max == 1.0);
  }

  @Test
  public void test_max_large() {
    double max = large[Utils.max(large)];
    assertTrue(max == 100.0);
  }
}
