package concuroo;

import static org.junit.Assert.*;

import ConcurooParser.ConcurooLexer;
import ConcurooParser.ConcurooParser;
import ConcurooParser.ConcurooParser.*;
import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.Node;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.SizeofSpecifier;
import concuroo.nodes.expression.binaryExpression.AssignmentExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.MultiplicativeExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalAndExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalOrExpression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;
import concuroo.nodes.expression.literalExpression.BoolLiteral;
import concuroo.nodes.expression.literalExpression.IntLiteral;
import concuroo.nodes.expression.unaryExpression.SizeofExpression;
import concuroo.nodes.statement.CompoundStatement;
import concuroo.nodes.statement.ExpressionStatement;
import concuroo.nodes.statement.IterationStatement;
import concuroo.nodes.statement.iterationStatement.WhileStatement;
import concuroo.nodes.statement.jumpStatement.BreakStatement;
import concuroo.nodes.statement.jumpStatement.ContinueStatement;
import concuroo.nodes.statement.jumpStatement.ReturnStatement;
import concuroo.nodes.statement.selectionStatement.IfStatement;
import concuroo.symbol.SymbolTable;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Before;
import org.junit.Test;

public class CSTVisitorTest {

  private SymbolTable st;

  @Before
  public void beforeAll() {
    st = new SymbolTable();
  }

  @Test
  public void visitEmptyCompoundStatement() {
    ConcurooParser parser = parse("{}");
    CompoundStatementContext ctx = parser.compoundStatement();
    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof CompoundStatement);
    assertEquals(0, ((CompoundStatement) n).size());
  }

  @Test
  public void visitNonEmptyCompoundStatement() {
    ConcurooParser parser = parse("{return;}");
    CompoundStatementContext ctx = parser.compoundStatement();
    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof CompoundStatement);
    assertEquals(1, ((CompoundStatement) n).size());
    assertTrue(((CompoundStatement) n).getStatement(0) instanceof ReturnStatement);
    assertEquals(((CompoundStatement) n).getScope().getParent(), st);
  }

  @Test
  public void visitIterationStatement() {
    ConcurooParser parser = parse("while(true) return;");
    IterationStatementContext ctx = parser.iterationStatement();

    Node n = new CSTVisitor(new SymbolTable()).visit(ctx);
    assertTrue(n instanceof IterationStatement);

    IterationStatement statement = (IterationStatement) n;
    assertTrue(statement.getCondition() instanceof BoolLiteral);
    assertTrue(statement.getConsequence() instanceof ReturnStatement);

    WhileStatement expr = (WhileStatement) n;
    TestBooleanLiteral((BoolLiteral) expr.getCondition(), true);
  }

  @Test
  public void visitIfStatementNoElse() {
    ConcurooParser parser = parse("if(true) return;");
    IfStatementContext ctx = parser.ifStatement();

    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof IfStatement);

    IfStatement statement = (IfStatement) n;
    assertTrue(statement.getCondition() instanceof BoolLiteral);
    assertTrue(statement.getConsequence() instanceof ReturnStatement);
    assertNull(statement.getAlternative());

    IfStatement expr = (IfStatement) n;
    TestBooleanLiteral((BoolLiteral) expr.getCondition(), true);
  }

  @Test
  public void visitIfStatementWithElse() {
    ConcurooParser parser = parse("if(true) return; else return;");
    SelectionStatementContext ctx = parser.selectionStatement();

    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof IfStatement);

    IfStatement statement = (IfStatement) n;
    assertTrue(statement.getCondition() instanceof BoolLiteral);
    assertTrue(statement.getConsequence() instanceof ReturnStatement);
    assertTrue(statement.getAlternative() instanceof ReturnStatement);

    IfStatement expr = (IfStatement) n;
    TestBooleanLiteral((BoolLiteral) expr.getCondition(), true);
  }

  @Test
  public void visitJumpStatementBreak() {
    ConcurooParser parser = parse("break;");
    JumpStatementContext ctx = parser.jumpStatement();

    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof BreakStatement);
  }

  @Test
  public void visitJumpStatementContinue() {
    ConcurooParser parser = parse("continue;");
    JumpStatementContext ctx = parser.jumpStatement();

    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof ContinueStatement);
  }

  @Test
  public void visitJumpStatementReturn() {
    ConcurooParser parser = parse("return;");
    JumpStatementContext ctx = parser.jumpStatement();

    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof ReturnStatement);
    assertNull(((ReturnStatement) n).getReturnValue());
  }

  @Test
  public void visitJumpStatementReturnWithIntValue() {
    ConcurooParser parser = parse("return 1;");
    JumpStatementContext ctx = parser.jumpStatement();

    Node n = new CSTVisitor(st).visit(ctx);
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

    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof ReturnStatement);

    ReturnStatement returnValue = ((ReturnStatement) n);

    ReturnStatement expr = (ReturnStatement) n;
    TestBooleanLiteral((BoolLiteral) expr.getReturnValue(), true);
  }

  @Test
  public void visitAssignmentExpressionInt() {
    ConcurooParser parser = parse("{ int a; a = 1; }");
    CompoundStatementContext ctx = parser.compoundStatement();

    CompoundStatement cs = (CompoundStatement) new CSTVisitor(st).visit(ctx);
    Node n = ((ExpressionStatement) cs.getStatement(1)).getExpr();
    assertTrue(n instanceof AssignmentExpression);
    assertTrue(((AssignmentExpression) n).getFirstOperand() instanceof VariableExpression);
    assertTrue(((AssignmentExpression) n).getSecondOperand() instanceof IntLiteral);
  }

  @Test
  public void visitAssignmentExpressionBool() {
    ConcurooParser parser = parse("{ bool a; a = true; }");
    CompoundStatementContext ctx = parser.compoundStatement();

    CompoundStatement cs = (CompoundStatement) new CSTVisitor(st).visit(ctx);
    Node n = ((ExpressionStatement) cs.getStatement(1)).getExpr();

    assertTrue(n instanceof AssignmentExpression);

    AssignmentExpression returnValue = ((AssignmentExpression) n);
    assertTrue(returnValue.getFirstOperand() instanceof VariableExpression);

    AssignmentExpression expr = (AssignmentExpression) n;
    TestBooleanLiteral((BoolLiteral) expr.getSecondOperand(), true);
  }

  @Test
  public void visitAdditiveExpression() {
    ConcurooParser parser = parse("1+2");
    AdditiveExpressionContext ctx = parser.additiveExpression();

    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof AdditiveExpression);

    Expression firstOperand = ((AdditiveExpression) n).getFirstOperand();
    Expression secondOperand = ((AdditiveExpression) n).getSecondOperand();
    assertTrue(firstOperand instanceof IntLiteral);
    assertTrue(secondOperand instanceof IntLiteral);

    TestIntegerLiteral((IntLiteral) firstOperand, 1);
    TestIntegerLiteral((IntLiteral) secondOperand, 2);
  }

  @Test
  public void visitMultiplicativeExpression() {
    ConcurooParser parser = parse("1*2");
    MultiplicativeExpressionContext ctx = parser.multiplicativeExpression();

    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof MultiplicativeExpression);

    Expression firstOperand = ((MultiplicativeExpression) n).getFirstOperand();
    Expression secondOperand = ((MultiplicativeExpression) n).getSecondOperand();
    assertTrue(firstOperand instanceof IntLiteral);
    assertTrue(secondOperand instanceof IntLiteral);

    TestIntegerLiteral((IntLiteral) firstOperand, 1);
    TestIntegerLiteral((IntLiteral) secondOperand, 2);
  }

  @Test
  public void visitLogicalAndExpression() {
    ConcurooParser parser = parse("true&&false");
    LogicalAndExpressionContext ctx = parser.logicalAndExpression();

    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof LogicalAndExpression);
    assertTrue(((LogicalAndExpression) n).getFirstOperand() instanceof BoolLiteral);
    assertTrue(((LogicalAndExpression) n).getSecondOperand() instanceof BoolLiteral);

    LogicalAndExpression expr = (LogicalAndExpression) n;
    TestBooleanLiteral((BoolLiteral) expr.getFirstOperand(), true);
    TestBooleanLiteral((BoolLiteral) expr.getSecondOperand(), false);

  }

  @Test
  public void visitLogicalOrExpressionBools() {
    ConcurooParser parser = parse("true||false");
    LogicalOrExpressionContext ctx = parser.logicalOrExpression();

    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof LogicalOrExpression);

    LogicalOrExpression expr = (LogicalOrExpression) n;
    TestBooleanLiteral((BoolLiteral) expr.getFirstOperand(), true);
    TestBooleanLiteral((BoolLiteral) expr.getSecondOperand(), false);
  }

  @Test
  public void visitLogicalOrExpressionIntEquality() {
    ConcurooParser parser = parse("1==1||2==3");
    LogicalOrExpressionContext ctx = parser.logicalOrExpression();

    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof LogicalOrExpression);
    assertTrue(((LogicalOrExpression) n).getFirstOperand() instanceof LogicalEqualityExpression);
    assertTrue(((LogicalOrExpression) n).getSecondOperand() instanceof LogicalEqualityExpression);
  }

  @Test
  public void visitEqualityExpression() {
    ConcurooParser parser = parse("true==false");
    EqualityExpressionContext ctx = parser.equalityExpression();

    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof LogicalEqualityExpression);

    LogicalEqualityExpression expr = (LogicalEqualityExpression) n;
    assertTrue(expr.getFirstOperand() instanceof BoolLiteral);
    assertTrue(expr.getSecondOperand() instanceof BoolLiteral);

    TestBooleanLiteral((BoolLiteral) expr.getFirstOperand(), true);
    TestBooleanLiteral((BoolLiteral) expr.getSecondOperand(), false);
  }

  @Test
  public void visitUnaryExpression() {
    ConcurooParser parser = parse("sizeof(1)");
    UnaryExpressionContext ctx = parser.unaryExpression();
    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof SizeofExpression);
    SizeofExpression sExpr = (SizeofExpression) n;
    assertTrue(sExpr.getFirstOperand() instanceof IntLiteral);
    IntLiteral iLit = (IntLiteral) sExpr.getFirstOperand();
    assertTrue(iLit.getValue() instanceof Integer);
    assertEquals((int) iLit.getValue(), 1);
  }

  @Test
  public void visitUnaryExpressionWithSizeofInt() {
    ConcurooParser parser = parse("sizeof(int)");
    UnaryExpressionContext ctx = parser.unaryExpression();
    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof SizeofSpecifier);
    SizeofSpecifier sExpr = (SizeofSpecifier) n;
    assertTrue(sExpr.getSpecifiers() != null);
    DeclarationSpecifierList decList = sExpr.getSpecifiers();
    assertEquals(decList.getSpecifiersCount(), 1);
    assertEquals(decList.getSpecifiers().get(0), "int");
  }

  @Test
  public void visitUnaryExpressionWithSizeofLongInt() {
    ConcurooParser parser = parse("sizeof(long int)");
    UnaryExpressionContext ctx = parser.unaryExpression();
    Node n = new CSTVisitor(st).visit(ctx);
    assertTrue(n instanceof SizeofSpecifier);
    SizeofSpecifier sExpr = (SizeofSpecifier) n;
    assertTrue(sExpr.getSpecifiers() != null);
    DeclarationSpecifierList decList = sExpr.getSpecifiers();
    assertEquals(decList.getSpecifiersCount(), 2);
    assertEquals(decList.getSpecifiers().get(0), "long");
    assertEquals(decList.getSpecifiers().get(1), "int");
  }

  private void TestBooleanLiteral(BoolLiteral boolLiteral, boolean Expected) {
    String expectedLiteral = Expected ? "true" : "false";
    assertEquals(expectedLiteral, boolLiteral.getLiteral());
    assertTrue(boolLiteral.getValue() instanceof Boolean);

    Boolean bool = (Boolean) boolLiteral.getValue();
    assertEquals(Expected, bool);
  }

  private void TestIntegerLiteral(IntLiteral intLiteral, int Expected) {
    assertEquals(intLiteral.getLiteral(), Integer.toString(Expected));
    assertTrue(intLiteral.getValue() instanceof Integer);

    int intValue = (int) intLiteral.getValue();
    assertEquals(Expected, intValue);
  }

  private ConcurooParser parse(String input){
    ConcurooLexer lex = new ConcurooLexer(CharStreams.fromString(input));
    ConcurooParser parser = new ConcurooParser(new CommonTokenStream(lex));
    parser.setBuildParseTree(true);
    return parser;
  }
}