package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.Node;
import concuroo.nodes.expression.Expression;

public interface Identifier extends Node {

  Expression getIdentifier();
  void setIdentifier(Expression identifier);

}
