package concuroo.nodes.lhsExpression;

import static org.junit.Assert.*;

import concuroo.CSTVisitor;
import concuroo.ReturnType;
import concuroo.Visitor;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.expression.LHSExpression;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.Test;

public class LHSExpressionInterfaceTest {

  public class TestClass implements LHSExpression {

    private ReturnType returnReturnType;

    @Override
    public String getLiteral() {
      return null;
    }

    @Override
    public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
      return null;
    }

    @Override
    public void visit(Visitor visitor) {

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
