package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.expression.Expression;

public class DereferenceExpression implements PrefixExpression {
  public DereferenceExpression() {
  }

  public DereferenceExpression(Expression firstOperand) {
    setFirstOperand(firstOperand);
  }

  private Expression firstOperand;

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
    return getOperator() + getFirstOperand().getLiteral() + ";";
  }
}
