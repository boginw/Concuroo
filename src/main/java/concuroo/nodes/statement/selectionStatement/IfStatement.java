package concuroo.nodes.statement.selectionStatement;

import ConcurooParser.ConcurooParser.IfStatementContext;
import concuroo.CSTVisitor;
import concuroo.nodes.Node;
import concuroo.nodes.Statement;
import concuroo.nodes.Expression;
import concuroo.nodes.statement.SelectionStatement;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents the if statement, which is of the form: if(condition) consequence OR
 * if(condition) consequence else alternative
 */
public class IfStatement implements SelectionStatement {

  private Expression condition;
  private Statement consequence;
  private Statement alternative;

  /**
   * Gets the alternative consequence
   *
   * @return The alternative
   */
  public Statement getAlternative() {
    return alternative;
  }

  /**
   * Sets the alternative consequence
   *
   * @param alternative The alternative
   */
  public void setAlternative(Statement alternative) {
    this.alternative = alternative;
  }

  @Override
  public Expression getCondition() {
    return condition;
  }

  @Override
  public void setCondition(Expression condition) {
    this.condition = condition;
  }

  @Override
  public Statement getConsequence() {
    return consequence;
  }

  @Override
  public void setConsequence(Statement consequence) {
    this.consequence = consequence;
  }

  @Override
  public String getLiteral() {
    StringBuilder sb = new StringBuilder();

    sb.append("if (").append(condition.getLiteral()).append(") ").append(consequence.getLiteral());

    if (alternative != null) {
      sb.append(" else ").append(alternative.getLiteral());
    }

    return sb.toString();
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    Node.checkCtx(ctx, IfStatementContext.class);

    if (ctx.children.size() == 3) {
      IfStatement st = (IfStatement) visitor.visit(ctx.getChild(0));
      st.setAlternative((Statement) visitor.visit(ctx.getChild(2)));
      return st;
    }

    setCondition((Expression) visitor.visit(ctx.getChild(2)));
    setConsequence((Statement) visitor.visit(ctx.getChild(4)));

    return this;
  }

}
