package pt.it.av.atnog.utils;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by mantunes on 1/20/16.
 */
public class CollectionIteratorTest {
    private static List<String> A, B, C;

    @BeforeClass
    public static void setup() {
        A = new ArrayList<>();
        A.add("A");
        A.add("D");
        A.add("F");

        B = new ArrayList<>();
        B.add("B");
        B.add("E");

        C = new ArrayList<>();
        C.add("C");
    }

    @Test
    public void test_zero_iterators() {
        List<Iterator<String>> its = new ArrayList<>();
        CollectionIterator<String> it = new CollectionIterator<String>(its);
        assertTrue(!it.hasNext());
    }

    @Test
    public void test_one_iterator() {
        List<Iterator<String>> its = new ArrayList<>();
        List<String> rv = new ArrayList<>(), list;
        CollectionIterator<String> it;

        its.add(A.iterator());
        it = new CollectionIterator<String>(its);
        list = Utils.iterator2List(it);
        rv.add("A");
        rv.add("D");
        rv.add("F");

        assertTrue(list.equals(rv));

        its.clear();
        rv.clear();
        its.add(B.iterator());
        it = new CollectionIterator<>(its);
        list = Utils.iterator2List(it);
        rv.add("B");
        rv.add("E");

        assertTrue(list.equals(rv));

        its.clear();
        rv.clear();
        its.add(C.iterator());
        it = new CollectionIterator<>(its);
        list = Utils.iterator2List(it);
        rv.add("C");

        assertTrue(list.equals(rv));
    }

    @Test
    public void test_two_iterator() {
        List<Iterator<String>> its = new ArrayList<>();
        List<String> rv = new ArrayList<>(), list;
        CollectionIterator<String> it;

        its.add(A.iterator());
        its.add(B.iterator());
        it = new CollectionIterator<String>(its);
        list = Utils.iterator2List(it);
        rv.add("A");
        rv.add("B");
        rv.add("D");
        rv.add("E");
        rv.add("F");

        assertTrue(list.equals(rv));

        its.clear();
        rv.clear();
        its.add(B.iterator());
        its.add(A.iterator());
        it = new CollectionIterator<>(its);
        list = Utils.iterator2List(it);
        rv.add("B");
        rv.add("A");
        rv.add("E");
        rv.add("D");
        rv.add("F");

        assertTrue(list.equals(rv));

        its.clear();
        rv.clear();
        its.add(C.iterator());
        its.add(A.iterator());
        it = new CollectionIterator<>(its);
        list = Utils.iterator2List(it);
        rv.add("C");
        rv.add("A");
        rv.add("D");
        rv.add("F");

        assertTrue(list.equals(rv));
    }

    @Test
    public void test_three_iterator() {
        List<Iterator<String>> its = new ArrayList<>();
        List<String> rv = new ArrayList<>(), list;
        CollectionIterator<String> it;

        its.add(A.iterator());
        its.add(B.iterator());
        its.add(C.iterator());
        it = new CollectionIterator<String>(its);
        list = Utils.iterator2List(it);
        rv.add("A");
        rv.add("B");
        rv.add("C");
        rv.add("D");
        rv.add("E");
        rv.add("F");

        assertTrue(list.equals(rv));
    }
}
