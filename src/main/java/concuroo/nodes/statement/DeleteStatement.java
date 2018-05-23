package concuroo.nodes.statement;

import concuroo.nodes.Statement;
import concuroo.nodes.expression.lhsExpression.VariableExpression;

public class DeleteStatement implements Statement {

  private VariableExpression variable;

  public DeleteStatement() {
  }

  public DeleteStatement(VariableExpression variable) {
    this.variable = variable;
  }

  public VariableExpression getVariable() {
    return variable;
  }

  public void setVariable(VariableExpression variable) {
    this.variable = variable;
  }

  @Override
  public String getLiteral() {
    return "delete " + variable.getIdentifier() + ";";
  }


}
