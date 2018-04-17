package concuroo.nodes.expression;

import concuroo.nodes.expression.binaryExpression.BinaryExpression;

public class RelationalExpression extends BinaryExpression implements CanSetOperator {

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