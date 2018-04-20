package concuroo.nodes.statement.iterationStatement;

import concuroo.nodes.Statement;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.statement.IterationStatement;

public class WhileStatement implements IterationStatement {

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
