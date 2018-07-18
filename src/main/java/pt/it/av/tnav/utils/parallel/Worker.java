package pt.it.av.tnav.utils.parallel;

import pt.it.av.tnav.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Worker<I, O> implements Runnable {
  protected static final Stop stop = new Stop();
  private final Function<I, O> t;
  private BlockingQueue<Object> sink, source;
  private Thread thread;

  public Worker(Function<I, O> t) {
    this(t, null, null);
  }

  public Worker(Function<I, O> t, BlockingQueue<Object> qIn, BlockingQueue<Object> qOut) {
    this.t = t;
    this.sink = qIn;
    this.source = qOut;
  }

  /**
   * @return
   */
  public static Stop stop() {
    return stop;
  }

  public void connect(BlockingQueue<Object> sink, BlockingQueue<Object> source) {
    this.sink = sink;
    this.source = source;
  }

  public void start() {
    thread = new Thread(this);
    thread.start();
  }

  public void join() throws InterruptedException {
    thread.join();
  }

  @Override
  public void run() {
    boolean done = false;
    List<O> out = new ArrayList<>();
    while (!done) {
      Object in = null;
      try {
        in = sink.take();
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (!in.equals(stop)) {
        t.process(Utils.cast(in), out);
        if (!out.isEmpty()) {
          try {
            for (Object o : out)
              source.put(o);
          } catch (Exception e) {
            e.printStackTrace();
          }
          out.clear();
        }
      } else {
        done = true;
      }
    }
  }

  /**
   * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
   * @version 1.0
   */
  public static final class Stop {

    /**
     *
     */
    private Stop() {
    }

    @Override
    public boolean equals(Object o) {
      boolean rv = false;
      // null check
      if (o != null) {
        if (this == o) {
          // self check
          rv = true;
        } else if (getClass() == o.getClass()) {
          // type check and cast
          rv = true;
        }
      }
      return rv;
    }

    @Override
    public int hashCode() {
      return 31;
    }

    @Override
    public String toString() {
      return "STOP";
    }
  }
}