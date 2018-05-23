package concuroo.nodes.statement;

import concuroo.nodes.Statement;
import concuroo.nodes.Expression;

/**
 * Encompases both selection AND iteration statements, since they both have the same required
 * methods
 */
public interface SelectionStatement extends Statement {

  /**
   * Gets the condition in which, if true, the consequence is executed
   *
   * @return The condition
   */
  Expression getCondition();

  /**
   * Sets the condition in which, if true, the consequence is executed
   *
   * @param condition The condition
   */
  void setCondition(Expression condition);

  /**
   * The consequence executed if the condition is true
   *
   * @return The consequence
   */
  Statement getConsequence();

  /**
   * Sets the consequence executed if the condition is true
   *
   * @param consequence The consequence
   */
  void setConsequence(Statement consequence);
}
