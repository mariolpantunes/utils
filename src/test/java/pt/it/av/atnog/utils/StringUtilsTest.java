package pt.it.av.atnog.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by mantunes on 5/4/15.
 */
public class StringUtilsTest {
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
