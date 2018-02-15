package nodes.expressions;

import symbol.SymbolType;

public class ProductExpression extends Expression{
    public ProductExpression(){
        super("*", 3, SymbolType.OPERATOR);
    }

    @Override
    public int getVal() {
        return left.getVal() * right.getVal();
    }
}
