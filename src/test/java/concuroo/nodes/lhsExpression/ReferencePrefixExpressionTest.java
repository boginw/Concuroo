package concuroo.nodes.lhsExpression;

import static org.junit.Assert.*;

import concuroo.nodes.expression.lhsExpression.LHSExpression;
import concuroo.nodes.expression.lhsExpression.ReferencePrefixExpression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;
import org.junit.Test;

public class ReferencePrefixExpressionTest {

  @Test
  public void shouldExists() {
    ReferencePrefixExpression subject = new ReferencePrefixExpression(
        new VariableExpression("literal"));
    assertNotNull(subject);
  }

  @Test
  public void shouldImplementLHSExpressionInterface() {
    ReferencePrefixExpression subject = new ReferencePrefixExpression(
        new VariableExpression("literal"));
    assertTrue(subject instanceof LHSExpression);
  }

  @Test
  public void shouldReturnCorrectLiteral() {
    ReferencePrefixExpression subject = new ReferencePrefixExpression(
        new VariableExpression("foo"));
    assertEquals("&foo", subject.getLiteral());
  }

}
