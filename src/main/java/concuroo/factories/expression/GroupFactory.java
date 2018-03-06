package concuroo.factories.expression;

import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.groups.Group;
import concuroo.nodes.expressions.operators.groups.Parenthesis;
import concuroo.parser.Parser;
import java.util.function.Supplier;

public class GroupFactory<T extends Group> implements ExpressionFactory<T> {

  private final char aChar;
  private final Supplier<T> supplier;

  public GroupFactory(char start, Supplier<T> supplier) {
    this.aChar = start;
    this.supplier = supplier;
  }

  @Override
  public String getPattern() {
    return String.format("^%s", aChar);
  }

  @Override
  public int is(String input) {
    char c = input.charAt(0);
    return (c == aChar) ? 1 : -1;
  }

  @Override
  public boolean is(Node node) {
    return node.getClass() == supplier.get().getClass() &&
        node.getLiteral().equals(aChar + "");
  }

  @Override
  public T makeNode(String literal) {
    T t = supplier.get();
    t.setLiteral(literal);
    return t;
  }

  @Override
  public Expression parse(Parser parser, Expression pre, Node in, Expression post) {
    Expression expression = parser.parseExpression();
    parser.consume(Parenthesis.class);
    return expression;
  }
}
