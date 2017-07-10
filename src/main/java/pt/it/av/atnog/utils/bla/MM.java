package pt.it.av.atnog.utils.bla;

/******************************************************************************
 *  Compilation:  javac MatrixMultiplication.java
 *  Execution:    java MatrixMultiplication
 *
 *  6 different ways to multiply two N-by-N matrices.
 *  Illustrates importance of row-major vs. column-major ordering.
 *
 *  % java MatrixMultiplication 500
 *  Generating input:  0.048 seconds
 *  Order ijk:   3.562 seconds
 *  Order ikj:   1.348 seconds
 *  Order jik:   2.368 seconds
 *  Order jki:   4.846 seconds
 *  Order kij:   1.407 seconds
 *  Order kji:   4.91 seconds
 *  Order jik JAMA optimized:   0.571 seconds
 *  Order ikj pure row:   0.483 seconds
 *
 *  These timings are on a SUN-FIRE-X4100 running Linux.
 *
 ******************************************************************************/

public class MM {
  public static double[] larray(double m[][]) {
    double[] rv = new double[m.length * m[0].length];
    int k = 0;
    for (int i = 0; i < m.length; i++) {
      for (int j = 0; j < m[i].length; j++) {
        rv[k++] = m[i][j];
      }
    }
    return rv;
  }


  public static void show(double[][] a) {
    int N = a.length;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        System.out.printf("%6.4f ", a[i][j]);
      }
      System.out.println();
    }
    System.out.println();
  }


  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    long start, stop;
    double elapsed;


    // generate input
    start = System.nanoTime();

    double[][] A = new double[N][N];
    double[][] B = new double[N][N];
    double[][] C;

    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        A[i][j] = Math.random();

    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        B[i][j] = Math.random();

    stop = System.nanoTime();
    elapsed = (stop - start) / 1000000000.0;
    System.out.println("Generating input:  " + elapsed + " seconds");

    // order 1: ijk = dot product version
    /*C = new double[N][N];
    start = System.currentTimeMillis();
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        for (int k = 0; k < N; k++)
          C[i][j] += A[i][k] * B[k][j];
    stop = System.currentTimeMillis();
    elapsed = (stop - start) / 1000.0;
    System.out.println("Order ijk:   " + elapsed + " seconds");
    if (N < 10) show(C);*/

    // order 2: ikj
    C = new double[N][N];
    start = System.nanoTime();
    for (int i = 0; i < N; i++)
      for (int k = 0; k < N; k++)
        for (int j = 0; j < N; j++)
          C[i][j] += A[i][k] * B[k][j];
    stop = System.nanoTime();
    elapsed = (stop - start) / 1000000000.0;
    System.out.println("Order ikj:   " + elapsed + " seconds");
    if (N < 10) show(C);

    // order 3: jik
    /*C = new double[N][N];
    start = System.currentTimeMillis();
    for (int j = 0; j < N; j++)
      for (int i = 0; i < N; i++)
        for (int k = 0; k < N; k++)
          C[i][j] += A[i][k] * B[k][j];
    stop = System.currentTimeMillis();
    elapsed = (stop - start) / 1000.0;
    System.out.println("Order jik:   " + elapsed + " seconds");
    if (N < 10) show(C);

    // order 4: jki = GAXPY version
    C = new double[N][N];
    start = System.currentTimeMillis();
    for (int j = 0; j < N; j++)
      for (int k = 0; k < N; k++)
        for (int i = 0; i < N; i++)
          C[i][j] += A[i][k] * B[k][j];
    stop = System.currentTimeMillis();
    elapsed = (stop - start) / 1000.0;
    System.out.println("Order jki:   " + elapsed + " seconds");
    if (N < 10) show(C);

    // order 5: kij
    C = new double[N][N];
    start = System.currentTimeMillis();
    for (int k = 0; k < N; k++)
      for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
          C[i][j] += A[i][k] * B[k][j];
    stop = System.currentTimeMillis();
    elapsed = (stop - start) / 1000.0;
    System.out.println("Order kij:   " + elapsed + " seconds");
    if (N < 10) show(C);

    // order 6: kji = outer product version
    C = new double[N][N];
    start = System.currentTimeMillis();
    for (int k = 0; k < N; k++)
      for (int j = 0; j < N; j++)
        for (int i = 0; i < N; i++)
          C[i][j] += A[i][k] * B[k][j];
    stop = System.currentTimeMillis();
    elapsed = (stop - start) / 1000.0;
    System.out.println("Order kji:   " + elapsed + " seconds");
    if (N < 10) show(C);*/


    // order 7: jik optimized ala JAMA
    C = new double[N][N];
    start = System.nanoTime();
    double[] bcolj = new double[N];
    for (int j = 0; j < N; j++) {
      for (int k = 0; k < N; k++) bcolj[k] = B[k][j];
      for (int i = 0; i < N; i++) {
        double[] arowi = A[i];
        double sum = 0.0;
        for (int k = 0; k < N; k++) {
          sum += arowi[k] * bcolj[k];
        }
        C[i][j] = sum;
      }
    }
    stop = System.nanoTime();
    elapsed = (stop - start) / 1000000000.0;
    System.out.println("Order jik JAMA optimized:   " + elapsed + " seconds");
    if (N < 10) show(C);

    // order 8: ikj pure row
    C = new double[N][N];
    start = System.nanoTime();
    for (int i = 0; i < N; i++) {
      double[] arowi = A[i];
      double[] crowi = C[i];
      for (int k = 0; k < N; k++) {
        double[] browk = B[k];
        double aik = arowi[k];
        for (int j = 0; j < N; j++) {
          crowi[j] += aik * browk[j];
        }
      }
    }
    stop = System.nanoTime();
    elapsed = (stop - start) / 1000000000.0;
    System.out.println("Order ikj pure row:   " + elapsed + " seconds");
    if (N < 10) show(C);

    // linear matrix
    double a[] = larray(A), b[] = larray(B);
    double c[] = new double[N * N];

    // order 9: ijkt
    start = System.nanoTime();
    MatrixMultiplication.ijkt(a, b, c, N, N, N);
    stop = System.nanoTime();
    elapsed = (stop - start) / 1000000000.0;
    System.out.println("Order ijkt:   " + elapsed + " seconds");

    // order 9.5: matrix cotr
    double bt[] = new double[b.length];
    start = System.nanoTime();
    MatrixTranspose.cotr(b, bt, N, N);
    stop = System.nanoTime();
    elapsed = (stop - start) / 1000000000.0;
    System.out.println("Order cotr:   " + elapsed + " seconds");

    // order 10: pmul
    start = System.nanoTime();
    MatrixMultiplication.pmul(a, b, c, N, N, N);
    stop = System.nanoTime();
    elapsed = (stop - start) / 1000000000.0;
    System.out.println("Order pmul:   " + elapsed + " seconds");

    // order 10.5: pmult
    start = System.nanoTime();
    MatrixMultiplication.pmult(a, b, c, N, N, N);
    stop = System.nanoTime();
    elapsed = (stop - start) / 1000000000.0;
    System.out.println("Order pmult:   " + elapsed + " seconds");

    // order 11: comul
    start = System.nanoTime();
    MatrixMultiplication.comul(a, b, c, N, N, N, 128);
    stop = System.nanoTime();
    elapsed = (stop - start) / 1000000000.0;
    System.out.println("Order comul:   " + elapsed + " seconds");

    // order 12: ikj
    start = System.nanoTime();
    MatrixMultiplication.ikj(a, b, c, N, N, N);
    stop = System.nanoTime();
    elapsed = (stop - start) / 1000000000.0;
    System.out.println("Order ikj:   " + elapsed + " seconds");


    // NMF
    /*Matrix V = Matrix.identity(N);


    start = System.nanoTime();
    NmfFactorization.nmf_mu(V, N / 3, 1000, 0.01);
    stop = System.nanoTime();
    elapsed = (stop - start) / 1000000000.0;
    System.out.println("Order nmfv1:   " + elapsed + " seconds");


    start = System.nanoTime();
    NmfFactorization.nmf_mu2(V, N / 3, 1000, 0.01);
    stop = System.nanoTime();
    elapsed = (stop - start) / 1000000000.0;
    System.out.println("Order nmfv2:   " + elapsed + " seconds");*/

    double t[] = new double[a.length];
    start = System.nanoTime();
    MatrixTranspose.cotr(a,t,N,N,64);
    stop = System.nanoTime();
    elapsed = (stop - start) / 1000000000.0;
    System.out.println("Order cotr:   " + elapsed + " seconds");
  }
}