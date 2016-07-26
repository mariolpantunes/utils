package pt.it.av.atnog.utils.parallel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class ThreadPool {
    private final int nCores;
    private final Worker workers[];
    private final BlockingQueue<Object> sink = new LinkedBlockingQueue<>(),
            source = new LinkedBlockingQueue<>();
    private final Task t;

    /**
     * @param t
     */
    public ThreadPool(final Task t) {
        this(t, Runtime.getRuntime().availableProcessors());
    }

    /**
     * @param t
     * @param nCores
     */
    public ThreadPool(final Task t, int nCores) {
        this.t = t;
        this.nCores = nCores;
        this.workers = new Worker[nCores];
    }

    /**
     *
     */
    public void start() {
        for (int i = 0; i < nCores; i++) {
            workers[i] = new Worker(t, sink, source);
            workers[i].start();
        }
    }

    /**
     * @return
     */
    public BlockingQueue<Object> sink() {
        return sink;
    }

    /**
     *
     * @return
     */
    public BlockingQueue<Object> source() {
        return source;
    }

    /**
     *
     * @throws InterruptedException
     */
    public void join() throws InterruptedException {
        Stop stop = new Stop();
        for (int i = 0; i < nCores; i++)
            sink.put(stop);
        for (Worker worker : workers)
            worker.join();
        source.add(stop);
    }
}
