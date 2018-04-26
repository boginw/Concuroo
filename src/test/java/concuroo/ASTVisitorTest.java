package concuroo;

import static org.junit.Assert.*;

import ConcurooParser.ConcurooLexer;
import ConcurooParser.ConcurooParser;
import ConcurooParser.ConcurooParser.*;
import concuroo.nodes.Node;
import concuroo.nodes.statement.CompoundStatement;
import concuroo.nodes.statement.IterationStatement;
import concuroo.nodes.statement.jumpStatement.ReturnStatement;
import concuroo.symbol.SymbolTable;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Before;
import org.junit.Test;

public class ASTVisitorTest {
  private SymbolTable st;

  @Before
  public void beforeAll() {
    st = new SymbolTable();
  }

  @Test
  public void visitEmptyCompoundStatement() {
    ConcurooParser parser = parse("{}");
    CompoundStatementContext ctx = parser.compoundStatement();
    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof CompoundStatement);
    assertEquals(0, ((CompoundStatement) n).size());
  }

  @Test
  public void visitNonEmptyCompoundStatement() {
    ConcurooParser parser = parse("{return;}");
    CompoundStatementContext ctx = parser.compoundStatement();
    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof CompoundStatement);
    assertEquals(1, ((CompoundStatement) n).size());
    assertTrue(((CompoundStatement) n).getStatement(0) instanceof ReturnStatement);
    assertEquals(((CompoundStatement) n).getScope().getParent(), st);
  }

  @Test
  public void visitIterationStatement() {
    ConcurooParser parser = parse("while(true) return;");
    IterationStatementContext ctx = parser.iterationStatement();

    Node n = new ASTVisitor(new SymbolTable()).visit(ctx);
    assertTrue(n instanceof IterationStatement);
    // assertTrue(((IterationStatement) n).getCondition() instanceof BooleanLiteral);
    assertTrue(((IterationStatement) n).getConsequence() instanceof ReturnStatement);
  }

  @Test
  public void visitIfStatement() {
  }

  @Test
  public void visitJumpStatement() {
  }

  @Test
  public void visitAssignmentExpression() {
  }

  @Test
  public void visitAdditiveExpression() {
  }

  @Test
  public void visitMultiplicativeExpression() {
  }

  @Test
  public void visitLogicalAndExpression() {
  }

  @Test
  public void visitLogicalOrExpression() {
  }

  @Test
  public void visitEqualityExpression() {
  }

  public ConcurooParser parse(String input){
    ConcurooLexer lex = new ConcurooLexer(CharStreams.fromString(input));
    ConcurooParser parser = new ConcurooParser(new CommonTokenStream(lex));
    parser.setBuildParseTree(true);
    return parser;
  }
}