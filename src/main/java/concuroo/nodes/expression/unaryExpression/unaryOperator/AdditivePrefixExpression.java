package concuroo.nodes.expression.unaryExpression.unaryOperator;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.unaryExpression.PrefixExpression;

public class AdditivePrefixExpression implements PrefixExpression {
  public AdditivePrefixExpression() {
  }

  public AdditivePrefixExpression(Expression firstOperand) {
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
    return "+";
  }

  @Override
  public String getLiteral() {
      return getOperator() + getFirstOperand().getLiteral() + ";";
  }
}
