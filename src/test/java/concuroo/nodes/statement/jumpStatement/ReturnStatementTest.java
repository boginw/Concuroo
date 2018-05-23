package concuroo.nodes.statement.jumpStatement;

import static org.junit.Assert.*;

import concuroo.nodes.Expression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
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
    Expression n = new AdditiveExpression();

    st.setReturnValue(n);
    assertEquals(n, st.getReturnValue());

  }
}