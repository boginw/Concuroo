package concuroo.nodes.expressions;

import concuroo.factories.expression.ExpressionFactory;
import concuroo.nodes.Node;

public interface Expression<T extends Expression> extends Node {

  /**
   * Gets the factory of the given expression node
   *
   * @return Factory for the given expression node
   */
  ExpressionFactory<T> getFactory();
}
