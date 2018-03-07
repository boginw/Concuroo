package concuroo.factories.expression;

import concuroo.factories.Factory;
import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.atoms.IdentifierNode;
import concuroo.parser.Parser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdentifierFactory implements ExpressionFactory<IdentifierNode> {

  @Override
  public String getPattern() {
    return "^[_a-zA-Z][_a-zA-Z0-9]{0,29}";
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
    return node instanceof IdentifierNode;
  }

  @Override
  public IdentifierNode makeNode(String literal) {
    return new IdentifierNode(literal);
  }



  @Override
  public Expression parse(Parser parser, Expression pre, Node in, Expression post) {
    return makeNode(in.getLiteral());
  }
}
