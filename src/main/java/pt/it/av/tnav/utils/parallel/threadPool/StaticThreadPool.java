package pt.it.av.tnav.utils.parallel.threadPool;

import pt.it.av.tnav.utils.Utils;
import pt.it.av.tnav.utils.parallel.Function;
import pt.it.av.tnav.utils.parallel.Worker;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class StaticThreadPool<I, O> implements ThreadPool<I, O> {
  //private final Function<I, O> t;
  private final int nWorkers;
  private final Worker<I, O> workers[];
  private final BlockingQueue<Object> sink = new LinkedBlockingQueue<>(),
      source = new LinkedBlockingQueue<>();

  /**
   * @param t
   */
  public StaticThreadPool(final Function<I, O> t) {
    this(t, Runtime.getRuntime().availableProcessors());
  }

  /**
   * @param t
   * @param nCores
   */
  public StaticThreadPool(final Function<I, O> t, int nWorkers) {
    //this.t = t;
    this.nWorkers = nWorkers;
    this.workers = Utils.cast(new Worker[nWorkers]);
    for (int i = 0; i < nWorkers; i++) {
      workers[i] = new Worker<I, O>(t, sink, source);
    }
  }

  @Override
  public void start() {
    for (int i = 0; i < nWorkers; i++) {
      workers[i].start();
    }
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
  public int nWorkers() {
    return nWorkers;
  }

  @Override
  public int aWorkers() {
    return nWorkers;
  }

  @Override
  public void join() throws InterruptedException {
    Object stop = Worker.stop();

    for (int i = 0; i < nWorkers; i++) {
      sink.put(stop);
    }

    for (Worker<I,O> worker : workers) {
      worker.join();
    }

    source.put(stop);
  }
}
