package concuroo.nodes.expression;

/**
 * Main interface to contain literals
 */
public interface LiteralExpression extends Expression {

  Object getValue();

  void setValue(Object value);
}