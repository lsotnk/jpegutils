package lsotnk.jpegutils;


/**
 * Calculates the category and the bits for the given value.
 *
 * <pre>
 *         value        | category |       bits
 * ---------------------+----------+------------------
 *           0          |     0    |        -
 *         -1,1         |     1    |       0,1
 *       -3,-2,2,3      |     2    |   00,01,10,11
 *          ...         |    ...   |       ...
 *   -32767,...-16384   |          |
 *    16384,...,32767   |    15    |       ...
 * </pre>
 */
public class Categories {

    private static final int NCATEGORIES = 16;
    private static final byte[] categories = new byte[InternalUtils.ALL_BITS[NCATEGORIES]];
    private static final int ADD = ((1 << NCATEGORIES) / 2) - 1;

    static {
        categories[0 + ADD] = 0;
        for (byte i = 1; i < NCATEGORIES; i++) {
            int from = -((1 << i) - 1);
            int to = -(1 << (i - 1));
            for (int right = -to, left = from; left <= to; left++, right++) {
                categories[left + ADD] = i;
                categories[right + ADD] = i;
            }
        }
    }

    /**
     * Returns the category (<code>0</code> to <code>15</code>) for the given
     * value (<code>-32767</code> to <code>32767</code>)
     */
    public static final int getCategory(final int value) {
        return categories[value + ADD] /* & 0xff */;
    }

    /**
     * Returns the bits of size <code>x</code> for the given value.<br/>
     * To determine <code>x</code> use {@link #getCategory(int)}.
     */
    public static final int getBits(final int value) {
        return value > -1 ? value : value - 1;
    }

}
