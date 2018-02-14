package nodes;

import symbol.Symbol;
import symbol.SymbolType;

public class IntegerNode extends Symbol implements Node{

    public IntegerNode(String literal){
        super(literal, 16, SymbolType.INT_LITERAL);
    }

    @Override
    public int getVal(){
        return Integer.parseInt(literal);
    }
}
