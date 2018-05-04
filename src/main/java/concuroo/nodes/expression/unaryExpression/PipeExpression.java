package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.expression.Expression;

public class PipeExpression implements PrefixExpression {
  public PipeExpression() {
  }

  public PipeExpression(Expression firstOperand) {
    setFirstOperand(firstOperand);
  }

  private CastExpression firstOperand;

  @Override
  public Expression getFirstOperand() {
    return firstOperand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = (CastExpression) firstOperand;
  }

  @Override
  public String getOperator() {
    return "<-";
  }

  @Override
  public String getLiteral() {
    return getOperator() + getFirstOperand().getLiteral() + ";";
  }
}
