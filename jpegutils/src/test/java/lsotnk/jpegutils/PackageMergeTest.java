package lsotnk.jpegutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import lsotnk.jpegutils.Node.Symbol;
import lsotnk.jpegutils.Node.Tree;
import lsotnk.jpegutils.SymbolBuffer.IntSymBuffer;

import org.junit.Test;

public class PackageMergeTest extends AbstractTest {

    @Test
    public void testComputeLengths2() {
        int maxHeight = 16;
        int contentSize = 1000000;
        int symSize = 65535; // + 1 (eof node)

        SymbolIterator it = createContent(contentSize, symSize);
        List<Symbol> symbols = new ArrayList<Symbol>();
        SymbolMap symbolMap = new SymbolMap();
        symbolMap.build(it, new CollectionAcceptor(symbols));

        Symbol eofSymbol = new Symbol();
        eofSymbol.frequency = Node.EOF_FREQUENCY;
        eofSymbol.sym = Node.EOF_SYM;
        symbols.add(eofSymbol);

        PackageMerge packageMerge = new PackageMerge(symbols);
        packageMerge.computeLengths(maxHeight);
        Tree rootTree = packageMerge.computeTreeFromLengths();
        int height = rootTree.getHeight();

        assertTrue(maxHeight >= height);

        boolean eofFound = false;
        int eofHeight = 0;
        Node node = rootTree;
        while (true) {
            if (node instanceof Tree) {
                node = ((Tree) node).right;
            }
            eofHeight++;
            if (node instanceof Symbol) {
                Symbol symbol = (Symbol) node;
                if (symbol == eofSymbol) {
                    eofFound = true;
                    break;
                } else {
                    fail("Could not find eof symbol");
                }

            }
        }
        assertEquals(height, eofHeight);
        assertTrue(eofFound);

    }

    @Test
    public void testComputeLengths1() {

        TreeSet<Symbol> symbols = new TreeSet<Symbol>(Node.FREQUENCY_COMPARATOR);
        symbols.add(createSymbol(1, 1));
        symbols.add(createSymbol(2, 2));
        symbols.add(createSymbol(3, 3));
        symbols.add(createSymbol(4, 4));
        symbols.add(createSymbol(5, 5));
        symbols.add(createSymbol(6, 6));
        symbols.add(createSymbol(7, 7));
        symbols.add(createSymbol(8, 8));
        symbols.add(createSymbol(9, 9));
        symbols.add(createSymbol(10, 9));
        symbols.add(createSymbol(10, 9));
        symbols.add(createSymbol(20, 20));
        symbols.add(createSymbol(20, 20));
        symbols.add(createSymbol(30, 30));
        symbols.add(createSymbol(0, 0));
        symbols.add(createSymbol(60, 60));

        PackageMerge packageMerge = new PackageMerge(symbols);
        packageMerge.computeLengths(7);

        assertSymbol(symbols.pollFirst(), 0, 7);
        assertSymbol(symbols.pollFirst(), 1, 7);
        assertSymbol(symbols.pollFirst(), 2, 6);
        assertSymbol(symbols.pollFirst(), 3, 6);
        assertSymbol(symbols.pollFirst(), 4, 6);
        assertSymbol(symbols.pollFirst(), 5, 5);
        assertSymbol(symbols.pollFirst(), 6, 5);
        assertSymbol(symbols.pollFirst(), 7, 5);
        assertSymbol(symbols.pollFirst(), 8, 5);
        assertSymbol(symbols.pollFirst(), 9, 4);
        assertSymbol(symbols.pollFirst(), 10, 4);
        assertSymbol(symbols.pollFirst(), 10, 4);
        assertSymbol(symbols.pollFirst(), 20, 3);
        assertSymbol(symbols.pollFirst(), 20, 3);
        assertSymbol(symbols.pollFirst(), 30, 3);
        assertSymbol(symbols.pollFirst(), 60, 2);

    }

    private void assertSymbol(final Symbol symbol, final int expectedSym, final int expectedLength) {
        assertEquals(expectedLength, symbol.length);
        assertEquals(expectedSym, symbol.sym);
    }

    private Symbol createSymbol(final int sym, final int freq) {
        Symbol symbol = new Symbol();
        symbol.sym = sym;
        symbol.frequency = freq;
        return symbol;
    }

    private SymbolIterator createContent(final int size, final int next) {
        IntSymBuffer out = new IntSymBuffer(size + 2);
        Random rnd = new Random(size);
        for (int i = 0; i < size; i++) {
            out.put(rnd.nextInt(next));

        }
        return out.singletonIterator();

    }

}
