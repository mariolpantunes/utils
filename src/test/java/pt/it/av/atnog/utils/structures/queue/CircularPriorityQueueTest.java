package pt.it.av.atnog.utils.structures.queue;

import org.junit.Test;

import java.util.Iterator;
import java.util.Queue;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link CircularPriorityQueue}.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class CircularPriorityQueueTest {
  private static String data3[] = {"A", "B", "C"};
  private static String data5[] = {"A", "B", "C", "D", "E"};

  @Test
  public void test_insert_full() {
    Queue<String> queue = new CircularPriorityQueue<>(3);
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
    Queue<String> queue = new CircularPriorityQueue<>(3);
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
}
