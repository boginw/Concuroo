package nodes;

import symbol.Symbol;
import symbol.SymbolType;

public class Braces extends Symbol implements Node{
    public Braces(String literal){
        super(literal, 0, SymbolType.CONTROL);
    }

    @Override
    public int getVal(){
        return 0;
    }
}
