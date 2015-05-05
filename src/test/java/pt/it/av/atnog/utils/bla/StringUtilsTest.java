package pt.it.av.atnog.utils.bla;

import org.junit.Test;
import pt.it.av.atnog.utils.StringUtils;

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
}
