package lsotnk.jpegutils.dct;

import lsotnk.jpegutils.BadMath;
import lsotnk.jpegutils.DCT;
import lsotnk.jpegutils.Matrix;
import lsotnk.jpegutils.MatrixView;
import lsotnk.jpegutils.matrix.DoubleMatrix;

public class SlowDCT extends DCT {

    private static final double PI_DIV_SIZE_MUL_2 = Math.PI / 16;

    private BadMath math;

    public SlowDCT(BadMath math) {
        super();
        this.math = math;
    }

    /**
     * The slowest transformation: O(n^4)
     */
    @Override
    public final Matrix dct(final MatrixView in) {
        check(in);
        double[][] out = new double[in.getRows()][in.getColumns()];
        for (int my = 0; my < in.getRows(); my += SIZE) {
            for (int mx = 0; mx < in.getColumns(); mx += SIZE) {
                for (int i = 0; i < SIZE; i++) {
                    double ip = (i * PI_DIV_SIZE_MUL_2);
                    for (int j = 0; j < SIZE; j++) {
                        double jp = (j * PI_DIV_SIZE_MUL_2);
                        double temp = 0d;
                        for (int y = 0; y < SIZE; y++) {
                            for (int x = 0; x < SIZE; temp += in.getInt(my + y, mx + x) * this.math.cos((((y << 1) + 1) * ip))
                                    * this.math.cos((((x << 1) + 1) * jp)), x++) {
                                ;
                            }
                        }
                        out[my + i][mx + j] = temp * 0.25 * getCValue(i) * getCValue(j);
                    }
                }
            }
        }
        return new DoubleMatrix(in.getRows(), in.getColumns(), out);
    }

    @Override
    public Matrix idct(final MatrixView in) {
        check(in);
        double[][] out = new double[in.getRows()][in.getColumns()];
        for (int my = 0; my < in.getRows(); my += SIZE) {
            for (int mx = 0; mx < in.getColumns(); mx += SIZE) {
                for (int x = 0; x < SIZE; x++) {
                    for (int y = 0; y < SIZE; y++) {
                        double temp = 0d;
                        for (int i = 0; i < SIZE; i++) {
                            double ip = (i * PI_DIV_SIZE_MUL_2);
                            for (int j = 0; j < SIZE; j++) {
                                double jp = (j * PI_DIV_SIZE_MUL_2);
                                temp += getCValue(i) * getCValue(j) * in.getDouble(my + i, mx + j) * this.math.cos((((x << 1) + 1) * ip))
                                        * this.math.cos((((y << 1) + 1) * jp));
                            }
                        }
                        out[my + x][mx + y] = temp * 0.25;
                    }
                }
            }
        }
        return new DoubleMatrix(in.getRows(), in.getColumns(), out);
    }

}
