package pt.it.av.atnog.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by mantunes on 5/4/15.
 */
public class StringUtilsTest {
    @Test
    public void test_count() {
        assertTrue(StringUtils.count("banana", 'b') == 1);
        assertTrue(StringUtils.count("banana", 'a') == 3);
        assertTrue(StringUtils.count("banana", 'n') == 2);
    }

    @Test
    public void test_indexOf() {
        List<Integer> r1 = new ArrayList<>(), r2 = new ArrayList<>(), r3 = new ArrayList<>();
        r1.add(0);
        r2.add(1);
        r2.add(3);
        r2.add(5);
        r3.add(2);
        r3.add(4);
        assertTrue(StringUtils.indexOf("banana", 'b').equals(r1));
        assertTrue(StringUtils.indexOf("banana", 'a').equals(r2));
        assertTrue(StringUtils.indexOf("banana", 'n').equals(r3));
    }

    @Test
    public void test_escape() {
        String origial = "\"banana\"", escaped = "\\\"banana\\\"";
        assertTrue(escaped.equals(StringUtils.escape(origial)));
    }

    @Test
    public void test_unescape() {
        String origial = "\"banana\"", escaped = "\\\"banana\\\"";
        assertTrue(origial.equals(StringUtils.unescape(escaped)));
    }

    @Test
    public void test_levenshtein() {
        assertTrue(StringUtils.levenshtein("kitten", "sitting") == 3);
        assertTrue(StringUtils.levenshtein("book", "back") == 2);
        assertTrue(StringUtils.levenshtein("book", "") == 4);
        assertTrue(StringUtils.levenshtein("zeil", "trials") == 4);
    }
}
