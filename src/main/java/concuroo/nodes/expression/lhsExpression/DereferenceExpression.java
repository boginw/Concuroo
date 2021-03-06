package concuroo.nodes.expression.lhsExpression;

import ConcurooParser.ConcurooParser.UnaryExpressionContext;
import concuroo.CSTVisitor;
import concuroo.types.ReturnType;
import concuroo.Visitor;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.expression.LHSExpression;
import concuroo.nodes.expression.UnaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form *a. This class represents the dereferencing of an
 * identifier
 */
public class DereferenceExpression implements LHSExpression, UnaryExpression {

  private ReturnType returnReturnType;
  private Expression firstOperand;

  /**
   * Default constructor
   */
  public DereferenceExpression() {
  }

  /**
   * Default constructor
   *
   * @param firstOperand The operand to dereference
   */
  public DereferenceExpression(Expression firstOperand) {
    setFirstOperand(firstOperand);
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
    return "*";
  }

  @Override
  public String getLiteral() {
    return getOperator() + getFirstOperand().getLiteral();
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    Node.checkCtx(ctx, UnaryExpressionContext.class);

    setFirstOperand((Expression) visitor.visit(ctx.getChild(1)));

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
    this.returnReturnType = returnReturnType;
  }
}
