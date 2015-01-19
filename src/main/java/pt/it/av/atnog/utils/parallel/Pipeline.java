package pt.it.av.atnog.utils.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Pipeline {
    private BlockingQueue<Object> sink = new LinkedBlockingQueue<Object>(), source = new LinkedBlockingQueue<Object>();
    private List<Worker> workers = new ArrayList<Worker>();
    private List<BlockingQueue<Object>> queues;

    public Pipeline() {
        queues = new ArrayList<BlockingQueue<Object>>();
    }

    public void add(Task task) {
        workers.add(new Worker(task));
    }

    public BlockingQueue<Object> sink() {
        return sink;
    }

    public BlockingQueue<Object> source() {
        return source;
    }

    public void start() {
        if (workers.size() > 0) {
            if (queues.size() != workers.size() - 1) {
                queues.clear();
                for (int i = 0; i < workers.size() - 1; i++)
                    queues.add(new LinkedBlockingQueue<Object>());
            }

            if (workers.size() > 1) {
                workers.get(0).connect(sink, queues.get(0));

                for (int i = 1; i < workers.size() - 1; i++)
                    workers.get(i).connect(queues.get(i - 1), queues.get(i));

                workers.get(workers.size() - 1).connect(
                        queues.get(queues.size() - 1), source);
            } else {
                workers.get(0).connect(sink, source);
            }

            for (Worker w : workers)
                w.start();
        }
    }

    public void join() throws InterruptedException {
        sink.put(new Stop());
        for (Worker w : workers)
            w.join();
    }
}