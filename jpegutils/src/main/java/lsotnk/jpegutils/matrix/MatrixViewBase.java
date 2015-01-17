package lsotnk.jpegutils.matrix;

import lsotnk.jpegutils.MatrixView;

public abstract class MatrixViewBase extends MatrixDescriptionBase implements MatrixView {

    protected int startCol;
    protected int startRow;

    public MatrixViewBase(final int type, final int startRow, final int rows, final int startCol, final int cols) {
        super(type, rows, cols);
        this.startRow = startRow;
        this.startCol = startCol;
    }

    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder(getColumns() * getRows() * 6);

        for (int i = 0; i < getRows(); i++) {
            for (int k = 0; k < getColumns(); k++) {
                builder.append('[').append(String.format("%5.1f", getDouble(i, k))).append("]");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}
