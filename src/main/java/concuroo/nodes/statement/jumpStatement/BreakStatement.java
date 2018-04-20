package concuroo.nodes.statement.jumpStatement;

import concuroo.nodes.statement.JumpStatement;

public class BreakStatement implements JumpStatement {

  @Override
  public String getLiteral() {
    return "break;";
  }
}
