package concuroo.nodes.statement;

import concuroo.nodes.Statement;
import concuroo.nodes.expression.Expression;

public class ExpressionStatement implements Statement {
  private Expression expr;

  @Override
  public String getLiteral() {
    return expr.getLiteral() + ";";
  }

  public Expression getExpr() {
    return expr;
  }

  public void setExpr(Expression expr) {
    this.expr = expr;
  }
}
