package concuroo.nodes.expressions.operators.groups;

import concuroo.nodes.expressions.operators.Operator;

/**
 * Represents a group of Nodes
 */
public interface Group extends Operator {

  /**
   * Whether or not the group token is a starting token or not
   *
   * @return True if token is starting token
   */
  boolean isStart();

  /**
   * Sets whether or not the group token is a starting token
   *
   * @param start Starting or not
   */
  void setStart(boolean start);

  /**
   * Sets the literal of the group
   *
   * @param literal Literal to set
   */
  void setLiteral(String literal);
}
