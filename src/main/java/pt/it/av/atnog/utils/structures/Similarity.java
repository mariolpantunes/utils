package pt.it.av.atnog.utils.structures;

/**
 * This interface allows to compute the similarity between two objects.
 * <p>
 * It is strongly recommended (though not required) that when objects are equals the similarity between them be 1.
 * </p>
 *
 * @param <T> the type of objects that this object may be compared to
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public interface Similarity<T> {
    /**
     * Returns the distance between two objects.
     *
     * @param s another object
     * @return distance between two objects
     */
    double similarityTo(T s);
}
