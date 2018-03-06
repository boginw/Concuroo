package concuroo.nodes.statements;

import concuroo.factories.statement.ExpressionStatementFactory;
import concuroo.factories.statement.StatementFactory;
import concuroo.nodes.expressions.Expression;

public class ExpressionStatement implements Statement {

  private Expression expr;

  @Override
  public String getLiteral() {
    return expr.getLiteral();
  }

  @Override
  public int getVal() {
    return expr.getVal();
  }

  public void setExpr(Expression expr) {
    this.expr = expr;
  }

  @Override
  public StatementFactory getFactory() {
    return new ExpressionStatementFactory();
  }

}
