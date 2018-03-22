package concuroo.factories.expression;

import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.groups.helpers.EndParenthesis;
import concuroo.nodes.expressions.operators.groups.Parenthesis;
import concuroo.nodes.expressions.operators.groups.helpers.StartParenthesis;
import concuroo.parser.Parser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParenthesisFactory implements ExpressionFactory<Parenthesis> {

  @Override
  public boolean is (Node node){
    return node instanceof Parenthesis;
  }

  @Override
  public Parenthesis makeNode(String literal) {
    if(this.is(literal) != -1){
      Parenthesis result;
      if(literal.equals(")")){
        result = new EndParenthesis();
      }else{
        result = new StartParenthesis();
      }

      result.setLiteral(literal);

      return result;
    }
    return null;
  }

  @Override
  public int is (String input){
    Pattern p = Pattern.compile(getPattern());
    Matcher matcher = p.matcher(input);
    if (matcher.find()) {
      return matcher.end();
    }

    return -1;
  }

  @Override
  public String getPattern() {
    return "^\\(|^\\)";
  }

  /**
   * This method will parse everything from '(' until it sees an ')' and then return it.
   * @param parser The parser instance.
   * @param pre The expression that comes immediately before this.
   * @param in The node that should be parsed
   * @param post The expression that comes immediately after this.
   * @return
   */
  @Override
  public Expression parse(Parser parser, Expression pre, Node in, Expression post) {
    Expression expression = parser.parseExpression();
    parser.consume(EndParenthesis.class);

    return expression;
  }

}
