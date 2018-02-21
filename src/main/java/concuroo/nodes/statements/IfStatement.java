package concuroo.nodes.statements;

import concuroo.nodes.Node;

public class IfStatement implements Statement {

  private Node condition;
  private Statement consequence;
  private Statement alternative;

  public IfStatement() {
  }

  public Node getCondition() {
    return condition;
  }

  public void setCondition(Node condition) {
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
}
