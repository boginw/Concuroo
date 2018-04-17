package concuroo.nodes.expression.binaryExpression;

import concuroo.nodes.expression.CanSetOperator;
import concuroo.nodes.expression.binaryExpression.BinaryExpression;

public abstract class ArithmeticBinaryExpression extends BinaryExpression implements
    CanSetOperator {

  private String operator;

  @Override
  public void setOperator(String operator) {
    this.operator = operator;
  }
}
