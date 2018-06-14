package concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.logicalRelationalExpression;

import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalRelantionalExpression;

/**
 * This class represents an expression of the form 1 < 1
 */
public class LogicalLessExpression extends LogicalRelantionalExpression {

  @Override
  public String getOperator() {
    return "<";
  }

}
