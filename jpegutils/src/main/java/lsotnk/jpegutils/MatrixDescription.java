package lsotnk.jpegutils;

public interface MatrixDescription {

    public static final int INT = 0x1;
    public static final int DOUBLE = 0x2;
    public static final int CHAR = 0x4;
    public static final int BYTE = 0x8;

    int getType();

    int getRows();

    int getColumns();

}
