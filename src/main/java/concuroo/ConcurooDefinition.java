package concuroo;

import concuroo.factories.expression.*;
import concuroo.factories.statement.IfFactory;
import concuroo.language.LanguageDefinition;
import concuroo.language.TokenType;
import concuroo.nodes.expressions.operators.groups.Curly;
import concuroo.nodes.expressions.operators.groups.Parenthesis;
import concuroo.nodes.expressions.operators.groups.Square;
import concuroo.language.LG;

import static concuroo.language.TokenType.*;

@SuppressWarnings("SpellCheckingInspection")
public class ConcurooDefinition extends LanguageDefinition{

    @Override
    public void registerTokens(LG g){
        // Operators
        g.registerToken("+", new SumFactory());
        // Braces
        g.registerToken("(", new GroupFactory<>('(', Parenthesis::new));
        g.registerToken(")", new GroupFactory<>(')', Parenthesis::new));
        g.registerToken("[", new GroupFactory<>('[', Square::new));
        g.registerToken("]", new GroupFactory<>(']', Square::new));
        g.registerToken("{", new GroupFactory<>('{', Curly::new));
        g.registerToken("}", new GroupFactory<>('}', Curly::new));
        // Separators
        g.registerToken(";", "^\\;", SEMICOLON);
        // Statements
        g.registerToken("IF", new IfFactory());
        g.registerToken("ELSE", "^else", STATEMENT);

        // Literals
        g.registerToken("LIT_INT", new IntFactory());
        // Types
        g.registerToken("INT","^int", TYPE);
        g.registerToken("CHAR","^char", TYPE);
        g.registerToken("FLOAT","^float", TYPE);
        g.registerToken("BOOL","^bool", TYPE);
        g.registerToken("STRING","^string", TYPE);
        g.registerToken("VOID","^void", TYPE);
    }

    @Override
    public void registerStatements(LG g) {
        g.registerStatement(new IfFactory(), new Object[][]{
                new Object[]{ "IF", "(", expr(), ")", stat() }
        });

    }
}
