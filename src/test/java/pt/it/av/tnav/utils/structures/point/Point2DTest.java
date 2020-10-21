package pt.it.av.tnav.utils.structures.point;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pt.it.av.tnav.utils.csv.CSV;

public class Point2DTest {
    @Test
    public void test_csv_load() {
        final String csv = "0,0\r\n1,1\r\n2,2";
        List<Point2D> points = null;
        try {
            points = Point2D.csvLoad(CSV.read(new StringReader(csv)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Point2D> expected = new ArrayList<>();
        expected.add(new Point2D(0, 0));
        expected.add(new Point2D(1, 1));
        expected.add(new Point2D(2, 2));

        assertEquals(expected, points);
    }

    @Test
    public void test_csv_dump() {
        List<Point2D> points = new ArrayList<>();
        points.add(new Point2D(0, 0));
        points.add(new Point2D(1, 1));
        points.add(new Point2D(2, 2));

        CSV csv = new CSV();
        csv.addLines(points);
        StringWriter w = new StringWriter();
        try {
            csv.write(w);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String expected = "0.0,0.0\r\n1.0,1.0\r\n2.0,2.0";

        assertEquals(expected, w.toString());
    }
}
