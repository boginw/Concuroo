package concuroo.factories.expression;

import concuroo.nodes.Node;
import concuroo.nodes.expressions.atoms.IdentifierNode;
import concuroo.nodes.expressions.atoms.IntegerNode;
import concuroo.nodes.expressions.operators.binary.ProductExpression;
import concuroo.nodes.expressions.operators.indecisive.SumExpression;
import concuroo.parser.Parser;
import concuroo.symbol.SymbolTable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Enclosed.class)
public class IdentifierFactoryTest {

  @RunWith(Parameterized.class)
  public static class RecognizeByNodes {
    @Parameters
    public static Collection<Object[]> data() {
      return Arrays.asList(new Object[][] {
          { new IdentifierNode("name"), true},
          { new IntegerNode("42"), false}
      });
    }

    private boolean expected;

    private Node input;

    public RecognizeByNodes(Node input, boolean expected){
      this.expected = expected;
      this.input = input;
    }

    @Test
    public void Should_recognize_correctly_by_nodes(){
      IdentifierFactory factory = new IdentifierFactory();

      Assert.assertEquals(this.expected, factory.is(this.input));
    }
  }

  @RunWith(Parameterized.class)
  public static class RecognizeByString{
    @Parameters
    public static Collection<Object[]> data() {
      return Arrays.asList(new Object[][]{
          {"name", 4},
          {"_name", 5},
          {"92name", -1},
          {"name=23", 4},
          {"__name", 6},
          {"n_a_m_e_", 8},
          {"name92", 6},
          {"name = 92", 4},
          {"!name", -1},
          {"-name", -1},
          {"@name", -1},
          {"~name", -1},
          {"øname", -1}, // Cannot start with illegal character
          {"Næåø", 1}, // æøå should be ignored
          {"naem!", 4},
          {"x", 1},
          {"a12345678910111213141516171812", 30}, // 30 characters should return 30
          {"a12345678910111213141516171812x", 31} // 31 characters should return 31
      });
    }
    
    private int expected;
    
    private String input;

    public RecognizeByString(String input, int expected){
      this.input = input;
      this.expected = expected;
    }

    @Test
    public void Should_recognize_correctly_by_string(){
      IdentifierFactory factory = new IdentifierFactory();

      Assert.assertEquals(this.expected, factory.is(this.input));
    }

  }


  public static class NotParameterizedPart {

    Iterator<Node> tokens = null;
    SymbolTable symTable = new SymbolTable();
    Parser parser;

    public NotParameterizedPart() {
      parser = new Parser(tokens, symTable);
    }

    @Test
    public void Should_be_able_to_create_a_identifier_node() {
      IdentifierFactory factory = new IdentifierFactory();

      Node actual = factory.makeNode("name");

      Assert.assertTrue((actual != null));
    }

    @Test
    public void Should_be_able_to_parse_the_expression() {
      IdentifierFactory factory = new IdentifierFactory();

      Node actual = factory.parse(parser, new SumExpression(), new IdentifierNode("name"), new ProductExpression());

      Assert.assertTrue((actual instanceof IdentifierNode));
    }


  }


}
