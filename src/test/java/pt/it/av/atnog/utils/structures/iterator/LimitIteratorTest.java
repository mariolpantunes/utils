package pt.it.av.atnog.utils.structures.iterator;

import org.junit.BeforeClass;
import org.junit.Test;
import pt.it.av.atnog.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Mário Antunes
 * @version 1.0
 */
public class LimitIteratorTest {
    private static List<String> list;

    @BeforeClass
    public static void setup() {
        list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
    }

    @Test
    public void test_empty() {
        LimitIterator<String> it = new LimitIterator<String>(new ArrayList<String>().iterator(), 10);
        assertTrue(!it.hasNext());
    }

    @Test
    public void test_iterator() {
        LimitIterator<String> it = new LimitIterator<>(list.iterator(), 10);

        assertTrue(it.hasNext());
        List l = Utils.iterator2List(it);
        assertTrue(l.equals(list));
        assertTrue(!it.hasNext());
    }

    @Test
    public void test_limit() {
        LimitIterator<String> it = new LimitIterator<>(list.iterator(), 3);

        assertTrue(it.hasNext());
        List l = Utils.iterator2List(it);
        assertTrue(l.equals(list.subList(0,3)));
        assertTrue(!it.hasNext());
    }
}
