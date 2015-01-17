package lsotnk.jpegutils;

import static java.util.Objects.requireNonNull;
import static lsotnk.jpegutils.InternalUtils.ALL_BITS;
import static lsotnk.jpegutils.InternalUtils.SINGLE_BITS;
import static lsotnk.jpegutils.InternalUtils.checkArgument;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A buffered output stream which writes bits and bytes to the underlying output
 * stream
 */
public class BitOutputStream extends OutputStream {

    /**
     * The buffer
     */
    private final byte[] buffer;

    /**
     * The buffer pointer pointing to the next free position
     */
    private int bufferPointer = 0;

    /**
     * The current byte
     */
    private byte singleByteBuffer = 0;

    /**
     * Points to the next free bit in the singleByteBuffer:<br>
     * <br>
     * <code>0</code> points to bit <code>xbbb bbbb</code><br>
     * <code>1</code> points to bit <code>bxbb bbbb</code><br>
     * ...<br>
     * <code>7</code> points to bit <code>bbbb bbbx</code><br>
     *
     */
    private int singleByteBufferPointer = 0;

    /**
     * The underlying output stream
     */
    private final OutputStream os;

    /**
     * The filler bit, used when the buffer is flushed
     */
    private int fillerBit = 1;

    /**
     * Creates a new bit stream for the given underlying output stream and
     * buffer size
     */
    public BitOutputStream(final OutputStream os, final int buffer) {
        checkArgument(buffer > 0, "Buffer must be greater than 0"); //$NON-NLS-1$
        this.os = requireNonNull(os, "Output stream must not be null"); //$NON-NLS-1$
        this.buffer = new byte[buffer];
    }

    /**
     * Returns the underlying output stream
     */
    public OutputStream getUnderlyingOutputStream() {
        return this.os;
    }

    /**
     * Sets the filler bit used by {@link #flush()}
     */
    public void setFillerBit(final int bit) {
        this.fillerBit = bit & 1;
    }

    /**
     * Writes a given amount of bits of a given value.<br>
     * <br>
     * <code>
     * value = ... 0010 0110 1110<br>
     * size = 7<br>
     * --&gt; 110 1110
     * </code>
     *
     * The amount of bits, should be between 0 and {@link Integer#SIZE}
     */
    public void writeBits(final int value, final int size) throws IOException {
        checkArgument((size >= 0) && (size <= 32), "Size must be between 0 and 32");
        for (int i = size; i > 0; i--, this.singleByteBufferPointer++) {
            ensureSingleByteBuffer();
            if (0 == (value & SINGLE_BITS[i])) {
                continue;
            }
            this.singleByteBuffer |= SINGLE_BITS[8 - this.singleByteBufferPointer];
        }

    }

    /**
     * Writes the lsb of the given value
     */
    public void writeBit(final int value) throws IOException {
        ensureSingleByteBuffer();
        if ((value & 1) == 1) {
            this.singleByteBuffer |= SINGLE_BITS[8 - this.singleByteBufferPointer];
        }
        this.singleByteBufferPointer++;
    }

    @Override
    public void write(final int b) throws IOException {
        /*
         * The idea is to test whether the single byte buffer is completely free
         * and write the whole byte at once. This is faster than writing bits
         * individually.
         */
        if (this.singleByteBufferPointer == 0) {
            ensureBuffer();
            this.buffer[this.bufferPointer++] = (byte) b;
            this.singleByteBuffer = 0;
        } else {
            writeBits(b, 8);
        }
    }

    /**
     * Makes sure that there is at least one free bit in the single byte buffer.
     */
    private final void ensureSingleByteBuffer() throws IOException {
        if (this.singleByteBufferPointer == 8) {
            this.singleByteBufferPointer = 0;
            ensureBuffer();
            this.buffer[this.bufferPointer++] = this.singleByteBuffer;
            this.singleByteBuffer = 0;
        }
    }

    /**
     * Makes sure that there is at least one free byte in the buffer.
     */
    private final void ensureBuffer() throws IOException {
        if (this.bufferPointer == this.buffer.length) {
            flushBuffer();
            this.bufferPointer = 0;
        }
    }

    /**
     * Flushes the buffer without the single byte buffer
     */
    private final void flushBuffer() throws IOException {
        this.os.write(this.buffer, 0, this.bufferPointer);
    }

    /**
     * Flushes the buffer and the single byte buffer to the underlying stream
     * filling the remaining bits with the filler bit
     */
    @Override
    public void flush() throws IOException {
        flushBuffer();
        if (this.singleByteBufferPointer > 0) {
            this.singleByteBuffer = this.fillerBit == 1 ? (byte) (this.singleByteBuffer | ALL_BITS[8 - this.singleByteBufferPointer]) : this.singleByteBuffer;
            this.os.write(this.singleByteBuffer);
        }
    }

    @Override
    public void close() throws IOException {
        this.os.close();
    }

}
