package concuroo.nodes.expression.unaryExpression.compoundExpression;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.unaryExpression.UnaryExpression;

public class CompoundPositiveExpression implements UnaryExpression {

  public CompoundPositiveExpression(UnaryExpression firstOperator) {
    setFirstOperand(firstOperator);
  }

  private UnaryExpression firstOperator;

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
    return "++";
  }

  @Override
  public String getLiteral() {
    return getOperator() + getFirstOperand().getLiteral() + ";";
  }
}
