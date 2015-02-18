package pt.it.av.atnog.utils;

import pt.it.av.atnog.utils.bla.Matrix;

/**
 * Created by mantunes on 22/01/2015.
 */
public class Test {
    public static void main(String[] args) {
        double data[] = {9, 5, -5, -4, 8, 3, -10, -4, -10, -6, 1, -10, 8, 9, 6, -4};
        Matrix M = new Matrix(4, 4, data);
        System.out.println(M);
        /*Matrix UBV[] = M.bidiagonal();
        System.out.println(UBV[1]);
        System.out.println(UBV[0]);
        System.out.println(UBV[2]);
        System.out.println(UBV[0].mul(UBV[1]).mul(UBV[2].transpose()));

        double data2[] = {1,3,2,
                5,6,4,
                7,8,9};
        Matrix M2 = new Matrix(3, 3, data2);
        System.out.println(M2);
        Matrix UBV2[] = M2.bidiagonal();
        System.out.println(UBV2[1]);
        System.out.println(UBV2[0]);
        System.out.println(UBV2[2]);
        System.out.println(UBV2[0].mul(UBV2[1]).mul(UBV2[2].transpose()));


        Matrix B = Matrix.rand(128,1024);
        Matrix UBV3[] = B.bidiagonal();*/

        double tmp[] = new double[16];
        Matrix.transpose2(data, tmp, 4, 4);

        Matrix B = new Matrix(4, 4, tmp);
        System.out.println(B);
    }
}
