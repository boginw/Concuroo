package concuroo.nodes.lhsExpression;

import static org.junit.Assert.*;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.lhsExpression.LHSExpression;

import org.junit.Before;
import org.junit.Test;

public class LHSExpressionInterfaceTest {

  public class TestClass implements LHSExpression {

    @Override
    public String getLiteral() {
      return null;
    }
  }

  @Test
  public void shouldImplementExpressionInterface() {
    TestClass subject = new TestClass();

    assertTrue(subject instanceof Expression);

  }
}
