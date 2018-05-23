package concuroo.nodes.statement.jumpStatement;

import concuroo.nodes.statement.JumpStatement;

/**
 * The continue statement, used in loops
 */
public class ContinueStatement implements JumpStatement {

  @Override
  public String getLiteral() {
    return "continue;";
  }
}
