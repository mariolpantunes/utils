package pt.it.av.tnav.utils.bla.factorization;

import pt.it.av.tnav.utils.ArrayUtils;
import pt.it.av.tnav.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Non negative matrix factorization.
 * http://www.albertauyeung.com/post/python-matrix-factorization/
 * 
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 */
public class LS {

    /**
     *
     */
    private LS() {
    }

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

    public static double[][] ls(final double data[], final int rows, final int cols, int k, int n) {
        // Initialize user and item latent feature matrice
        double alpha = 0.01, beta = 0.1, eps = MathUtils.eps();
        double P[] = ArrayUtils.gaussian(rows * k, 0, 1.0 / k),
        Q[] = ArrayUtils.gaussian(cols * k, 0, 1.0 / k);
        // double P[] = ArrayUtils.random(rows * k, eps, 1), Q[] =
        // ArrayUtils.random(cols * k, eps, 1);
        double PQ[] = new double[data.length], gradient[] = new double[k];

        // Initialize the biases
        // double b_r[] = new double[rows], b_c[] = new double[cols];
        // double b = ArrayUtils.mean(ArrayUtils.select_different(data, 0));

        // Create a list of training samples
        List<Sample> samples = new ArrayList<Sample>();
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0) {
                int r = ArrayUtils.idx2r(i, cols), c = ArrayUtils.idx2c(i, cols);
                samples.add(new Sample(r, c, data[i]));
            }
        }

        // Perform stochastic gradient descent for number of iterations
        for (int i = 0; i < n; i++) {
            // Shuffle Samples
            Collections.shuffle(samples);
            for (Sample s : samples) {
                // Computer prediction and error
                double prediction = ArrayUtils.dotProduct(P,(s.r*k), Q, (s.c * k), k),
                e = (s.v - prediction);

                // Update biases
                // b_r[s.r] += alpha * (e - beta * b_r[s.r]);
                // b_c[s.c] += alpha * (e - beta * b_r[s.c]);

                // Update user and item latent feature matrices
                ArrayUtils.wsub(Q, s.c * k, P, s.r * k, gradient, 0, e, beta, k);
                ArrayUtils.mul(gradient, 0, alpha, gradient, 0, k);
                ArrayUtils.add(P, s.r * k, gradient, 0, P, s.r * k, k);
                ArrayUtils.set_neg(P, eps);

                ArrayUtils.wsub(P, s.r * k, Q, s.c * k, gradient, 0, e, beta, k);
                ArrayUtils.mul(gradient, 0, alpha, gradient, 0, k);
                ArrayUtils.add(Q, s.c * k, gradient, 0, Q, s.c * k, k);
                ArrayUtils.set_neg(Q, eps);
            }
        }

        return new double[][] { P, Q };
    }
}