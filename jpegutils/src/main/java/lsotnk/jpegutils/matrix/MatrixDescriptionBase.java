package lsotnk.jpegutils.matrix;

import lsotnk.jpegutils.MatrixDescription;

public class MatrixDescriptionBase implements MatrixDescription {

    protected final int type;
    protected final int rows;
    protected final int cols;

    public MatrixDescriptionBase(final int type, final int rows, final int cols) {
        this.type = type;
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public int getColumns() {
        return this.cols;
    }

}
