package lsotnk.jpegutils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import lsotnk.jpegutils.Node.Symbol;
import lsotnk.jpegutils.Node.Tree;

public class PackageMerge {

    private List<Symbol> symbols = new ArrayList<Symbol>(512);

    public PackageMerge(Iterable<Symbol> symbols) {
        super();
        for (Symbol symbol : symbols) {
            this.symbols.add(symbol);
        }
    }

    public Iterable<Symbol> getSymbols() {
        return this.symbols;
    }

    /**
     * Computes and sets the lengths for the given sym nodes and a given maximum
     * tree height.
     */
    public void computeLengths(final int l) {
        ArrayList<Node> s = new ArrayList<Node>((l * 2) + 2); // The solutions
        TreeSet<Node> current = new TreeSet<Node>(Node.FREQUENCY_COMPARATOR); // ==L_d
        current.addAll(this.symbols);
        int x = current.size() - 1; // see paper: width(T) = n - 1
        int d = -l; // meaning: L_-1 = 0.5, L_-2 = 0.25, L_-3 = 0.125, ...
        // --> l = 4 --> L_-4 = 0.0625 = r
        int diadic = getMinDiadicExpansion(x); // 100 = 2^6 + 2^5 + 2^2,
        // getDiadicExpansion returns
        // 2

        while (x > 0) {

            // Precondition: There is no non-empty list left
            InternalUtils.checkState(current.size() > 0);
            int minwidth = 1 << diadic; // The minwidth is always in the
            // first array field
            double r = Math.pow(2, d);
            InternalUtils.checkState(r <= minwidth);
            if (r == minwidth) {
                s.add(current.pollFirst());
                diadic = getMinDiadicExpansion(x -= minwidth);
            }
            current = createPackage(current); // Represents P_d+1, the L_d is
            // discarded, because we assign
            // the current to the new current
            if (++d < 0) { // L_0+ are empty, so we don't
                // need to add any of the starting nodes
                current.addAll(this.symbols); // nodes represent the starting
                // sets
                // L_d+1 --> L_d+1 = merge(P_d+1, L_d+1)
            }

        }

        for (Node node : s) {
            setLengths(node);
        }
    }

    /**
     * Packages the given set of nodes as pairs beginning from the first node.
     * If the number of nodes is uneven the last node is ignored.
     *
     * @return A new package
     */
    private TreeSet<Node> createPackage(final TreeSet<Node> current) {
        TreeSet<Node> pckg = new TreeSet<Node>(Node.FREQUENCY_COMPARATOR);
        int pairs = current.size() / 2;
        for (int i = 0; i < pairs; i++) {
            Tree tree = new Tree();
            tree.left = current.pollFirst();
            tree.right = current.pollFirst();
            tree.frequency = tree.left.frequency + tree.right.frequency;
            pckg.add(tree);
        }
        return pckg;
    }

    /**
     * Counts the symbols and sets the lengths accordingly
     */
    private void setLengths(final Node node) {
        if (node instanceof Symbol) {
            ((Symbol) node).length += 1;
        } else if (node instanceof Tree) {
            setLengths(((Tree) node).left);
            setLengths(((Tree) node).right);
        }
    }

    private int getMinDiadicExpansion(int n) {
        int k = 0;
        while (n != 0) {
            int ak = n % 2;
            n = n / 2;
            if (ak != 0) {
                return k;
            }
            k++;
        }
        return -1;
    }

    public void computeCodesFromLengths() {
        computeFromLengths(false);
    }

    public Tree computeTreeFromLengths() {
        return computeFromLengths(true);
    }

    private Tree computeFromLengths(final boolean createTree) {
        TreeSet<Symbol> sortedSymbols = new TreeSet<Symbol>(Node.LENGTH_SYMBOL_COMPARATOR);
        for (Symbol symbol : this.symbols) {
            sortedSymbols.add(symbol);
        }
        Iterator<Symbol> iterator = sortedSymbols.iterator();
        Symbol sym = null;
        Tree root = new Tree();
        for (int nextCode = 0, previousBucket = 0; iterator.hasNext(); nextCode++) {
            sym = iterator.next();
            if (sym.length != previousBucket) {
                nextCode <<= sym.length - previousBucket;
                previousBucket = sym.length;
            }
            sym.code = nextCode;
            if (createTree) {
                createPath(root, nextCode, sym.length - 1, sym, 1);
            }
        }
        return root;
    }

    private Node createPath(final Tree parent, final int code, final int height, final Symbol symbol, final int length) {
        int currentBit = (code >> (height)) & 1;
        if (currentBit == 0) {
            if (parent.left == null) {
                if (height == 0) {
                    parent.left = symbol;
                    return parent.left;
                }
                parent.left = new Tree();
                parent.left.frequency = Node.NONE_FREQUENCY;
            }
            return createPath((Tree) parent.left, code, height - 1, symbol, length + 1);
        }
        if (parent.right == null) {
            if (height == 0) {
                parent.right = symbol;
                return parent.right;
            }
            parent.right = new Tree();
            parent.right.frequency = Node.NONE_FREQUENCY;
        }
        return createPath((Tree) parent.right, code, height - 1, symbol, length + 1);
    }
}
