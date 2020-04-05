package pt.it.av.tnav.utils.parallel.threadPool;

import pt.it.av.tnav.utils.parallel.Function;
import pt.it.av.tnav.utils.parallel.IdleWorker;
import pt.it.av.tnav.utils.parallel.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DynamicThreadPool<I, O> implements ThreadPool<I, O>, Runnable {
  private static final long MAX_IDLE_TIME = 60 * 1000;
  //private final Function<I, O> t;
  private final int nWorkers;
  private final long maxIdleTime;
  private final BlockingQueue<Object> sink = new LinkedBlockingQueue<>(), source = new LinkedBlockingQueue<>();
  private final BlockingQueue<IdleWorker<I, O>> freeWorkers = new LinkedBlockingQueue<>(),
      idleWorkers = new LinkedBlockingQueue<>();
  private final List<IdleWorker<I, O>> workers = new ArrayList<>();
  private Thread thr;

  /**
   * @param t
   */
  public DynamicThreadPool(final Function<I, O> t) {
    this(t, Runtime.getRuntime().availableProcessors(), MAX_IDLE_TIME);
  }

  /**
   * @param t
   * @param maxIdleTime
   */
  public DynamicThreadPool(final Function<I, O> t, final long maxIdleTime) {
    this(t, Runtime.getRuntime().availableProcessors(), maxIdleTime);
  }

  /**
   * @param t
   * @param nWorkers
   * @param maxIdleTime
   */
  public DynamicThreadPool(final Function<I, O> t, final int nWorkers, final long maxIdleTime) {
    //this.t = t;
    this.nWorkers = nWorkers;
    this.maxIdleTime = maxIdleTime;
    for (int i = 0; i < nWorkers; i++) {
      IdleWorker<I, O> iw = new IdleWorker<>(t, source, freeWorkers);
      idleWorkers.add(iw);
      workers.add(iw);
    }
  }

  @Override
  public void start() {
    thr = new Thread(this);
    thr.start();
  }

  @Override
  public BlockingQueue<Object> sink() {
    return sink;
  }

  @Override
  public BlockingQueue<Object> source() {
    return source;
  }

  @Override
  public void join() throws InterruptedException {
    sink.put(Worker.stop());
    thr.join();

    for (IdleWorker<I, O> worker : workers) {
      worker.join();
    }

    source.add(Worker.stop());
  }

  @Override
  public int nWorkers() {
    return nWorkers;
  }

  @Override
  public int aWorkers() {
    int rv = 0;
    for (final IdleWorker<I, O> worker : workers) {
      if (worker.isActive()) {
        rv++;
      }
    }
    return rv;
  }

  @Override
  public void run() {
    boolean done = false;
    while (!done) {
      Object in = null;
      try {
        in = sink.poll(maxIdleTime / 2, TimeUnit.MILLISECONDS);
        if (in != null) {
          if (!in.equals(Worker.stop())) {
            IdleWorker<I, O> w = null;
            if (freeWorkers.isEmpty() && !idleWorkers.isEmpty()) {
              // Grab a idle worker
              w = idleWorkers.take();
              w.start();
            } else {
              // Wait for a free worker
              try {
                w = freeWorkers.take();
              } catch (final Exception e) {
                e.printStackTrace();
              }
            }
            w.addWork(in);
          } else {
            done = true;
          }
        }
        // Free idle workers
        final List<IdleWorker<I, O>> freeWorkerList = new ArrayList<>(freeWorkers.size());
        while (!freeWorkers.isEmpty()) {
          final IdleWorker<I, O> iw = freeWorkers.take();
          if (iw.idle() >= maxIdleTime) {
            iw.join();
            idleWorkers.add(iw);
          } else {
            freeWorkerList.add(iw);
          }
        }
        freeWorkers.addAll(freeWorkerList);

      } catch (final Exception e) {
        e.printStackTrace();
        done = true;
      }
    }
  }
}