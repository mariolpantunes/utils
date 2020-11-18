package pt.it.av.tnav.utils.csv;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CSVTest {

    @Test
    public void escape_textdata() {
        String input = "aaa", out = CSV.escape(input);
        assertEquals(input, out);
    }

    @Test
    public void escape_dqoute() {
        String input = "a\"a\"a", out = CSV.escape(input);
        assertEquals("\"a\"\"a\"\"a\"", out);
    }

    @Test
    public void escaped_comma() {
        String input = "a,a,a", out = CSV.escape(input);
        assertEquals("\"a,a,a\"", out);
    }

    @Test
    public void test_empty() throws IOException {
        String csv = "";
        CSV c = CSV.read(new StringReader(csv));
        StringWriter w = new StringWriter();
        c.write(w);
        assertEquals(csv, w.toString());
    }

    @Test
    public void test_single_field() throws IOException {
        String csv = "field";
        CSV c = CSV.read(new StringReader(csv));
        StringWriter w = new StringWriter();
        c.write(w);
        assertEquals(csv, w.toString());
    }

    @Test
    public void test_two_line_single_field() throws IOException {
        String csv = "field1\r\nfield2";
        CSV c = CSV.read(new StringReader(csv));
        StringWriter w = new StringWriter();
        c.write(w);
        assertEquals(csv, w.toString());
    }

    @Test
    public void test_two_fields() throws IOException {
        String csv = "field1,field2";
        CSV c = CSV.read(new StringReader(csv));
        StringWriter w = new StringWriter();
        c.write(w);
        assertEquals(csv, w.toString());
    }

    @Test
    public void test_two_lines_two_fields() throws IOException {
        String csv = "field1,field2\r\nfield1,field2";
        CSV c = CSV.read(new StringReader(csv));
        StringWriter w = new StringWriter();
        c.write(w);
        assertEquals(csv, w.toString());
    }

    @Test
    public void test_quoted_single_field() throws IOException {
        String csv = "\"field\"";
        CSV c = CSV.read(new StringReader(csv));
        StringWriter w = new StringWriter();
        c.write(w);
        assertEquals("field", w.toString());
    }

    @Test
    public void test_rfc() throws IOException {
        String csv = "\"aaa\",\"b \r\n bb\",\"ccc\"\r\nzzz,yyy,xxx";
        CSV c = CSV.read(new StringReader(csv));
        StringWriter w = new StringWriter();
        c.write(w);
        assertEquals("aaa,\"b \r\n bb\",ccc\r\nzzz,yyy,xxx", w.toString());
    }
}
