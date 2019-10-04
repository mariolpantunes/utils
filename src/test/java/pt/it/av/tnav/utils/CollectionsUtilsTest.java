package pt.it.av.tnav.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link CollectionsUtils}.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class CollectionsUtilsTest {
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

    assertEquals(R, CollectionsUtils.intertwine(A, B));
  }

  @Test
  public void test_equals_true() {
    List<String> l1 = new ArrayList<>(),
        l2 = new ArrayList<>();
    l1.add("a");
    l1.add("b");
    l1.add("c");

    l2.add("c");
    l2.add("a");
    l2.add("b");

    assertTrue(CollectionsUtils.equals(l1, l2));
  }

  @Test
  public void test_equals_false() {
    List<String> l1 = new ArrayList<>(),
        l2 = new ArrayList<>();
    l1.add("a");
    l1.add("b");
    l1.add("c");

    l2.add("a");
    l2.add("b");
    l2.add("c");
    l2.add("d");

    assertFalse(CollectionsUtils.equals(l1, l2));
  }
}
