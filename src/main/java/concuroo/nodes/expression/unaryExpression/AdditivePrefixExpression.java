package concuroo.nodes.expression.unaryExpression;

import com.sun.org.apache.xpath.internal.operations.Bool;
import concuroo.nodes.expression.CanSetOperator;
import concuroo.nodes.expression.Expression;

public class AdditivePrefixExpression implements PrefixExpression, CanSetOperator {
  private Expression firstOperand;
  private String operator;

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
  public void setOperator(String operator) {
    this.operator = operator;
  }

  @Override
  public String getLiteral() {
      return operator + firstOperand.getLiteral() + ";";
  }
}
