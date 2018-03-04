package concuroo.parser;

import static org.junit.Assert.assertEquals;

import concuroo.ConcurooDefinition;
import concuroo.language.LG;
import concuroo.lexer.Lexer;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.atoms.Atom;
import concuroo.nodes.expressions.operators.binary.BinaryOperator;
import concuroo.nodes.expressions.operators.unary.UnaryOperator;
import concuroo.symbol.SymbolTable;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class NegativeParseExpressions {

  @Parameters(name = "{0}")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {
            "Throws on alphabetic and numeric input",
            "1a+1"
        },
        {
            "Throws on missing operand",
            "1*"
        },
        {
            "Throws on multiple operators",
            "1*+*1"
        },
        {
            "Throws on operator only",
            "+"
        },
        {
            "Throws on missing right parenthesis",
            "(1+1",
        },
        {
            "Throws on missing right parenthesis, long statement",
            "(1+1+(1+1)",
        },
        {
            "Throws on illegal character in number",
            "1´1"
        },
        {
            "Throws on illegal character beginning number",
            "¨1"
        },
        {
            "Throws on illegal character in statement",
            "1+¨1"
        },
        {
            "Throws on illegal character beginning statement",
            "¨1+1"
        }
    });
  }

  private final String input;

  public NegativeParseExpressions(String name, String input) {
    this.input = input;
  }

  @Test (expected = Exception.class)
  public void test() {
    LG lg = new ConcurooDefinition().getGrammar();
    SymbolTable st = new SymbolTable();
    Lexer l = new Lexer(st, lg);
    Parser p = new Parser(l, st);
    StringBuilder sb = new StringBuilder();

    l.reset(input);
    p.parseExpression();
  }
}