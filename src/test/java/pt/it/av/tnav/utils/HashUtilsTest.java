package pt.it.av.tnav.utils;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link HashUtils}.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class HashUtilsTest {

  @Test
  public void test_fnv1a_empty() {
    long hash = HashUtils.fnv1a("".getBytes(StandardCharsets.US_ASCII));
    assertEquals(2166136261L, hash);
  }

  @Test
  public void test_jenkins_empty() {
    long hash = HashUtils.jenkins("".getBytes(StandardCharsets.US_ASCII));
    assertEquals(0, hash);
  }
}
