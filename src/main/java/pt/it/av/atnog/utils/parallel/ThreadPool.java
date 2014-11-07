package pt.it.av.atnog.utils.parallel;

import java.util.concurrent.SynchronousQueue;

/**
 * Created by mantunes on 11/6/14.
 */
public class ThreadPool {
    private int nCores = Runtime.getRuntime().availableProcessors();
    private Thread workers[] = new Thread[nCores];
    private SynchronousQueue queue = new SynchronousQueue();

    public void add(Object o) {
        queue.put(o);
    }

    public void done() {

    }

    protected class Worker implements Runnable {
        public Task t;

        public void run() {
            Object o = queue.poll();
            t.process(o);
        }
    }
}
