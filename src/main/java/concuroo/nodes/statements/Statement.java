package concuroo.nodes.statements;

import concuroo.factories.statement.StatementFactory;
import concuroo.nodes.Node;

public interface Statement<T extends Statement> extends Node {

  /**
   * Gets the factory of the given expression node
   *
   * @return Factory for the given expression node
   */
  StatementFactory<T> getFactory();
}
