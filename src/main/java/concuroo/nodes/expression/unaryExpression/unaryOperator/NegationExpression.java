package concuroo.nodes.expression.unaryExpression.unaryOperator;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.unaryExpression.PrefixExpression;

public class NegationExpression implements PrefixExpression {

  private Expression firstOperand;

  public NegationExpression(Expression firstOperand) {
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
    return "!";
  }

  @Override
  public String getLiteral() {
    return getOperator() + getFirstOperand().getLiteral();
  }
}