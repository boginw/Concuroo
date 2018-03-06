package concuroo.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import concuroo.ConcurooDefinition;
import concuroo.language.LG;
import concuroo.nodes.Node;
import concuroo.nodes.expressions.atoms.IntegerNode;
import concuroo.nodes.expressions.operators.indecisive.SumExpression;
import concuroo.nodes.statements.IfStatement;
import concuroo.symbol.SymbolTable;
import org.junit.BeforeClass;
import org.junit.Test;

public class LexerTest {

  private static Lexer l;

  @BeforeClass
  public static void beforeAll() {
    LG lg = new ConcurooDefinition().getGrammar();
    l = new Lexer(new SymbolTable(), lg);
  }

  @Test
  public void ignoresWhiteSpace() {
    Node[] tokens = l.lex("   \n   if\t    \n");
    assertTrue(tokens[0] instanceof IfStatement);
  }

  @Test
  public void keyword() {
    Node[] tokens = l.lex("if");
    assertTrue(tokens[0] instanceof IfStatement);
  }

  @Test
  public void expression() {
    Node[] tokens = l.lex("1+1");
    assertEquals(3, tokens.length);
    assertTrue(tokens[0] instanceof IntegerNode);
    assertTrue(tokens[1] instanceof SumExpression);
    assertTrue(tokens[2] instanceof IntegerNode);
  }
}