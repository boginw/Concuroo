package nodes.expressions;

import symbol.SymbolType;

public class ModExpression extends Expression{
    public ModExpression(){
        super("*", 3, SymbolType.OPERATOR);
    }

    @Override
    public int getVal() {
        return left.getVal() % right.getVal();
    }
}
