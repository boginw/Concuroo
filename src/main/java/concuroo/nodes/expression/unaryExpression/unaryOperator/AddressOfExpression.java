package concuroo.nodes.expression.unaryExpression.unaryOperator;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.lhsExpression.LHSExpression;
import concuroo.nodes.expression.unaryExpression.PrefixExpression;

public class AddressOfExpression implements PrefixExpression {

  private LHSExpression firstOperand;

  public AddressOfExpression(Expression firstOperand) {
    setFirstOperand(firstOperand);
  }

  @Override
  public LHSExpression getFirstOperand() {
    return firstOperand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = (LHSExpression) firstOperand;
  }

  @Override
  public String getOperator() {
    return "&";
  }

  @Override
  public String getLiteral() {
    return getOperator() + firstOperand.getLiteral();
  }
}
