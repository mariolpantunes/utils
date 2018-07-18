package pt.it.av.tnav.utils.parallel.pipeline;

import pt.it.av.tnav.utils.parallel.Function;
import pt.it.av.tnav.utils.parallel.Worker;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implementation of a simple multitask pipeline.
 * <p>
 *   Each {@link Function} can be added to the head or tail of the pipeline.
 *   When the pipeline start it will run all the Tasks through the sequence where they were inserted.
 *   The Tasks run in parallel inside its own {@link Worker}.
 *   The Sink and Source {@link BlockingQueue} are used to dump and retrive values respectively.
 * </p>
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Pipeline {
    private final BlockingQueue<Object> sink = new LinkedBlockingQueue<>(),
            source = new LinkedBlockingQueue<>();
    private final Deque<Worker> workers = new ArrayDeque<>();
    private final List<BlockingQueue<Object>> queues;

    /**
     * Constructor for the Pipeline class.
     */
    public Pipeline() {
        queues = new ArrayList<BlockingQueue<Object>>();
    }

    /**
     * Adds a {@link Function} on the head of the sequence.
     *
     * @param function {@link Function} to be added on the head of the sequence.
     */
    public void addFirst(Function function) {
      workers.addFirst(new Worker(function));
    }

  /**
   * Adds a {@link Function} on the tail of the sequence.
   *
   * @param function {@link Function} to be added on the tail of the sequence.
   */
  public void addLast(Function function) {
    workers.addLast(new Worker(function));
    }

    /**
     * Returns the Sink {@link BlockingQueue}.
     * The Sink {@link BlockingQueue} is used to dump {@link Object} to process.
     *
     * @return Sink {@link BlockingQueue}.
     */
    public BlockingQueue<Object> sink() {
        return sink;
    }

    /**
     * Returns the Source {@link BlockingQueue}.
     * The Source {@link BlockingQueue} is used to retrieve processed {@link Object}.
     *
     * @return Source {@link BlockingQueue}.
     */
    public BlockingQueue<Object> source() {
        return source;
    }

    /**
     * Causes the {@link Pipeline} to begin execution.
     * Each {@link Worker} begins its execution and runs the process method of its {@link Function}.
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
     * Waits for the {@link Pipeline} to stop working.
     *
     * @throws InterruptedException  if any thread has interrupted the current {@link Worker}.
     * The interrupted status of the {@link Worker} is cleared when this exception is thrown.
     */
    public void join() throws InterruptedException {
      Worker.Stop stop = Worker.stop();
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