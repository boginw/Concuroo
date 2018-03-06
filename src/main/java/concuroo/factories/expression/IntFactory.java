package concuroo.factories.expression;

import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.atoms.IntegerNode;
import concuroo.parser.Parser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntFactory implements ExpressionFactory<IntegerNode> {

  @Override
  public String getPattern() {
    return "^[0-9]+";
  }

  @Override
  public int is(String input) {
    Pattern p = Pattern.compile(getPattern());
    Matcher matcher = p.matcher(input);
    if (matcher.find()) {
      return matcher.end();
    }

    return -1;
  }

  @Override
  public boolean is(Node node) {
    return node instanceof IntegerNode;
  }

  @Override
  public IntegerNode makeNode(String literal) {
    return new IntegerNode(literal);
  }

  @Override
  public Expression parse(Parser parser, Expression pre, Node in, Expression post) {
    return new IntegerNode(in.getLiteral());
  }
}
