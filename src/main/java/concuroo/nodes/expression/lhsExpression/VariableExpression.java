package concuroo.nodes.expression.lhsExpression;

import concuroo.nodes.expression.unaryExpression.Identifier;
import concuroo.nodes.statement.VariableDefinition;

public class VariableExpression implements LHSExpression, Identifier {

  private String literal;
  private VariableDefinition definition;

  public VariableExpression(String literal) {
    this.literal = literal;
  }

  @Override
  public String getLiteral() {
    return literal;
  }

  @Override
  public String getID() {
    return literal;
  }
}
