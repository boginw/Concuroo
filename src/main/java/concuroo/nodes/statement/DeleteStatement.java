package concuroo.nodes.statement;

import ConcurooParser.ConcurooParser.DeleteStatementContext;
import concuroo.CSTVisitor;
import concuroo.nodes.Node;
import concuroo.nodes.Statement;
import concuroo.nodes.expression.lhsExpression.VariableExpression;
import org.antlr.v4.runtime.ParserRuleContext;

public class DeleteStatement implements Statement {

  private VariableExpression variable;

  public DeleteStatement() {
  }

  public DeleteStatement(VariableExpression variable) {
    this.variable = variable;
  }

  public VariableExpression getVariable() {
    return variable;
  }

  public void setVariable(VariableExpression variable) {
    this.variable = variable;
  }

  @Override
  public String getLiteral() {
    return "delete " + variable.getIdentifier() + ";";
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    DeleteStatementContext actx = Node.checkCtx(ctx, DeleteStatementContext.class);

    String identifier = actx.Identifier().getText();

    VariableDeclaration def = (VariableDeclaration) visitor.getGlobal().lookup(identifier);

    if (def == null) {
      throw new RuntimeException(
          "Variable Definition was not found in Variable " + identifier);
    }

    setVariable(new VariableExpression(identifier, def));

    return this;
  }


}
