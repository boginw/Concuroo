package concuroo.nodes.expression.binaryExpression.logicalBinaryExpression;

import ConcurooParser.ConcurooParser.EqualityExpressionContext;
import ConcurooParser.ConcurooParser.RelationalExpressionContext;
import concuroo.CSTVisitor;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.expression.binaryExpression.LogicalBinaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form 1 == 1
 */
public class LogicalEqualityExpression extends LogicalBinaryExpression {

  @Override
  public String getOperator() {
    return "==";
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    EqualityExpressionContext actx = Node.checkCtx(ctx, EqualityExpressionContext.class);

    if (ctx.getChild(0) instanceof RelationalExpressionContext) {
      return visitor.visitRelationalExpression(actx.relationalExpression());
    }

    setFirstOperand((Expression) visitor.visit(ctx.getChild(0)));
    setSecondOperand((Expression) visitor.visit(ctx.getChild(2)));
    return this;
  }

}