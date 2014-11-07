package pt.it.av.atnog.utils.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Filter implements Runnable {
    private Task t;
    private AtomicBoolean running;
    private BlockingQueue<Object> qIn, qOut;
    private Thread thread;

    public Filter() {
        running = new AtomicBoolean(false);
    }

    public void connect(BlockingQueue<Object> in, BlockingQueue<Object> out) {
        if (!running.get()) {
            this.qIn = in;
            this.qOut = out;
        }
    }

    public void start() {
        if (!running.getAndSet(true)) {
            thread = new Thread(this);
            thread.start();
        }
    }

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
                if (out.size() > 0) {
                    try {
                        for (Object o : out)
                            qOut.put(o);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    out.clear();
                }
            } else {
                try {
                    qOut.put(in);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    done = true;
                }
            }

        }
    }

    public void join() throws InterruptedException {
        if (running.get()) {
            try {
                thread.join();
                running.set(false);
            } catch (InterruptedException e) {
                throw e;
            } finally {
                running.set(false);
            }
        }
    }
}