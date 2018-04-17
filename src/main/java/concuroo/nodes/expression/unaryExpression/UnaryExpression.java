package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.expression.Expression;

public interface UnaryExpression extends Expression {

  Expression getFirstOperand();

  void setFirstOperand(Expression firstOperand);

  String getOperator();

  void setOperator(String operator);
}
