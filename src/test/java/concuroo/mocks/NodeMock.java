package concuroo.mocks;

import concuroo.CodeGenerator;
import concuroo.nodes.Declaration;

public class NodeMock implements Declaration {

  public boolean hit = false;

  @Override
  public String getLiteral() {
    return null;
  }

  @Override
  public void accept(CodeGenerator cg) {
    hit = true;
  }
}
