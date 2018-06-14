package concuroo.nodes.statement;

import ConcurooParser.ConcurooParser.CoroutineStatementContext;
import concuroo.CSTVisitor;
import concuroo.nodes.Node;
import concuroo.nodes.Statement;
import concuroo.nodes.Expression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents the coroutine statement, that is, starting a function a coroutine. This is
 * done by saying go fn()
 */
public class CoroutineStatement implements Statement {

  private Expression expression;

  public Expression getExpression() {
    return expression;
  }

  public void setExpression(Expression expression) {
    this.expression = expression;
  }

  @Override
  public String getLiteral() {
    return "go " + expression.getLiteral() + ";";
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    CoroutineStatementContext actx = Node.checkCtx(ctx, CoroutineStatementContext.class);
    setExpression((Expression) visitor.visitExpression(actx.expression()));
    return this;
  }
}
