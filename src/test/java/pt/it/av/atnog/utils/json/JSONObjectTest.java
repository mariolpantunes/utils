package pt.it.av.atnog.utils.json;

import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;

/**
 * Created by mantunes on 24/05/2015.
 */
public class JSONObjectTest {
    @Test
    public void test_empty() throws IOException {
        JSONObject j = JSONObject.read(new StringReader("{}"));
        StringWriter w = new StringWriter();
        j.write(w);
        assertTrue(w.toString().equals("{}"));
    }

    @Test
    public void test_string() throws IOException {
        JSONObject j = JSONObject.read(new StringReader("{\"name\":\"value\"}"));
        StringWriter w = new StringWriter();
        j.write(w);
        assertTrue(w.toString().equals("{\"name\":\"value\"}"));
    }

    @Test
    public void test_number() throws IOException {
        JSONObject j = JSONObject.read(new StringReader("{\"name\":15.5}"));
        StringWriter w = new StringWriter();
        j.write(w);
        assertTrue(w.toString().equals("{\"name\":15.5}"));
    }

    @Test
    public void test_boolean() throws IOException {
        JSONObject j = JSONObject.read(new StringReader("{\"name\":true}"));
        StringWriter w = new StringWriter();
        j.write(w);
        assertTrue(w.toString().equals("{\"name\":true}"));
    }

    @Test
    public void test_null() throws IOException {
        JSONObject j = JSONObject.read(new StringReader("{\"name\":null}"));
        StringWriter w = new StringWriter();
        j.write(w);
        assertTrue(w.toString().equals("{\"name\":null}"));
    }

    @Test
    public void test_small() throws IOException {
        JSONObject j = JSONObject.read(new StringReader("{\"friends\":[{\"id\":1,\"name\":\"Corby Page\"},{\"id\":2,\"name\":\"Carter Page\"}]}"));
        StringWriter w = new StringWriter();
        j.write(w);
        assertTrue(w.toString().equals("{\"friends\":[{\"id\":1,\"name\":\"Corby Page\"},{\"id\":2,\"name\":\"Carter Page\"}]}"));
    }
}
