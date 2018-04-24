package concuroo.nodes.statement.jumpStatement;

import static org.junit.Assert.*;
import concuroo.nodes.statement.JumpStatement;
import org.junit.Test;

public class BreakStatementTest {

  @Test
  public void shouldExists() {
    BreakStatement subject = new BreakStatement();
    assertNotNull(subject);
  }

  @Test
  public void shouldImplementJumpStatement() {
    BreakStatement st = new BreakStatement();
    assertTrue(st instanceof JumpStatement);
  }
}