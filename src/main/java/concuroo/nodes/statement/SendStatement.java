package concuroo.nodes.statement;


import ConcurooParser.ConcurooParser.SendStatementContext;
import concuroo.CSTVisitor;
import concuroo.types.ReturnType;
import concuroo.types.Types;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.Statement;
import concuroo.nodes.expression.binaryExpression.BinaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents a statement of the form c <- 1, that is, a send statement. In order to have
 * a more uniform interface, this class extends Binary Expression, even though it's a statement. We
 * insure that it evaluates to void. The CFG, will block this statement, of ever being an
 * expression.
 */
public class SendStatement extends BinaryExpression implements Statement {

  @Override
  public String getLiteral() {
    return getFirstOperand().getLiteral() + " <- " + getSecondOperand().getLiteral();
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    Node.checkCtx(ctx, SendStatementContext.class);

    setFirstOperand((Expression) visitor.visit(ctx.getChild(0)));
    setSecondOperand((Expression) visitor.visit(ctx.getChild(2)));

    return this;
  }

  @Override
  public String getOperator() {
    return "<-";
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    throw new RuntimeException("Cannot set return type of send statement. It's a statement");
  }

  @Override
  public ReturnType getReturnType() {
    return new ReturnType() {{
      type = Types.VOID;
    }};
  }
}
