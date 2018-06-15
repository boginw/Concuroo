package concuroo.nodes.statement;

import concuroo.CSTVisitor;
import concuroo.Visitor;
import concuroo.nodes.Node;
import concuroo.nodes.Statement;
import org.antlr.v4.runtime.ParserRuleContext;

public class RawStatement implements Statement {

  private String rawCode;

  public RawStatement(String code) {
    this.rawCode = code;
  }

  public String getRawCode() {
    return rawCode;
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
