package concuroo.nodes.expression.unaryExpression.compoundExpression;

import concuroo.nodes.expression.CanSetOperator;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.unaryExpression.UnaryExpression;

public class CompoundExpression implements UnaryExpression, CanSetOperator {

  private UnaryExpression firstOperator;
  private String operator;


  public CompoundExpression(UnaryExpression firstOperator, String operator) {
    setFirstOperand(firstOperator);
    setOperator(operator);
  }

  @Override
  public Expression getFirstOperand() {
    return firstOperator;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperator = (UnaryExpression) firstOperand;
  }

  @Override
  public String getOperator() {
    return operator;
  }

  @Override
  public String getLiteral() {
    return getOperator() + getFirstOperand().getLiteral() + ";";
  }

  @Override
  public void setOperator(String operator) {
    this.operator = operator;
  }
}
