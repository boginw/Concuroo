package concuroo.nodes;

import concuroo.ReturnType;

/**
 * This interface should be used for all nodes that can be evaluated to a value.
 */
public interface Expression extends Node {

  /**
   * Sets the return type of the expression
   *
   * @param returnReturnType The return type
   */
  void setReturnType(ReturnType returnReturnType);

  /**
   * Gets the return type of the expression
   *
   * @return The return type
   */
  ReturnType getReturnType();
}

