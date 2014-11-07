package pt.it.av.atnog.utils.parallel;

import java.util.List;

/**
 * Created by mantunes on 11/7/14.
 */
public interface Task {
    public void process(Object in, List<Object> out);
}
