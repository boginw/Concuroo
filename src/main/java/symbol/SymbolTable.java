package symbol;

import nodes.Node;

import java.util.Hashtable;

public class SymbolTable {
    private Hashtable<String, Node> symbols;

    public SymbolTable(){
        symbols = new Hashtable<>();
    }

    public Node lookup(String literal) {
        return symbols.get(literal);
    }

    public void insert(Node s){
        symbols.put(s.getLiteral(), s);
    }
}
