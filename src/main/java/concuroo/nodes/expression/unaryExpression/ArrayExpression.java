package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;

public class ArrayExpression implements UnaryExpression{

  private VariableExpression operand;
  private Expression operator;


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
    return operator.getLiteral();
  }

  public void setOperator(Expression operator) {
    this.operator = operator;
  }


  @Override
  public String getLiteral() {
    return operand.getLiteral() + '(' + operator.getLiteral() + ')';
  }
}
