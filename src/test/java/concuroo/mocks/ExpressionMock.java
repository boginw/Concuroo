package concuroo.mocks;

import concuroo.CSTVisitor;
import concuroo.ReturnType;
import concuroo.Visitor;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import org.antlr.v4.runtime.ParserRuleContext;

public class ExpressionMock implements Expression {

  @Override
  public void setReturnType(ReturnType returnReturnType) {

  }

  @Override
  public ReturnType getReturnType() {
    return null;
  }

  @Override
  public String getLiteral() {
    return null;
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    return null;
  }

  @Override
  public void visit(Visitor visitor) {

  }
}
