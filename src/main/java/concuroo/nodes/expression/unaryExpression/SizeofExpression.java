package concuroo.nodes.expression.unaryExpression;

import ConcurooParser.ConcurooParser.UnaryExpressionContext;
import concuroo.CSTVisitor;
import concuroo.types.ReturnType;
import concuroo.types.Types;
import concuroo.Visitor;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.expression.UnaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of sizeof(1+1), that is the size of the expression 1+1.
 */
public class SizeofExpression implements UnaryExpression {

  private Expression firstOperand;
  private ReturnType returnReturnType;

  public SizeofExpression() {
    this(null);
  }

  /**
   * Default constructor
   *
   * @param firstOperand The operand to take the size of.
   */
  public SizeofExpression(Expression firstOperand) {
    setFirstOperand(firstOperand);
    returnReturnType = new ReturnType();
    returnReturnType.type = Types.INT;
  }

  @Override
  public Expression getFirstOperand() {
    return firstOperand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = firstOperand;
  }

  @Override
  public String getOperator() {
    return "sizeof";
  }

  @Override
  public String getLiteral() {
    return getOperator() + " " + getFirstOperand().getLiteral();
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    UnaryExpressionContext actx = Node.checkCtx(ctx, UnaryExpressionContext.class);
    setFirstOperand((Expression) visitor.visitUnaryExpression(actx.unaryExpression()));
    return this;
  }

  @Override
  public void visit(Visitor visitor) {
    visitor.visit(getFirstOperand());
  }

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    throw new RuntimeException("Cannot set return type of a sizeof operation");
  }
}
