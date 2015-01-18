package lsotnk.jpegutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

public class BitInputStreamTest extends AbstractTest {

    @Test
    public void testReadBits() throws IOException {
        BitInputStream bis = new BitInputStream(new ByteArrayInputStream(new byte[] { (byte) 186, 25 }), 8);
        assertEquals(11, bis.readBits(4));
        assertEquals(161, bis.readBits(8));
        bis.close();
    }

    @Test
    public void testReadBit() throws IOException {
        BitInputStream bis = new BitInputStream(new ByteArrayInputStream(new byte[] { (byte) 186, 25 }), 8);

        // 10111010
        assertEquals(1, bis.readBit());
        assertEquals(0, bis.readBit());
        assertEquals(1, bis.readBit());
        assertEquals(1, bis.readBit());
        assertEquals(1, bis.readBit());
        assertEquals(0, bis.readBit());
        assertEquals(1, bis.readBit());
        assertEquals(0, bis.readBit());

        // 00011001
        assertEquals(0, bis.readBit());
        assertEquals(0, bis.readBit());
        assertEquals(0, bis.readBit());
        assertEquals(1, bis.readBit());
        assertEquals(1, bis.readBit());
        assertEquals(0, bis.readBit());
        assertEquals(0, bis.readBit());
        assertEquals(1, bis.readBit());

        assertEquals(-1, bis.readBit());

        bis.close();
    }

    public void testIsEos() throws IOException {
        BitInputStream bis = new BitInputStream(new ByteArrayInputStream(new byte[] { 0x4a }), 4096);

        assertEquals(0, bis.readBit());
        assertEquals(1, bis.readBit());
        assertEquals(0, bis.readBit());
        assertEquals(0, bis.readBit());
        assertEquals(1, bis.readBit());
        assertEquals(0, bis.readBit());
        assertEquals(1, bis.readBit());
        assertEquals(0, bis.readBit());

        assertFalse(bis.isEos());

        bis.readBit();

        assertTrue(bis.isEos());

        bis.readBit();

        assertTrue(bis.isEos());

        bis.close();
    }

    @Test
    public void testGetLastBitsRead() throws IOException {
        BitInputStream bis = new BitInputStream(new ByteArrayInputStream(new byte[] { (byte) 0x78 }), 4096);
        int value = bis.readBits(8);

        assertEquals(0x78, BitInputStream.getValue(value, bis.getLastBitsRead()));
        assertEquals(8, bis.getLastBitsRead());
        assertFalse(bis.getLastBitsRead() < 8);

        value = bis.readBits(5);

        assertEquals(0, bis.getLastBitsRead());
        assertTrue(bis.getLastBitsRead() < 5);

        bis.close();
    }

    public void testMixed() throws IOException {
        BitInputStream bis = new BitInputStream(new ByteArrayInputStream(new byte[] { (byte) 0xfd, (byte) 0x5c }), 4096);

        int value = bis.readBits(8);
        int theValue = BitInputStream.getValue(value, bis.getLastBitsRead());
        int bitsRead = bis.getLastBitsRead();
        boolean eof = bis.isEos();

        assertEquals(0xfd, theValue);
        assertEquals(8, bitsRead);
        assertFalse(eof);

        value = bis.readBits(4);
        theValue = BitInputStream.getValue(value, bis.getLastBitsRead());
        bitsRead = bis.getLastBitsRead();
        eof = bitsRead < 4;

        assertEquals(5, theValue);
        assertEquals(4, bitsRead);
        assertFalse(eof);

        value = bis.readBits(6);
        theValue = BitInputStream.getValue(value, bis.getLastBitsRead());
        bitsRead = bis.getLastBitsRead();
        eof = bis.isEos();

        assertEquals(0xc, theValue);
        assertEquals(4, bitsRead);
        assertTrue(eof);

        value = bis.readBits(2);
        bitsRead = bis.getLastBitsRead();
        eof = bis.isEos();

        assertTrue(eof);
        assertEquals(0, bitsRead);

        bis.close();
    }

}
