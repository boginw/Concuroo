package concuroo.nodes.expression.binaryExpression;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.binaryExpression.BinaryExpression;

public class AssignmentExpression implements BinaryExpression {
  private Expression first;
  private Expression second;

  public void setFirstOperand(Expression first) {
    this.first = first;
  }

  public Expression getFirstOperand() {
    return this.first;
  }

  public void setSecondOperand(Expression right) {
    this.second = right;
  }

  public Expression getSecondOperand() {
    return this.second;
  }

  public String getLiteral() {
    return first.getLiteral() + " " + getOperator() + " " + first.getLiteral();
  }

  public String getOperator() {
    return "=";
  }
}
