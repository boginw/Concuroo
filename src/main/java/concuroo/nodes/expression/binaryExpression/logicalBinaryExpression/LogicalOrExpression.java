package concuroo.nodes.expression.binaryExpression.logicalBinaryExpression;

import concuroo.nodes.expression.binaryExpression.LogicalBinaryExpression;

/**
 * This class represents an expression of the form true || true
 */
public class LogicalOrExpression extends LogicalBinaryExpression {

  @Override
  public String getOperator() {
    return "||";
  }
}
