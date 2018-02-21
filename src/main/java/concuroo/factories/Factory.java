package concuroo.factories;

import concuroo.nodes.Node;

/**
 * This interface defines two critical part of the lexer and helps the AST later on.
 *
 * @param <T> Type of Node
 */
public interface Factory<T extends Node> {

  /**
   * A method that tests if a given input has Node T at the start the string.
   *
   * @param input String to check
   * @return -1 for no match, length of match otherwise
   */
  int is(String input);

  /**
   * Given a node, tell if it is this factory that creates it
   *
   * @param node Node to check
   * @return Whether or not this factory can create it
   */
  boolean is(Node node);

  /**
   * Creates a Node T from string This is used by the lexer
   *
   * @param literal The literal string found
   * @return A Node T from input
   */
  T makeNode(String literal);
}
