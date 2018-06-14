package concuroo.nodes.expression.unaryExpression;

import ConcurooParser.ConcurooParser.UnaryExpressionContext;
import concuroo.CSTVisitor;
import concuroo.ReturnType;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.expression.LHSExpression;
import concuroo.nodes.expression.UnaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form &a
 */
public class AddressOfExpression implements UnaryExpression {

  private ReturnType returnReturnType;
  private LHSExpression firstOperand;

  /**
   * Default constructor
   */
  public AddressOfExpression() {
  }

  /**
   * The default constructor
   *
   * @param firstOperand The operand to take the address of
   */
  public AddressOfExpression(Expression firstOperand) {
    setFirstOperand(firstOperand);
  }

  @Override
  public LHSExpression getFirstOperand() {
    return firstOperand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = (LHSExpression) firstOperand;
  }

  @Override
  public String getOperator() {
    return "&";
  }

  @Override
  public String getLiteral() {
    return getOperator() + firstOperand.getLiteral();
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    Node.checkCtx(ctx, UnaryExpressionContext.class);

    setFirstOperand((Expression) visitor.visit(ctx.getChild(1)));

    return this;
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
