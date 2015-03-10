package pt.it.av.atnog.utils.bla;

import java.util.concurrent.TimeUnit;

/**
 * Created by mantunes on 22/01/2015.
 */
public class Test {
    public static void main(String[] args) {
        Matrix A = Matrix.rand(1024, 1024);
        Matrix B = Matrix.rand(1024, 1024);

        int reps = 10;
        long time = 0;
        /*for (int i = 0; i < reps; i++) {
            long begin = System.nanoTime();
            Naive.mul(A, B);
            time += System.nanoTime() - begin;
        }
        time /= reps;
        System.out.println("Time SEQ: " + TimeUnit.NANOSECONDS.toMillis(time));

        time = 0;
        for (int i = 0; i < reps; i++) {
            long begin = System.nanoTime();
            Naive.mul_par(A, B);
            time += System.nanoTime() - begin;
        }
        time /= reps;
        System.out.println("Time PAR: " + TimeUnit.NANOSECONDS.toMillis(time));

        time = 0;
        Matrix C = null;
        for (int i = 0; i < reps; i++) {
            long begin = System.nanoTime();
            C = A.mul_r(B);
            time += System.nanoTime() - begin;
        }
        time /= reps;
        System.out.println("Time REC: " + TimeUnit.NANOSECONDS.toMillis(time));*/

        time = 0;
        Matrix D = null;
        for (int i = 0; i < reps; i++) {
            long begin = System.nanoTime();
            D = A.mul(B);
            time += System.nanoTime() - begin;
        }
        time /= reps;
        System.out.println("Time NEW: " + TimeUnit.NANOSECONDS.toMillis(time));

        /*Matrix R = Naive.mul_par(A, B);
        if (R.equals(C))
            System.out.println("REC is OK");
        else
            System.out.println("Rec is Not OK");

        if (R.equals(D))
            System.out.println("NEW is OK");
        else
            System.out.println("NEW is Not OK");*/
    }
}
