package concuroo.nodes.expression.binaryExpression.logicalBinaryExpression;

import concuroo.nodes.expression.binaryExpression.LogicalBinaryExpression;

public class LogicalAndExpression extends LogicalBinaryExpression {

  @Override
  public String getOperator() {
    return "&&";
  }
}
