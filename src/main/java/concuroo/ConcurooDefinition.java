package concuroo;

import static concuroo.language.TokenType.SEMICOLON;
import static concuroo.language.TokenType.STATEMENT;
import static concuroo.language.TokenType.TYPE;

import concuroo.factories.expression.DifferenceFactory;
import concuroo.factories.expression.GroupFactory;
import concuroo.factories.expression.IntFactory;
import concuroo.factories.expression.ProductFactory;
import concuroo.factories.expression.SumFactory;
import concuroo.factories.statement.BlockFactory;
import concuroo.factories.statement.IfFactory;
import concuroo.language.LG;
import concuroo.language.LanguageDefinition;
import concuroo.nodes.expressions.operators.groups.Parenthesis;
import concuroo.nodes.expressions.operators.groups.Square;

@SuppressWarnings("SpellCheckingInspection")
public class ConcurooDefinition extends LanguageDefinition {

  @Override
  public void registerTokens(LG g) {
    // Arithmetic Operators
    g.registerToken("*", new ProductFactory());
    g.registerToken("+", new SumFactory());
    g.registerToken("-", new DifferenceFactory());
    // Braces
    g.registerToken("(", new GroupFactory<>('(', Parenthesis::new));
    g.registerToken(")", new GroupFactory<>(')', Parenthesis::new));
    g.registerToken("[", new GroupFactory<>('[', Square::new));
    g.registerToken("]", new GroupFactory<>(']', Square::new));
    g.registerToken("{", new BlockFactory());
    g.registerToken("}", new BlockFactory());
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
