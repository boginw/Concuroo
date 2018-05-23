package concuroo.nodes.lhsExpression;

import static org.junit.Assert.*;

import concuroo.ReturnType;
import concuroo.nodes.Expression;
import concuroo.nodes.expression.LHSExpression;

import org.junit.Test;

public class LHSExpressionInterfaceTest {

  public class TestClass implements LHSExpression {

    private ReturnType returnReturnType;

    @Override
    public String getLiteral() {
      return null;
    }

    @Override
    public void setReturnType(ReturnType returnReturnType) {
      this.returnReturnType = returnReturnType;
    }

    @Override
    public ReturnType getReturnType() {
      return null;
    }
  }

  @Test
  public void shouldImplementExpressionInterface() {
    TestClass subject = new TestClass();

    assertTrue(subject instanceof Expression);

  }
}
