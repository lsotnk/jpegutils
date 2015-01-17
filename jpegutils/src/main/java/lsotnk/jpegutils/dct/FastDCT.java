package lsotnk.jpegutils.dct;

import lsotnk.jpegutils.DCT;
import lsotnk.jpegutils.Matrix;
import lsotnk.jpegutils.MatrixView;
import lsotnk.jpegutils.matrix.DoubleMatrix;

public class FastDCT extends DCT {

    /**
     * The coeff. matrix
     */
    private static final double[][] A_MATRIX = new double[SIZE][SIZE];
    static {
        fillAMatrix(A_MATRIX);
    }

    /**
     * Creates the coeff. matrix
     */
    private static void fillAMatrix(final double[][] a) {
        for (int k = 0; k < SIZE; k++) {
            for (int n = 0; n < SIZE; n++) {
                a[k][n] = (getCValue(k) * SQRT_2_8 * Math.cos((((2 * n) + 1) * k * Math.PI) / (2 * SIZE)));
            }
        }
    }

    /**
     * Equals <code>Math.sqrt((double) 2 / SIZE)</code>
     */
    private static final double SQRT_2_8 = Math.sqrt((double) 2 / SIZE);

    /**
     * A fast transformation: O(n^3), Y = AX(A^T)
     */
    @Override
    public Matrix dct(final MatrixView in) {
        check(in);
        double[][] TMP_M_2D = new double[SIZE][SIZE];
        double[][] out = new double[in.getRows()][in.getColumns()];
        for (int my = 0; my < in.getRows(); my += SIZE) {
            for (int mx = 0; mx < in.getColumns(); mx += SIZE) {
                for (int k = 0; k < SIZE; k++) {
                    for (int n = 0; n < SIZE; n++) {
                        double value = 0d;
                        for (int i = 0; i < SIZE; value += A_MATRIX[k][i] * in.getInt(my + i, mx + n), TMP_M_2D[k][n] = value, i++) {
                        }
                    }
                }

                for (int k = 0; k < SIZE; k++) {
                    for (int n = 0; n < SIZE; n++) {
                        double value = 0d;
                        for (int i = 0; i < SIZE; value += TMP_M_2D[k][i] * A_MATRIX[n][i], out[my + k][mx + n] = value, i++) {
                        }
                    }
                }

            }
        }
        return new DoubleMatrix(in.getRows(), in.getColumns(), out);

    }

    /**
     * A fast inverse transformation O(N^3), X = (A^T)YA
     */
    @Override
    public Matrix idct(final MatrixView in) {
        check(in);
        double[][] TMP_M_2D = new double[SIZE][SIZE];
        double[][] out = new double[in.getRows()][in.getColumns()];
        for (int my = 0; my < in.getRows(); my += SIZE) {
            for (int mx = 0; mx < in.getColumns(); mx += SIZE) {
                for (int k = 0; k < SIZE; k++) {
                    for (int n = 0; n < SIZE; n++) {
                        double value = 0d;
                        for (int i = 0; i < SIZE; value += A_MATRIX[i][k] * in.getDouble(my + i, mx + n), TMP_M_2D[k][n] = value, i++) {
                        }
                    }
                }

                for (int k = 0; k < SIZE; k++) {
                    for (int n = 0; n < SIZE; n++) {
                        double value = 0d;
                        for (int i = 0; i < SIZE; value += TMP_M_2D[k][i] * A_MATRIX[i][n], out[my + k][mx + n] = value, i++) {
                        }
                    }
                }

            }
        }
        return new DoubleMatrix(in.getRows(), in.getColumns(), out);
    }
}
