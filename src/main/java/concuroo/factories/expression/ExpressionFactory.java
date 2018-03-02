package concuroo.factories.expression;

import concuroo.factories.Factory;
import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.parser.Parser;

/**
 * Defines how Expressions should be created by the Lexer
 *
 * @param <T> Expression T
 */
public interface ExpressionFactory<T extends Expression> extends Factory<T> {

  /**
   * Gets the Regex pattern to detect the Expression T
   *
   * @return Regex Pattern
   */
  String getPattern();

  /**
   * Parses a specific operator
   *
   * @param parser A parser
   * @param pre What came before
   * @param in What token was read
   * @param post What came after
   * @return A parsed expression
   */
  Expression parse(Parser parser, Expression pre, Node in, Expression post);

}
