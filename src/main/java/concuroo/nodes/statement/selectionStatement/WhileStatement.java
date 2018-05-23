package concuroo.nodes.statement.selectionStatement;

import concuroo.nodes.Statement;
import concuroo.nodes.Expression;
import concuroo.nodes.statement.SelectionStatement;

/**
 * This class represents a statement of the form: while(condition) statements
 */
public class WhileStatement implements SelectionStatement {

  private Expression condition;
  private Statement consequence;

  @Override
  public Expression getCondition() {
    return condition;
  }

  @Override
  public void setCondition(Expression condition) {
    this.condition = condition;
  }

  @Override
  public Statement getConsequence() {
    return consequence;
  }

  @Override
  public void setConsequence(Statement consequence) {
    this.consequence = consequence;
  }

  @Override
  public String getLiteral() {
    return "while(" + condition.getLiteral() + ") " + consequence.getLiteral();
  }
}
