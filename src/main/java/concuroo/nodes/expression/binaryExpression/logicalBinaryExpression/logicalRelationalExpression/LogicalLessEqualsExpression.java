package concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.logicalRelationalExpression;

import concuroo.CSTVisitor;
import concuroo.nodes.Node;
import concuroo.nodes.expression.binaryExpression.LogicalBinaryExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalRelantionalExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form 1 <= 1
 */
public class LogicalLessEqualsExpression extends LogicalRelantionalExpression {

  @Override
  public String getOperator() {
    return "<=";
  }

}
