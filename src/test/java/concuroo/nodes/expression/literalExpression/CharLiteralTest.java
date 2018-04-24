package concuroo.nodes.expression.literalExpression;

import concuroo.nodes.expression.LiteralExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class CharLiteralTest {

  @Test
  public void charLiteralDirectMethodTest() {
    String literalString = "a";
    String otherLiteralString = "c";

    LiteralExpression node = new CharLiteral(literalString);

    assertEquals("\'" + literalString + "\'", (node.getLiteral()));
    assertEquals(literalString, node.getValue());

    node.setValue(otherLiteralString);

    assertEquals("\'" + otherLiteralString + "\'", (node.getLiteral()));
    assertEquals(otherLiteralString, node.getValue());
  }


}