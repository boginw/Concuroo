package concuroo.nodes.expression.literalExpression;

import concuroo.nodes.expression.LiteralExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class CharLiteralTest {

    @Test
    public void charLiteralDirectMethodTest(){
        char literalChar = 'a';
        char otherLiteralChar = 'c';

        LiteralExpression node = new CharLiteral(literalChar);

        assertEquals(literalChar, (node.getLiteral().charAt(1)));
        assertEquals(literalChar, node.getValue());

        node.setValue(otherLiteralChar);

        assertEquals(otherLiteralChar, (node.getLiteral().charAt(1)));
        assertEquals(otherLiteralChar, node.getValue());
    }


}