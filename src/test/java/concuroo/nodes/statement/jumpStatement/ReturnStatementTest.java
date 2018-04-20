package concuroo.nodes.statement.jumpStatement;

import static org.junit.Assert.*;

import concuroo.nodes.Node;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.statement.JumpStatement;
import org.junit.Test;

public class ReturnStatementTest {

  @Test
  public void shouldExists() {
    ReturnStatement subject = new ReturnStatement();
    assertNotNull(subject);
  }

  @Test
  public void shouldImplementJumpStatement() {
    ReturnStatement st = new ReturnStatement();
    assertTrue(st instanceof JumpStatement);
  }

  @Test
  public void canSetReturnValue(){
    ReturnStatement st = new ReturnStatement();
    Expression n = () -> "yolo";

    st.setReturnValue(n);
    assertEquals(n, st.getReturnValue());

  }
}