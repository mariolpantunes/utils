package pt.it.av.tnav.utils.csv;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CSVTest {
    @Test
    public void test_empty() throws IOException {
        CSV c = CSV.read(new StringReader(""));
        StringWriter w = new StringWriter();
        c.write(w);
        assertEquals(w.toString(), "");
    }

    @Test
    public void test_single_field() throws IOException {
        CSV c = CSV.read(new StringReader("field"));
        StringWriter w = new StringWriter();
        c.write(w);
        assertEquals(w.toString(), "field");
    }
}
