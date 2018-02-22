package concuroo.factories.statement;

import concuroo.factories.Factory;
import concuroo.nodes.Node;
import concuroo.nodes.statements.Statement;
import concuroo.symbol.SymbolTable;
import java.util.List;

/**
 * Factory that creates Statement Nodes
 *
 * @param <T> A type T that extends the Statement interface
 */
public interface StatementFactory<T extends Statement> extends Factory {

  /**
   * Creates an populated instance of the given Node, given the arguments to do so.
   *
   * @param symbols Symbols that recognized the node
   * @param arguments Arguments for instantiating.
   * @param symbolTable current SymbolTable at node's scope
   * @return Instance of the given statement node.
   */
  T makeInstance(Node[] symbols, List<Node> arguments, SymbolTable symbolTable);
}
