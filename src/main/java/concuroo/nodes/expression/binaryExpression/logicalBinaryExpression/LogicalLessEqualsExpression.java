package concuroo.nodes.expression.binaryExpression.logicalBinaryExpression;

import concuroo.nodes.expression.binaryExpression.LogicalBinaryExpression;

/**
 * This class represents an expression of the form 1 <= 1
 */
public class LogicalLessEqualsExpression extends LogicalBinaryExpression {

  @Override
  public String getOperator() {
    return "<=";
  }

}
