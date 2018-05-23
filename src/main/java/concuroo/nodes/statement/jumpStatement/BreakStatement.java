package concuroo.nodes.statement.jumpStatement;

import concuroo.nodes.statement.JumpStatement;

/**
 * The break statement, used in loops
 */
public class BreakStatement implements JumpStatement {

  @Override
  public String getLiteral() {
    return "break;";
  }
}
