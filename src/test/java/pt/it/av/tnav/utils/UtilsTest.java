package pt.it.av.tnav.utils;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;

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
    z = Integer.valueOf(0);
    o = Integer.valueOf(1);
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
