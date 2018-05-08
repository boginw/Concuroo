package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.expression.Expression;

public class SizeofExpression implements UnaryExpression {
  private Expression firstOperand;

  public SizeofExpression(Expression firstOperand) {
    setFirstOperand(firstOperand);
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
    return "sizeof";
  }

  @Override
  public String getLiteral() {
    return getOperator() + " " + getFirstOperand().getLiteral();
  }
}
