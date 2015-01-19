package pt.it.av.atnog.utils.parallel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by mantunes on 11/6/14.
 */
public class ThreadPool {
    private int nCores = Runtime.getRuntime().availableProcessors();
    private Worker workers[] = new Worker[nCores];
    private BlockingQueue<Object> sink = new LinkedBlockingQueue<>(), source = new LinkedBlockingQueue<>();
    private Task t;

    public ThreadPool(Task t) {
        this.t = t;
    }

    public void start() {
        for (int i = 0; i < nCores; i++) {
            workers[i] = new Worker(t, sink, source);
            workers[i].start();
        }
    }

    public BlockingQueue<Object> sink() {
        return sink;
    }

    public BlockingQueue<Object> source() {
        return source;
    }

    public void join() throws InterruptedException {
        Stop stop = new Stop();
        for (int i = 0; i < nCores; i++)
            sink.put(stop);
        for (Worker worker : workers)
            worker.join();
    }
}
