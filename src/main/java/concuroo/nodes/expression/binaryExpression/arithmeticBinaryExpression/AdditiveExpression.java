package concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression;

import ConcurooParser.ConcurooParser;
import ConcurooParser.ConcurooParser.AdditiveExpressionContext;
import concuroo.CSTVisitor;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.expression.binaryExpression.ArithmeticBinaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form 1 + 1, and 1 - 1
 */
public class AdditiveExpression extends ArithmeticBinaryExpression {

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    AdditiveExpressionContext actx = Node.checkCtx(ctx, AdditiveExpressionContext.class);

    if (ctx.children.size() == 1) {
      return visitor.visitMultiplicativeExpression(actx.multiplicativeExpression());
    }

    setFirstOperand((Expression) visitor.visit(ctx.getChild(0)));
    setOperator(ctx.children.get(1).toString());
    setSecondOperand((Expression) visitor.visit(ctx.getChild(2)));

    return this;
  }
}
