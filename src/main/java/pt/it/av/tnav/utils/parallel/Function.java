package pt.it.av.tnav.utils.parallel;

import java.util.List;

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public interface Function {
    /**
     * @param in
     * @param out
     */
    void process(Object in, List<Object> out);
}
