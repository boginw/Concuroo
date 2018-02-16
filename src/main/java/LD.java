
import factories.expression.*;
import factories.statement.IfFactory;
import nodes.expressions.operators.groups.Curly;
import nodes.expressions.operators.groups.Parenthesis;
import nodes.expressions.operators.groups.Square;
import symbol.LG;

import static symbol.LG.LanguageType.*;
import static symbol.TokenType.*;

public class LD {
    public static void registerTokens(LG g){
        // Operators
        g.registerToken("+", new SumFactory());
        /*g.registerToken("-", new DifferenceFactory());
        g.registerToken("*", new ProductFactory());
        g.registerToken("/", new DivideFactory());
        g.registerToken("%", new ModFactory());*/
        // Braces
        g.registerToken("(", new GroupFactory<>('(', Parenthesis::new));
        g.registerToken(")", new GroupFactory<>(')', Parenthesis::new));
        g.registerToken("[", new GroupFactory<>('[', Square::new));
        g.registerToken("]", new GroupFactory<>(']', Square::new));
        g.registerToken("{", new GroupFactory<>('{', Curly::new));
        g.registerToken("}", new GroupFactory<>('}', Curly::new));
        // Separators
        g.registerToken(";", "\\;", SEMICOLON);
        // Statements
        //g.registerToken("=", "=", STATEMENT);
        g.registerToken("IF", new IfFactory());
        //g.registerToken("WHILE", "while", STATEMENT);
        // Literals
        g.registerToken("LIT_INT", new IntFactory());
        //g.registerToken("LIT_IDENT", new IdentifierFactory());
        // Types
        g.registerToken("INT","int", TYPE);
        g.registerToken("CHAR","char", TYPE);
        g.registerToken("FLOAT","float", TYPE);
        g.registerToken("BOOL","bool", TYPE);
        g.registerToken("STRING","string", TYPE);
        g.registerToken("VOID","void", TYPE);

        g.registerStatement(new IfFactory(), new Object[][]{
                new Object[]{"IF","(",EXPR,")","{",STAT,"}"}
        });

        /*g.registerStatement(new DeclarationFactory(), new Object[][]{
                new Object[]{TYPE,"IDENT","="}
        });

        g.registerStatement(new FunctionDeclarationFactory(), new Object[][]{
                new Object[]{TYPE,"IDENT","(",")"}
        });

        g.registerStatement(new WhileFactory(), new Object[][]{
                new Object[]{"WHILE"}
        });*/
    }
}
