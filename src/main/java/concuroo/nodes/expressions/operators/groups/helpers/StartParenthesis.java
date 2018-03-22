package concuroo.nodes.expressions.operators.groups.helpers;

import concuroo.nodes.expressions.operators.groups.Parenthesis;

/**
 * This is a helper class, that can be used
 * when there is a need to distinguish between
 * the different parenthesis types
 */
public class StartParenthesis extends Parenthesis {
  public StartParenthesis(){
    super.setLiteral("(");
  }
}
