package pt.it.av.tnav.utils.structures;

import pt.it.av.tnav.utils.json.JSONObject;

/**
 * This interface allows to dump and load and object from a
 * {@link pt.it.av.tnav.utils.json.JSONObject}.
 * <p>
 * The resulting object can be written to a text file, or sent remotely.
 * </p>
 *
 * @param <T> the type of objects that will be dumped and loaded
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public interface JSONify<T> {
  /**
   * Dumps the object {@link T} into a {@link JSONObject}.
   *
   * @return a {@link JSONObject} that represents the object {@link T}.
   */
  JSONObject jsonDump();

  /**
   * Loads a {@link json} {@link JSONObject} into a object {@link T}.
   *
   * @param json object {@link T} encoded into a {@link JSONObject}
   * @return the object {@link T} represented by the {@link json} {@link JSONObject}
   */
  T jsonLoad(JSONObject json);
}
