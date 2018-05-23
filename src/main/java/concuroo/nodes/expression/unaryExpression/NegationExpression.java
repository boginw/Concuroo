package concuroo.nodes.expression.unaryExpression;

import concuroo.ReturnType;
import concuroo.Types;
import concuroo.nodes.Expression;
import concuroo.nodes.expression.UnaryExpression;

/**
 * This class represents an expression of the form !a
 */
public class NegationExpression implements UnaryExpression {

  private ReturnType returnReturnType;
  private Expression firstOperand;

  /**
   * Default constructor
   *
   * @param firstOperand The operand to negate
   */
  public NegationExpression(Expression firstOperand) {
    setFirstOperand(firstOperand);
    returnReturnType = new ReturnType();
    returnReturnType.type = Types.BOOL;
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
    return "!";
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
    // TODO: this!!
  }
}
