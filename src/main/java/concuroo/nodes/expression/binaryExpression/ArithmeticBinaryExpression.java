package concuroo.nodes.expression.binaryExpression;

import concuroo.nodes.expression.CanSetOperator;

public abstract class ArithmeticBinaryExpression extends BinaryExpression implements
    CanSetOperator {

  private String operator;

  @Override
  public void setOperator(String operator) {
    this.operator = operator;
  }

  @Override
  public String getOperator() {
    return this.operator;
  }
}
