package concuroo.factories.expression;

import concuroo.factories.expression.parselets.Parselets;
import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.indecisive.SumExpression;
import concuroo.parser.Parser;

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
    return new SumExpression();
  }

  @Override
  public Expression parse(Parser parser, Expression pre, Node in, Expression post) {
    return Parselets.parse(makeNode("+"), parser, pre, in, post);
  }
}
