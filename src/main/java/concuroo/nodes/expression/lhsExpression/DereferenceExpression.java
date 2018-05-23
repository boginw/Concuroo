package concuroo.nodes.expression.lhsExpression;

import concuroo.ReturnType;
import concuroo.nodes.Expression;
import concuroo.nodes.expression.LHSExpression;
import concuroo.nodes.expression.UnaryExpression;

/**
 * This class represents an expression of the form *a. This class represents the dereferencing of an
 * identifier
 */
public class DereferenceExpression implements LHSExpression, UnaryExpression {

  private ReturnType returnReturnType;
  private Expression firstOperand;

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
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    this.returnReturnType = returnReturnType;
  }
}
