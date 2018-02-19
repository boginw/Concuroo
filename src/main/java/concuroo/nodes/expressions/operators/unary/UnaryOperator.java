package concuroo.nodes.expressions.operators.unary;

import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.Operator;

/**
 * An operator with a single operator
 */
public interface UnaryOperator extends Operator{
    /**
     * Gets the left side of the expression
     * @return Expression
     */
    Expression getLeft();

    /**
     * Sets the left side of the expression
     * @param e Expression
     */
    void setLeft(Expression e);
}
