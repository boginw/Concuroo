package concuroo;

import static concuroo.language.TokenType.SEMICOLON;
import static concuroo.language.TokenType.STATEMENT;
import static concuroo.language.TokenType.TYPE;

import concuroo.factories.expression.CurlyFactory;
import concuroo.factories.expression.DifferenceFactory;
import concuroo.factories.expression.IntFactory;
import concuroo.factories.expression.ParenthesisFactory;
import concuroo.factories.expression.ProductFactory;
import concuroo.factories.expression.SquareFactory;
import concuroo.factories.expression.SumFactory;
import concuroo.factories.statement.BlockFactory;
import concuroo.factories.statement.IfFactory;
import concuroo.language.LG;
import concuroo.language.LanguageDefinition;

@SuppressWarnings("SpellCheckingInspection")
public class ConcurooDefinition extends LanguageDefinition {

  @Override
  public void registerTokens(LG g) {
    // Arithmetic Operators
    g.registerToken("*", new ProductFactory());
    g.registerToken("+", new SumFactory());
    g.registerToken("-", new DifferenceFactory());
    // Braces
    g.registerToken("(", new ParenthesisFactory());
    g.registerToken(")", new ParenthesisFactory());
    g.registerToken("[", new SquareFactory());
    g.registerToken("]", new SquareFactory());
    g.registerToken("{", new CurlyFactory());
    g.registerToken("}", new CurlyFactory());
    // Separators
    g.registerToken(";", "^\\;", SEMICOLON);
    // Statements
    g.registerToken("IF", new IfFactory());
    g.registerToken("ELSE", "^else", STATEMENT);
    g.registerToken("BREAK", "^break", STATEMENT);
    g.registerToken("CONTINUE", "^continue", STATEMENT);
    // Literals
    g.registerToken("LIT_INT", new IntFactory());
    // Types
    g.registerToken("INT", "^int", TYPE);
    g.registerToken("CHAR", "^char", TYPE);
    g.registerToken("FLOAT", "^float", TYPE);
    g.registerToken("BOOL", "^bool", TYPE);
    g.registerToken("STRING", "^string", TYPE);
    g.registerToken("VOID", "^void", TYPE);
  }

  @Override
  public void registerStatements(LG g) {
    // ORDER OF REGISTRATION MATTERS, REGISTER EXPRESSION STATEMENTS LAST!

    /*g.registerStatement(new IfFactory(), new Object[][]{
        new Object[]{"IF", "(", expr(), ")", stat(), "ELSE", stat()},
        new Object[]{"IF", "(", expr(), ")", stat()}
    });*/
  }
}
