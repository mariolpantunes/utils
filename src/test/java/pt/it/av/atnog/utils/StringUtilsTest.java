package pt.it.av.atnog.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link StringUtils}.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class StringUtilsTest {
    /*@Test
    public void test_count() {
        assertTrue(StringUtils.count("banana", 'b') == 1);
        assertTrue(StringUtils.count("banana", 'a') == 3);
        assertTrue(StringUtils.count("banana", 'n') == 2);
    }*/

    /*@Test
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
    }*/

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

    /*@Test
    public void test_clauses() {
        String setence1 = "My estate goes to my husband, son, daughter-in-law, and nephew.";
        List<String> clauses1 = StringUtils.clauses(setence1);
        assertTrue(clauses1.size() == 1);
        assertTrue(clauses1.get(0).equals(setence1));

        String setence2 = "After he walked all the way home, he shut the door.";
        List<String> clauses2 = StringUtils.clauses(setence2);
        assertTrue(clauses2.size() == 2);
        assertTrue(clauses2.get(0).equals("After he walked all the way home"));
        assertTrue(clauses2.get(1).equals("he shut the door."));

        String setence3 = "I am, by the way, very nervous about this.";
        List<String> clauses3 = StringUtils.clauses(setence3);
        assertTrue(clauses3.size() == 2);
        assertTrue(clauses3.get(0).equals("I am very nervous about this."));
        assertTrue(clauses3.get(1).equals("by the way"));
    }*/
}
