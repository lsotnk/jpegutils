package lsotnk.jpegutils;

import lsotnk.jpegutils.matrix.IntMatrix;

public class Quantization {

    public static final void quantize(final Matrix in, final MatrixView qt) {
        InternalUtils.checkArgument(((in.getColumns() % 8) == 0) && ((in.getRows() % 8) == 0), "Invalide matrix size");
        double value;
        for (int my = 0; my < in.getRows(); my += 8) {
            for (int mx = 0; mx < in.getColumns(); mx += 8) {
                for (int y = 0; y < 8; y++) {
                    for (int x = 0; x < 8; x++) {
                        value = (in.getDouble(my + y, mx + x) / qt.getDouble(y, x));
                        in.setDouble(my + y, mx + x, value);
                    }
                }
            }
        }
    }

    /**
     * ITU T.81 (p 143)
     **/
    public static final Matrix JPEG_LUMINANCE = new IntMatrix(8, 8, new int[][] {
            // @formatter:off
            { 16, 11, 10, 16, 24, 40, 51, 61 },
            { 12, 12, 14, 19, 26, 58, 60, 55 },
            { 14, 13, 16, 24, 40, 57, 69, 56 },
            { 14, 17, 22, 29, 51, 87, 80, 62 },
            { 18, 22, 37, 56, 68, 109, 103, 77 },
            { 24, 35, 55, 64, 81, 104, 113, 92 },
            { 49, 64, 78, 87, 103, 121, 120, 101 },
            { 72, 92, 95, 98, 112, 100, 103, 99 }
            // @formatter:on
            });

    /**
     * ITU T.81 (p 143)
     **/
    public static final Matrix JPEG_CHROMINANCE = new IntMatrix(8, 8, new int[][] {
            // @formatter:off
            { 17, 18, 24, 47, 99, 99, 99, 99 },
            { 18, 21, 26, 66, 99, 99, 99, 99 },
            { 24, 26, 56, 99, 99, 99, 99, 99 },
            { 47, 66, 99, 99, 99, 99, 99, 99 },
            { 99, 99, 99, 99, 99, 99, 99, 99 },
            { 99, 99, 99, 99, 99, 99, 99, 99 },
            { 99, 99, 99, 99, 99, 99, 99, 99 },
            { 99, 99, 99, 99, 99, 99, 99, 99 }
            // @formatter:on
            });

    /**
     * Canon EOS 40D
     **/
    public static final Matrix EOS40D_LUMINANCE = new IntMatrix(8, 8, new int[][] {
            // @formatter:off
            { 1, 1, 1, 1, 1, 2, 3, 3 },
            { 1, 1, 1, 1, 1, 3, 3, 3 },
            { 1, 1, 1, 1, 2, 3, 3, 3 },
            { 1, 1, 1, 2, 3, 4, 4, 3 },
            { 1, 1, 2, 3, 3, 5, 5, 4 },
            { 1, 2, 3, 3, 4, 5, 5, 4 },
            { 2, 3, 4, 4, 5, 6, 6, 5 },
            { 4, 4, 5, 5, 5, 5, 5, 5 }
            // @formatter:on
            });

    /**
     * Canon EOS 40D
     **/
    public static final Matrix EOS40D_CHROMINANCE = new IntMatrix(8, 8, new int[][] {
            // @formatter:off
            { 1, 1, 1, 2, 5, 5, 5, 5 },
            { 1, 1, 1, 3, 5, 5, 5, 5 },
            { 1, 1, 3, 5, 5, 5, 5, 5 },
            { 2, 3, 5, 5, 5, 5, 5, 5 },
            { 5, 5, 5, 5, 5, 5, 5, 5 },
            { 5, 5, 5, 5, 5, 5, 5, 5 },
            { 5, 5, 5, 5, 5, 5, 5, 5 },
            { 5, 5, 5, 5, 5, 5, 5, 5 }
            // @formatter:on
            });

    /**
     * Nikon D70
     **/
    public static final Matrix D70_LUMINANCE = new IntMatrix(8, 8, new int[][] {
            // @formatter:off
            { 2, 2, 2, 2, 2, 3, 7, 10 },
            { 2, 2, 2, 2, 3, 5, 9, 13 },
            { 1, 2, 2, 3, 5, 8, 11, 13 },
            { 2, 3, 3, 4, 8, 9, 12, 13 },
            { 3, 4, 5, 7, 9, 11, 14, 15 },
            { 5, 8, 8, 12, 15, 14, 17, 14 },
            { 7, 8, 9, 11, 14, 15, 16, 14 },
            { 8, 8, 8, 8, 11, 13, 14, 14 }
            // @formatter:on
            });

    /**
     * Nikon D70
     **/
    public static final Matrix D70_CHROMINANCE = new IntMatrix(8, 8, new int[][] {
            // @formatter:off
            { 2, 2, 3, 6, 14, 14, 14, 14 },
            { 2, 3, 4, 9, 14, 14, 14, 14 },
            { 3, 4, 8, 14, 14, 14, 14, 14 },
            { 6, 9, 14, 14, 14, 14, 14, 14 },
            { 14, 14, 14, 14, 14, 14, 14, 14 },
            { 14, 14, 14, 14, 14, 14, 14, 14 },
            { 14, 14, 14, 14, 14, 14, 14, 14 },
            { 14, 14, 14, 14, 14, 14, 14, 14 }
            // @formatter:on
            });

    /**
     * BenQ AE100
     **/
    public static final Matrix AE100_LUMINANCE = new IntMatrix(8, 8, new int[][] {
            // @formatter:off
            { 1, 1, 1, 1, 2, 3, 4, 4 },
            { 1, 1, 1, 1, 2, 4, 4, 4 },
            { 1, 1, 1, 2, 3, 4, 5, 4 },
            { 1, 1, 2, 2, 4, 6, 6, 4 },
            { 1, 2, 3, 4, 5, 8, 7, 5 },
            { 2, 2, 4, 5, 6, 7, 8, 6 },
            { 3, 5, 5, 6, 7, 9, 8, 7 },
            { 5, 6, 7, 7, 8, 7, 7, 7 }
            // @formatter:on
            });

    /**
     * BenQ AE100
     **/
    public static final Matrix AE100_CHROMINANCE = new IntMatrix(8, 8, new int[][] {
            // @formatter:off
            { 1, 1, 2, 3, 7, 7, 7, 7 },
            { 1, 1, 2, 5, 7, 7, 7, 7 },
            { 2, 2, 4, 7, 7, 7, 7, 7 },
            { 3, 5, 7, 7, 7, 7, 7, 7 },
            { 7, 7, 7, 7, 7, 7, 7, 7 },
            { 7, 7, 7, 7, 7, 7, 7, 7 },
            { 7, 7, 7, 7, 7, 7, 7, 7 },
            { 7, 7, 7, 7, 7, 7, 7, 7 }
            // @formatter:on
            });

    public static final Matrix EYE = new IntMatrix(8, 8, new int[][] {
            // @formatter:off
            { 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1 }
            // @formatter:on
            });

    public static final Matrix EYE_LUMINANCE = EYE;

    public static final Matrix EYE_CHROMINANCE = EYE;

}
