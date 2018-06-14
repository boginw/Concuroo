package concuroo.nodes.expression.binaryExpression.logicalBinaryExpression;

import ConcurooParser.ConcurooParser;
import ConcurooParser.ConcurooParser.LogicalAndExpressionContext;
import concuroo.CSTVisitor;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.expression.binaryExpression.LogicalBinaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form true && true
 */
public class LogicalAndExpression extends LogicalBinaryExpression {

  @Override
  public String getOperator() {
    return "&&";
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    LogicalAndExpressionContext rctx = Node.checkCtx(ctx, LogicalAndExpressionContext.class);

    if (ctx.getChild(0) instanceof ConcurooParser.EqualityExpressionContext) {
      return visitor.visitEqualityExpression(rctx.equalityExpression());
    }

    setFirstOperand((Expression) visitor.visit(ctx.getChild(0)));
    setSecondOperand((Expression) visitor.visit(ctx.getChild(2)));

    return this;
  }
}
