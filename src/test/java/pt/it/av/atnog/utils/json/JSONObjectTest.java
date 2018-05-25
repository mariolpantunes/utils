package pt.it.av.atnog.utils.json;

import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

/**
 * {@link JSONObject} unit tests.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class JSONObjectTest {
  @Test
  public void test_empty() throws IOException {
    JSONObject j = JSONObject.read(new StringReader("{}"));
    StringWriter w = new StringWriter();
    j.write(w);
    assertEquals(w.toString(), "{}");
  }

  @Test
  public void test_string() throws IOException {
    JSONObject j = JSONObject.read(new StringReader("{\"name\":\"value\"}"));
    StringWriter w = new StringWriter();
    j.write(w);
    assertEquals(w.toString(), "{\"name\":\"value\"}");
  }

  @Test
  public void test_number() throws IOException {
    JSONObject j = JSONObject.read(new StringReader("{\"a\":15.5,\"b\":3,\"c\":10000}"));
    StringWriter w = new StringWriter();
    j.write(w);
    assertEquals(w.toString(), "{\"a\":15.5,\"b\":3,\"c\":10000}");
  }

  @Test
  public void test_boolean() throws IOException {
    JSONObject j = JSONObject.read(new StringReader("{\"name\":true}"));
    StringWriter w = new StringWriter();
    j.write(w);
    assertEquals(w.toString(), "{\"name\":true}");
  }

  @Test
  public void test_null() throws IOException {
    JSONObject j = JSONObject.read(new StringReader("{\"name\":null}"));
    StringWriter w = new StringWriter();
    j.write(w);
    assertEquals(w.toString(), "{\"name\":null}");
  }

  @Test
  public void test_small() throws IOException {
    JSONObject a = JSONObject.read(new StringReader("{\"friends\":[{\"id\":1,\"name\":\"Corby Page\"},{\"id\":2,\"name\":\"Carter Page\"}]}"));
    JSONObject b = new JSONObject();
    JSONArray array = new JSONArray();
    JSONObject id1 = new JSONObject(), id2 = new JSONObject();
    id1.put("id", 1);
    id1.put("name", "Corby Page");
    id2.put("id", 2);
    id2.put("name", "Carter Page");
    array.add(id1);
    array.add(id2);
    b.put("friends", array);
    assertEquals(a, b);
  }
}
