package lsotnk.jpegutils.dct;

import lsotnk.jpegutils.DCT;
import lsotnk.jpegutils.Matrix;
import lsotnk.jpegutils.MatrixView;
import lsotnk.jpegutils.matrix.DoubleMatrix;

public class AANDCT extends DCT {

    /**
     * Constant Z0 (AAN)
     */
    private static final double Z0 = .7071068;

    /**
     * Constant Z1 (AAN)
     */
    private static final double Z1 = .4903926;

    /**
     * Constant Z2 (AAN)
     */
    private static final double Z2 = .4619398;

    /**
     * Constant Z3 (AAN)
     */
    private static final double Z3 = .4157348;

    /**
     * Constant Z4 (AAN)
     */
    private static final double Z4 = .3535534;

    /**
     * Constant Z5 (AAN)
     */
    private static final double Z5 = .2777851;

    /**
     * Constant Z6 (AAN)
     */
    private static final double Z6 = .1913417;

    /**
     * Constant Z7 (AAN)
     */
    private static final double Z7 = .0975452;

    /**
     * A fast transformation with O(n^2) based on:<br>
     * <b>[A]rai, [A]gui, [N]akajima</b> (Transactions of the IEICE, vol. 71,
     * no. 11, pp. 1095-1097, 1988)
     */
    @Override
    public Matrix dct(final MatrixView in) {
        check(in);
        double[][] out = new double[in.getRows()][in.getColumns()];
        double tmpm0 = 0, tmpm1 = 0, tmpm2 = 0, tmpm3 = 0, tmpm4 = 0, tmpm5 = 0, tmpm6 = 0, tmpm7 = 0;
        double tmpm20 = 0, tmpm21 = 0, tmpm22 = 0, tmpm23 = 0, tmpm24 = 0, tmpm25 = 0, tmpm26 = 0, tmpm27 = 0;

        for (int my = 0; my < in.getRows(); my += SIZE) {
            for (int mx = 0; mx < in.getColumns(); mx += SIZE) {

                for (int i = 0; i < SIZE; i++) { // HT

                    tmpm0 = in.getInt(my + i, mx + 0);
                    tmpm1 = in.getInt(my + i, mx + 1);
                    tmpm2 = in.getInt(my + i, mx + 2);
                    tmpm3 = in.getInt(my + i, mx + 3);
                    tmpm4 = in.getInt(my + i, mx + 4);
                    tmpm5 = in.getInt(my + i, mx + 5);
                    tmpm6 = in.getInt(my + i, mx + 6);
                    tmpm7 = in.getInt(my + i, mx + 7);

                    tmpm20 = tmpm0 + tmpm7;
                    tmpm27 = tmpm0 - tmpm7;
                    tmpm21 = tmpm1 + tmpm6;
                    tmpm26 = tmpm1 - tmpm6;
                    tmpm22 = tmpm2 + tmpm5;
                    tmpm25 = tmpm2 - tmpm5;
                    tmpm23 = tmpm3 + tmpm4;
                    tmpm24 = tmpm3 - tmpm4;

                    double outmyi[] = out[my + i];

                    tmpm0 = tmpm20 + tmpm23;
                    tmpm1 = tmpm21 + tmpm22;
                    tmpm2 = tmpm21 - tmpm22;
                    tmpm3 = tmpm20 - tmpm23;
                    tmpm4 = tmpm24;
                    tmpm5 = (tmpm26 - tmpm25) * Z0;
                    tmpm6 = (tmpm26 + tmpm25) * Z0;
                    tmpm7 = tmpm27;
                    outmyi[mx + 0] = (tmpm0 + tmpm1) * Z4;
                    outmyi[mx + 4] = (tmpm0 - tmpm1) * Z4;
                    outmyi[mx + 2] = (tmpm2 * Z6) + (tmpm3 * Z2);
                    outmyi[mx + 6] = (tmpm3 * Z6) - (tmpm2 * Z2);
                    tmpm24 = tmpm4 + tmpm5;
                    tmpm27 = tmpm7 + tmpm6;
                    tmpm25 = tmpm4 - tmpm5;
                    tmpm26 = tmpm7 - tmpm6;
                    outmyi[mx + 1] = (tmpm24 * Z7) + (tmpm27 * Z1);
                    outmyi[mx + 5] = (tmpm25 * Z3) + (tmpm26 * Z5);
                    outmyi[mx + 7] = (tmpm27 * Z7) - (tmpm24 * Z1);
                    outmyi[mx + 3] = (tmpm26 * Z3) - (tmpm25 * Z5);
                }

                for (int i = 0; i < SIZE; i++) { // VT
                    tmpm20 = out[my + 0][mx + i] + out[my + 7][mx + i];
                    tmpm27 = out[my + 0][mx + i] - out[my + 7][mx + i];
                    tmpm21 = out[my + 1][mx + i] + out[my + 6][mx + i];
                    tmpm26 = out[my + 1][mx + i] - out[my + 6][mx + i];
                    tmpm22 = out[my + 2][mx + i] + out[my + 5][mx + i];
                    tmpm25 = out[my + 2][mx + i] - out[my + 5][mx + i];
                    tmpm23 = out[my + 3][mx + i] + out[my + 4][mx + i];
                    tmpm24 = out[my + 3][mx + i] - out[my + 4][mx + i];

                    tmpm0 = tmpm20 + tmpm23;
                    tmpm1 = tmpm21 + tmpm22;
                    tmpm2 = tmpm21 - tmpm22;
                    tmpm3 = tmpm20 - tmpm23;
                    tmpm4 = tmpm24;
                    tmpm5 = (tmpm26 - tmpm25) * Z0;
                    tmpm6 = (tmpm26 + tmpm25) * Z0;
                    tmpm7 = tmpm27;
                    out[my + 0][mx + i] = (tmpm0 + tmpm1) * Z4;
                    out[my + 4][mx + i] = (tmpm0 - tmpm1) * Z4;
                    out[my + 2][mx + i] = (tmpm2 * Z6) + (tmpm3 * Z2);
                    out[my + 6][mx + i] = (tmpm3 * Z6) - (tmpm2 * Z2);
                    tmpm24 = tmpm4 + tmpm5;
                    tmpm27 = tmpm7 + tmpm6;
                    tmpm25 = tmpm4 - tmpm5;
                    tmpm26 = tmpm7 - tmpm6;
                    out[my + 1][mx + i] = (tmpm24 * Z7) + (tmpm27 * Z1);
                    out[my + 5][mx + i] = (tmpm25 * Z3) + (tmpm26 * Z5);
                    out[my + 7][mx + i] = (tmpm27 * Z7) - (tmpm24 * Z1);
                    out[my + 3][mx + i] = (tmpm26 * Z3) - (tmpm25 * Z5);
                }
            }
        }

        return new DoubleMatrix(in.getRows(), in.getColumns(), out);
    }

    /**
     * A fast inverse transformation with O(n^2) based on:<br>
     * <b>[A]rai, [A]gui, [N]akajima</b> (Transactions of the IEICE, vol. 71,
     * no. 11, pp. 1095-1097, 1988)
     */
    @Override
    public Matrix idct(final MatrixView in) {
        check(in);
        double[][] out = new double[in.getRows()][in.getColumns()];
        double tmp1, tmp2, tmp3, tmp4;
        double tmpm0 = 0, tmpm1 = 0, tmpm2 = 0, tmpm3 = 0, tmpm4 = 0, tmpm5 = 0, tmpm6 = 0, tmpm7 = 0;
        double tmpm20, tmpm21, tmpm22, tmpm23, tmpm24, tmpm25, tmpm26, tmpm27 = 0;
        double[] outtmpmyi = null;

        for (int my = 0; my < in.getRows(); my += SIZE) {
            for (int mx = 0; mx < in.getColumns(); mx += SIZE) {
                for (int i = 0; i < 8; i++) {
                    outtmpmyi = out[my + i];

                    tmpm0 = in.getDouble(my + i, mx + 0);
                    tmpm1 = in.getDouble(my + i, mx + 1);
                    tmpm2 = in.getDouble(my + i, mx + 2);
                    tmpm3 = in.getDouble(my + i, mx + 3);
                    tmpm4 = in.getDouble(my + i, mx + 4);
                    tmpm5 = in.getDouble(my + i, mx + 5);
                    tmpm6 = in.getDouble(my + i, mx + 6);
                    tmpm7 = in.getDouble(my + i, mx + 7);

                    tmp1 = (tmpm1 * Z7) - (tmpm7 * Z1);
                    tmp4 = (tmpm7 * Z7) + (tmpm1 * Z1);
                    tmp2 = (tmpm5 * Z3) - (tmpm3 * Z5);
                    tmp3 = (tmpm3 * Z3) + (tmpm5 * Z5);

                    tmpm20 = (tmpm0 + tmpm4) * Z4;
                    tmpm21 = (tmpm0 - tmpm4) * Z4;
                    tmpm22 = (tmpm2 * Z6) - (tmpm6 * Z2);
                    tmpm23 = (tmpm6 * Z6) + (tmpm2 * Z2);
                    tmpm4 = tmp1 + tmp2;
                    tmpm25 = tmp1 - tmp2;
                    tmpm26 = tmp4 - tmp3;
                    tmpm7 = tmp4 + tmp3;

                    tmpm5 = (tmpm26 - tmpm25) * Z0;
                    tmpm6 = (tmpm26 + tmpm25) * Z0;
                    tmpm0 = tmpm20 + tmpm23;
                    tmpm1 = tmpm21 + tmpm22;
                    tmpm2 = tmpm21 - tmpm22;
                    tmpm3 = tmpm20 - tmpm23;

                    outtmpmyi[mx + 0] = tmpm0 + tmpm7;
                    outtmpmyi[mx + 7] = tmpm0 - tmpm7;
                    outtmpmyi[mx + 1] = tmpm1 + tmpm6;
                    outtmpmyi[mx + 6] = tmpm1 - tmpm6;
                    outtmpmyi[mx + 2] = tmpm2 + tmpm5;
                    outtmpmyi[mx + 5] = tmpm2 - tmpm5;
                    outtmpmyi[mx + 3] = tmpm3 + tmpm4;
                    outtmpmyi[mx + 4] = tmpm3 - tmpm4;

                }

                for (int i = 0; i < 8; i++) {

                    tmpm0 = out[my + 0][mx + i];
                    tmpm1 = out[my + 1][mx + i];
                    tmpm2 = out[my + 2][mx + i];
                    tmpm3 = out[my + 3][mx + i];
                    tmpm4 = out[my + 4][mx + i];
                    tmpm5 = out[my + 5][mx + i];
                    tmpm6 = out[my + 6][mx + i];
                    tmpm7 = out[my + 7][mx + i];

                    tmp1 = (tmpm1 * Z7) - (tmpm7 * Z1);
                    tmp4 = (tmpm7 * Z7) + (tmpm1 * Z1);
                    tmp2 = (tmpm5 * Z3) - (tmpm3 * Z5);
                    tmp3 = (tmpm3 * Z3) + (tmpm5 * Z5);

                    tmpm20 = (tmpm0 + tmpm4) * Z4;
                    tmpm21 = (tmpm0 - tmpm4) * Z4;
                    tmpm22 = (tmpm2 * Z6) - (tmpm6 * Z2);
                    tmpm23 = (tmpm6 * Z6) + (tmpm2 * Z2);
                    tmpm4 = tmp1 + tmp2;
                    tmpm25 = tmp1 - tmp2;
                    tmpm26 = tmp4 - tmp3;
                    tmpm7 = tmp4 + tmp3;

                    tmpm5 = (tmpm26 - tmpm25) * Z0;
                    tmpm6 = (tmpm26 + tmpm25) * Z0;
                    tmpm0 = tmpm20 + tmpm23;
                    tmpm1 = tmpm21 + tmpm22;
                    tmpm2 = tmpm21 - tmpm22;
                    tmpm3 = tmpm20 - tmpm23;

                    out[my + 0][mx + i] = tmpm0 + tmpm7;
                    out[my + 7][mx + i] = tmpm0 - tmpm7;
                    out[my + 1][mx + i] = tmpm1 + tmpm6;
                    out[my + 6][mx + i] = tmpm1 - tmpm6;
                    out[my + 2][mx + i] = tmpm2 + tmpm5;
                    out[my + 5][mx + i] = tmpm2 - tmpm5;
                    out[my + 3][mx + i] = tmpm3 + tmpm4;
                    out[my + 4][mx + i] = tmpm3 - tmpm4;
                }
            }
        }

        return new DoubleMatrix(in.getRows(), in.getColumns(), out);
    }

}
