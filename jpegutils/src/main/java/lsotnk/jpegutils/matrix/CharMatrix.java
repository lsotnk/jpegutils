package lsotnk.jpegutils.matrix;

import lsotnk.jpegutils.BadMath;
import lsotnk.jpegutils.Matrix;
import lsotnk.jpegutils.MatrixView;

public class CharMatrix extends MatrixViewBase implements Matrix {

    private final char[][] impl;

    public CharMatrix(final int y, final int x) {
        this(y, x, new char[y][x]);
    }

    public CharMatrix(final int y, final int x, final char[][] matrix) {
        this(0, y, 0, x, matrix);
    }

    protected CharMatrix(final int y, final int dy, final int x, final int dx, final char[][] matrix) {
        super(CHAR, y, dy, x, dx);
        this.impl = matrix;
    }

    @Override
    public void replace(final int y, final int x, final MatrixView matrix) {
        for (int y1 = 0; y1 < matrix.getRows(); y1++) {
            for (int x1 = 0; x1 < matrix.getColumns(); x1++) {
                this.impl[y1 + y][x1 + x] = (char) matrix.getInt(y1, x1);
            }
        }
    }

    @Override
    public final char[][] getImpl() {
        return this.impl;
    }

    @Override
    public MatrixView getView(final int startRow, final int dy, final int startCol, final int dx) {
        return new CharMatrixView(startRow, dy, startCol, dx, this.impl);
    }

    @Override
    public void setDouble(final int y, final int x, final double value) {
        this.impl[this.startRow + y][this.startCol + x] = (char) BadMath.INSTANCE.fastRound(value);

    }

    @Override
    public void setInt(final int y, final int x, final int value) {
        this.impl[this.startRow + y][this.startCol + x] = (char) value;

    }

    @Override
    public final int getInt(final int y, final int x) {
        return this.impl[y + this.startRow][x + this.startCol];
    }

    @Override
    public final double getDouble(final int y, final int x) {
        return this.impl[y + this.startRow][x + this.startCol];
    }

    private static final class CharMatrixView extends CharMatrix {

        protected CharMatrixView(final int y, final int dy, final int x, final int dx, final char[][] matrix) {
            super(y, dy, x, dx, matrix);
        }

        @Override
        public MatrixView getView(final int startRow, final int dy, final int startCol, final int dx) {
            throw new IllegalStateException();
        }

        @Override
        public void setDouble(final int y, final int x, final double value) {
            throw new IllegalStateException();
        }

        @Override
        public void setInt(final int y, final int x, final int value) {
            throw new IllegalStateException();
        }

        @Override
        public void replace(final int y, final int x, final MatrixView matrix) {
            throw new IllegalStateException();
        }

    }

}
