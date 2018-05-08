package concuroo.nodes.expression.unaryExpression.unaryOperator;

import concuroo.nodes.expression.CanSetOperator;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.unaryExpression.PrefixExpression;

public class AdditivePrefixExpression implements PrefixExpression, CanSetOperator {

  private Expression firstOperand;
  private String operator;

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
}
