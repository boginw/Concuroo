package nodes.expressions.operators.unary;

import nodes.expressions.Expression;
import nodes.expressions.operators.Operator;

public interface UnaryOperator extends Operator{
    Expression getLeft();
    void setLeft(Expression e);
}
