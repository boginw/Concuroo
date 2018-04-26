package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.lhsExpression.LHSExpression;

public class AddressOfExpression implements PrefixExpression {
  private LHSExpression firstOperand;
  private String operator = "&";

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
    return operator;
  }

  @Override
  public String getLiteral() {
    return operator + firstOperand.getLiteral() + ";";
  }
}
