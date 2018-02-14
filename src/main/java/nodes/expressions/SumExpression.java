package nodes.expressions;

import symbol.SymbolType;

public class SumExpression extends Expression {
    public SumExpression(){
        super("+", 4, SymbolType.OPERATOR);
    }
    
    @Override
    public int getVal() {
        return left.getVal() + right.getVal();
    }
}
