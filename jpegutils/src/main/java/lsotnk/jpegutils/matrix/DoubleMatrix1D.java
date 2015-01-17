package lsotnk.jpegutils.matrix;

import lsotnk.jpegutils.BadMath;
import lsotnk.jpegutils.Matrix;
import lsotnk.jpegutils.MatrixView;

public class DoubleMatrix1D extends MatrixViewBase implements Matrix {
    private final double[] impl;

    public DoubleMatrix1D(final int y, final int x) {
        this(y, x, new double[x * y]);
    }

    public DoubleMatrix1D(final int y, final int x, final double[] matrix) {
        this(0, y, 0, x, matrix);
    }

    protected DoubleMatrix1D(final int y, final int dy, final int x, final int dx, final double[] matrix) {
        super(DOUBLE, y, dy, x, dx);
        this.impl = matrix;
    }

    @Override
    public void replace(final int y, final int x, final MatrixView matrix) {
        for (int y1 = 0; y1 < matrix.getRows(); y1++) {
            for (int x1 = 0; x1 < matrix.getColumns(); x1++) {
                this.impl[((y1 + y) * this.cols) + x1 + x] = matrix.getInt(y1, x1);
            }
        }
    }

    @Override
    public final double[] getImpl() {
        return this.impl;
    }

    @Override
    public MatrixView getView(final int startRow, final int dy, final int startCol, final int dx) {
        return new DoubleMatrixView1D(startRow, dy, startCol, dx, this.impl);
    }

    @Override
    public void setDouble(final int y, final int x, final double value) {
        this.impl[((y + this.startRow) * this.cols) + (x + this.startCol)] = value;

    }

    @Override
    public void setInt(final int y, final int x, final int value) {
        this.impl[((y + this.startRow) * this.cols) + (x + this.startCol)] = value;

    }

    @Override
    public final int getInt(final int y, final int x) {
        return BadMath.INSTANCE.fastRound((this.impl[((y + this.startRow) * this.cols) + (x + this.startCol)]));
    }

    @Override
    public final double getDouble(final int y, final int x) {
        return this.impl[((y + this.startRow) * this.cols) + (x + this.startCol)];
    }

    private static final class DoubleMatrixView1D extends DoubleMatrix1D {

        protected DoubleMatrixView1D(final int y, final int dy, final int x, final int dx, final double[] matrix) {
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
