package concuroo.nodes.expression;

import concuroo.nodes.expression.binaryExpression.BinaryExpression;

public class RelationalExpression implements BinaryExpression {

  @Override
  public void setFirstOperand(Expression left) {

  }

  @Override
  public Expression getFirstOperand() {
    return null;
  }

  @Override
  public void setSecondOperand(Expression right) {

  }

  @Override
  public Expression getSecondOperand() {
    return null;
  }

  @Override
  public String getOperator() {
    return null;
  }

  @Override
  public String getLiteral() {
    return null;
  }
}