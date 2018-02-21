package concuroo.nodes.statements;

import concuroo.nodes.Node;

public class ExpressionStatement implements Statement {

  private Node expr;

  @Override
  public String getLiteral() {
    return expr.getLiteral();
  }

  @Override
  public int getVal() {
    return expr.getVal();
  }

  public void setExpr(Node expr) {
    this.expr = expr;
  }
}
