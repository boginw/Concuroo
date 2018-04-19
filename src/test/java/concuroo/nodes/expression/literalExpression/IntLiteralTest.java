package concuroo.nodes.expression.literalExpression;

import concuroo.nodes.expression.LiteralExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntLiteralTest {

    @Test
    public void intLiteralDirectMethodTest(){
        int literalInt = 42;
        int otherLiteralInt = 84;

        LiteralExpression node = new IntLiteral(literalInt);

        assertEquals(literalInt, Integer.parseInt(node.getLiteral()));
        assertEquals(literalInt, node.getValue());

        node.setValue(otherLiteralInt);

        assertEquals(otherLiteralInt, Integer.parseInt(node.getLiteral()));
        assertEquals(otherLiteralInt, node.getValue());
    }
}