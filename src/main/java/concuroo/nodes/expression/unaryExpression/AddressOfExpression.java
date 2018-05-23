package concuroo.nodes.expression.unaryExpression;

import concuroo.ReturnType;
import concuroo.nodes.Expression;
import concuroo.nodes.expression.LHSExpression;
import concuroo.nodes.expression.UnaryExpression;

/**
 * This class represents an expression of the form &a
 */
public class AddressOfExpression implements UnaryExpression {

  private ReturnType returnReturnType;
  private LHSExpression firstOperand;

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
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    this.returnReturnType = returnReturnType;
  }
}
