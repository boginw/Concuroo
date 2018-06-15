package concuroo.nodes.statement.jumpStatement;

import ConcurooParser.ConcurooParser.JumpStatementContext;
import concuroo.CSTVisitor;
import concuroo.Visitor;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.statement.JumpStatement;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an statement of the form return and return exp
 */
public class ReturnStatement implements JumpStatement {

  private Expression returnValue;

  // TODO: interface me
  public Expression getReturnValue() {
    return returnValue;
  }

  // TODO: interface me
  public void setReturnValue(Expression returnValue) {
    this.returnValue = returnValue;
  }

  @Override
  public String getLiteral() {
    StringBuilder sb = new StringBuilder("return");
    if (returnValue != null) {
      sb.append(" ");
      sb.append(returnValue.getLiteral());
    }
    sb.append(";");
    return sb.toString();
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    JumpStatementContext actx = Node.checkCtx(ctx, JumpStatementContext.class);

    if (ctx.children.size() == 3) {
      setReturnValue((Expression) visitor.visitExpression(actx.expression()));

    } else if (ctx.children.size() == 1) {

      throw new RuntimeException("Missing semicolon");
    }

    return this;
  }

  @Override
  public void visit(Visitor visitor) {
    visitor.visit(this);
  }
}
