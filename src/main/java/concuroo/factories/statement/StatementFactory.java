package concuroo.factories.statement;

import concuroo.factories.Factory;
import concuroo.nodes.Node;
import concuroo.nodes.statements.Statement;
import concuroo.parser.Parser;

/**
 * Factory that creates Statement Nodes
 *
 * @param <T> A type T that extends the Statement interface
 */
public interface StatementFactory<T extends Statement> extends Factory {

  T parse(Parser parser, Node token);
}
