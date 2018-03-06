package concuroo.nodes;

/**
 * The main object that is stored in the abstract syntax tree
 */
public interface Node {

  /**
   * Fetches the literal of the node
   *
   * @return Literal of the node
   */
  String getLiteral();

  /**
   * Gets or evaluates the value of the node
   *
   * @return Value of the node
   * @deprecated This should be handled by the code-generator
   */
  int getVal();
}
