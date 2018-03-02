package concuroo.factories.expression;

import concuroo.factories.expression.parselets.Parselets;
import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.binary.ProductExpression;
import concuroo.parser.Parser;

public class ProductFactory implements ExpressionFactory<ProductExpression> {

  @Override
  public String getPattern() {
    return "^\\*";
  }

  @Override
  public int is(String input) {
    return input.charAt(0) == '*' ? 1 : -1;
  }

  @Override
  public boolean is(Node node) {
    return node instanceof ProductExpression;
  }

  @Override
  public ProductExpression makeNode(String literal) {
    return new ProductExpression();
  }

  @Override
  public Expression parse(Parser parser, Expression pre, Node in, Expression post) {
    return Parselets.parse(makeNode("*"), parser, pre, in, post);

  }
}
