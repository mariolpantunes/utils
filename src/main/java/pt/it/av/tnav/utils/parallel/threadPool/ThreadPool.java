package pt.it.av.tnav.utils.parallel.threadPool;

import java.util.concurrent.BlockingQueue;

public interface ThreadPool {

  /**
   *
   */
  void start();

  /**
   * @return
   */
  BlockingQueue<Object> sink();

  /**
   * @return
   */
  BlockingQueue<Object> source();

  /**
   * @throws InterruptedException
   */
  void join() throws InterruptedException;

  /**
   * @return
   */
  int nWorkers();

  /**
   * @return
   */
  int aWorkers();
}
