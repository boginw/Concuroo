package concuroo.nodes.expression.binaryExpression.logicalBinaryExpression;

import ConcurooParser.ConcurooParser.RelationalExpressionContext;
import concuroo.CSTVisitor;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.expression.binaryExpression.LogicalBinaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class LogicalRelantionalExpression extends LogicalBinaryExpression {

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    Node.checkCtx(ctx, RelationalExpressionContext.class);
    setFirstOperand((Expression) visitor.visit(ctx.getChild(0)));
    setSecondOperand((Expression) visitor.visit(ctx.getChild(2)));
    return this;
  }
}
