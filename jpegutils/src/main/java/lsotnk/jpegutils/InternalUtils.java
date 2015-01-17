package lsotnk.jpegutils;

class InternalUtils {

    /**
     * Masks single bits:<br>
     * <br>
     * <code>
     * SINGLE_BITS[0] = ... 0000 0000<br>
     * SINGLE_BITS[1] = ... 0000 0001<br>
     * SINGLE_BITS[2] = ... 0000 0010<br>
     * ...<br>
     * SINGLE_BITS[8] = ... 1000 0000<br>
     * ...<br>}
     * </code>
     */
    static final int SINGLE_BITS[] = new int[Integer.SIZE + 1];
    static {
        for (int i = 0; i < SINGLE_BITS.length; SINGLE_BITS[i] = i == 0 ? 0 : 1 << (i - 1), i++) {
        }
    }

    /**
     * Masks bits series:<br>
     * <br>
     * <code>
     * ALL_BITS[0] = ... 0000 0000<br>
     * ALL_BITS[1] = ... 0000 0001<br>
     * ALL_BITS[2] = ... 0000 0011<br>
     * ALL_BITS[3] = ... 0000 0111<br>
     * ...<br>
     * ALL_BITS[8] = ... 1111 1111<br>
     * ...<br>
     * </code>
     */
    static final int ALL_BITS[] = new int[Integer.SIZE + 1];
    static {
        for (int i = 0; i < ALL_BITS.length; ALL_BITS[i] = i == 0 ? 0 : (ALL_BITS[i - 1] << 1) | 1, i++) {
        }
    }

    static final void checkArgument(final boolean expression, final String errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
