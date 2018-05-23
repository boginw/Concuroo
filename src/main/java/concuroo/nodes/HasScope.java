package concuroo.nodes;

import concuroo.symbol.SymbolTable;

/**
 * This interface is to be used when a Node contains a scope of its own
 */
public interface HasScope {

  /**
   * Gets the scope of the Node
   *
   * @return The scope of the Node
   */
  SymbolTable getScope();

  /**
   * Sets the scope of the Node
   *
   * @param scope The scope fo the Node
   */
  void setScope(SymbolTable scope);
}
