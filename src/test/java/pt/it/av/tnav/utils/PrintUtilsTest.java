package pt.it.av.tnav.utils;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintUtilsTest extends TestCase {

    @Test
    public void test_object() {
        SomeClass someClass = new SomeClass();
        //@formatter:off
        String expected = "SomeClass:\n" +
                "{\n" +
                "stringField:stringValue\n" +
                "intValue:5\n" +
                "array:[\n" +
                " 0:0\n" +
                " 1:1\n" +
                " 2:2\n" +
                " 3:3\n" +
                " 4:4\n" +
                " 5:5\n" +
                " ]\n" +
                "list:[\n" +
                " 0:1\n" +
                " 1:2\n" +
                " 2:3\n" +
                " ]\n" +
                "map:{\n" +
                " key1:value1\n" +
                " key2:value2\n" +
                " }\n" +
                "innerClass:InnerClass:\n" +
                " {\n" +
                " stringField:stringValue\n" +
                " intValue:5\n" +
                " array:[\n" +
                "  0:0\n" +
                "  1:1\n" +
                "  2:2\n" +
                "  3:3\n" +
                "  4:4\n" +
                "  5:5\n" +
                "  ]\n" +
                " }\n" +
                "}";
        //@formatter:on
        String actual = PrintUtils.object(someClass);

        System.out.println(actual);
        assertEquals(expected, actual);
    }

    public static class SomeClass {

        private final String stringField = "stringValue";

        private final int intValue = 5;

        private final int[] array = new int[] {0, 1, 2, 3, 4, 5};

        private final List<Integer> list = new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(3);
            }
        };

        private final Map<String, String> map = new HashMap<String, String>() {
            {
                put("key1", "value1");
                put("key2", "value2");
            }
        };

        private final InnerClass innerClass = new InnerClass();
    }

    private static class InnerClass {

        private final String stringField = "stringValue";

        private final int intValue = 5;

        private final int[] array = new int[] {0, 1, 2, 3, 4, 5};
    }
}