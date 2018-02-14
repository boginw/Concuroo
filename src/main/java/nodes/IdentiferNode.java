package nodes;

import symbol.Symbol;
import symbol.SymbolType;

public class IdentiferNode extends Symbol implements Node {
    private int value = 0;

    public IdentiferNode() {
        super("IDENT", 15, SymbolType.IDENT);
    }

    public void setValue(int val){
        value = val;
    }

    @Override
    public int getVal(){
        return value;
    }
}
