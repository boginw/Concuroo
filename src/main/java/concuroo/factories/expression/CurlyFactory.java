package concuroo.factories.expression;

import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.groups.Curly;
import concuroo.nodes.expressions.operators.groups.helpers.EndCurly;
import concuroo.nodes.expressions.operators.groups.helpers.StartCurly;
import concuroo.parser.Parser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurlyFactory implements ExpressionFactory<Curly> {

  @Override
  public String getPattern() {
    return "^\\{|^\\}";
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
    return node instanceof Curly;
  }

  @Override
  public Curly makeNode(String literal) {
    if(this.is(literal) != -1){
      Curly result;
      if(literal.equals("{")) {
        result = new StartCurly();
      }else{
        result = new EndCurly();
      }
      return result;
    }
    return null;
  }

  @Override
  public Expression parse(Parser parser, Expression pre, Node in, Expression post) {
    Expression expression = parser.parseExpression();
    parser.consume(EndCurly.class);

    return expression;
  }
}
