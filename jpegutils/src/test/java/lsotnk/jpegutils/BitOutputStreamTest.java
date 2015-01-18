package lsotnk.jpegutils;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

public class BitOutputStreamTest extends AbstractTest {

    @Test
    public void testWriteNoBits() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitOutputStream bos = new BitOutputStream(baos, 8);
        bos.writeBits(4, 0);
        bos.writeBits(4, 0);
        bos.flush();
        byte[] bytes = baos.toByteArray();
        assertEquals(0, bytes.length);
        bos.close();
    }

    @Test
    public void testWriteFourBits() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitOutputStream bos = new BitOutputStream(baos, 8);
        bos.writeBits(4, 4);
        bos.flush();
        byte[] bytes = baos.toByteArray();
        assertEquals(79, bytes[0]);
        bos.close();
    }

    @Test
    public void testWriteEightBits() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitOutputStream bos = new BitOutputStream(baos, 8);
        bos.writeBits(7, 8);
        bos.flush();
        byte[] bytes = baos.toByteArray();
        assertEquals(7, bytes[0]);
        bos.close();
    }

    @Test
    public void testWriteTwoTimes1() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitOutputStream bos = new BitOutputStream(baos, 8);
        bos.writeBits(4, 3);
        bos.writeBits(1, 3);
        bos.flush();
        byte[] bytes = baos.toByteArray();
        assertEquals((byte) 135, bytes[0]);
        bos.close();
    }

    @Test
    public void testWriteTwoTimes2() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitOutputStream bos = new BitOutputStream(baos, 8);
        bos.writeBits(5, 6);
        bos.writeBits(8, 7);
        bos.flush();
        byte[] bytes = baos.toByteArray();
        assertEquals((byte) 20, bytes[0]);
        assertEquals((byte) 71, bytes[1]);
        bos.close();
    }

    @Test
    public void testWriteTwoTimes3() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitOutputStream bos = new BitOutputStream(baos, 1);
        bos.writeBits(32, 8);
        bos.writeBits(4, 7);
        bos.flush();
        byte[] bytes = baos.toByteArray();
        assertEquals(2, bytes.length);
        assertEquals(32, bytes[0]);
        assertEquals(9, bytes[1]);
        bos.close();
    }

    @Test
    public void testWriteThreeTimes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitOutputStream bos = new BitOutputStream(baos, 1);
        bos.writeBits(32, 8);
        bos.writeBits(4, 4);
        bos.writeBits(8, 4);
        bos.flush();
        byte[] bytes = baos.toByteArray();
        assertEquals(2, bytes.length);
        assertEquals(32, bytes[0]);
        assertEquals(72, bytes[1]);
        bos.close();
    }

}
