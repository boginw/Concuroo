package concuroo.nodes.expression;

import concuroo.nodes.Expression;

/**
 * This interface represents all expression of the form OPERATOR EXPRESSION, and EXPRESSION OPERATOR
 * where operator includes, but is not limited to: ++, --, +, -, *, &, <- and sizeof
 */
public interface UnaryExpression extends Expression {

  /**
   * Gets the first operand of the expression
   *
   * @return The first operand of the expression
   */
  Expression getFirstOperand();

  /**
   * Sets the fist operand of the expression
   *
   * @param firstOperand The first operand of the expression
   */
  void setFirstOperand(Expression firstOperand);

  /**
   * Gets the operator in the expression
   *
   * @return The operator in the expression
   */
  String getOperator();
}
