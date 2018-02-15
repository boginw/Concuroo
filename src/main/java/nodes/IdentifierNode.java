package nodes;

import symbol.Symbol;
import symbol.SymbolType;

public class IdentifierNode extends Symbol implements Node {
    private int value = 0;

    public IdentifierNode(String literal) {
        super(literal, 15, SymbolType.IDENT);
    }

    public void setValue(int val){
        value = val;
    }

    @Override
    public int getVal(){
        return value;
    }
}
