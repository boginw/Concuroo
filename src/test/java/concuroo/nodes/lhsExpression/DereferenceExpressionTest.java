package concuroo.nodes.lhsExpression;

import static org.junit.Assert.*;

import concuroo.nodes.expression.lhsExpression.LHSExpression;
import concuroo.nodes.expression.lhsExpression.DereferenceExpression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;
import org.junit.Test;

public class DereferenceExpressionTest {

  @Test
  public void shouldExists() {
    DereferenceExpression subject = new DereferenceExpression(new VariableExpression("literal"));
    assertNotNull(subject);
  }

  @Test
  public void shouldImplementLHSExpressionInterface() {
    DereferenceExpression subject = new DereferenceExpression(new VariableExpression("literal"));
    assertTrue(subject instanceof LHSExpression);
  }

  @Test
  public void shouldReturnCorrectLiteral() {
    DereferenceExpression subject = new DereferenceExpression(new VariableExpression("foo"));
    assertEquals("*foo", subject.getLiteral());
  }

}
