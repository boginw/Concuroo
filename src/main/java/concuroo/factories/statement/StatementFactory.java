package concuroo.factories.statement;

import concuroo.factories.Factory;
import concuroo.nodes.Node;
import concuroo.nodes.statements.Statement;
import concuroo.symbol.SymbolTable;
import java.util.List;

/**
 * The factory type that creates statements
 *
 * @param <T> Type of the statement
 */
public interface StatementFactory<T extends Statement> extends Factory {

  /**
   * Creates an instance and populates it with the arguments given
   *
   * @param symbols Symbols used to identifies the statement
   * @param arguments Arguments found in the symbols
   * @param symbolTable Symbol table
   * @return An initialized instance of the statement
   */
  T makeInstance(Node[] symbols, List<Node> arguments, SymbolTable symbolTable);
}
