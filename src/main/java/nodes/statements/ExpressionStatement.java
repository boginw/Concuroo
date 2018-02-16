package nodes.statements;

import nodes.Node;

public class ExpressionStatement implements Statement {
    private final Node expr;

    public ExpressionStatement(Node expr) {
        this.expr = expr;
    }

    @Override
    public String getLiteral() {
        return expr.getLiteral();
    }

    @Override
    public int getVal() {
        return expr.getVal();
    }
}
