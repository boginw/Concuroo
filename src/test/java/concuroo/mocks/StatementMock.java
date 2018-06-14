package concuroo.mocks;

import concuroo.CSTVisitor;
import concuroo.nodes.Node;
import concuroo.nodes.Statement;
import org.antlr.v4.runtime.ParserRuleContext;

public class StatementMock implements Statement {

  @Override
  public String getLiteral() {
    return null;
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    return null;
  }
}
