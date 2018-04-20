package concuroo.nodes.statement.jumpStatement;

import concuroo.nodes.statement.JumpStatement;

public class ContinueStatement implements JumpStatement {

  @Override
  public String getLiteral() {
    return "continue;";
  }
}
