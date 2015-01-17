package lsotnk.jpegutils;

import static org.junit.Assert.assertEquals;
import lsotnk.jpegutils.matrix.IntMatrix;

import org.junit.Test;

public class QuantizationTest {

    public static Matrix TESTM = new IntMatrix(16, 16, new int[][] {
            // @formatter:off
            { 100,  1, 1, 1, 1, 2, 3, 3,  60,  1, 1, 1, 1, 2, 3, 3 },
            {   1, 50, 1, 1, 1, 3, 3, 3,  1, 50, 1, 1, 1, 3, 3, 3 },
            { 1, 1, 1, 1, 2, 3, 3, 3,     1, 1, 1, 1, 2, 3, 3, 3 },
            { 1, 1, 1, 2, 3, 4, 4, 20,    1, 1, 1, 2, 3, 4, 4, 20 },
            { 1, 1, 2, 3, 3, 5, 5, 4,     1, 1, 2, 3, 3, 5, 5, 4 },
            { 1, 2, 3, 3, 4, 5, 5, 4,     1, 2, 3, 3, 4, 5, 5, 4 },
            { 2, 3, 4, 4, 5, 6, 6, 5,     2, 3, 4, 4, 5, 6, 6, 5 },
            { 4, 4, 5, 5, 5, 5, 5, 30,    4, 4, 5, 5, 5, 5, 5, 15 },
            { 100, 1, 1, 1, 1, 2, 3, 3, 60, 1, 1, 1, 1, 2, 3, 3 },
            { 1, 50, 1, 1, 1, 3, 3, 3,    1, 50, 1, 1, 1, 3, 3, 3 },
            { 1, 1, 1, 1, 2, 3, 3, 3,     1, 1, 1, 1, 2, 3, 3, 3 },
            { 1, 1, 1, 2, 3, 4, 4, 20,    1, 1, 1, 2, 3, 4, 4, 20 },
            { 1, 1, 2, 3, 3, 5, 5, 4,     1, 1, 2, 3, 3, 5, 5, 4 },
            { 1, 2, 3, 3, 4, 5, 5, 4,     1, 2, 3, 3, 4, 5, 5, 4 },
            { 2, 3, 4, 4, 5, 6, 6, 5,     2, 3, 4, 4, 5, 6, 6, 5 },
            { 4, 4, 5, 5, 5, 5, 5, 30,    4, 4, 5, 5, 5, 5, 5, 15 }
            // @formatter:on
            });

    public static Matrix TESTM_Q = new IntMatrix(8, 8, new int[][] {
            // @formatter:off
            { 20, 1, 1, 1, 1, 2, 3, 3 },
            { 1, 5, 1, 1, 1, 3, 3, 3 },
            { 1, 1, 1, 1, 2, 3, 3, 3 },
            { 1, 1, 1, 2, 3, 4, 4, 2 },
            { 1, 1, 2, 3, 3, 5, 5, 4},
            { 1, 2, 3, 3, 4, 5, 5, 4 },
            { 2, 3, 4, 4, 5, 6, 6, 5 },
            { 4, 4, 5, 5, 5, 5, 5, 3 }
            // @formatter:on
            });

    @Test
    public void test() {
        Quantization.quantize(TESTM, TESTM_Q);
        assertEquals(5, TESTM.getInt(0, 0));
        assertEquals(10, TESTM.getInt(3, 7));
        assertEquals(5, TESTM.getInt(7, 15));
        assertEquals(3, TESTM.getInt(0, 8));
        assertEquals(5, TESTM.getInt(8, 0));
        assertEquals(10, TESTM.getInt(11, 7));
        assertEquals(5, TESTM.getInt(15, 15));
        assertEquals(3, TESTM.getInt(8, 8));

    }

}
