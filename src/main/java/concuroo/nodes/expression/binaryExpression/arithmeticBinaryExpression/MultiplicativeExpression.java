package concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression;

import ConcurooParser.ConcurooParser;
import ConcurooParser.ConcurooParser.MultiplicativeExpressionContext;
import concuroo.CSTVisitor;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.expression.binaryExpression.ArithmeticBinaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form 1 * 1, 1 % 1, and 1 / 1
 */
public class MultiplicativeExpression extends ArithmeticBinaryExpression {

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    MultiplicativeExpressionContext mctx = Node.checkCtx(ctx, MultiplicativeExpressionContext .class);

    if (ctx.children.size() == 1) {
      return visitor.visitCastExpression(mctx.castExpression());
    }

    setFirstOperand((Expression) visitor.visit(ctx.getChild(0)));
    setOperator(ctx.children.get(1).toString());
    setSecondOperand((Expression) visitor.visit(ctx.getChild(2)));

    return this;
  }
}
