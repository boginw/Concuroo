package concuroo.nodes.expression.literalExpression;

import concuroo.nodes.expression.LiteralExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringLiteralTest {

    @Test
    public void stringLiteralDirectMethodTest() {
        String literalString = "Test";
        String otherLiteralString = "Other Test";

        LiteralExpression node = new StringLiteral(literalString);

        assertEquals(literalString, node.getLiteral());
        assertEquals(literalString, node.getValue());

        node.setValue(otherLiteralString);

        assertEquals(otherLiteralString, node.getLiteral());
        assertEquals(otherLiteralString, node.getValue());
    }

}