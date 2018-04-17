package concuroo.nodes.expression.binaryExpression;

import concuroo.nodes.expression.Expression;

public interface BinaryExpression extends Expression {
  void setFirstOperand(Expression left);
  Expression getFirstOperand();

  void setSecondOperand(Expression right);
  Expression getSecondOperand();

  String getOperator();
}
