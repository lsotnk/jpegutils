package lsotnk.jpegutils;

import lsotnk.jpegutils.Node.Symbol;

public class SymbolMap implements SymbolLookup, SymbolAcceptor {

    private Symbol[] symbols;

    public SymbolMap() {
        this(0xffff + 16);
    }

    public SymbolMap(final int size) {
        this.symbols = new Symbol[size];
    }

    /**
     * Some array fields might be null
     */
    public Symbol[] asArray() {
        return this.symbols;
    }

    @Override
    public final Symbol get(final int sym) {
        return this.symbols[sym];
    }

    public final Symbol put(final int sym, final Symbol symbol) {
        this.symbols[sym] = symbol;
        return symbol;
    }

    @Override
    public void accept(Symbol symbol) {
        put(symbol.sym, symbol);

    }

    public final void clear() {
        this.symbols = new Symbol[this.symbols.length];
    }

    public void build(final SymbolIterator symbols, final SymbolAcceptor acceptor) {
        int sym;
        for (Symbol symbol; symbols.hasNext(); symbol.frequency++) {
            if (null == (symbol = get(sym = symbols.getNext()))) {
                symbol = new Symbol();
                symbol.sym = sym;
                symbol.frequency = 0;
                put(sym, symbol);
                acceptor.accept(symbol);
            }
        }
    }

}
