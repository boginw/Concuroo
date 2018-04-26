package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.expression.CanSetOperator;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.PrimaryExpression;

public class CastExpression implements PrefixExpression, CanSetOperator {

  private String specifier;
  private PrimaryExpression firstOperand;

  @Override
  public String getLiteral() {
    return "(" + specifier + ") " + firstOperand.getLiteral() + ";";
  }

  @Override
  public Expression getFirstOperand() {
    return (Expression) firstOperand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = (PrimaryExpression) firstOperand;
  }

  @Override
  public String getOperator() {
    return specifier;
  }

  @Override
  public void setOperator(String specifier) {
    this.specifier = specifier;
  }
}