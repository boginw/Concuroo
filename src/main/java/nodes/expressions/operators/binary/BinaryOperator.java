package nodes.expressions.operators.binary;

import nodes.expressions.Expression;
import nodes.expressions.operators.Operator;

public interface BinaryOperator extends Operator {
    Expression getLeft();
    Expression getRight();
    void setLeft(Expression e);
    void setRight(Expression e);
}
