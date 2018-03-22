package concuroo.nodes.expressions.operators.groups;

import concuroo.nodes.expressions.operators.Operator;

/**
 * Represents a group of Nodes
 */
public interface Group extends Operator {

  /**
   * Sets the literal of the group
   *
   * @param literal Literal to set
   */
  void setLiteral(String literal);
}
