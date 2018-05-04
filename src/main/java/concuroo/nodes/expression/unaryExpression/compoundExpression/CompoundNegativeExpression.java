package concuroo.nodes.expression.unaryExpression.compoundExpression;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.unaryExpression.UnaryExpression;

public class CompoundNegativeExpression implements UnaryExpression {

  private UnaryExpression firstOperand;

  @Override
  public Expression getFirstOperand() {
    return firstOperand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = (UnaryExpression) firstOperand;
  }

  @Override
  public String getOperator() {
    return "--";
  }

  @Override
  public String getLiteral() {
    return getOperator() + getFirstOperand().getLiteral() + ";";
  }
}
