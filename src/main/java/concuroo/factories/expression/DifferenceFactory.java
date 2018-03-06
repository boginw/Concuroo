package concuroo.factories.expression;

import concuroo.factories.expression.parselets.Parselets;
import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.indecisive.DifferenceOperator;
import concuroo.parser.Parser;

public class DifferenceFactory implements ExpressionFactory<DifferenceOperator> {

  @Override
  public String getPattern() {
    return "^-";
  }

  @Override
  public int is(String input) {
    return input.charAt(0) == '-' ? 1 : -1;
  }

  @Override
  public boolean is(Node node) {
    return node instanceof DifferenceOperator;
  }

  @Override
  public DifferenceOperator makeNode(String literal) {
    return new DifferenceOperator();
  }

  @Override
  public Expression parse(Parser parser, Expression pre, Node in, Expression post) {
    return Parselets.parse(makeNode("-"), parser, pre, in, post);
  }
}
