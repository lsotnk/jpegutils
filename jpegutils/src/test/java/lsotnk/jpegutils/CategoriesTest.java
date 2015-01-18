package lsotnk.jpegutils;

import static lsotnk.jpegutils.Categories.getBits;
import static lsotnk.jpegutils.Categories.getCategory;
import static lsotnk.jpegutils.InternalUtils.ALL_BITS;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CategoriesTest extends AbstractTest {

    @Test
    public void testCategories() {
        for (int i = -32767; i < 32768; i++) {
            int v = Math.abs(i);
            if (i == 0) {
                assertEquals(0, Categories.getCategory(i));
                testBits(i, 0, 0);
            } else if (v == 1) {
                assertEquals(1, Categories.getCategory(i));
                testBits(i, 1, 1);
            } else if ((v >= 2) && (v <= 3)) {
                assertEquals(2, Categories.getCategory(i));
                testBits(i, 2, 3);
            } else if ((v >= 4) && (v <= 7)) {
                assertEquals(3, Categories.getCategory(i));
                testBits(i, 4, 7);
            } else if ((v >= 8) && (v <= 15)) {
                assertEquals(4, Categories.getCategory(i));
                testBits(i, 8, 15);
            } else if ((v >= 16) && (v <= 31)) {
                assertEquals(5, Categories.getCategory(i));
                testBits(i, 16, 31);
            } else if ((v >= 32) && (v <= 63)) {
                assertEquals(6, Categories.getCategory(i));
                testBits(i, 32, 63);
            } else if ((v >= 64) && (v <= 127)) {
                assertEquals(7, Categories.getCategory(i));
                testBits(i, 64, 127);
            } else if ((v >= 128) && (v <= 255)) {
                assertEquals(8, Categories.getCategory(i));
                testBits(i, 128, 255);
            } else if ((v >= 256) && (v <= 511)) {
                assertEquals(9, Categories.getCategory(i));
                testBits(i, 256, 511);
            } else if ((v >= 512) && (v <= 1023)) {
                assertEquals(10, Categories.getCategory(i));
                testBits(i, 512, 1023);
            } else if ((v >= 1024) && (v <= 2047)) {
                assertEquals(11, Categories.getCategory(i));
                testBits(i, 1024, 2047);
            } else if ((v >= 2048) && (v <= 4095)) {
                assertEquals(12, Categories.getCategory(i));
                testBits(i, 2048, 4095);
            } else if ((v >= 4096) && (v <= 8191)) {
                assertEquals(13, Categories.getCategory(i));
                testBits(i, 4096, 8191);
            } else if ((v >= 8192) && (v <= 16383)) {
                assertEquals(14, Categories.getCategory(i));
                testBits(i, 8192, 16383);
            } else if ((v >= 16384) && (v <= 32767)) {
                assertEquals(15, Categories.getCategory(i));
                testBits(i, 16384, 32767);
            }

        }
    }

    private void testBits(final int value, final int from, final int to) {
        if (value > 0) {
            assertEquals(value, Categories.getBits(value));
        } else if (value < 0) {
            int expected = to + value;
            assertEquals(expected, Categories.getBits(value) & ALL_BITS[Categories.getCategory(value)]);
        } else if (value == 0) {
            assertEquals(0, Categories.getBits(value));
        }
    }

    @Test
    public void test() {
        assertEquals(0, getCategory(0));
        assertEquals(0, getBits(0));

        assertEquals(1, getCategory(-1));
        assertEquals(1, getCategory(1));
        assertEquals(0, getBits(-1) & ALL_BITS[getCategory(-1)]);
        assertEquals(1, getBits(1));

        assertEquals(3, getCategory(-7));
        assertEquals(3, getCategory(4));
        assertEquals(0, getBits(-7) & ALL_BITS[getCategory(-7)]);
        assertEquals(7, getBits(7));
        assertEquals(3, getBits(-4) & ALL_BITS[getCategory(-4)]);

        assertEquals(8, getCategory(-255));
        assertEquals(8, getCategory(128));
        assertEquals(8, getCategory(255));
        assertEquals(0, getBits(-255) & ALL_BITS[getCategory(-1)]);
        assertEquals(255, getBits(255));

        assertEquals(16, getBits(16));

        assertEquals(0, getBits(-32767) & ALL_BITS[getCategory(-32767)]);
        assertEquals(1, getBits(-32766) & ALL_BITS[getCategory(-32766)]);
        assertEquals(16383, getBits(-16384) & ALL_BITS[getCategory(-16384)]);
        assertEquals(32767, getBits(32767) & ALL_BITS[getCategory(32767)]);
        assertEquals(16384, getBits(16384) & ALL_BITS[getCategory(16384)]);
        assertEquals(14, getCategory(16383));
        assertEquals(14, getCategory(16383));
    }

}
