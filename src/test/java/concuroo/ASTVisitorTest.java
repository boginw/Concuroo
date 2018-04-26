package concuroo;

import static org.junit.Assert.*;

import ConcurooParser.ConcurooLexer;
import ConcurooParser.ConcurooParser;
import ConcurooParser.ConcurooParser.*;
import concuroo.nodes.Node;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.binaryExpression.AssignmentExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.MultiplicativeExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalAndExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalOrExpression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;
import concuroo.nodes.expression.literalExpression.BoolLiteral;
import concuroo.nodes.expression.literalExpression.IntLiteral;
import concuroo.nodes.statement.CompoundStatement;
import concuroo.nodes.statement.IterationStatement;
import concuroo.nodes.statement.jumpStatement.BreakStatement;
import concuroo.nodes.statement.jumpStatement.ContinueStatement;
import concuroo.nodes.statement.jumpStatement.ReturnStatement;
import concuroo.nodes.statement.selectionStatement.IfStatement;
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
    assertTrue(((IterationStatement) n).getCondition() instanceof BoolLiteral);
    assertTrue(((IterationStatement) n).getConsequence() instanceof ReturnStatement);
  }

  @Test
  public void visitIfStatementNoElse() {
    ConcurooParser parser = parse("if(true) return;");
    SelectionStatementContext ctx = parser.selectionStatement();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof IfStatement);
    assertTrue(((IfStatement) n).getCondition() instanceof BoolLiteral);
    assertTrue(((IfStatement) n).getConsequence() instanceof ReturnStatement);
    assertNull(((IfStatement) n).getAlternative());
  }

  @Test
  public void visitIfStatementWithElse() {
    ConcurooParser parser = parse("if(true) return; else return;");
    SelectionStatementContext ctx = parser.selectionStatement();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof IfStatement);
    assertTrue(((IfStatement) n).getCondition() instanceof BoolLiteral);
    assertTrue(((IfStatement) n).getConsequence() instanceof ReturnStatement);
    assertTrue(((IfStatement) n).getAlternative() instanceof ReturnStatement);
  }

  @Test
  public void visitJumpStatementBreak() {
    ConcurooParser parser = parse("break;");
    JumpStatementContext ctx = parser.jumpStatement();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof BreakStatement);
  }

  @Test
  public void visitJumpStatementContinue() {
    ConcurooParser parser = parse("continue;");
    JumpStatementContext ctx = parser.jumpStatement();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof ContinueStatement);
  }

  @Test
  public void visitJumpStatementReturn() {
    ConcurooParser parser = parse("return;");
    JumpStatementContext ctx = parser.jumpStatement();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof ReturnStatement);
    assertNull(((ReturnStatement) n).getReturnValue());
  }

  @Test
  public void visitJumpStatementReturnWithIntValue() {
    ConcurooParser parser = parse("return 1;");
    JumpStatementContext ctx = parser.jumpStatement();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof ReturnStatement);

    Expression returnValue = ((ReturnStatement) n).getReturnValue();
    assertTrue(((ReturnStatement) n).getReturnValue() instanceof IntLiteral);

    String intLiteral = ((IntLiteral) returnValue).getLiteral();
    assertEquals("1", intLiteral);
  }

  @Test
  public void visitJumpStatementReturnWithBoolValue() {
    ConcurooParser parser = parse("return true;");
    JumpStatementContext ctx = parser.jumpStatement();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof ReturnStatement);

    Expression returnValue = ((ReturnStatement) n).getReturnValue();
    assertTrue(returnValue instanceof BoolLiteral);

    String boolLiteral = ((BoolLiteral) returnValue).getLiteral();
    assertEquals("true", boolLiteral);
  }

  @Test
  public void visitAssignmentExpressionInt() {
    ConcurooParser parser = parse("a = 1");
    AssignmentExpressionContext ctx = parser.assignmentExpression();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof AssignmentExpression);
    assertTrue(((AssignmentExpression) n).getFirstOperand() instanceof VariableExpression);
    assertTrue(((AssignmentExpression) n).getSecondOperand() instanceof IntLiteral);
  }

  @Test
  public void visitAssignmentExpressionBool() {
    ConcurooParser parser = parse("a = true");
    AssignmentExpressionContext ctx = parser.assignmentExpression();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof AssignmentExpression);
    assertTrue(((AssignmentExpression) n).getFirstOperand() instanceof VariableExpression);
    assertTrue(((AssignmentExpression) n).getSecondOperand() instanceof BoolLiteral);
  }

  @Test
  public void visitAdditiveExpression() {
    ConcurooParser parser = parse("1+2");
    AdditiveExpressionContext ctx = parser.additiveExpression();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof AdditiveExpression);

    Expression firstOperand = ((AdditiveExpression) n).getFirstOperand();
    Expression secondOperand = ((AdditiveExpression) n).getSecondOperand();

    /* Testing first operand */
    assertTrue(firstOperand instanceof IntLiteral);

    IntLiteral firstLiteral = (IntLiteral) firstOperand;
    assertEquals(firstLiteral.getLiteral(), "1");
    assertTrue(firstLiteral.getValue() instanceof Integer);

    int firstInt = (int) ((IntLiteral) firstOperand).getValue();
    assertEquals(firstInt, 1);

    /* Testing second operand */
    assertTrue(secondOperand instanceof IntLiteral);

    IntLiteral secondLiteral = (IntLiteral) secondOperand;
    assertEquals(secondLiteral.getLiteral(), "2");
    assertTrue(secondLiteral.getValue() instanceof Integer);

    int secondInt = (int) ((IntLiteral) secondOperand).getValue();
    assertEquals(secondInt, 2);
  }

  @Test
  public void visitMultiplicativeExpression() {
    ConcurooParser parser = parse("1*2");
    MultiplicativeExpressionContext ctx = parser.multiplicativeExpression();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof MultiplicativeExpression);

    Expression firstOperand = ((MultiplicativeExpression) n).getFirstOperand();
    Expression secondOperand = ((MultiplicativeExpression) n).getSecondOperand();

    /* Testing first operand */
    assertTrue(firstOperand instanceof IntLiteral);

    IntLiteral firstLiteral = (IntLiteral) firstOperand;
    assertEquals(firstLiteral.getLiteral(), "1");
    assertTrue(firstLiteral.getValue() instanceof Integer);

    int firstInt = (int) ((IntLiteral) firstOperand).getValue();
    assertEquals(firstInt, 1);

    /* Testing second operand */
    assertTrue(secondOperand instanceof IntLiteral);

    IntLiteral secondLiteral = (IntLiteral) secondOperand;
    assertEquals(secondLiteral.getLiteral(), "2");
    assertTrue(secondLiteral.getValue() instanceof Integer);

    int secondInt = (int) ((IntLiteral) secondOperand).getValue();
    assertEquals(secondInt, 2);
  }

  @Test
  public void visitLogicalAndExpression() {
    ConcurooParser parser = parse("true&&false");
    LogicalAndExpressionContext ctx = parser.logicalAndExpression();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof LogicalAndExpression);
    assertTrue(((LogicalAndExpression) n).getFirstOperand() instanceof BoolLiteral);
    assertTrue(((LogicalAndExpression) n).getSecondOperand() instanceof BoolLiteral);

    Expression expr = ((LogicalAndExpression) n).getFirstOperand();
    Object obj = ((BoolLiteral) expr).getValue();
    Boolean boolLit = (Boolean) obj;
    assertTrue(boolLit);

    expr = ((LogicalAndExpression) n).getSecondOperand();
    obj = ((BoolLiteral) expr).getValue();
    boolLit = (Boolean) obj;
    assertFalse(boolLit);
  }

  @Test
  public void visitLogicalOrExpressionBools() {
    ConcurooParser parser = parse("true||false");
    LogicalOrExpressionContext ctx = parser.logicalOrExpression();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof LogicalOrExpression);
    assertTrue(((LogicalOrExpression) n).getFirstOperand() instanceof BoolLiteral);
    assertTrue(((LogicalOrExpression) n).getSecondOperand() instanceof BoolLiteral);

    Expression expr = ((LogicalOrExpression) n).getFirstOperand();
    Object obj = ((BoolLiteral) expr).getValue();
    Boolean boolLit = (Boolean) obj;
    assertTrue(boolLit);

    expr = ((LogicalOrExpression) n).getSecondOperand();
    obj = ((BoolLiteral) expr).getValue();
    boolLit = (Boolean) obj;
    assertFalse(boolLit);
  }

  @Test
  public void visitLogicalOrExpressionIntEquality() {
    ConcurooParser parser = parse("1==1||2==3");
    LogicalOrExpressionContext ctx = parser.logicalOrExpression();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof LogicalOrExpression);
    assertTrue(((LogicalOrExpression) n).getFirstOperand() instanceof LogicalEqualityExpression);
    assertTrue(((LogicalOrExpression) n).getSecondOperand() instanceof LogicalEqualityExpression);
  }

  @Test
  public void visitEqualityExpression() {
    ConcurooParser parser = parse("true==false");
    EqualityExpressionContext ctx = parser.equalityExpression();

    Node n = new ASTVisitor(st).visit(ctx);
    assertTrue(n instanceof LogicalEqualityExpression);
    assertTrue(((LogicalEqualityExpression) n).getFirstOperand() instanceof BoolLiteral);
    assertTrue(((LogicalEqualityExpression) n).getSecondOperand() instanceof BoolLiteral);

    Expression expr = ((LogicalEqualityExpression) n).getFirstOperand();
    Object obj = ((BoolLiteral) expr).getValue();
    Boolean boolLit = (Boolean) obj;
    assertTrue(boolLit);

    expr = ((LogicalEqualityExpression) n).getSecondOperand();
    obj = ((BoolLiteral) expr).getValue();
    boolLit = (Boolean) obj;
    assertFalse(boolLit);
  }

  public ConcurooParser parse(String input){
    ConcurooLexer lex = new ConcurooLexer(CharStreams.fromString(input));
    ConcurooParser parser = new ConcurooParser(new CommonTokenStream(lex));
    parser.setBuildParseTree(true);
    return parser;
  }
}