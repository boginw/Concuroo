package concuroo.nodes.expression.binaryExpression;

import concuroo.nodes.expression.CanSetOperator;

public class AssignmentExpression extends BinaryExpression {
  @Override
  public String getOperator() {
    return "=";
  }
}
