package concuroo.nodes.statements;

import concuroo.factories.statement.IfFactory;
import concuroo.factories.statement.StatementFactory;
import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;

public class IfStatement implements Statement {

  private Expression condition;
  private Statement consequence;
  private Statement alternative;

  public IfStatement() {
  }

  public Node getCondition() {
    return condition;
  }

  public void setCondition(Expression condition) {
    this.condition = condition;
  }

  public Statement getConsequence() {
    return consequence;
  }

  public void setConsequence(Statement consequence) {
    this.consequence = consequence;
  }

  public Statement getAlternative() {
    return alternative;
  }

  public void setAlternative(Statement alternative) {
    this.alternative = alternative;
  }

  @Override
  public String getLiteral() {
    return "if";
  }

  @Override
  public int getVal() {
    if (condition != null) {
      if (condition.getVal() != 0) {
        return consequence.getVal();
      } else if (alternative != null && alternative.getVal() != 0) {
        return alternative.getVal();
      }
    }

    return 0;
  }

  @Override
  public StatementFactory getFactory() {
    return new IfFactory();
  }

}
