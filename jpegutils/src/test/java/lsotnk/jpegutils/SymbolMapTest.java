package lsotnk.jpegutils;

import static org.junit.Assert.assertEquals;

import java.util.TreeSet;

import lsotnk.jpegutils.Node.Symbol;
import lsotnk.jpegutils.SymbolBuffer.IntSymBuffer;

import org.junit.Assert;
import org.junit.Test;

public class SymbolMapTest extends AbstractTest {

    @Test
    public void testBuild() {

        // 100: 5
        // 200: 2
        // 300: 6
        // 400: 1
        // 500: 3
        IntSymBuffer buffer = new IntSymBuffer(300);
        for (int symbol : new int[] { 300, 300, 500, 100, 400, 300, 100, 200, 300, 100, 100, 300, 200, 500, 300, 100, 500 }) {
            buffer.put(symbol);
        }
        TreeSet<Symbol> symbols = new TreeSet<Symbol>(Node.FREQUENCY_COMPARATOR);
        SymbolMap symbolMap = new SymbolMap();
        symbolMap.build(buffer, new CollectionAcceptor(symbols));

        assertEquals(5, symbols.size());
        for (Symbol node : symbols) {
            switch (node.sym) {
            case 100:
                Assert.assertEquals(5, node.frequency);
                break;
            case 200:
                Assert.assertEquals(2, node.frequency);
                break;
            case 300:
                Assert.assertEquals(6, node.frequency);
                break;
            case 400:
                Assert.assertEquals(1, node.frequency);
                break;
            case 500:
                Assert.assertEquals(3, node.frequency);
                break;
            default:
                break;
            }
        }

    }
}
