package concuroo.mocks;

import concuroo.CSTVisitor;
import concuroo.CodeGenerator;
import concuroo.nodes.Declaration;
import concuroo.nodes.Node;
import org.antlr.v4.runtime.ParserRuleContext;

public class NodeMock implements Declaration {

  public boolean hit = false;

  @Override
  public String getLiteral() {
    return null;
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    return null;
  }

  @Override
  public void accept(CodeGenerator cg) {
    hit = true;
  }
}
