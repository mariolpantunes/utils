package pt.it.av.tnav.utils.parallel.threadPool;

import pt.it.av.tnav.utils.parallel.Function;
import pt.it.av.tnav.utils.parallel.IdleWorker;
import pt.it.av.tnav.utils.parallel.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DynamicThreadPool implements ThreadPool, Runnable {
  private static final long MAX_IDLE_TIME = 60 * 1000;
  private final Function t;
  private final int nWorkers;
  private final long maxIdleTime;
  private final BlockingQueue<Object> sink = new LinkedBlockingQueue<>(),
      source = new LinkedBlockingQueue<>();
  private final BlockingQueue<IdleWorker> freeWorkers = new LinkedBlockingQueue<>(),
      idleWorkers = new LinkedBlockingQueue<>();
  private final IdleWorker workers[];
  private Thread thr;


  /**
   * @param t
   */
  public DynamicThreadPool(final Function t) {
    this(t, Runtime.getRuntime().availableProcessors(), MAX_IDLE_TIME);
  }

  /**
   * @param t
   * @param maxIdleTime
   */
  public DynamicThreadPool(final Function t, final long maxIdleTime) {
    this(t, Runtime.getRuntime().availableProcessors(), maxIdleTime);
  }

  /**
   * @param t
   * @param nWorkers
   * @param maxIdleTime
   */
  public DynamicThreadPool(final Function t, final int nWorkers, final long maxIdleTime) {
    this.t = t;
    this.nWorkers = nWorkers;
    workers = new IdleWorker[nWorkers];
    this.maxIdleTime = maxIdleTime;
    for (int i = 0; i < nWorkers; i++) {
      IdleWorker iw = new IdleWorker(t, source, freeWorkers);
      idleWorkers.add(iw);
      workers[i] = iw;
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

    for (IdleWorker worker : workers) {
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
    for (IdleWorker worker : workers) {
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
            IdleWorker w = null;
            if (freeWorkers.isEmpty() && !idleWorkers.isEmpty()) {
              // Grab a idle worker
              w = idleWorkers.take();
              w.start();
            } else {
              // Wait for a free worker
              try {
                w = freeWorkers.take();
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
            w.addWork(in);
          } else {
            done = true;
          }
        }
        // Free idle workers
        List<IdleWorker> freeWorkerList = new ArrayList<>(freeWorkers.size());
        int i = 0;
        while (!freeWorkers.isEmpty()) {
          IdleWorker iw = freeWorkers.take();
          if (iw.idle() >= maxIdleTime) {
            iw.join();
            idleWorkers.add(iw);
          } else {
            freeWorkerList.add(iw);
          }
          i++;
        }
        freeWorkers.addAll(freeWorkerList);

      } catch (Exception e) {
        e.printStackTrace();
        done = true;
      }
    }
  }
}