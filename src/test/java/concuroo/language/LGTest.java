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
import concuroo.symbol.SymbolTable;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import static concuroo.language.TokenType.*;
import static org.junit.Assert.*;

public class LGTest {
    private static LG lg;
    private static SymbolTable st;
    private static GroupFactory<Parenthesis> lpar;
    private static GroupFactory<Parenthesis> rpar;
    private static GroupFactory<Curly> lcur;
    private static GroupFactory<Curly> rcur;

    @Before
    public void beforeEach() {
        lg = new LG();
        st = new SymbolTable();
        lpar = new GroupFactory<>('(', Parenthesis::new);
        rpar = new GroupFactory<>(')', Parenthesis::new);
        lcur = new GroupFactory<>('{', Curly::new);
        rcur = new GroupFactory<>('}', Curly::new);
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

        Pair<Node, Integer> n = lg.lookupStatement(new Node[]{
                new IntegerNode("1")
        }, st);

        assertTrue(n.getKey() instanceof ExpressionStatement);
    }

    @Test
    public void registersEmptyBlockStatement() {
        lg.registerToken("{", new GroupFactory<>('{', Curly::new));
        lg.registerToken("}", new GroupFactory<>('}', Curly::new));

        lg.registerStatement(new BlockFactory(), new Object[][]{
                new Object[]{ "{", new Class<?>[]{Statement.class}, "}" },
                new Object[]{ "{", "}" },
        });

        lg.registerStatement(new ExpressionStatementFactory(), new Object[][]{
                new Object[]{ Expression.class }
        });

        Pair<Node, Integer> n = lg.lookupStatement(new Node[]{
                lcur.makeNode("{"),
                rcur.makeNode("}"),
        }, st);

        assertTrue(n.getKey() instanceof BlockStatement);
        assertTrue(n.getValue() == 2);
        assertTrue(((BlockStatement) n.getKey()).getStatements().size() == 0);
    }

    @Test
    public void registersBlockStatement() {
        lg.registerToken("{", new GroupFactory<>('{', Curly::new));
        lg.registerToken("}", new GroupFactory<>('}', Curly::new));
        lg.registerToken(";", "^\\;", SEMICOLON);


        lg.registerStatement(new BlockFactory(), new Object[][]{
                new Object[]{ "{", new Class<?>[]{Statement.class}, "}" },
                new Object[]{ "{", "}" },
        });

        lg.registerStatement(new ExpressionStatementFactory(), new Object[][]{
                new Object[]{ Expression.class }
        });

        Pair<Node, Integer> n = lg.lookupStatement(new Node[]{
                lcur.makeNode("{"),
                new IntegerNode("1"),
                new TokenNode(";", TokenType.SEMICOLON, ";"),
                new IntegerNode("2"),
                new TokenNode(";", TokenType.SEMICOLON, ";"),
                rcur.makeNode("}"),
        }, st);

        assertTrue(n.getKey() instanceof BlockStatement);
        assertTrue(n.getValue() == 6);
        assertTrue(((BlockStatement) n.getKey()).getStatements().size() == 2);
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

        Pair<Node, Integer> n = lg.lookupStatement(new Node[]{
                new IfStatement(),
                lpar.makeNode("("),
                new IntegerNode("1"),
                rpar.makeNode(")"),
                new IntegerNode("1")
        }, st);

        assertTrue(n.getKey() instanceof IfStatement);
        assertTrue(5 == n.getValue());
        assertTrue(((IfStatement) n.getKey()).getCondition() instanceof IntegerNode);
        assertTrue(((IfStatement) n.getKey()).getConsequence() instanceof ExpressionStatement);
    }

    @Test
    public void registersIfElseStatement() {
        lg.registerToken("IF", new IfFactory());
        lg.registerToken("(", new GroupFactory<>('(', Parenthesis::new));
        lg.registerToken(")", new GroupFactory<>(')', Parenthesis::new));
        lg.registerToken("ELSE", "^else", STATEMENT);

        lg.registerStatement(new IfFactory(), new Object[][]{
                new Object[]{ "IF", "(", Expression.class, ")", Statement.class, "ELSE", Statement.class },
                new Object[]{ "IF", "(", Expression.class, ")", Statement.class }
        });

        lg.registerStatement(new ExpressionStatementFactory(), new Object[][]{
                new Object[]{ Expression.class }
        });

        Pair<Node, Integer> n = lg.lookupStatement(new Node[]{
                new IfStatement(),
                lpar.makeNode("("),
                new IntegerNode("1"),
                rpar.makeNode(")"),
                new IntegerNode("1"),
                new TokenNode("ELSE", TokenType.STATEMENT, "else"),
                new IntegerNode("1")
        }, st);

        assertTrue(n.getKey() instanceof IfStatement);
        assertTrue(7 == n.getValue());
        assertTrue(((IfStatement) n.getKey()).getCondition() instanceof IntegerNode);
        assertTrue(((IfStatement) n.getKey()).getConsequence() instanceof ExpressionStatement);
        assertTrue(((IfStatement) n.getKey()).getAlternative() instanceof ExpressionStatement);
    }


    @Test
    public void registersIfElseIfStatement() {
        lg.registerToken("IF", new IfFactory());
        lg.registerToken("(", new GroupFactory<>('(', Parenthesis::new));
        lg.registerToken(")", new GroupFactory<>(')', Parenthesis::new));
        lg.registerToken("ELSE", "^else", STATEMENT);

        lg.registerStatement(new IfFactory(), new Object[][]{
                new Object[]{ "IF", "(", Expression.class, ")", Statement.class, "ELSE", Statement.class },
                new Object[]{ "IF", "(", Expression.class, ")", Statement.class }
        });

        lg.registerStatement(new ExpressionStatementFactory(), new Object[][]{
                new Object[]{ Expression.class }
        });

        Pair<Node, Integer> n = lg.lookupStatement(new Node[]{
                new IfStatement(),
                lpar.makeNode("("),
                new IntegerNode("1"),
                rpar.makeNode(")"),
                new IntegerNode("1"),
                new TokenNode("ELSE", TokenType.STATEMENT, "else"),
                new IfStatement(),
                lpar.makeNode("("),
                new IntegerNode("1"),
                rpar.makeNode(")"),
                new IntegerNode("1")
        }, st);

        assertTrue(n.getKey() instanceof IfStatement);
        assertTrue(11 == n.getValue());
        IfStatement st = (IfStatement) n.getKey();
        assertTrue(st.getCondition() instanceof IntegerNode);
        assertTrue(st.getConsequence() instanceof ExpressionStatement);
        assertTrue(st.getAlternative() instanceof IfStatement);
        assertTrue(((IfStatement) st.getAlternative()).getCondition() instanceof IntegerNode);
        assertTrue(((IfStatement) st.getAlternative()).getConsequence() instanceof ExpressionStatement);
    }
}