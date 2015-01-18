package lsotnk.jpegutils;

import java.util.Comparator;

public abstract class Node {

    public static final int EOF_FREQUENCY = 0;

    public static final int NONE_FREQUENCY = -1;

    public static final int EOF_SYM = 0x00ffffff;

    public static final Comparator<Node> FREQUENCY_COMPARATOR = new NodeFrequencyComparator();

    public static final Comparator<Symbol> LENGTH_SYMBOL_COMPARATOR = new SymbolLengthComparator();

    public int frequency = 0;

    @Override
    public String toString() {
        return "[frequency=" + this.frequency + "]";
    }

    public static class Symbol extends Node {
        public int sym = 0;
        public int code = 0;
        public int length = 0;
    }

    public static class Tree extends Node {
        public Node left = null;
        public Node right = null;

        public int getHeight() {
            return doGetHeight(this); // Minus the root
        }

        private int doGetHeight(final Node node) {
            if (node == null) {
                return 0;
            }
            if (node instanceof Tree) {
                return 1 + Math.max(doGetHeight(((Tree) node).left), doGetHeight(((Tree) node).right));
            }
            return 0;
        }
    }

    private static final class NodeFrequencyComparator implements Comparator<Node> {

        @Override
        public int compare(final Node o1, final Node o2) {
            if (o1 == o2) {
                return 0;
            }
            if (o1.frequency < o2.frequency) {
                return -1;
            }
            // tree nodes are considered bigger than sym nodes
            if (o1.frequency == o2.frequency) {
                if (!(o1 instanceof Symbol)) {
                    return 1;
                }
                if (!(o2 instanceof Symbol)) {
                    return -1;
                }
            }
            return 1;
        }

    }

    private static final class SymbolLengthComparator implements Comparator<Symbol> {

        @Override
        public int compare(final Symbol o1, final Symbol o2) {

            if (o1.length < o2.length) {
                return -1;
            }
            if (o1.length == o2.length) {
                if (o1.sym < o2.sym) {
                    return -1;
                }
                return 1;
            }
            if (o1 == o2) {
                return 0;
            }
            return 1;
        }

    }
}
