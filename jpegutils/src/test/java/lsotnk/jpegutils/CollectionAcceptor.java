package lsotnk.jpegutils;

import java.util.Collection;

import lsotnk.jpegutils.Node.Symbol;

public class CollectionAcceptor implements SymbolAcceptor {

    private Collection<Symbol> collection;

    public CollectionAcceptor(Collection<Symbol> collection) {
        super();
        this.collection = collection;
    }

    @Override
    public void accept(Symbol symbol) {
        this.collection.add(symbol);
    }

}