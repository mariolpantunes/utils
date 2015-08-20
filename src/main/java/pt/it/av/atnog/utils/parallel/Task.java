package pt.it.av.atnog.utils.parallel;

import java.util.List;

public interface Task {
    void process(Object in, List<Object> out);
}
