package concuroo.nodes.expression.binaryExpression;

import ConcurooParser.ConcurooParser.RelationalExpressionContext;
import concuroo.CSTVisitor;
import concuroo.ReturnType;
import concuroo.Types;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form EXPRESSION (>=, >, <=, <, &&, ||) EXPRESSION
 */
public abstract class LogicalBinaryExpression extends BinaryExpression {

  private ReturnType returnReturnType = new ReturnType();

  @Override
  public ReturnType getReturnType() {
    returnReturnType.type = Types.BOOL;
    return returnReturnType;
  }
}
