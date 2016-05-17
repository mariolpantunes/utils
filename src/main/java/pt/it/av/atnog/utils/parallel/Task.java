package pt.it.av.atnog.utils.parallel;

import java.util.List;

/**
 *
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public interface Task {
    void process(Object in, List<Object> out);
}
