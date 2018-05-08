package concuroo.nodes.expression.unaryExpression.unaryOperator;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.unaryExpression.CastExpression;
import concuroo.nodes.expression.unaryExpression.PrefixExpression;

public class PipeExpression implements PrefixExpression {

  private CastExpression firstOperand;

  public PipeExpression(Expression firstOperand) {
    setFirstOperand(firstOperand);
  }

  @Override
  public Expression getFirstOperand() {
    return firstOperand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = (CastExpression) firstOperand;
  }

  @Override
  public String getOperator() {
    return "<-";
  }

  @Override
  public String getLiteral() {
    return getOperator() + getFirstOperand().getLiteral();
  }
}
