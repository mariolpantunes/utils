package pt.it.av.atnog.utils.parallel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by mantunes on 11/6/14.
 */
public class ThreadPool {
    private int nCores = Runtime.getRuntime().availableProcessors();
    private Worker workers[] = new Worker[nCores];
    private BlockingQueue qIn = new LinkedBlockingQueue<Object>();
    private BlockingQueue qOut = new LinkedBlockingQueue<Object>();
    private Task t;

    public ThreadPool(Task t) {
        this.t = t;
        for (int i = 0; i < nCores; i++) {
            workers[i] = new Worker(t, qIn, qOut);
            workers[i].start();
        }
    }

    public void add(Object o) throws Exception {
        qIn.put(o);
    }

    public void done() throws InterruptedException {
        Stop stop = new Stop();

        for (int i = 0; i < nCores; i++)
            qIn.put(stop);

        for (Worker worker : workers)
            worker.join();
    }
}
