package pt.it.av.tnav.utils.structures.iterator;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Iterator;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link BRIterator}
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class BRIteratorTest {
    private static BufferedReader br0, br1, brn;


    @BeforeClass
    public static void setup() {
        br0 = new BufferedReader(new StringReader(""));
        br1 = new BufferedReader(new StringReader("single"));
        brn = new BufferedReader(new StringReader("one\ntwo\nthree"));
    }

    @Test
    public void test_empty_reader() {
        Iterator<String> it = new BRIterator(br0);
        assertTrue(it.hasNext() == false);
        assertTrue(it.next() == null);
    }

    @Test
    public void test_single_line_reader() {
        Iterator<String> it = new BRIterator(br1);
        assertTrue(it.hasNext() == true);
        String line = it.next();
        assertTrue(line.equals("single"));
        assertTrue(it.hasNext() == false);
        assertTrue(it.next() == null);
    }

    @Test
    public void test_n_lines_reader() {
        Iterator<String> it = new BRIterator(brn);
        assertTrue(it.hasNext() == true);
        String line = it.next();
        assertTrue(line.equals("one"));
        assertTrue(it.hasNext() == true);
        line = it.next();
        assertTrue(line.equals("two"));
        assertTrue(it.hasNext() == true);
        line = it.next();
        assertTrue(line.equals("three"));
        assertTrue(it.hasNext() == false);
        assertTrue(it.next() == null);
    }
}
