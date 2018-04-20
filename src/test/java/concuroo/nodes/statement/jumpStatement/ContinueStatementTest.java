package concuroo.nodes.statement.jumpStatement;

import static org.junit.Assert.*;

import concuroo.nodes.statement.JumpStatement;
import org.junit.Test;

public class ContinueStatementTest {

  @Test
  public void shouldExists() {
    ContinueStatement subject = new ContinueStatement();
    assertNotNull(subject);
  }

  @Test
  public void shouldImplementJumpStatement() {
    ContinueStatement st = new ContinueStatement();
    assertTrue(st instanceof JumpStatement);
  }
}