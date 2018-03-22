package concuroo.factories.expression;


import concuroo.ConcurooDefinition;
import concuroo.language.LG;
import concuroo.lexer.Lexer;
import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.atoms.IntegerNode;
import concuroo.nodes.expressions.operators.binary.BinaryOperator;

import concuroo.nodes.expressions.operators.groups.helpers.EndCurly;
import concuroo.nodes.expressions.operators.groups.helpers.EndParenthesis;
import concuroo.nodes.expressions.operators.groups.helpers.EndSquare;
import concuroo.nodes.expressions.operators.groups.helpers.StartCurly;
import concuroo.nodes.expressions.operators.groups.helpers.StartParenthesis;
import concuroo.nodes.expressions.operators.groups.helpers.StartSquare;
import concuroo.nodes.expressions.operators.indecisive.SumExpression;
import concuroo.parser.Parser;
import concuroo.symbol.SymbolTable;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Enclosed.class)
public class ParenthesisFactoryTest {

  @RunWith(Parameterized.class)
  public static class RecognizeByNodes {
    @Parameters
    public static Collection<Object[]> data() {
      return Arrays.asList(new Object[][] {
          {new StartParenthesis(), true},
          {new EndParenthesis(), true},
          {new StartCurly(), false},
          {new EndCurly(), false},
          {new StartSquare(), false},
          {new EndSquare(), false}
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

      boolean actual = new ParenthesisFactory().is(this.input);

      Assert.assertEquals(this.expected, actual);
    }
  }

  @RunWith(Parameterized.class)
  public static class RecognizeByString{
      @Parameters
      public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"(", 1},
            {"{", -1},
            {"[", -1},
            {")", 1},
            {"}", -1},
            {"]", -1},
            {"((", 1},
            {"()", 1},
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
        int actual = new ParenthesisFactory().is(this.input);

        Assert.assertEquals(this.expected, actual);
      }

    }

  public static class NotParameterizedPart {
    LG lg = new ConcurooDefinition().getGrammar();
    SymbolTable st = new SymbolTable();
    Lexer l = new Lexer(st, lg);
    Parser parser;

    public NotParameterizedPart() {
      parser = new Parser(l, st);
    }

    @Test
    public void Should_be_able_to_create_a_identifier_node() {
      ParenthesisFactory factory = new ParenthesisFactory();

      Node actual1 = factory.makeNode("(");
      Node actual2 = factory.makeNode(")");

      Assert.assertTrue((actual1 != null));
      Assert.assertTrue(actual1 instanceof StartParenthesis);
      Assert.assertTrue((actual2 != null));
      Assert.assertTrue(actual2 instanceof EndParenthesis);
    }


    @Test
    public void Should_not_be_able_to_create_a_identifier_node_with_wrong_literal() {
      ParenthesisFactory factory = new ParenthesisFactory();

      Node actual = factory.makeNode("{");

      Assert.assertTrue((actual == null));
    }

    /**
     * WHAT THE FUCK AM I SUPPOSE TO TEST ????
     */
    @Test
    public void Should_be_able_to_parse_the_expression() {

      String input = "(1+2)";
      l.reset(input);
      ParenthesisFactory factory = new ParenthesisFactory();
      Node in = parser.consume();
      Expression n = factory.parse(parser, null, in,null);


      Assert.assertTrue(n instanceof SumExpression);

      BinaryOperator x = (BinaryOperator) n;

      Assert.assertTrue(x.getOperand() instanceof IntegerNode);
      Assert.assertTrue(x.getSecondOperand() instanceof IntegerNode);

    }


  }

}
