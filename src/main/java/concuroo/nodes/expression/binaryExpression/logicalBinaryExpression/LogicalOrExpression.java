package concuroo.nodes.expression.binaryExpression.logicalBinaryExpression;

import ConcurooParser.ConcurooParser.LogicalAndExpressionContext;
import ConcurooParser.ConcurooParser.LogicalOrExpressionContext;
import concuroo.CSTVisitor;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.expression.binaryExpression.LogicalBinaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form true || true
 */
public class LogicalOrExpression extends LogicalBinaryExpression {

  @Override
  public String getOperator() {
    return "||";
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    LogicalOrExpressionContext actx = Node.checkCtx(ctx, LogicalOrExpressionContext.class);

    if (ctx.getChild(0) instanceof LogicalAndExpressionContext) {
      return visitor.visitLogicalAndExpression(actx.logicalAndExpression());
    }

    setFirstOperand((Expression) visitor.visit(ctx.getChild(0)));
    setSecondOperand((Expression) visitor.visit(ctx.getChild(2)));

    return this;
  }
}
