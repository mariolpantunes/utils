package pt.it.av.tnav.utils.structures;

import pt.it.av.tnav.utils.csv.CSV;

/**
 * This interface allows to dump and load and object from a
 * {@link pt.it.av.tnav.utils.csv.CSV}.
 * <p>
 * The resulting object can be written to a text file, or sent remotely.
 * </p>
 *
 * @param <T> the type of objects that will be dumped and loaded
 * @author Mário Antunes
 * @version 1.0
 */
public interface CSVify<T> {

     /**
   * Dumps the object {@link T} into a {@link CSV.CSVRecord}.
   *
   * @return a {@link CSV.CSVRecord} that represents the object {@link T}.
   */
  CSV.CSVRecord csvDump();

  /**
   * Loads a {@link json} {@link JSONObject} into a object {@link T}.
   *
   * @param json object {@link T} encoded into a {@link JSONObject}
   * @return the object {@link T} represented by the {@link json} {@link JSONObject}
   */
  //List<T> csvLoad(CSV csv);
}
