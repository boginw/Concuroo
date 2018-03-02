package concuroo.nodes.expressions.operators.binary;

import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.Operator;

/**
 * An operator with two operands
 */
public interface BinaryOperator extends Operator {

  /**
   * Gets the left side of the expression
   *
   * @return Expression
   */
  Expression getOperand();

  /**
   * Sets the left side of the expression
   *
   * @param e Expression
   */
  void setOperand(Expression e);

  /**
   * Gets the right side of the expression
   *
   * @return Expression
   */
  Expression getSecondOperand();

  /**
   * Sets the right side of the expression
   *
   * @param e Expression
   */
  void setSecondOperand(Expression e);
}
