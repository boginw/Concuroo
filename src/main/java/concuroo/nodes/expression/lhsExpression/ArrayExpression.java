package concuroo.nodes.expression.lhsExpression;

import ConcurooParser.ConcurooParser.PostfixExpressionContext;
import ConcurooParser.ConcurooParser.UnaryExpressionContext;
import concuroo.CSTVisitor;
import concuroo.ReturnType;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.expression.LHSExpression;
import concuroo.nodes.expression.binaryExpression.BinaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form a[0]. This class represents all array indexing
 */
public class ArrayExpression extends BinaryExpression implements LHSExpression {

  private ReturnType returnReturnType;

  @Override
  public String getLiteral() {
    return this.getFirstOperand().getLiteral() + "[" + this.getSecondOperand().getLiteral() + "]";
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    PostfixExpressionContext actx = Node.checkCtx(ctx, PostfixExpressionContext.class);

    //This is flawed, since it doesn't support (*a)[2]
    setFirstOperand((Expression) visitor.visitPostfixExpression(actx.postfixExpression()));
    setSecondOperand((Expression) visitor.visitExpression(actx.expression()));

    return this;
  }

  @Override
  public String getOperator() {
    return "";
  }

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    this.returnReturnType = returnReturnType;
  }

}
