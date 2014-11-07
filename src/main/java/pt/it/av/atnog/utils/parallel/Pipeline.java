package pt.it.av.atnog.utils.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Pipeline {
    private AtomicBoolean running;
    private BlockingQueue<Object> sink, source;
    private List<Filter> filters;
    private List<BlockingQueue<Object>> queues;

    public Pipeline() {
        running = new AtomicBoolean(false);
        filters = new ArrayList<Filter>();
        queues = new ArrayList<BlockingQueue<Object>>();
        sink = new LinkedBlockingQueue<Object>();
        source = new LinkedBlockingQueue<Object>();
    }

    public void add(Filter filter) {
        if (!running.get() && filter != null) {
            filters.add(filter);
        }
    }

    public BlockingQueue<Object> sink() {
        return sink;
    }

    public BlockingQueue<Object> source() {
        return source;
    }

    public void start() {
        if (filters.size() > 0 && !running.getAndSet(true)) {
            if (queues.size() != filters.size() - 1) {
                queues.clear();
                for (int i = 0; i < filters.size() - 1; i++)
                    queues.add(new LinkedBlockingQueue<Object>());
            }

            if (filters.size() > 1) {
                filters.get(0).connect(sink, queues.get(0));

                for (int i = 1; i < filters.size() - 1; i++)
                    filters.get(i).connect(queues.get(i - 1), queues.get(i));

                filters.get(filters.size() - 1).connect(
                        queues.get(queues.size() - 1), source);
            } else {
                filters.get(0).connect(sink, source);
            }

            for (Filter f : filters)
                f.start();
        }
    }

    public void join() throws InterruptedException {
        if (running.get()) {
            for (Filter f : filters)
                f.join();
            running.set(false);
        }
    }

    public void stop() throws InterruptedException {
        if (running.get())
            sink.put(new Stop());
    }

    public void stopJoin() throws InterruptedException {
        if (running.get()) {
            sink.put(new Stop());
            for (Filter f : filters)
                f.join();
            running.set(false);
        }
    }
}