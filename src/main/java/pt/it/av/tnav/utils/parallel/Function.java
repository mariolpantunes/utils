package pt.it.av.tnav.utils.parallel;

import java.util.List;

/**
 * This interface allows the user to specific a function that will be executed in parallel.
 *
 * @param <I> the type of the input object
 * @param <O> The type of the output {@link List}
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public interface Function<I, O> {
    /**
     * @param in
     * @param out
     */
    void process(I in, List<O> out);
}
