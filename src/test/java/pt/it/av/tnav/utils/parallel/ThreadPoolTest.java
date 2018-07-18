package pt.it.av.tnav.utils.parallel;

import org.junit.Test;
import pt.it.av.tnav.utils.parallel.threadPool.DynamicThreadPool;
import pt.it.av.tnav.utils.parallel.threadPool.StaticThreadPool;
import pt.it.av.tnav.utils.parallel.threadPool.ThreadPool;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ThreadPoolTest {
  private static final int IT = 10;
  private static final int RV = 10;

  @Test(timeout = 3000)
  public void test_static_tp() {
    Function t = (Object o, List<Object> l) -> {
      Integer i = (Integer) o;
      l.add(i + RV);
    };

    ThreadPool tp = new StaticThreadPool(t);

    BlockingQueue<Object> source = tp.source();
    BlockingQueue<Object> sink = tp.sink();

    tp.start();

    assertEquals(tp.nWorkers(), tp.aWorkers());

    try {
      for (int i = 0; i < IT; i++) {
        sink.put(0);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      tp.join();
      int c = 0;
      boolean done = false;
      while (!done) {
        Object ob = source.take();
        if (!ob.equals(Worker.stop())) {
          Integer i = (Integer) ob;
          assertEquals(RV, (int) i);
          c++;
        } else {
          done = true;
        }
      }
      assertEquals(IT, c);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test(timeout = 3000)
  public void test_dynamic_tp() {
    Function t = (Object o, List<Object> l) -> {
      Integer i = (Integer) o;
      l.add(i + RV);
    };

    ThreadPool tp = new DynamicThreadPool(t);

    BlockingQueue<Object> source = tp.source();
    BlockingQueue<Object> sink = tp.sink();

    tp.start();

    assertEquals(0, tp.aWorkers());

    try {
      for (int i = 0; i < IT; i++) {
        sink.put(0);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      tp.join();
      boolean done = false;
      int c = 0;
      while (!done) {
        Object ob = source.take();
        if (!ob.equals(Worker.stop())) {
          Integer i = (Integer) ob;
          assertEquals(RV, (int) i);
          c++;
        } else {
          done = true;
        }
      }
      assertEquals(IT, c);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test(timeout = 3000)
  public void test_dynamic_tp_idle() {
    Function t = (Object o, List<Object> l) -> {
      Integer i = (Integer) o;
      l.add(i + RV);
    };

    ThreadPool tp = new DynamicThreadPool(t, 100);

    BlockingQueue<Object> source = tp.source();
    BlockingQueue<Object> sink = tp.sink();

    tp.start();

    assertEquals(0, tp.aWorkers());

    try {
      for (int i = 0; i < IT; i++) {
        sink.put(0);
        Thread.sleep(100);
        assertTrue(tp.aWorkers() < tp.nWorkers());
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      tp.join();
      boolean done = false;
      int c = 0;
      while (!done) {
        Object ob = source.take();
        if (!ob.equals(Worker.stop())) {
          Integer i = (Integer) ob;
          assertEquals(RV, (int) i);
          c++;
        } else {
          done = true;
        }
      }
      assertEquals(IT, c);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

