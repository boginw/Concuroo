package concuroo.nodes.expression.binaryExpression;

import concuroo.types.ReturnType;
import concuroo.Visitor;
import concuroo.nodes.Expression;

/**
 * This class represents an expression of the form EXPRESSION OPERATOR EXPRESSION
 */
public abstract class BinaryExpression implements Expression {

  private ReturnType returnReturnType;
  private Expression firstOperand;
  private Expression secondOperand;

  /**
   * Gets the first operand of the expression
   *
   * @return The first operand
   */
  public Expression getFirstOperand() {
    return this.firstOperand;
  }

  /**
   * Sets the first operand of the expression
   *
   * @param firstOperand Expression to be set as first operand
   */
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = firstOperand;
  }

  /**
   * Gets the second operand of the expression
   *
   * @return The second operand
   */
  public Expression getSecondOperand() {
    return this.secondOperand;
  }

  /**
   * Sets the second operand of the expression
   *
   * @param secondOperand Expressino to be set as second operand
   */
  public void setSecondOperand(Expression secondOperand) {
    this.secondOperand = secondOperand;
  }

  @Override
  public String getLiteral() {
    return this.getFirstOperand().getLiteral() + this.getOperator() + this.getSecondOperand()
        .getLiteral();
  }

  /**
   * Gets the operator of the expression
   *
   * @return Operator of the expression
   */
  public abstract String getOperator();

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    this.returnReturnType = returnReturnType;
  }

  @Override
  public void visit(Visitor visitor) {
    visitor.visit(getFirstOperand());
    visitor.visit(getSecondOperand());
  }
}
