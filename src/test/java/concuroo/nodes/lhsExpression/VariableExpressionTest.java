package concuroo.nodes.lhsExpression;

import static org.junit.Assert.*;

import concuroo.nodes.expression.lhsExpression.LHSExpression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;
import org.junit.Test;

public class VariableExpressionTest {

  @Test
  public void shouldExist() {
    VariableExpression subject = new VariableExpression("literal");
    assertNotNull(subject);
  }

  @Test
  public void shouldImplementLHSExpressionInterface() {
    VariableExpression subject = new VariableExpression("literal");
    assertTrue(subject instanceof LHSExpression);
  }

  @Test
  public void shouldReturnCorrectLiteral() {
    VariableExpression subject = new VariableExpression("foo");
    assertEquals("foo", subject.getLiteral());
  }

}
