package lsotnk.jpegutils;

public abstract class DCT {

    /**
     * The size of the matrix, must be 8
     */
    protected static final int SIZE = 8;

    /**
     * Equals <code>1/Math.sqrt(2)</code>
     */
    private static final double _1_SQRT_2 = 1 / Math.sqrt(2);

    /**
     * Returns <code>1</code> if <code>n</code> is not <code>0</code>, otherwise
     * {@link #_1_SQRT_2};
     */
    protected static double getCValue(final int n) {
        return n == 0 ? _1_SQRT_2 : 1;
    }

    protected static void check(final MatrixDescription in) {
        if (!(((in.getRows() % SIZE) == 0) && ((in.getColumns() % SIZE) == 0))) {
            throw new IllegalArgumentException();
        }
    }

    public abstract Matrix dct(final MatrixView in);

    public abstract Matrix idct(final MatrixView in);

}
