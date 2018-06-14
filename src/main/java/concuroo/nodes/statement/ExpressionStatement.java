package concuroo.nodes.statement;

import ConcurooParser.ConcurooParser.ExpressionStatementContext;
import concuroo.CSTVisitor;
import concuroo.nodes.Node;
import concuroo.nodes.Statement;
import concuroo.nodes.Expression;
import org.antlr.v4.runtime.ParserRuleContext;

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

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    ExpressionStatementContext actx = Node.checkCtx(ctx, ExpressionStatementContext.class);
    setExpression((Expression) visitor.visit(ctx.getChild(0)));

    return this;
  }

  public Expression getExpression() {
    return expr;
  }

  public void setExpression(Expression expr) {
    this.expr = expr;
  }
}
