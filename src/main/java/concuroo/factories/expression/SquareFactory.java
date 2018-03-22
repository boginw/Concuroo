package concuroo.factories.expression;

import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.groups.Square;
import concuroo.nodes.expressions.operators.groups.helpers.EndSquare;
import concuroo.nodes.expressions.operators.groups.helpers.StartSquare;
import concuroo.parser.Parser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SquareFactory implements ExpressionFactory<Square>{


  @Override
  public String getPattern() {
    return "^\\[|^\\]";
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
    return node instanceof Square;
  }

  @Override
  public Square makeNode(String literal) {
    if(this.is(literal) != -1){
      Square result;
      if(literal.equals("[")) {
        result = new StartSquare();
      }else{
        result = new EndSquare();
      }
      return result;
    }
    return null;
  }

  @Override
  public Expression parse(Parser parser, Expression pre, Node in, Expression post) {
    Expression expression = parser.parseExpression();
    parser.consume(EndSquare.class);

    return expression;
  }

}
