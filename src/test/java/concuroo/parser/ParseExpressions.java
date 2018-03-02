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
public class ParseExpressions {

  @Parameters(name = "{0}")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {
            "Handles Parselets",
            "-1+1",
            "((-1)+1)"
        },
        {
            "Handles Unaries",
            "----1",
            "(-(-(-(-1))))"
        },
        {
            "Handles Unaries",
            "1+-1",
            "(1+(-1))"
        },
        {
            "Handles Parenthesis",
            "(-1)+1",
            "((-1)+1)"
        },
        {
            "Handles Precedence",
            "5+1*2",
            "(5+(1*2))"
        }
    });
  }

  private final String input;
  private final String expected;

  public ParseExpressions(String name, String input, String expected) {
    this.input = input;
    this.expected = expected;
  }

  @Test
  public void test() {
    LG lg = new ConcurooDefinition().getGrammar();
    SymbolTable st = new SymbolTable();
    Lexer l = new Lexer(st, lg);
    Parser p = new Parser(l, st);
    StringBuilder sb = new StringBuilder();

    l.reset(input);
    Expression n = p.parseExpression();
    buildExpressionString(n, sb);
    String actual = sb.toString();

    assertEquals(expected, actual);
  }

  private void buildExpressionString(Expression e, StringBuilder sb) {
    if (e instanceof Atom) {
      sb.append(e.getLiteral());
    } else if (e instanceof BinaryOperator) {
      BinaryOperator bo = (BinaryOperator) e;

      sb.append("(");

      buildExpressionString(bo.getOperand(), sb);
      sb.append(bo.getLiteral());
      buildExpressionString(bo.getSecondOperand(), sb);

      sb.append(")");
    } else if (e instanceof UnaryOperator) {
      UnaryOperator uo = (UnaryOperator) e;

      sb.append("(");

      if (uo.isPrefix()) {
        sb.append(uo.getLiteral());
        buildExpressionString(uo.getOperand(), sb);
      } else {
        buildExpressionString(uo.getOperand(), sb);
        sb.append(uo.getLiteral());
      }

      sb.append(")");
    }
  }
}