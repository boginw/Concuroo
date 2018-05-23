package concuroo.nodes.expression.unaryExpression;

import concuroo.ReturnType;
import concuroo.nodes.expression.CanSetOperator;
import concuroo.nodes.Expression;
import concuroo.nodes.expression.UnaryExpression;

/**
 * This class represents an expression of the form +1 and -1
 */
public class AdditivePrefixExpression implements UnaryExpression, CanSetOperator {

  private ReturnType returnReturnType;
  private Expression firstOperand;
  private String operator;

  /**
   * Default constructor
   *
   * @param firstOperand The operand
   * @param operator The operator
   */
  public AdditivePrefixExpression(Expression firstOperand, String operator) {
    setFirstOperand(firstOperand);
    setOperator(operator);
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
    return operator;
  }

  @Override
  public String getLiteral() {
    return getOperator() + getFirstOperand().getLiteral();
  }

  @Override
  public void setOperator(String operator) {
    this.operator = operator;
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
