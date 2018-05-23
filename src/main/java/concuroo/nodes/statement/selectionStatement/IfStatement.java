package concuroo.nodes.statement.selectionStatement;

import concuroo.nodes.Statement;
import concuroo.nodes.Expression;
import concuroo.nodes.statement.SelectionStatement;

/**
 * This class represents the if statement, which is of the form: if(condition) consequence OR
 * if(condition) consequence else alternative
 */
public class IfStatement implements SelectionStatement {

  private Expression condition;
  private Statement consequence;
  private Statement alternative;

  /**
   * Gets the alternative consequence
   *
   * @return The alternative
   */
  public Statement getAlternative() {
    return alternative;
  }

  /**
   * Sets the alternative consequence
   *
   * @param alternative The alternative
   */
  public void setAlternative(Statement alternative) {
    this.alternative = alternative;
  }

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
    StringBuilder sb = new StringBuilder();

    sb.append("if (").append(condition.getLiteral()).append(") ").append(consequence.getLiteral());

    if (alternative != null) {
      sb.append(" else ").append(alternative.getLiteral());
    }

    return sb.toString();
  }

}
