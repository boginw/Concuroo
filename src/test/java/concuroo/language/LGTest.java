package concuroo.language;

import static concuroo.language.TokenType.STATEMENT;
import static org.junit.Assert.assertEquals;

import concuroo.factories.expression.IntFactory;
import concuroo.factories.statement.IfFactory;
import concuroo.nodes.Node;
import concuroo.nodes.TokenNode;
import org.junit.Before;
import org.junit.Test;

public class LGTest {

  private static LG lg;

  @Before
  public void beforeEach() {
    lg = new LG();
  }

  @Test
  public void registersIntToken() {
    lg.registerToken("INT", new IntFactory());
    Node n = lg.lookupToken("123");
    Node expected = new IntFactory().makeNode("123");

    assertEquals(expected.getClass(), n.getClass());
    assertEquals(expected.getVal(), n.getVal());
    assertEquals(expected.getLiteral(), n.getLiteral());
  }

  @Test
  public void registersIfToken() {
    lg.registerToken("IF", new IfFactory());
    Node n = lg.lookupToken("if(test)");
    Node expected = new IfFactory().makeNode("if(test)");

    assertEquals(expected.getClass(), n.getClass());
    assertEquals(expected.getVal(), n.getVal());
    assertEquals(expected.getLiteral(), n.getLiteral());
  }

  @Test
  public void registersNonFactoryToken() {
    lg.registerToken("ELSE", "^else", STATEMENT);
    Node n = lg.lookupToken("else{\n\n}");
    Node expected = new TokenNode("ELSE", TokenType.STATEMENT, "else");

    assertEquals(expected.getClass(), n.getClass());
    assertEquals(expected.getVal(), n.getVal());
    assertEquals(expected.getLiteral(), n.getLiteral());
  }
}