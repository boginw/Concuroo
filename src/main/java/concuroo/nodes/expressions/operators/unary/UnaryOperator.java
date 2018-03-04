package concuroo.nodes.expressions.operators.unary;

import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.Operator;

/**
 * An operator with a single operator
 */
public interface UnaryOperator extends Operator {

  /**
   * Gets the operand of the expression
   *
   * @return Expression
   */
  Expression getOperand();

  /**
   * Sets the operand of the expression
   *
   * @param e Expression
   */
  void setOperand(Expression e);

  /**
   * Checks whether this operator is prefix or postfix
   *
   * @return True or False
   */
  boolean isPrefix();
}
