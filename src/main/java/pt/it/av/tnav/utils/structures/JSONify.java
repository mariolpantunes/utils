package pt.it.av.tnav.utils.structures;

import pt.it.av.tnav.utils.json.JSONObject;

/**
 * This interface allows to dump and load and object from a
 * {@link pt.it.av.tnav.utils.json.JSONObject}.
 * <p>
 * The resulting object can be written to a text file, or sent remotelly.
 * </p>
 *
 * @param <T> the type of objects that will be dumped and loaded
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public interface JSONify<T> {
  JSONObject jsonDump();

  T jsonLoad(JSONObject json);
}
