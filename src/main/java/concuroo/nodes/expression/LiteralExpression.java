package concuroo.nodes.expression;

import concuroo.nodes.Expression;

/**
 * This interface is to be used for all literal types.
 */
public interface LiteralExpression<T> extends Expression {

  /**
   * Gets the literal value
   *
   * @return The value of the literal
   */
  T getValue();

  /**
   * Sets the literal value
   *
   * @param value The value of the literal
   */
  void setValue(T value);
}