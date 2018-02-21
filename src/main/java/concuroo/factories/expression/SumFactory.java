package concuroo.factories.expression;

import concuroo.nodes.Node;
import concuroo.nodes.expressions.operators.binary.SumExpression;

public class SumFactory implements ExpressionFactory<SumExpression> {

  @Override
  public String getPattern() {
    return "^\\+";
  }

  @Override
  public int is(String input) {
    return input.charAt(0) == '+' ? 1 : -1;
  }

  @Override
  public boolean is(Node node) {
    return node instanceof SumExpression;
  }

  @Override
  public SumExpression makeNode(String literal) {
    return new SumExpression(literal);
  }
}
