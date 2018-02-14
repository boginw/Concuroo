package nodes.expressions;

import symbol.SymbolType;

public class DivideExpression extends Expression{
    public DivideExpression(){
        super("*", 3, SymbolType.OPERATOR);
    }

    @Override
    public int getVal() {
        return left.getVal() / right.getVal();
    }
}
