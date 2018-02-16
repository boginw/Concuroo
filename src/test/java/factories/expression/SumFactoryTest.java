package factories.expression;

import nodes.expressions.operators.binary.SumExpression;
import org.junit.Assert;
import org.junit.Test;
import symbol.Associativity;


public class SumFactoryTest {

    @Test
    public void getPattern(){
        // TODO: make test
    }

    @Test
    public void is() {
        Assert.assertTrue(new SumFactory().is("+") != -1);
        Assert.assertFalse(new SumFactory().is("1+1") != -1);
    }

    @Test
    public void makeNode() {
        SumExpression se = new SumFactory().makeNode("+");

        Assert.assertTrue(se.getLeft() == null);
        Assert.assertTrue(se.getRight() == null);
        Assert.assertTrue(se.getAssociativity() == Associativity.LeftToRight);
        Assert.assertTrue(se.getLiteral().equals("+"));
    }
}