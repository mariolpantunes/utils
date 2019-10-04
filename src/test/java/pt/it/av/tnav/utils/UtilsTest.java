package pt.it.av.tnav.utils;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link Utils}.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class UtilsTest {
  public static Integer z, o;

  @BeforeClass
  public static void setup() {
    z = new Integer(0);
    o = new Integer(1);
  }

  @Test
  public void test_max() {
    assertEquals(o, Utils.max(z,o));
    assertEquals(o, Utils.max(z,o, Comparator.naturalOrder()));
  }

  @Test
  public void test_min() {
    assertEquals(z, Utils.min(z,o));
    assertEquals(z, Utils.min(z,o, Comparator.naturalOrder()));
  }
}
