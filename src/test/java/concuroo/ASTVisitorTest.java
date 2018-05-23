package concuroo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ConcurooParser.ConcurooLexer;
import ConcurooParser.ConcurooParser;
import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.FunctionDeclaration;
import concuroo.nodes.Node;
import concuroo.nodes.Program;
import concuroo.nodes.Statement;
import concuroo.nodes.Expression;
import concuroo.nodes.expression.SizeofSpecifier;
import concuroo.nodes.expression.binaryExpression.AssignmentExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.MultiplicativeExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalAndExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalGreaterEqualsExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalGreaterExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalLessEqualsExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalLessExpression;
import concuroo.nodes.expression.lhsExpression.ArrayExpression;
import concuroo.nodes.expression.lhsExpression.DereferenceExpression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;
import concuroo.nodes.expression.literalExpression.BoolLiteral;
import concuroo.nodes.expression.literalExpression.CharLiteral;
import concuroo.nodes.expression.literalExpression.FloatLiteral;
import concuroo.nodes.expression.literalExpression.IntLiteral;
import concuroo.nodes.expression.literalExpression.StringLiteral;
import concuroo.nodes.expression.unaryExpression.AdditivePrefixExpression;
import concuroo.nodes.expression.unaryExpression.AddressOfExpression;
import concuroo.nodes.expression.unaryExpression.CastExpression;
import concuroo.nodes.expression.unaryExpression.FunctionExpression;
import concuroo.nodes.expression.unaryExpression.IncrementDecrementExpression;
import concuroo.nodes.expression.unaryExpression.MakeExpression;
import concuroo.nodes.expression.unaryExpression.NegationExpression;
import concuroo.nodes.expression.unaryExpression.PipeExpression;
import concuroo.nodes.expression.unaryExpression.SizeofExpression;
import concuroo.nodes.statement.VariableDeclaration;
import concuroo.symbol.SymbolTable;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ASTVisitorTest {

  private SymbolTable st;
  private TypeTest typeTest;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(System.out);
    System.setErr(System.err);
  }

  @Before
  public void beforeAll() {
    st = new SymbolTable();

    FunctionDeclaration loop = new FunctionDeclaration();
    loop.setIdentifier("loop");
    loop.setSpecifiers(new DeclarationSpecifierList(new ArrayList<String>() {{
      add("void");
    }}));
    st.insert(loop);

    FunctionDeclaration setup = new FunctionDeclaration();
    setup.setIdentifier("setup");
    setup.setSpecifiers(new DeclarationSpecifierList(new ArrayList<String>() {{
      add("void");
    }}));
    st.insert(setup);

    typeTest = new TypeTest();
  }

  private Node setupTest(String input) {
    ConcurooParser parser = parse(input);
    ConcurooParser.StartContext ctx = parser.start();
    Node program = new CSTVisitor(st).visitStart(ctx);
    ASTVisitor aVisitor = new ASTVisitor(st);
    aVisitor.visit((Program) program);
    return program;
  }

  private Node mainWrapperSetup(String input) {
    return setupTest("void main(){" + input + "}");
  }

  private Node mainWrapperSetup(String preDefinitions, String input) {
    return setupTest(preDefinitions + " void main(){" + input + "}");
  }

  private ConcurooParser parse(String input) {
    ConcurooLexer lex = new ConcurooLexer(CharStreams.fromString(input));
    ConcurooParser parser = new ConcurooParser(new CommonTokenStream(lex));
    parser.setBuildParseTree(true);
    return parser;
  }

  private void TestBooleanLiteral(Node n, boolean Expected) {
    assertTrue(n instanceof BoolLiteral);
    BoolLiteral boolLiteral = (BoolLiteral) n;

    String expectedLiteral = Expected ? "true" : "false";
    assertEquals(expectedLiteral, boolLiteral.getLiteral());
    assertTrue(boolLiteral.getValue() instanceof Boolean);

    boolean bool = (Boolean) boolLiteral.getValue();
    assertEquals(Expected, bool);
  }

  private void TestIntegerLiteral(Node n, int Expected) {
    assertTrue(n instanceof IntLiteral);
    IntLiteral intLiteral = (IntLiteral) n;

    assertEquals(intLiteral.getLiteral(), Integer.toString(Expected));
    assertTrue(intLiteral.getValue() instanceof Integer);

    int intValue = (int) intLiteral.getValue();
    assertEquals(Expected, intValue);
  }

  private void TestFloatLiteral(Node n, double Expected) {
    assertTrue(n instanceof FloatLiteral);
    FloatLiteral floatLiteral = (FloatLiteral) n;

    assertEquals(floatLiteral.getLiteral(), Double.toString(Expected));
    assertTrue(floatLiteral.getValue() instanceof Double);

    double floatValue = (double) floatLiteral.getValue();
    assertEquals(Expected, floatValue, 0.01);
  }

  private void TestCharLiteral(Node n, char Expected) {
    assertTrue(n instanceof CharLiteral);
    CharLiteral charLiteral = (CharLiteral) n;
    String ExpectedString = "'" + Expected + "'";

    assertEquals(charLiteral.getValue(), ExpectedString);
  }

  private void TestStringLiteral(Node actual, String expected) {
    assertTrue(actual instanceof StringLiteral);
    StringLiteral stringLiteral = (StringLiteral) actual;

    assertEquals("\"" + expected + "\"", stringLiteral.getValue());
  }

  private void testExpression(Expression expected, Node actualExpression, Types returnType) {
    Expression actual = (Expression) actualExpression;
    assertEquals(expected.getClass(), actual.getClass());
    Types actualType = actual.getReturnType().type;

    assertEquals(returnType, actualType);
    assertEquals(actual.getReturnType().prefix, Pair.of(null, null));
  }

  private void testExpression(Expression expected, Node actualExpression,
      Types returnType, Pair<TypeModifier, TypeModifier> prefix) {
    Expression actual = (Expression) actualExpression;
    assertEquals(expected.getClass(), actual.getClass());
    Types actualType = actual.getReturnType().type;

    assertEquals(returnType, actualType);
    assertEquals(prefix, actual.getReturnType().prefix);
  }

  private void testExpression(Statement expected, Node actualStatement) {
    Statement actual = (Statement) actualStatement;
    assertEquals(expected.getClass(), actual.getClass());
    //Types actualType = actual.getReturnType();
    //assertEquals(returnType, actual.getReturnType());
  }

  @Test
  public void functionDefinitionTest() {
    Node n = setupTest("void main(){}");
    assertTrue(n instanceof Program);
    Program p = (Program) n;
    assertEquals(1, p.size());

    assertTrue(p.get(0) instanceof FunctionDeclaration);
    FunctionDeclaration funcDef = (FunctionDeclaration) p.get(0);
    assertEquals("main", funcDef.getIdentifier());
    assertEquals(0, funcDef.getBody().size());
  }

  @Test
  public void functionCallTest() {
    Node n = setupTest("int a(){return 1;} void main(){a();}");
    assertTrue(n instanceof Program);
    Program p = (Program) n;
    assertEquals(2, p.size());

    assertTrue(p.get(0) instanceof FunctionDeclaration);
    FunctionDeclaration funcDef0 = (FunctionDeclaration) p.get(0);
    assertEquals("a", funcDef0.getIdentifier());
    assertEquals(1, funcDef0.getBody().size());

    assertTrue(p.get(1) instanceof FunctionDeclaration);
    FunctionDeclaration funcDef1 = (FunctionDeclaration) p.get(1);
    assertEquals("main", funcDef1.getIdentifier());
    assertEquals(1, funcDef1.getBody().size());

//    assertTrue(funcDef1.getBody().getStatement(0) instanceof ExpressionStatement);
//    ExpressionStatement exprStm = (ExpressionStatement) funcDef1.getBody().getStatement(0);
//    assertTrue(exprStm.getExpression() instanceof FunctionExpression);
//
//    FunctionExpression funcExpr = (FunctionExpression) exprStm.getExpression();

//    assertEquals("a", funcExpr.getIdentifier());
//    assertNotNull(funcExpr.getDefinition());
//    assertEquals(funcDef0, funcExpr.getDefinition());
  }

  @Test
  public void functionCall1Test() {
    Node n = setupTest("void main(){b();} int b(){return 1;}");
    assertTrue(n instanceof Program);
    Program p = (Program) n;
    assertEquals(2, p.size());

    assertTrue(p.get(1) instanceof FunctionDeclaration);
    FunctionDeclaration funcDef1 = (FunctionDeclaration) p.get(1);
    assertEquals("b", funcDef1.getIdentifier());
    assertEquals(1, funcDef1.getBody().size());

    assertTrue(p.get(0) instanceof FunctionDeclaration);
    FunctionDeclaration funcDef0 = (FunctionDeclaration) p.get(0);
    assertEquals("main", funcDef0.getIdentifier());
    assertEquals(1, funcDef0.getBody().size());
  }

  @Test
  public void variableDefinitionTest() {
    setupTest("int a;");
  }

  @Test
  public void int_intAdditiveTypeTest() {
    typeTest.setInput(mainWrapperSetup("1 + 2;"));
    testExpression(new AdditiveExpression(), typeTest.operands.get(0), Types.INT);

    TestIntegerLiteral(typeTest.operands.get(1), 1);
    TestIntegerLiteral(typeTest.operands.get(2), 2);
  }

  @Test
  public void float_intAdditiveTypeTest() {
    typeTest.setInput(mainWrapperSetup("1.0 + 2;"));

    testExpression(new AdditiveExpression(), typeTest.operands.get(0), Types.DOUBLE);

    TestFloatLiteral(typeTest.operands.get(1), 1.0);
    TestIntegerLiteral(typeTest.operands.get(2), 2);
  }


  @Test
  public void int_exprAdditiveTypeTest() {
    typeTest.setInput(mainWrapperSetup("1 + 2 + 3;"));

    testExpression(new AdditiveExpression(), typeTest.operands.get(0), Types.INT);
    testExpression(new AdditiveExpression(), typeTest.operands.get(1), Types.INT);

    TestIntegerLiteral(typeTest.operands.get(2), 1);
    TestIntegerLiteral(typeTest.operands.get(3), 2);
    TestIntegerLiteral(typeTest.operands.get(4), 3);
  }

  @Test
  public void int_floatAdditiveTypeTest() {
    typeTest.setInput(mainWrapperSetup("1 + 1.1;"));

    testExpression(new AdditiveExpression(), typeTest.operands.get(0), Types.DOUBLE);

    TestIntegerLiteral(typeTest.operands.get(1), 1);
    TestFloatLiteral(typeTest.operands.get(2), 1.1);
  }

  @Test
  public void float_floatAdditiveTypeTest() {
    typeTest.setInput(mainWrapperSetup("1.1 + 2.2;"));

    testExpression(new AdditiveExpression(), typeTest.operands.get(0), Types.DOUBLE);

    TestFloatLiteral(typeTest.operands.get(1), 1.1);
    TestFloatLiteral(typeTest.operands.get(2), 2.2);
  }

  @Test(expected = RuntimeException.class)
  public void int_boolErrorAdditiveTypeTest() {
    typeTest.setInput(mainWrapperSetup("1 + true;"));
  }

  @Test(expected = RuntimeException.class)
  public void bool_intErrorAdditiveTypeTest() {
    typeTest.setInput(mainWrapperSetup("true + 1;"));
  }

  @Test
  public void int_parenthesizedAdditiveTypeTest() {
    typeTest.setInput(mainWrapperSetup("1 + (2 + 3);"));

    testExpression(new AdditiveExpression(), typeTest.operands.get(0), Types.INT);

    TestIntegerLiteral(typeTest.operands.get(1), 1);

    testExpression(new AdditiveExpression(), typeTest.operands.get(2), Types.INT);

    TestIntegerLiteral(typeTest.operands.get(3), 2);
    TestIntegerLiteral(typeTest.operands.get(4), 3);
  }

  @Test
  public void intMultiplicativeTypeTest() {
    typeTest.setInput(mainWrapperSetup("1 * 2;"));

    testExpression(new MultiplicativeExpression(), typeTest.operands.get(0), Types.INT);

    TestIntegerLiteral(typeTest.operands.get(1), 1);
    TestIntegerLiteral(typeTest.operands.get(2), 2);
  }

  @Test
  public void int_parenthesizedMultiplicativeTypeTest() {
    typeTest.setInput(mainWrapperSetup("2 * (3 * 4);"));

    testExpression(new MultiplicativeExpression(), typeTest.operands.get(0), Types.INT);

    TestIntegerLiteral(typeTest.operands.get(1), 2);

    testExpression(new MultiplicativeExpression(), typeTest.operands.get(2), Types.INT);

    TestIntegerLiteral(typeTest.operands.get(3), 3);
    TestIntegerLiteral(typeTest.operands.get(4), 4);
  }

  @Test
  public void true_falseLogicalAndTypeTest() {
    typeTest.setInput(mainWrapperSetup("true && false;"));

    testExpression(new LogicalAndExpression(), typeTest.operands.get(0), Types.BOOL);

    TestBooleanLiteral(typeTest.operands.get(1), true);
    TestBooleanLiteral(typeTest.operands.get(2), false);
  }

  @Test
  public void false_trueLogicalEqualityExpression() {
    typeTest.setInput(mainWrapperSetup("false == true;"));

    testExpression(new LogicalEqualityExpression(), typeTest.operands.get(0), Types.BOOL);

    TestBooleanLiteral(typeTest.operands.get(1), false);
    TestBooleanLiteral(typeTest.operands.get(2), true);
  }

  @Test
  public void false_true_falseLogicalAndExpression() {
    typeTest.setInput(mainWrapperSetup("false && true && false;"));

    testExpression(new LogicalAndExpression(), typeTest.operands.get(0), Types.BOOL);
    testExpression(new LogicalAndExpression(), typeTest.operands.get(1), Types.BOOL);

    TestBooleanLiteral(typeTest.operands.get(2), false);
    TestBooleanLiteral(typeTest.operands.get(3), true);
    TestBooleanLiteral(typeTest.operands.get(4), false);
  }

  @Test
  public void int_intVariableDefinitionExpression() {
    typeTest.setInput(mainWrapperSetup("int a = 1;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);

    TestIntegerLiteral(typeTest.operands.get(1), 1);
  }

  @Test
  public void intAssignmentExpression() {
    typeTest.setInput(mainWrapperSetup("int a;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);
  }

  @Test
  public void float_intVariableDefinitionExpression() {
    typeTest.setInput(mainWrapperSetup("double a = 1;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.DOUBLE);

    TestIntegerLiteral(typeTest.operands.get(1), 1);
  }

  @Test
  public void int_floatVariableDefinitionExpression() {
    typeTest.setInput(mainWrapperSetup("int a = 1.0;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);

    TestFloatLiteral(typeTest.operands.get(1), 1.0);

    assertEquals("Trying to assign DOUBLE to INT, but this will lose precision.",
        outContent.toString());
  }

  @Test
  public void int_floatExprVariableDefinition() {
    typeTest.setInput(mainWrapperSetup("int a = 1 + 1.0;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);
    testExpression(new AdditiveExpression(), typeTest.operands.get(1), Types.DOUBLE);

    TestIntegerLiteral(typeTest.operands.get(2), 1);
    TestFloatLiteral(typeTest.operands.get(3), 1.0);

    assertEquals("Trying to assign DOUBLE to INT, but this will lose precision.",
        outContent.toString());
  }

  @Test
  public void bool_boolVariableDefinitionExpression() {
    typeTest.setInput(mainWrapperSetup("bool a = true;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.BOOL);

    TestBooleanLiteral(typeTest.operands.get(1), true);
  }

  @Test
  public void bool_boolExprVariableDefinitionExpression() {
    typeTest.setInput(mainWrapperSetup("bool a = true && false;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.BOOL);
    testExpression(new LogicalAndExpression(), typeTest.operands.get(1), Types.BOOL);

    TestBooleanLiteral(typeTest.operands.get(2), true);
    TestBooleanLiteral(typeTest.operands.get(3), false);
  }

  @Test(expected = RuntimeException.class)
  public void bool_intVariableDefinitionExpression() {
    typeTest.setInput(mainWrapperSetup("bool a = 1;"));
  }

  @Test(expected = RuntimeException.class)
  public void int_intExprVariableDefinitionExpression() {
    typeTest.setInput(mainWrapperSetup("bool a = 1 + 1;"));
  }

  @Test(expected = RuntimeException.class)
  public void int_boolVariableDefinitionExpression() {
    typeTest.setInput(mainWrapperSetup("int a = true;"));
  }

  @Test
  public void int_intAssignmentExpression() {
    typeTest.setInput(mainWrapperSetup("int a = 1; a = 2;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);

    TestIntegerLiteral(typeTest.operands.get(1), 1);

    testExpression(new AssignmentExpression(), typeTest.operands.get(2), Types.INT);
    testExpression(new VariableExpression(null, null), typeTest.operands.get(3), Types.INT);

    TestIntegerLiteral(typeTest.operands.get(4), 2);
  }

  @Test
  public void int_exprAssignmentExpression() {
    typeTest.setInput(mainWrapperSetup("int a = 1; a = 2 + 3;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);

    TestIntegerLiteral(typeTest.operands.get(1), 1);

    testExpression(new AssignmentExpression(), typeTest.operands.get(2), Types.INT);
    testExpression(new VariableExpression(null, null), typeTest.operands.get(3), Types.INT);
    testExpression(new AdditiveExpression(), typeTest.operands.get(4), Types.INT);

    TestIntegerLiteral(typeTest.operands.get(5), 2);
    TestIntegerLiteral(typeTest.operands.get(6), 3);
  }

  /*@Test
  public void ifStatement() {
    typeTest.setInput(mainWrapperSetup("if(true){}"));

    testExpression(new IfStatement(), typeTest.operands.get(0));

    IfStatement iStm = (IfStatement) typeTest.operands.get(0);

    assertNull(iStm.getConsequence());
    assertNull(iStm.getAlternative());
  }*/

  @Test
  public void functionDefinition() {
    typeTest.setInput(mainWrapperSetup("int a(){return 1;}", "a();"));
  }

  @Test(expected = RuntimeException.class)
  public void functionDefinitionNoReturn() {
    typeTest.setInput(mainWrapperSetup("int a(){}", "a();"));
  }

  @Test
  public void functionDefinitionIntWithIf() {
    typeTest.setInput(mainWrapperSetup("int a(){if(true){return 1;} return 2;}", "a();"));
  }

  @Test
  public void functionDefinitionVoidWithIf() {
    typeTest.setInput(mainWrapperSetup("void a(){if(true){}}", "a();"));
  }

  @Test(expected = RuntimeException.class)
  public void functionDefinitionIntWithEmptyIf() {
    typeTest.setInput(mainWrapperSetup("int a(){if(true){}}", "a();"));
  }

  @Test(expected = RuntimeException.class)
  public void functionDefinitionWithWhile() {
    typeTest.setInput(mainWrapperSetup("int a(){while(true){return 1;}}", "a();"));
  }

  @Test
  public void functionDefinitionIfWithStatement() {
    typeTest
        .setInput(mainWrapperSetup("int a(){while(true){int a = 1; return 1;} return 1;}", "a();"));
  }

  @Test(expected = RuntimeException.class)
  public void functionCallThatDoesNotExist() {
    typeTest.setInput(mainWrapperSetup("a();"));
  }

  @Test
  public void functionExpressionNoParameters() {
    typeTest.setInput(mainWrapperSetup("void a(){}", "a();"));

    testExpression(new FunctionExpression("a"), typeTest.operands.get(0), Types.VOID);
  }

  @Test
  public void functionExpressionIntParameter() {
    typeTest.setInput(mainWrapperSetup("void a(int b){}", "a(1);"));

    testExpression(new FunctionExpression("a"), typeTest.operands.get(0), Types.VOID);
    FunctionExpression funcExpr = (FunctionExpression) typeTest.operands.get(0);
    assertEquals(1, funcExpr.getParameterList().size());
  }

  @Test
  public void functionExpressionIntParameterFloatInput() {
    typeTest.setInput(mainWrapperSetup("void a(int b){}", "a(1.0);"));

    testExpression(new FunctionExpression("a"), typeTest.operands.get(0), Types.VOID);
    FunctionExpression funcExpr = (FunctionExpression) typeTest.operands.get(0);
    assertEquals(1, funcExpr.getParameterList().size());
  }

  @Test
  public void functionExpressionFloatParameterIntInput() {
    typeTest.setInput(mainWrapperSetup("void a(double b){}", "a(1);"));

    testExpression(new FunctionExpression("a"), typeTest.operands.get(0), Types.VOID);
    FunctionExpression funcExpr = (FunctionExpression) typeTest.operands.get(0);
    assertEquals(1, funcExpr.getParameterList().size());
  }

  @Test(expected = RuntimeException.class)
  public void functionExpressionIntParameterBoolInput() {
    typeTest.setInput(mainWrapperSetup("void a(int b){}", "a(true);"));

    testExpression(new FunctionExpression("a"), typeTest.operands.get(0), Types.VOID);
    FunctionExpression funcExpr = (FunctionExpression) typeTest.operands.get(0);
    assertEquals(1, funcExpr.getParameterList().size());
  }

  @Test(expected = RuntimeException.class)
  public void functionExpressionBoolParameterIntInput() {
    typeTest.setInput(mainWrapperSetup("void a(bool b){}", "a(1);"));

    testExpression(new FunctionExpression("a"), typeTest.operands.get(0), Types.VOID);
    FunctionExpression funcExpr = (FunctionExpression) typeTest.operands.get(0);
    assertEquals(1, funcExpr.getParameterList().size());
  }

  @Test
  public void functionExpressionIntParametersIntInput() {
    typeTest.setInput(mainWrapperSetup("void a(int b, int c){}", "a(1, 2);"));

    testExpression(new FunctionExpression("a"), typeTest.operands.get(0), Types.VOID);
    FunctionExpression funcExpr = (FunctionExpression) typeTest.operands.get(0);
    assertEquals(2, funcExpr.getParameterList().size());
  }

  @Test
  public void functionExpressionIntDoubleParametersIntInput() {
    typeTest.setInput(mainWrapperSetup("void a(int b, double c){}", "a(1, 2);"));

    testExpression(new FunctionExpression("a"), typeTest.operands.get(0), Types.VOID);
    FunctionExpression funcExpr = (FunctionExpression) typeTest.operands.get(0);
    assertEquals(2, funcExpr.getParameterList().size());
  }

  @Test
  public void functionExpressionDoubleIntParametersIntInput() {
    typeTest.setInput(mainWrapperSetup("void a(double b, int c){}", "a(1, 2);"));

    testExpression(new FunctionExpression("a"), typeTest.operands.get(0), Types.VOID);
    FunctionExpression funcExpr = (FunctionExpression) typeTest.operands.get(0);
    assertEquals(2, funcExpr.getParameterList().size());
  }

  @Test
  public void functionExpressionDoubleIntParametersDoubleIntInput() {
    typeTest.setInput(mainWrapperSetup("void a(double b, int c){}", "a(1.5, 2);"));

    testExpression(new FunctionExpression("a"), typeTest.operands.get(0), Types.VOID);
    FunctionExpression funcExpr = (FunctionExpression) typeTest.operands.get(0);
    assertEquals(2, funcExpr.getParameterList().size());
  }

  @Test
  public void arithmeticLongIntExpression() {
    typeTest.setInput(mainWrapperSetup("long int a = 1;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT,
        Pair.of(TypeModifier.LONG, null));
    TestIntegerLiteral(typeTest.operands.get(1), 1);
  }

  @Test
  public void arithmeticDoubleLongIntExpression() {
    typeTest.setInput(mainWrapperSetup("long int a = 1; double b = a;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT,
        Pair.of(TypeModifier.LONG, null));
    TestIntegerLiteral(typeTest.operands.get(1), 1);

    testExpression(new VariableDeclaration(), typeTest.operands.get(2), Types.DOUBLE);
    testExpression(new VariableExpression("a", (VariableDeclaration) typeTest.operands.get(0)),
        typeTest.operands.get(3), Types.INT, Pair.of(TypeModifier.LONG, null));
  }

  @Test
  public void arithmeticLongIntDoubleExpression() {
    typeTest.setInput(mainWrapperSetup("double a = 1; long int b = a;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.DOUBLE);
    TestIntegerLiteral(typeTest.operands.get(1), 1);

    testExpression(new VariableDeclaration(), typeTest.operands.get(2), Types.INT,
        Pair.of(TypeModifier.LONG, null));
    testExpression(new VariableExpression("a", (VariableDeclaration) typeTest.operands.get(0)),
        typeTest.operands.get(3), Types.DOUBLE);

    assertEquals("Trying to assign DOUBLE to LONG INT, but this will lose precision.",
        outContent.toString());
  }

  @Test
  public void addressOfInt() {
    typeTest.setInput(mainWrapperSetup("int a; &a;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);
    testExpression(new AddressOfExpression((Expression) typeTest.operands.get(3)),
        typeTest.operands.get(2), Types.INT);
  }

  @Test
  public void addressOfDouble() {
    typeTest.setInput(mainWrapperSetup("double a; &a;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.DOUBLE);
    testExpression(new AddressOfExpression((Expression) typeTest.operands.get(3)),
        typeTest.operands.get(2), Types.DOUBLE);
  }

  @Test
  public void addressOfChar() {
    typeTest.setInput(mainWrapperSetup("char a; &a;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.CHAR);
    testExpression(new AddressOfExpression((Expression) typeTest.operands.get(3)),
        typeTest.operands.get(2), Types.CHAR);
  }

  @Test
  public void addressOfBool() {
    typeTest.setInput(mainWrapperSetup("bool a; &a;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.BOOL);
    testExpression(new AddressOfExpression((Expression) typeTest.operands.get(3)),
        typeTest.operands.get(2), Types.BOOL);
  }

  @Test
  public void dereferenceInt() {
    typeTest.setInput(mainWrapperSetup("int a; *a;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);
    VariableExpression expr = new VariableExpression("a",
        (VariableDeclaration) typeTest.operands.get(0));
    testExpression(new DereferenceExpression(expr), typeTest.operands.get(2), Types.INT);
  }

  @Test
  public void dereferenceChar() {
    typeTest.setInput(mainWrapperSetup("char a; *a;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.CHAR);
    VariableExpression expr = new VariableExpression("a",
        (VariableDeclaration) typeTest.operands.get(0));
    testExpression(new DereferenceExpression(expr), typeTest.operands.get(2), Types.CHAR);
  }

  @Test
  public void dereferenceDouble() {
    typeTest.setInput(mainWrapperSetup("double a; *a;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.DOUBLE);
    VariableExpression expr = new VariableExpression("a",
        (VariableDeclaration) typeTest.operands.get(0));
    testExpression(new DereferenceExpression(expr), typeTest.operands.get(2), Types.DOUBLE);
  }

  @Test
  public void dereferenceBool() {
    typeTest.setInput(mainWrapperSetup("bool a; *a;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.BOOL);
    VariableExpression expr = new VariableExpression("a",
        (VariableDeclaration) typeTest.operands.get(0));
    testExpression(new DereferenceExpression(expr), typeTest.operands.get(2), Types.BOOL);
  }

  @Test
  public void greaterThanInt() {
    typeTest.setInput(mainWrapperSetup("2 > 1;"));

    testExpression(new LogicalGreaterExpression(), typeTest.operands.get(0), Types.BOOL);

    TestIntegerLiteral(typeTest.operands.get(1), 2);
    TestIntegerLiteral(typeTest.operands.get(2), 1);
  }

  @Test
  public void greaterThanDouble() {
    typeTest.setInput(mainWrapperSetup("2.0 > 1;"));

    testExpression(new LogicalGreaterExpression(), typeTest.operands.get(0), Types.BOOL);

    TestFloatLiteral(typeTest.operands.get(1), 2.0);
    TestIntegerLiteral(typeTest.operands.get(2), 1);
  }

  @Test
  public void lessThanInt() {
    typeTest.setInput(mainWrapperSetup("2 < 1;"));

    testExpression(new LogicalLessExpression(), typeTest.operands.get(0), Types.BOOL);

    TestIntegerLiteral(typeTest.operands.get(1), 2);
    TestIntegerLiteral(typeTest.operands.get(2), 1);
  }

  @Test
  public void lessThanEqualsInt() {
    typeTest.setInput(mainWrapperSetup("2 <= 1;"));

    testExpression(new LogicalLessEqualsExpression(), typeTest.operands.get(0), Types.BOOL);

    TestIntegerLiteral(typeTest.operands.get(1), 2);
    TestIntegerLiteral(typeTest.operands.get(2), 1);
  }

  @Test
  public void greaterThanEqualsInt() {
    typeTest.setInput(mainWrapperSetup("2 >= 1;"));

    testExpression(new LogicalGreaterEqualsExpression(), typeTest.operands.get(0), Types.BOOL);

    TestIntegerLiteral(typeTest.operands.get(1), 2);
    TestIntegerLiteral(typeTest.operands.get(2), 1);
  }

  @Test(expected = RuntimeException.class)
  public void lessThanOneBoolLeftside() {
    typeTest.setInput(mainWrapperSetup("true >= 1;"));
  }

  @Test(expected = RuntimeException.class)
  public void lessThanOneBoolRightside() {
    typeTest.setInput(mainWrapperSetup("1 >= true;"));
  }

  @Test(expected = RuntimeException.class)
  public void lessThanTwoBools() {
    typeTest.setInput(mainWrapperSetup("true >= true;"));
  }


  @Test
  public void notBool() {
    typeTest.setInput(mainWrapperSetup("!true;"));

    testExpression(new NegationExpression(new BoolLiteral(true)), typeTest.operands.get(0),
        Types.BOOL);

    TestBooleanLiteral(typeTest.operands.get(1), true);
  }

  @Test(expected = RuntimeException.class)
  public void notInt() {
    typeTest.setInput(mainWrapperSetup("!1;"));
  }

  @Test
  public void notBoolVariable() {
    typeTest.setInput(mainWrapperSetup("bool a; !a;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0));

    testExpression(new NegationExpression(new BoolLiteral(true)), typeTest.operands.get(2),
        Types.BOOL);

    //VariableDeclaration varDef = (VariableDeclaration) typeTest.operands.get(0);
  }


  @Test(expected = RuntimeException.class)
  public void stringNoCharPointer() {
    typeTest.setInput(mainWrapperSetup("char a = \"Error expected\";"));
  }

  //TODO: Remove " around statement in the check.
  @Test
  public void stringTypeTest() {
    typeTest.setInput(mainWrapperSetup("char *a = \"Hello, World!\";"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.CHAR);
    TestStringLiteral(typeTest.operands.get(1), "Hello, World!");
  }

  @Test
  public void returnAsAssignment() {
    typeTest.setInput(mainWrapperSetup("int a(int b){return 1;}", "int b = a(2);"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);
    testExpression(new FunctionExpression("a"), typeTest.operands.get(1), Types.INT);
  }

  @Test(expected = RuntimeException.class)
  public void returnAsAssignmentWrongReturnValue() {
    typeTest.setInput(mainWrapperSetup("int a(int b){return 1;}", "bool b = a(2);"));
  }

  @Test
  public void intCastExpression() {
    typeTest.setInput(mainWrapperSetup("int a = (int) 2.0"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);
    testExpression(new CastExpression(), typeTest.operands.get(1), Types.INT);

    TestFloatLiteral(typeTest.operands.get(2), 2.0);
  }

  @Test
  public void intCastExpressionFromVariable() {
    typeTest.setInput(mainWrapperSetup("double a = 1.4; int b = (int) a;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.DOUBLE);
    TestFloatLiteral(typeTest.operands.get(1), 1.4);
    testExpression(new VariableDeclaration(), typeTest.operands.get(2), Types.INT);
    testExpression(new CastExpression(), typeTest.operands.get(3), Types.INT);

    VariableDeclaration varDef = (VariableDeclaration) typeTest.operands.get(0);
    testExpression(new VariableExpression("a", varDef), typeTest.operands.get(4), Types.DOUBLE);
  }

  @Test(expected = RuntimeException.class)
  public void intToBoolCastExpression() {
    typeTest.setInput(mainWrapperSetup("bool a = (bool) 1"));
  }

  @Test
  public void charAssignment() {
    typeTest.setInput(mainWrapperSetup("char a = 'b'"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.CHAR);
    TestCharLiteral(typeTest.operands.get(1), 'b');
  }

  @Test
  public void castCharToInt() {
    typeTest.setInput(mainWrapperSetup("int a = (int)'b';"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);
    testExpression(new CastExpression(), typeTest.operands.get(1), Types.INT);
    TestCharLiteral(typeTest.operands.get(2), 'b');
  }

  @Test
  public void incrementDoubleAssignToInt() {
    typeTest.setInput(mainWrapperSetup("double a = 1.5; int b = a++;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.DOUBLE);
    TestFloatLiteral(typeTest.operands.get(1), 1.5);
    testExpression(new VariableDeclaration(), typeTest.operands.get(2), Types.INT);
    testExpression(new IncrementDecrementExpression(), typeTest.operands.get(3), Types.INT);

    VariableDeclaration varDef = (VariableDeclaration) typeTest.operands.get(0);
    testExpression(new VariableExpression("a", varDef), typeTest.operands.get(4), Types.DOUBLE);
  }

  @Test
  public void sizeofInt() {
    typeTest.setInput(mainWrapperSetup("sizeof(int);"));

    testExpression(new SizeofSpecifier(null), typeTest.operands.get(0), Types.INT);
  }

  @Test
  public void sizeofExpression() {
    typeTest.setInput(mainWrapperSetup("sizeof(1);"));

    testExpression(new SizeofExpression(null), typeTest.operands.get(0), Types.INT);
  }

  @Test
  public void addressOfAssignedToPointer() {
    typeTest.setInput(mainWrapperSetup("int b = 1; int *a = &b;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);
    TestIntegerLiteral(typeTest.operands.get(1), 1);
    testExpression(new VariableDeclaration(), typeTest.operands.get(2), Types.INT);
    VariableDeclaration var = (VariableDeclaration) typeTest.operands.get(2);
    assertTrue(var.isPointer());
    testExpression(new AddressOfExpression(null), typeTest.operands.get(3), Types.INT);

    VariableDeclaration varDef = (VariableDeclaration) typeTest.operands.get(0);
    testExpression(new VariableExpression("b", varDef), typeTest.operands.get(4), Types.INT);

    assertTrue(outContent.toString().isEmpty());
  }

  @Test
  public void intArrayReference() {
    typeTest.setInput(mainWrapperSetup("int *a = 1; int b = a[0];"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);
    VariableDeclaration var = (VariableDeclaration) typeTest.operands.get(0);
    assertTrue(var.isPointer());

    TestIntegerLiteral(typeTest.operands.get(1), 1);

    testExpression(new VariableDeclaration(), typeTest.operands.get(2), Types.INT);
    testExpression(new ArrayExpression(), typeTest.operands.get(3), Types.INT);

    testExpression(new VariableExpression("a", var), typeTest.operands.get(4), Types.INT);

    TestIntegerLiteral(typeTest.operands.get(5), 0);
  }

  @Test
  public void boolArrayReference() {
    typeTest.setInput(mainWrapperSetup("bool *a = true; bool b = a[0];"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.BOOL);
    VariableDeclaration var = (VariableDeclaration) typeTest.operands.get(0);
    assertTrue(var.isPointer());

    TestBooleanLiteral(typeTest.operands.get(1), true);

    testExpression(new VariableDeclaration(), typeTest.operands.get(2), Types.BOOL);
    testExpression(new ArrayExpression(), typeTest.operands.get(3), Types.BOOL);

    testExpression(new VariableExpression("a", var), typeTest.operands.get(4), Types.BOOL);

    TestIntegerLiteral(typeTest.operands.get(5), 0);
  }

  @Test
  public void intArrayInitialization() {
    typeTest.setInput(mainWrapperSetup("int a[3] = {1, 2, 3};"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);
  }

  @Test
  public void intArrayInitializationWithMixedInput() {
    typeTest.setInput(mainWrapperSetup("int a[3] = {1.0, 2, 'a'};"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);
  }

  @Test(expected = RuntimeException.class)
  public void intArrayInitializationIllegalInput() {
    typeTest.setInput(mainWrapperSetup("int a[3] = {true, 2, 3};"));
  }

  @Test(expected = RuntimeException.class)
  public void intArrayInitializationIllegalInputLater() {
    typeTest.setInput(mainWrapperSetup("int a[3] = {1, 2, true};"));
  }

  @Test(expected = RuntimeException.class)
  public void intArrayInitializationIllegalArithmeticInput() {
    typeTest.setInput(mainWrapperSetup("int a[3] = 1 + 2;"));
  }

  @Test
  public void intDeclarationWithNegativeNumber() {
    typeTest.setInput(mainWrapperSetup("int a = -100;"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT);
    testExpression(new AdditivePrefixExpression(null, "-"), typeTest.operands.get(1), Types.INT);
    TestIntegerLiteral(typeTest.operands.get(2), 100);
  }

  @Test
  public void intNotEqualsInt() {
    typeTest.setInput(mainWrapperSetup("1 != 2;"));

    testExpression(new LogicalEqualityExpression(), typeTest.operands.get(0), Types.BOOL);
    TestIntegerLiteral(typeTest.operands.get(1), 1);
    TestIntegerLiteral(typeTest.operands.get(2), 2);
  }

  @Test
  public void intChan() {
    typeTest.setInput(mainWrapperSetup("chan int a = make(chan int);"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.INT,
        Pair.of(TypeModifier.CHAN, null));
    testExpression(new MakeExpression(), typeTest.operands.get(1), Types.INT,
        Pair.of(TypeModifier.CHAN, null));
  }

  @Test
  public void doubleChan() {
    typeTest.setInput(mainWrapperSetup("chan double a = make(chan double);"));

    testExpression(new VariableDeclaration(), typeTest.operands.get(0), Types.DOUBLE,
        Pair.of(TypeModifier.CHAN, null));
    testExpression(new MakeExpression(), typeTest.operands.get(1), Types.DOUBLE,
        Pair.of(TypeModifier.CHAN, null));
  }

  @Test(expected = RuntimeException.class)
  public void doubleChanBoolVariable() {
    typeTest.setInput(mainWrapperSetup("chan bool a = make(double);"));
  }

  @Test(expected = RuntimeException.class)
  public void intChanNoChanPrefix() {
    typeTest.setInput(mainWrapperSetup("int a = make(int);"));
  }

  @Test(expected = RuntimeException.class)
  public void boolChanNoChanPrefixIntVariable() {
    typeTest.setInput(mainWrapperSetup("int a = make(bool);"));
  }

  @Test
  public void routineSomething() {
    typeTest.setInput(mainWrapperSetup("void a(){}", "<-a();"));

    testExpression(new PipeExpression(null), typeTest.operands.get(0), Types.VOID);
    testExpression(new FunctionExpression("a"), typeTest.operands.get(1), Types.VOID);
    int a = 0;
  }

}