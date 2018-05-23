package concuroo.nodes.statement;

import concuroo.nodes.Statement;
import concuroo.nodes.Expression;

/**
 * In order to make assignments, call functions etc. We need an expression statement to evaluate
 * arbitrary expressions without storing its return value
 */
public class ExpressionStatement implements Statement {

  private Expression expr;

  @Override
  public String getLiteral() {
    return expr.getLiteral() + ";";
  }

  public Expression getExpression() {
    return expr;
  }

  public void setExpression(Expression expr) {
    this.expr = expr;
  }
}
