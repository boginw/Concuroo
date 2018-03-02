package concuroo.factories.expression;

import concuroo.language.Associativity;
import concuroo.nodes.expressions.operators.indecisive.SumExpression;
import org.junit.Assert;
import org.junit.Test;


public class SumFactoryTest {

  @Test
  public void is() {
    Assert.assertTrue(new SumFactory().is("+") != -1);
    Assert.assertFalse(new SumFactory().is("1+1") != -1);
  }

  @Test
  public void makeNode() {
    SumExpression se = new SumFactory().makeNode("+");

    Assert.assertTrue(se.getOperand() == null);
    Assert.assertTrue(se.getSecondOperand() == null);
    Assert.assertTrue(se.getAssociativity() == Associativity.LeftToRight);
    Assert.assertTrue(se.getLiteral().equals("+"));
  }
}