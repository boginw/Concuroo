package concuroo.nodes.expression.literalExpression;

import concuroo.nodes.expression.LiteralExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class FloatLiteralTest {

  @Test
  public void FloatLiteralDirectMethodTest() {
    double literalFloat = 42.42f;
    double otherLiteralFloat = 84.84f;

    LiteralExpression node = new FloatLiteral(literalFloat);

    double delta = 0;
    assertEquals(literalFloat, Double.valueOf(node.getLiteral()), delta);
    assertEquals(literalFloat, node.getValue());

    node.setValue(otherLiteralFloat);

    assertEquals(otherLiteralFloat, Double.valueOf(node.getLiteral()), delta);
    assertEquals(otherLiteralFloat, node.getValue());
  }

}