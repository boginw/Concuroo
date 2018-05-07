package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.FunctionDefinition;
import concuroo.nodes.expression.ArgumentExpressionList;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;


public class FunctionExpression implements Expression, Identifier {

  private String identifier;
  private FunctionDefinition definition;
  private ArgumentExpressionList parameters = new ArgumentExpressionList();

  public FunctionExpression(String identifier) {
    this.identifier = identifier;
  }

  public void setParameterList(ArgumentExpressionList parameters) {
    this.parameters = parameters;
  }

  public ArgumentExpressionList getParameterList() {
    return parameters;
  }

  @Override
  public String getLiteral() {
    return identifier + " " + parameters.getLiteral();
  }

  @Override
  public Expression getIdentifier() {
    return this;
  }

  @Override
  public void setIdentifier(Expression identifier) {
    FunctionExpression fe = (FunctionExpression) identifier;
    this.identifier = fe.identifier;
  }
}
