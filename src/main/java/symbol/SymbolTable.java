package symbol;

import java.util.Hashtable;

public class SymbolTable {
    private Hashtable<String, Symbol> symbols;

    public SymbolTable(){
        symbols = new Hashtable<>();
    }

    public Symbol lookup(String literal) {
        return symbols.get(literal);
    }

    public void insert(Symbol s){
        symbols.put(s.literal, s);
    }
}
