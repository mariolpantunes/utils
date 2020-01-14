package pt.it.av.tnav.utils.bla.factorization;

import pt.it.av.tnav.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Simple implementation of a Alternating Least Square (ALS) for matrix
 * factorization.
 * 
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 */
public class ALS {
    /**
     * Utility class, lets make the constructor private.
     */
    private ALS() {
    }

    /**
     * Private class used only to store dataset samples.
     */
    private static class Sample {
        protected final int r, c;
        protected final double v;

        public Sample(final int r, final int c, final double v) {
            this.r = r;
            this.c = c;
            this.v = v;
        }

        public String toString() {
            return "[" + this.r + "; " + this.c + "] = " + this.v;
        }
    }

    public static double[][] als(final double data[], final int rows, final int cols, final int k,
            final double alpha, final double beta, final int n) {
        // Initialize user and item latent feature matrice
        double P[] = ArrayUtils.gaussian(rows * k, 0, 1.0 / k), Q[] = ArrayUtils.gaussian(cols * k, 0, 1.0 / k),
                gradient[] = new double[k];

        // filter out zeros and create a list of training samples
        List<Sample> samples = new ArrayList<Sample>();
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0) {
                int r = ArrayUtils.idx2r(i, cols), c = ArrayUtils.idx2c(i, cols);
                samples.add(new Sample(r, c, data[i]));
            }
        }

        // perform stochastic gradient descent for number of iterations
        for (int i = 0; i < n; i++) {
            // shuffle Samples
            Collections.shuffle(samples);
            for (Sample s : samples) {
                // computer prediction and error
                double prediction = ArrayUtils.dotProduct(P, (s.r * k), Q, (s.c * k), k), e = (s.v - prediction);

                // Update user and item latent feature matrices
                ArrayUtils.wsub(Q, s.c * k, P, s.r * k, gradient, 0, e, beta, k);
                ArrayUtils.mul(gradient, 0, alpha, gradient, 0, k);
                ArrayUtils.add(P, s.r * k, gradient, 0, P, s.r * k, k);

                ArrayUtils.wsub(P, s.r * k, Q, s.c * k, gradient, 0, e, beta, k);
                ArrayUtils.mul(gradient, 0, alpha, gradient, 0, k);
                ArrayUtils.add(Q, s.c * k, gradient, 0, Q, s.c * k, k);
            }
        }

        return new double[][] { P, Q };
    }
}