package concuroo.nodes.expression.literalExpression;

import concuroo.nodes.expression.LiteralExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoolLiteralTest {

    @Test
    public void boolLiteralDirectMethodTest(){
        boolean literalBool = true;
        boolean otherLiteralBool = false;

        LiteralExpression node = new BoolLiteral(literalBool);

        assertEquals(literalBool, Boolean.valueOf(node.getLiteral()));
        assertEquals(literalBool, node.getValue());

        node.setValue(otherLiteralBool);

        assertEquals(otherLiteralBool, Boolean.valueOf(node.getLiteral()));
        assertEquals(otherLiteralBool, node.getValue());
    }


}