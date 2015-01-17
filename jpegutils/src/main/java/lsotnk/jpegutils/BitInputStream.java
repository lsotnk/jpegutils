package lsotnk.jpegutils;

import static java.util.Objects.requireNonNull;
import static lsotnk.jpegutils.InternalUtils.ALL_BITS;
import static lsotnk.jpegutils.InternalUtils.SINGLE_BITS;
import static lsotnk.jpegutils.InternalUtils.checkArgument;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * A buffered input stream which reads bits from the underlying input stream
 */
public class BitInputStream implements Closeable {

    /**
     * A convenience method. Returns the value masked with a mask (
     * {@link lsotnk.InternalUtils.Utils#ALL_BITS}) for the given size.
     */
    public static int getValue(final int value, final int size) {
        return ALL_BITS[size] & value;
    }

    /**
     * The buffer
     */
    private final byte[] buffer;

    /**
     * The actual buffer length
     */
    private int bufferLength = 0;

    /**
     * Points to the next byte in the buffer
     */
    private int bufferPointer = 0;

    /**
     * The underlying input stream
     */
    private final InputStream in;

    /**
     * Holds to the current byte
     */
    private byte singleByteBuffer = 0;

    /**
     * Points to the current position in the current byte
     */
    private int singleByteBufferPointer = -1;

    /**
     * The amount of bits read by the last read operation.
     */
    private int lastBitsRead = 0;

    /**
     * Indicates whether the end of stream has been reached.
     */
    private boolean isEos = false;

    /**
     * Creates a new bit input stream for the given underlying input stream and
     * buffer size.
     */
    public BitInputStream(final InputStream in, final int size) {
        this.in = requireNonNull(in, "Input stream must not be null"); //$NON-NLS-1$
        checkArgument(size > 0, "Buffer must be greater than 0"); //$NON-NLS-1$
        this.buffer = new byte[size];
    }

    /**
     * Returns the amount of bits read in the last read operation,
     * {@link #readBit()} or {@link #readBits(int)}
     */
    public int getLastBitsRead() {
        return this.lastBitsRead;
    }

    /**
     * Returns <code>true</code> if the end of stream has been reached,
     * otherwise <code>false</code>
     */
    public boolean isEos() {
        return this.isEos;
    }

    /**
     * Reads the given amount (0-32) of bits. The amount of bits to read should
     * be <code>&lt;=32</code>. The amount actually read can be obtained by
     * {@link #getLastBitsRead()}.
     */
    public int readBits(final int size) throws IOException {
        checkArgument((size > 0) && (size <= 32), "Size must be between 0 and 32");
        int value = 0, bitsRead = 0;
        for (int bit = 0; (bitsRead < size) && ((bit = readBit()) != -1); bitsRead++, value = (value << 1) | bit) {
        }
        this.lastBitsRead = bitsRead;
        return value;
    }

    /**
     * Reads a bit and returns 0 or 1. Returns -1, if the end of stream has been
     * reached.
     */
    public int readBit() throws IOException {
        if (this.singleByteBufferPointer < 0) {
            ensureSingleByteBuffer();
            if (this.isEos) {
                this.lastBitsRead = 0;
                return -1;
            }
        }
        this.lastBitsRead = 1;
        if ((this.singleByteBuffer & SINGLE_BITS[this.singleByteBufferPointer-- + 1]) == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * Reads the next byte from the buffer and resets the single byte buffer
     * pointer, if necessary.
     */
    private final void ensureSingleByteBuffer() throws IOException {
        if (this.singleByteBufferPointer < 0) {
            ensureBuffer();
            if (this.bufferLength <= 0) {
                this.isEos = true;
                return;
            }
            this.singleByteBufferPointer = 7;
            this.isEos = false;
            this.singleByteBuffer = this.buffer[this.bufferPointer++];
        }
    }

    /**
     * Reads the next bytes from the underlying stream and resets the buffer
     * pointer, if necessary.
     */
    private final void ensureBuffer() throws IOException {
        if (this.bufferPointer == this.bufferLength) {
            this.bufferLength = this.in.read(this.buffer);
            this.bufferPointer = 0;
        }
    }

    @Override
    public void close() throws IOException {
        this.in.close();
    }

}
