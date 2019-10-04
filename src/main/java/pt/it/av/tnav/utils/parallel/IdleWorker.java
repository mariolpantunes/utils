package pt.it.av.tnav.utils.parallel;

import pt.it.av.tnav.utils.Utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class IdleWorker<I, O> implements Runnable {
  private final Function<I, O> t;
  private final BlockingQueue<Object> source;
  private final BlockingQueue<IdleWorker> freeWorkers;
  private final Semaphore mutex = new Semaphore(1), slots = new Semaphore(1),
      objs = new Semaphore(0), idle = new Semaphore(1);
  private long ts = 0;
  private Object parameters = null;
  private Thread thread;

  /**
   * @param t
   * @param source
   * @param freeWorkers
   */
  public IdleWorker(Function<I, O> t, BlockingQueue<Object> source, BlockingQueue<IdleWorker> freeWorkers) {
    this.t = t;
    this.source = source;
    this.freeWorkers = freeWorkers;
  }

  /**
   * @param in
   * @throws InterruptedException
   */
  public void addWork(final Object parameters) throws InterruptedException {
    slots.acquire();
    mutex.acquire();
    this.parameters = parameters;
    mutex.release();
    objs.release();
  }

  /**
   * @return
   * @throws InterruptedException
   */
  private Object getWork() throws InterruptedException {
    Object rv = null;
    objs.acquire();
    mutex.acquire();
    rv = this.parameters;
    mutex.release();
    slots.release();
    return rv;
  }

  /**
   *
   */
  public void start() {
    ts = Instant.now().toEpochMilli();
    thread = new Thread(this);
    thread.start();
  }

  /**
   * @throws InterruptedException
   */
  public void join() throws InterruptedException {
    if (isActive()) {
      addWork(Worker.stop);
      thread.join();
      thread = null;
    }
  }

  /**
   * @return
   */
  public boolean isActive() {
    return thread != null && thread.isAlive();
  }

  /**
   * @return
   * @throws InterruptedException
   */
  public long idle() throws InterruptedException {
    long rv = 0;
    idle.acquire();
    rv = Instant.now().toEpochMilli() - ts;
    idle.release();
    return rv;
  }

  @Override
  public void run() {
    boolean done = false;
    List<O> out = new ArrayList<>();
    while (!done) {
      try {
        Object in = getWork();
        if (!in.equals(Worker.stop)) {
          idle.acquire();
          ts = Instant.now().toEpochMilli();
          idle.release();
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
          freeWorkers.add(this);
        } else {
          done = true;
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
        done = true;
      }
    }
  }
}
