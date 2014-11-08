package pt.it.av.atnog.utils.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by mantunes on 11/6/14.
 */
public class ThreadPool {
    private int nCores = Runtime.getRuntime().availableProcessors();
    private Thread workers[] = new Thread[nCores];
    private BlockingQueue qIn = new LinkedBlockingQueue<Object>();
    private BlockingQueue qOut = new LinkedBlockingQueue<Object>();
    private Task t;

    public ThreadPool(Task t) {
        this.t = t;
        for (int i = 0; i < nCores; i++) {
            workers[i] = new Thread(new Worker());
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

        for (Thread worker : workers)
            worker.join();
    }

    protected class Worker implements Runnable {
        public void run() {
            boolean done = false;
            List<Object> out = new ArrayList<Object>();
            while (!done) {
                Object in = null;
                try {
                    in = qIn.take();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!(in instanceof Stop)) {
                    t.process(in, out);
                    try {
                        for (Object o : out)
                            qOut.put(o);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    done = true;
                }
            }
        }
    }
}
