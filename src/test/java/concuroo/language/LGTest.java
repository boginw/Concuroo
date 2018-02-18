package concuroo.language;

import concuroo.factories.expression.GroupFactory;
import concuroo.factories.expression.IntFactory;
import concuroo.factories.statement.BlockFactory;
import concuroo.factories.statement.ExpressionStatementFactory;
import concuroo.factories.statement.IfFactory;
import concuroo.nodes.Node;
import concuroo.nodes.TokenNode;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.atoms.IntegerNode;
import concuroo.nodes.expressions.operators.groups.Curly;
import concuroo.nodes.expressions.operators.groups.Parenthesis;
import concuroo.nodes.statements.BlockStatement;
import concuroo.nodes.statements.ExpressionStatement;
import concuroo.nodes.statements.IfStatement;
import concuroo.nodes.statements.Statement;
import org.junit.Before;
import org.junit.Test;

import static concuroo.language.TokenType.*;
import static org.junit.Assert.*;

public class LGTest {
    private static LG lg;
    private static GroupFactory<Parenthesis> lpar;
    private static GroupFactory<Parenthesis> rpar;
    private static GroupFactory<Curly> lqur;
    private static GroupFactory<Curly> rqur;

    @Before
    public void beforeEach() {
        lg = new LG();
        lpar = new GroupFactory<>('(', Parenthesis::new);
        rpar = new GroupFactory<>(')', Parenthesis::new);
        lqur = new GroupFactory<>('{', Curly::new);
        rqur = new GroupFactory<>('}', Curly::new);
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

    @Test
    public void registersExpressionStatements(){
        lg.registerStatement(new ExpressionStatementFactory(), new Object[][]{
                new Object[]{ Expression.class }
        });

        Node n = lg.lookupStatement(new Node[]{
                new IntegerNode("1")
        });

        assertTrue(n instanceof ExpressionStatement);
    }

    @Test
    public void registersBlockStatement() {
        lg.registerToken("{", new GroupFactory<>('{', Curly::new));
        lg.registerToken("}", new GroupFactory<>('}', Curly::new));

        lg.registerStatement(new BlockFactory(), new Object[][]{
                new Object[]{ "{", Statement.class, "}" },
                new Object[]{ "{", "}" },
        });

        lg.registerStatement(new ExpressionStatementFactory(), new Object[][]{
                new Object[]{ Expression.class }
        });

        Node n = lg.lookupStatement(new Node[]{
                lqur.makeNode("{"),
                new IntegerNode("1"),
                rqur.makeNode("}"),
        });

        assertTrue(n instanceof BlockStatement);
    }

    @Test
    public void registersIfStatement() {
        lg.registerToken("IF", new IfFactory());
        lg.registerToken("(", new GroupFactory<>('(', Parenthesis::new));
        lg.registerToken(")", new GroupFactory<>(')', Parenthesis::new));


        lg.registerStatement(new IfFactory(), new Object[][]{
                new Object[]{ "IF", "(", Expression.class, ")", Statement.class }
        });

        lg.registerStatement(new ExpressionStatementFactory(), new Object[][]{
                new Object[]{ Expression.class }
        });

        Node n = lg.lookupStatement(new Node[]{
                new IfStatement(),
                lpar.makeNode("("),
                new IntegerNode("1"),
                rpar.makeNode(")"),
                new IntegerNode("1")
        });

        assertTrue(n instanceof IfStatement);
    }
}