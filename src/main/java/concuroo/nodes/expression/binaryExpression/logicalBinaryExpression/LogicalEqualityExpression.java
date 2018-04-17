package concuroo.nodes.expression.binaryExpression.logicalBinaryExpression;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.binaryExpression.LogicalBinaryExpression;

public class LogicalEqualityExpression extends LogicalBinaryExpression {
  @Override
  public String getOperator() {
    return "==";
  }
}