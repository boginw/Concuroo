package concuroo.nodes.expression.unaryExpression;

import concuroo.ReturnType;
import concuroo.nodes.Expression;
import concuroo.nodes.expression.UnaryExpression;

/**
 * This class represents an expression of the form <-c, that is receiving from a channel
 */
public class PipeExpression implements UnaryExpression {

  private Expression firstOperand;
  private ReturnType returnReturnType;

  /**
   * Default constructor
   *
   * @param firstOperand The operand receive from
   */
  public PipeExpression(Expression firstOperand) {
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
    return "<-";
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
