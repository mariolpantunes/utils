package pt.it.av.atnog.utils.parallel;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Pipeline {
    private final BlockingQueue<Object> sink = new LinkedBlockingQueue<>(),
            source = new LinkedBlockingQueue<>();
    private final Deque<Worker> workers = new ArrayDeque<>();
    private final List<BlockingQueue<Object>> queues;

    /**
     *
     */
    public Pipeline() {
        queues = new ArrayList<BlockingQueue<Object>>();
    }

    /**
     * @param task
     */
    public void addFirst(Task task) {
        workers.addFirst(new Worker(task));
    }

    /**
     *
     * @param task
     */
    public void addLast(Task task) {
        workers.addLast(new Worker(task));
    }

    /**
     *
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
     */
    public void start() {
        if (workers.size() > 0) {
            if (queues.size() != workers.size() - 1) {
                queues.clear();
                for (int i = 0; i < workers.size() - 1; i++)
                    queues.add(new LinkedBlockingQueue<Object>());
            }
            Iterator<Worker> it = workers.iterator();
            Worker w = it.next();
            if (workers.size() > 1) {
                w.connect(sink, queues.get(0));
                w.start();
                for (int i = 1; it.hasNext(); i++) {
                    w = it.next();
                    if (it.hasNext())
                        w.connect(queues.get(i - 1), queues.get(i));
                    else
                        w.connect(queues.get(queues.size() - 1), source);
                    w.start();
                }
            } else {
                w.connect(sink, source);
                w.start();
            }
        }
    }

    /**
     *
     * @throws InterruptedException
     */
    public void join() throws InterruptedException {
        Stop stop = new Stop();
        sink.put(stop);
        Iterator<Worker> it = workers.iterator();
        for (int i = 0; i < queues.size(); i++) {
            it.next().join();
            queues.get(i).put(stop);
        }
        it.next().join();
        source.add(stop);
    }
}