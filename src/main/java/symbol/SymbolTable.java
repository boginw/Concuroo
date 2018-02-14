package symbol;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable {
    private List<Symbol> symbols;

    public SymbolTable(){
        symbols = new ArrayList<>();
    }

    public Symbol lookup(String literal) {
        return null;
    }

    public void insert(Symbol s){
        symbols.add(s);
    }
}
