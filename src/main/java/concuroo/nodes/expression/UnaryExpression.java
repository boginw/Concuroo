package concuroo.nodes.expression;

public interface UnaryExpression extends Expression {
  Expression getFirstOperand();
  void setFirstOperand(Expression firstOperand);

  String getOperator();
  void setOperator(String operator);
}