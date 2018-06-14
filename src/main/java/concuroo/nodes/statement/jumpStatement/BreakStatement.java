package concuroo.nodes.statement.jumpStatement;

import ConcurooParser.ConcurooParser.JumpStatementContext;
import concuroo.CSTVisitor;
import concuroo.nodes.Node;
import concuroo.nodes.statement.JumpStatement;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * The break statement, used in loops
 */
public class BreakStatement implements JumpStatement {

  @Override
  public String getLiteral() {
    return "break;";
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    Node.checkCtx(ctx, JumpStatementContext.class);
    return this;
  }
}
