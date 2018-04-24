package concuroo.nodes.expression.binaryExpression;

import concuroo.nodes.expression.Expression;


public abstract class BinaryExpression implements Expression {

  private Expression firstOperand;
  private Expression secondOperand;

  public Expression getFirstOperand() {
    return this.firstOperand;
  }

  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = firstOperand;
  }

  public Expression getSecondOperand() {
    return this.secondOperand;
  }

  public void setSecondOperand(Expression secondOperand) {
    this.secondOperand = secondOperand;
  }

  @Override
  public String getLiteral() {
    return this.getFirstOperand().getLiteral() + this.getOperator() + this.getSecondOperand()
        .getLiteral();
  }

  public abstract String getOperator();
}
