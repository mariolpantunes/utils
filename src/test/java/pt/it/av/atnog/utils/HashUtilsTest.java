package pt.it.av.atnog.utils;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertTrue;

public class HashUtilsTest {

  @Test
  public void test_fnv1a_empty() {
    long hash = HashUtils.fnv1a("".getBytes(StandardCharsets.US_ASCII));
    assertTrue(hash == 2166136261L);
  }

  @Test
  public void test_jenkins_empty() {
    long hash = HashUtils.jenkins("".getBytes(StandardCharsets.US_ASCII));
    assertTrue(hash == 0);
  }
}
