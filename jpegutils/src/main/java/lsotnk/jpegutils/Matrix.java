package lsotnk.jpegutils;

public interface Matrix extends MatrixView {

    void setDouble(int y, int x, double value);

    void setInt(int y, int x, int value);

    public void replace(final int y, final int x, final MatrixView matrix);

    MatrixView getView(int y, int dy, int x, int dx);

    Object getImpl();

}
