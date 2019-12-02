package pt.it.av.tnav.utils.json;

import java.io.IOException;
import java.io.Writer;

/**
 * JSON Value.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public interface JSONValue {
  /**
   * @param w
   * @throws IOException
   */
  void write(Writer w) throws IOException;

  /**
   * @return
   */
  default JSONObject asObject() {
    return (JSONObject) this;
  }

  /**
   * @return
   */
  default JSONArray asArray() {
    return (JSONArray) this;
  }

  /**
   * @return
   */
  default String asString() {
    return ((JSONString) this).s;
  }

  /**
   * @return
   */
  default double asNumber() {
    return ((JSONNumber) this).n;
  }

  /**
   * @return
   */
  default int asInt() {
    return (int) ((JSONNumber) this).n;
  }

  /**
   * @return
   */
  default long asLong() {
    return (long) ((JSONNumber) this).n;
  }

  /**
   * @return
   */
  default float asFloat() {
    return (float) ((JSONNumber) this).n;
  }

  /**
   * @return
   */
  default double asDouble() {
    return asNumber();
  }

  /**
   * @return
   */
  default boolean asBoolean() {
    return ((JSONBoolean) this).b;
  }

  /**
   * @return
   */
  default boolean isObject() {
    return this instanceof JSONObject;
  }

  /**
   * @return
   */
  default boolean isArray() {
    return this instanceof JSONArray;
  }

  /**
   * @return
   */
  default boolean isString() {
    return this instanceof JSONString;
  }
}
