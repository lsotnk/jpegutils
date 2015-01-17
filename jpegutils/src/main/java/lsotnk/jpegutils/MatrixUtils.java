package lsotnk.jpegutils;

import lsotnk.jpegutils.matrix.ByteMatrix;

public class MatrixUtils {

    public static final int[] createIntZigZag8x8(final MatrixView in) {
        InternalUtils.checkArgument((in.getColumns() == in.getRows()) && (in.getColumns() == 8), "Invalid matrix size");
        int[] output = new int[64];
        output[0] = in.getInt(0, 0);
        output[1] = in.getInt(0, 1);
        output[2] = in.getInt(1, 0);
        output[3] = in.getInt(2, 0);
        output[4] = in.getInt(1, 1);
        output[5] = in.getInt(0, 2);
        output[6] = in.getInt(0, 3);
        output[7] = in.getInt(1, 2);
        output[8] = in.getInt(2, 1);
        output[9] = in.getInt(3, 0);
        output[10] = in.getInt(4, 0);
        output[11] = in.getInt(3, 1);
        output[12] = in.getInt(2, 2);
        output[13] = in.getInt(1, 3);
        output[14] = in.getInt(0, 4);
        output[15] = in.getInt(0, 5);
        output[16] = in.getInt(1, 4);
        output[17] = in.getInt(2, 3);
        output[18] = in.getInt(3, 2);
        output[19] = in.getInt(4, 1);
        output[20] = in.getInt(5, 0);
        output[21] = in.getInt(6, 0);
        output[22] = in.getInt(5, 1);
        output[23] = in.getInt(4, 2);
        output[24] = in.getInt(3, 3);
        output[25] = in.getInt(2, 4);
        output[26] = in.getInt(1, 5);
        output[27] = in.getInt(0, 6);
        output[28] = in.getInt(0, 7);
        output[29] = in.getInt(1, 6);
        output[30] = in.getInt(2, 5);
        output[31] = in.getInt(3, 4);
        output[32] = in.getInt(4, 3);
        output[33] = in.getInt(5, 2);
        output[34] = in.getInt(6, 1);
        output[35] = in.getInt(7, 0);
        output[36] = in.getInt(7, 1);
        output[37] = in.getInt(6, 2);
        output[38] = in.getInt(5, 3);
        output[39] = in.getInt(4, 4);
        output[40] = in.getInt(3, 5);
        output[41] = in.getInt(2, 6);
        output[42] = in.getInt(1, 7);
        output[43] = in.getInt(2, 7);
        output[44] = in.getInt(3, 6);
        output[45] = in.getInt(4, 5);
        output[46] = in.getInt(5, 4);
        output[47] = in.getInt(6, 3);
        output[48] = in.getInt(7, 2);
        output[49] = in.getInt(7, 3);
        output[50] = in.getInt(6, 4);
        output[51] = in.getInt(5, 5);
        output[52] = in.getInt(4, 6);
        output[53] = in.getInt(3, 7);
        output[54] = in.getInt(4, 7);
        output[55] = in.getInt(5, 6);
        output[56] = in.getInt(6, 5);
        output[57] = in.getInt(7, 4);
        output[58] = in.getInt(7, 5);
        output[59] = in.getInt(6, 6);
        output[60] = in.getInt(5, 7);
        output[61] = in.getInt(6, 7);
        output[62] = in.getInt(7, 6);
        output[63] = in.getInt(7, 7);
        return output;

    }

    public static void convertRGB2YCbCr(final Matrix[] image, final int width, final int height) {

        int tempY, tempCb, tempCr, valueY, valueCb, valueCr;
        final Matrix imageY = image[0], imageCb = image[1], imageCr = image[2];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                valueY = imageY.getInt(y, x);
                valueCb = imageCb.getInt(y, x);
                valueCr = imageCr.getInt(y, x);

                // @formatter:off
                tempY = (int) ( 0 + (0.299 * valueY)
                        + (0.587 * valueCb)
                        + (0.114 * valueCr));
                tempCb = (int) ( (128 - (0.169 * valueY)
                        - (0.331 * valueCb))
                        + (0.500 * valueCr));
                tempCr = (int)  (((128 + (0.500 * valueY)))
                        - (0.419 * valueCb)
                        - (0.081 * valueCr));

                imageY.setInt(y, x, tempY);
                imageCb.setInt(y, x,tempCb);
                imageCr.setInt(y, x, tempCr);
                // @formatter:on
            }
        }

    }

    public static Matrix sample(final Matrix canvas, final int factor) {
        InternalUtils.checkArgument((factor <= canvas.getRows()) && (factor <= canvas.getColumns()), "Invalid matrix size");
        Matrix newMatrix = new ByteMatrix(canvas.getRows() / factor, canvas.getColumns() / factor);
        for (int row = 0, size = factor * factor; row < canvas.getRows(); row += factor) {
            for (int col = 0, num = 0, rowDivFactor = row / factor, rowPlusFactor = row + factor; col < canvas.getColumns(); col += factor, num = 0) {
                for (int y = row; y < (rowPlusFactor); y++) {
                    for (int x = col; x < (col + factor); num += canvas.getInt(y, x), x++) {
                    }
                }

                newMatrix.setDouble(rowDivFactor, col / factor, ((float) num / (float) (size)));

            }
        }
        return newMatrix;
    }

}
