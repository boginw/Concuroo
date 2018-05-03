package concuroo.nodes.statement;

import concuroo.nodes.Statement;
import concuroo.nodes.expression.Expression;

public class CoroutineStatement implements Statement {

  private Expression expression;

  public Expression getExpression(){
    return expression;
  }

  public void setExpression(Expression expression) {
    this.expression = expression;
  }

  @Override
  public String getLiteral() {
    return "go " + expression.getLiteral() + ";";
  }
}
