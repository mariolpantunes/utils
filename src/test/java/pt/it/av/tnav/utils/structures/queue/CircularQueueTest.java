package pt.it.av.tnav.utils.structures.queue;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link CircularQueue}.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class CircularQueueTest {
  private static String data3[] = {"A", "B", "C"};
  private static String data5[] = {"A", "B", "C", "D", "E"};

  @Test
  public void test_insert_full() {
    Queue<String> queue = new CircularQueue<>(3);
    for (String datum : data3) {
      queue.add(datum);
    }

    Iterator<String> it = queue.iterator();
    int i = 0;
    while (it.hasNext()) {
      assertTrue(it.next().equals(data3[i++]));
    }
    assertTrue(i == queue.size());
  }

  @Test
  public void test_insert_overfull() {
    Queue<String> queue = new CircularQueue<>(3);
    for (String datum : data5) {
      queue.add(datum);
    }

    Iterator<String> it = queue.iterator();
    int i = 0;
    while (it.hasNext()) {
      assertTrue(it.next().equals(data5[i++ + (data5.length - queue.size())]));
    }
    assertTrue(i == queue.size());
  }

  @Test(expected = NoSuchElementException.class)
  public void test_middle_empty() {
    CircularQueue<String> q = new CircularQueue<>(3);
    q.middle();
  }

  @Test(expected = NoSuchElementException.class)
  public void test_middle_array_empty() {
    CircularQueue<String> q = new CircularQueue<>(3);
    String array[] = new String[1];
    q.middle(array);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_middle_array_parity() {
    CircularQueue<String> q = new CircularQueue<>(3);
    q.add("A");
    q.add("B");
    q.add("C");
    String array[] = new String[2];
    q.middle(array);
  }

  @Test
  public void test_middle_odd() {
    CircularQueue<String> q = new CircularQueue<>(3);
    q.add("A");
    assertTrue(q.middle().equals("A"));
    q.add("B");
    q.add("C");
    assertTrue(q.middle().equals("B"));
  }

  @Test
  public void test_middle_even() {
    CircularQueue<String> q = new CircularQueue<>(3);
    q.add("A");
    q.add("B");
    assertTrue(q.middle().equals("B"));
  }

  @Test
  public void test_middle_array_odd() {
    CircularQueue<String> q = new CircularQueue<>(5);
    q.add("A");
    String array[] = new String[1];
    q.middle(array);
    String r1[] = {"A"};
    assertTrue(Arrays.equals(array, r1));
    q.add("B");
    q.add("C");
    array = new String[3];
    q.middle(array);
    String r2[] = {"A", "B", "C"};
    assertTrue(Arrays.equals(array, r2));
  }

  @Test
  public void test_middle_array_even() {
    CircularQueue<String> q = new CircularQueue<>(5);
    q.add("A");
    q.add("B");
    String array[] = new String[2];
    q.middle(array);
    String r1[] = {"A", "B"};
    assertTrue(Arrays.equals(array, r1));
    q.add("C");
    q.add("D");
    array = new String[4];
    q.middle(array);
    String r2[] = {"A", "B", "C", "D"};
    assertTrue(Arrays.equals(array, r2));
  }
}
