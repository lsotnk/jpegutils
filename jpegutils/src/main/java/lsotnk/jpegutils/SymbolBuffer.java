package lsotnk.jpegutils;

public interface SymbolBuffer {

    void put(int sym);

    void putQuickly(int sym);

    SymbolIterator singletonIterator();

    int size();

    void dispose();

    public static class IntSymBuffer implements SymbolBuffer, SymbolIterator {

        private int[] buffer;
        private int bufferPointer = 0;
        private final int size;
        private int itPointer = 0;

        public IntSymBuffer(final int size) {
            this.size = size;
            this.buffer = new int[size];
        }

        @Override
        public final void put(final int i) {
            ensureBuffer();
            this.buffer[this.bufferPointer++] = i;
        }

        @Override
        public final void putQuickly(final int i) {
            this.buffer[this.bufferPointer++] = i;
        }

        @Override
        public final int size() {
            return this.bufferPointer;
        }

        private final void ensureBuffer() {
            if (this.bufferPointer >= this.buffer.length) {
                int[] newArray = new int[this.buffer.length + this.size];
                System.arraycopy(this.buffer, 0, newArray, 0, this.bufferPointer);
                this.buffer = newArray;
            }
        }

        public void clean() {
            this.buffer = new int[this.size];
            this.bufferPointer = 0;
        }

        @Override
        public void dispose() {
            this.bufferPointer = 0;
            this.buffer = new int[0];
        }

        @Override
        public SymbolIterator singletonIterator() {
            return this;
        }

        @Override
        public int getNext() {
            return this.buffer[this.itPointer++];
        }

        @Override
        public boolean hasNext() {
            return this.itPointer < this.bufferPointer;
        }

        @Override
        public boolean reset() {
            this.itPointer = 0;
            return true;
        }

        public int[] getBuffer() {
            return this.buffer;
        }

    }

}
