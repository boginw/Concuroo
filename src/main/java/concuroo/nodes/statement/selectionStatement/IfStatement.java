package concuroo.nodes.statement.selectionStatement;

import concuroo.nodes.Statement;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.statement.SelectionStatement;

public class IfStatement implements SelectionStatement {
  private Expression condition;
  private Statement consequence;
  private Statement alternative;

  @Override
  public String getLiteral() {
    StringBuilder sb = new StringBuilder();

    sb.append("if (").append(condition.getLiteral()).append(") ").append(consequence.getLiteral());

    if(alternative != null){
      sb.append("else ").append(alternative.getLiteral());
    }

    return sb.toString();
  }

  public Expression getCondition() {
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
}
