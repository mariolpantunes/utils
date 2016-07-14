package pt.it.av.atnog.utils.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Worker implements Runnable {
    private Task t;
    private BlockingQueue<Object> sink, source;
    private Thread thread;

    public Worker(Task t) {
        this(t, null, null);
    }

    public Worker(Task t, BlockingQueue<Object> qIn, BlockingQueue<Object> qOut) {
        this.t = t;
        this.sink = qIn;
        this.source = qOut;
    }

    public void connect(BlockingQueue<Object> sink, BlockingQueue<Object> source) {
        this.sink = sink;
        this.source = source;
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void join() throws InterruptedException {
        thread.join();
    }

    @Override
    public void run() {
        boolean done = false;
        List<Object> out = new ArrayList<>();
        while (!done) {
            Object in = null;
            try {
                in = sink.take();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!(in instanceof Stop)) {
                t.process(in, out);
                if (!out.isEmpty()) {
                    try {
                        for (Object o : out)
                            source.put(o);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    out.clear();
                }
            } else
                done = true;
        }
    }
}