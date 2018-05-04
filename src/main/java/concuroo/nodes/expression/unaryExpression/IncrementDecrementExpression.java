package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.PrimaryExpression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;

public class IncrementDecrementExpression implements UnaryExpression {

  VariableExpression operand;
  private String operator;

  @Override
  public Expression getFirstOperand() {
    return operand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.operand = (VariableExpression) firstOperand;
  }

  @Override
  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator){
    this.operator = operator;
  }

  @Override
  public String getLiteral() {
    return operand.getLiteral() + operator + ';';
  }
}
