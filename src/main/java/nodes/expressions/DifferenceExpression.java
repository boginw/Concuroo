package nodes.expressions;

import symbol.SymbolType;

public class DifferenceExpression extends Expression {
    public DifferenceExpression(){
        super("-", 4, SymbolType.OPERATOR);
    }

    @Override
    public int getVal() {
        return left.getVal() - right.getVal();
    }

}
