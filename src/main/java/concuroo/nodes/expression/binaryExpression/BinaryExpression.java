package concuroo.nodes.expression.binaryExpression;

import concuroo.nodes.expression.Expression;

public abstract class BinaryExpression implements Expression {

  private Expression firstOperand;
  private Expression secondOperand;

  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = firstOperand;
  }

  public Expression getFirstOperand() {
    return this.firstOperand;
  }

  public void setSecondOperand(Expression secondOperand) {
    this.secondOperand = secondOperand;
  }

  public Expression getSecondOperand() {
    return this.secondOperand;
  }

  @Override
  public String getLiteral() {
    return this.getFirstOperand().getLiteral() + this.getLiteral() + this.getSecondOperand();
  }

  public abstract String getOperator();
}
