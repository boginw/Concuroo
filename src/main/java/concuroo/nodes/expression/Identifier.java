package concuroo.nodes.expression;

import concuroo.nodes.Node;

/**
 * This interface should be used where an identifier is involved
 */
public interface Identifier extends Node {

  /**
   * Gets the identifier
   *
   * @return The identifier
   */
  String getIdentifier();

  /**
   * Sets the identifier
   *
   * @param identifier Identifier
   */
  void setIdentifier(String identifier);

}
