package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.FunctionDefinition;
import concuroo.nodes.expression.ArgumentExpressionList;
import concuroo.nodes.expression.Expression;


public class FunctionExpression implements UnaryExpression, Identifier{

  public FunctionExpression(String identifier) {
    this.identifier = identifier;
  }

  private String identifier;
  private FunctionDefinition definition;
  private ArgumentExpressionList parameters = new ArgumentExpressionList();

  public void setParameterList(ArgumentExpressionList parameters) {
    this.parameters = parameters;
  }
  public ArgumentExpressionList getParameterList() { return parameters; }

  @Override
  public Expression getFirstOperand() {
    return null;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {

  }

  @Override
  public String getOperator() {
    return null;
  }

  @Override
  public String getLiteral() {
    return identifier;
  }

  @Override
  public String getID() {
    return identifier;
  }
}
