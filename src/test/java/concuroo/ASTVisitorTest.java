package concuroo;

import static org.junit.Assert.*;

import ConcurooParser.ConcurooLexer;
import ConcurooParser.ConcurooParser;
import ConcurooParser.ConcurooParser.*;
import concuroo.nodes.Node;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.statement.CompoundStatement;
import concuroo.nodes.statement.IterationStatement;
import concuroo.nodes.statement.JumpStatement;
import concuroo.nodes.statement.SelectionStatement;
import concuroo.nodes.statement.jumpStatement.BreakStatement;
import concuroo.nodes.statement.jumpStatement.ContinueStatement;
import concuroo.nodes.statement.jumpStatement.ReturnStatement;
import concuroo.nodes.statement.selectionStatement.IfStatement;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

public class ASTVisitorTest {

  @Test
  public void visitEmptyCompoundStatement() {
    ConcurooParser parser = parse("{}");
    CompoundStatementContext ctx = parser.compoundStatement();
    Node n = new ASTVisitor().visit(ctx);
    assertTrue(n instanceof CompoundStatement);
    assertEquals(0, ((CompoundStatement) n).size());
  }

  @Test
  public void visitNonEmptyCompoundStatement() {
    ConcurooParser parser = parse("{return;}");
    CompoundStatementContext ctx = parser.compoundStatement();
    Node n = new ASTVisitor().visit(ctx);
    assertTrue(n instanceof CompoundStatement);
    assertEquals(1, ((CompoundStatement) n).size());
    assertTrue(((CompoundStatement) n).getStatement(0) instanceof ReturnStatement);
  }

  @Test
  public void visitIterationStatement() {
    ConcurooParser parser = parse("while(true) return;");
    IterationStatementContext ctx = parser.iterationStatement();

    Node n = new ASTVisitor().visit(ctx);
    assertTrue(n instanceof IterationStatement);
    // assertTrue(((IterationStatement) n).getCondition() instanceof BooleanLiteral);
    assertTrue(((IterationStatement) n).getConsequence() instanceof ReturnStatement);
  }

  @Test
  public void visitIfStatementNoElse() {
    ConcurooParser parser = parse("if(1 == 1) return;");
    SelectionStatementContext ctx = parser.selectionStatement();

    Node n = new ASTVisitor().visit(ctx);
    assertTrue(n instanceof IfStatement);
    assertTrue(((IfStatement) n).getCondition() instanceof Expression);
    assertTrue(((IfStatement) n).getConsequence() instanceof ReturnStatement);
    assertNull(((IfStatement) n).getAlternative());
  }

  @Test
  public void visitIfStatementWithElse() {
    ConcurooParser parser = parse("if(1 == 1) return else return;");
    SelectionStatementContext ctx = parser.selectionStatement();

    Node n = new ASTVisitor().visit(ctx);
    assertTrue(n instanceof IfStatement);
    assertTrue(((IfStatement) n).getCondition() instanceof LogicalEqualityExpression);
    assertTrue(((IfStatement) n).getConsequence() instanceof ReturnStatement);
    assertTrue(((IfStatement) n).getAlternative() instanceof ReturnStatement);
  }

  @Test
  public void visitJumpStatementBreak() {
    ConcurooParser parser = parse("break;");
    JumpStatementContext ctx = parser.jumpStatement();

    Node n = new ASTVisitor().visit(ctx);
    assertTrue(n instanceof BreakStatement);
  }

  @Test
  public void visitJumpStatementContinue() {
    ConcurooParser parser = parse("continue;");
    JumpStatementContext ctx = parser.jumpStatement();

    Node n = new ASTVisitor().visit(ctx);
    assertTrue(n instanceof ContinueStatement);
  }

  @Test
  public void visitJumpStatementReturn() {
    ConcurooParser parser = parse("return;");
    JumpStatementContext ctx = parser.jumpStatement();

    Node n = new ASTVisitor().visit(ctx);
    assertTrue(n instanceof ReturnStatement);
    assertNull(((ReturnStatement) n).getReturnValue());
  }

  @Test
  public void visitJumpStatementReturnWithValue() {
    ConcurooParser parser = parse("return 1;");
    JumpStatementContext ctx = parser.jumpStatement();

    Node n = new ASTVisitor().visit(ctx);
    assertTrue(n instanceof ReturnStatement);
    assertTrue(((ReturnStatement) n).getReturnValue() instanceof );

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