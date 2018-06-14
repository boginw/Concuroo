package concuroo.nodes.expression.binaryExpression;

import ConcurooParser.ConcurooParser;
import ConcurooParser.ConcurooParser.AssignmentExpressionContext;
import concuroo.CSTVisitor;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.expression.LHSExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form a = true
 */
public class AssignmentExpression extends BinaryExpression {

  @Override
  public String getOperator() {
    return "=";
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    AssignmentExpressionContext actx = Node.checkCtx(ctx, AssignmentExpressionContext.class);

    if (ctx.getChild(0) instanceof ConcurooParser.LogicalOrExpressionContext) {
      return visitor.visitLogicalOrExpression(actx.logicalOrExpression());
    }

    LHSExpression ld = (LHSExpression) visitor.visit(actx.unaryExpression());

    if (ld == null) {
      throw new RuntimeException("Left side of assignment is not LHS expression");
    }

    setFirstOperand(ld);
    setSecondOperand((Expression) visitor.visit(ctx.getChild(2)));

    return this;
  }
}
