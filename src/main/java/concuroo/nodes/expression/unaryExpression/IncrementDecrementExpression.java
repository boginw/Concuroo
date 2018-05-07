package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.PrimaryExpression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;

public class IncrementDecrementExpression implements UnaryExpression {

  private VariableExpression operand;
  private String operator;
  private boolean isPrefix;

  public boolean isPrefix() {
    return isPrefix;
  }

  public void setPrefix(boolean prefix) {
    isPrefix = prefix;
  }

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

  public void setOperator(String operator) {
    this.operator = operator;
  }

  @Override
  public String getLiteral() {
    if (isPrefix){
      return operator + " " + operand.getLiteral();
    }
    return operand.getLiteral() + operator + ';';
  }
}
