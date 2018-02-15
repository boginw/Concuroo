package nodes.statements;

import nodes.Node;
import symbol.SymbolType;

public class ExpressionStatement extends Statement {
    private final Node expr;

    public ExpressionStatement(Node expr) {
        super("", 0, SymbolType.INT_LITERAL);
        this.expr = expr;
    }

    @Override
    public int getVal() {
        return expr.getVal();
    }
}
