package concuroo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import concuroo.exceptions.ExpressionNotFoundException;
import concuroo.exceptions.StatementNotFoundException;
import concuroo.generators.ArduinoCodeGenerator;
import concuroo.mocks.ExpressionMock;
import concuroo.mocks.NodeMock;
import concuroo.mocks.StatementMock;
import concuroo.nodes.ArgumentExpressionList;
import concuroo.nodes.Declaration;
import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.Expression;
import concuroo.nodes.FunctionDeclaration;
import concuroo.nodes.Program;
import concuroo.nodes.Statement;
import concuroo.nodes.expression.SizeofSpecifier;
import concuroo.nodes.expression.binaryExpression.AssignmentExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.MultiplicativeExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalAndExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalOrExpression;
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
import concuroo.nodes.statement.CompoundStatement;
import concuroo.nodes.statement.CoroutineStatement;
import concuroo.nodes.statement.ExpressionStatement;
import concuroo.nodes.statement.SendStatement;
import concuroo.nodes.statement.VariableDeclaration;
import concuroo.nodes.statement.jumpStatement.BreakStatement;
import concuroo.nodes.statement.jumpStatement.ContinueStatement;
import concuroo.nodes.statement.jumpStatement.ReturnStatement;
import concuroo.nodes.statement.selectionStatement.IfStatement;
import concuroo.nodes.statement.selectionStatement.WhileStatement;
import concuroo.types.ReturnType;
import concuroo.types.TypeRules;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ArduinoCodeGeneratorTest {

  private ArduinoCodeGenerator cg;
  private Builder mockTarget = new Builder();
  private String prefixer = "__concuroo__";


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() {
    cg = new ArduinoCodeGenerator(mockTarget);
    cg.visit(new Program());
    mockTarget.clear();
  }

  @After
  public void reset() {
    mockTarget.clear();
  }


  /**
   * START OF STATEMENTS TESTS
   */

  @Test
  public void ensureConstructorIteratesOverProgram() {
    Declaration first = new NodeMock();
    Declaration second = new NodeMock();

    Program program = new Program();

    program.add(first);
    program.add(second);

    new ArduinoCodeGenerator().visit(program);

    /*
     * Ensures that the constructor loops through every item in program and invoke the accept-method
     * This allows future test to only care about output, since we know that the path of invocation is correct
     */
    assertTrue(((NodeMock) first).hit);
    assertTrue(((NodeMock) second).hit);
  }

  @Test
  public void FunctionDefinition() {
    List<String> typeSpecifiers = new ArrayList<String>() {{
      add("int");
    }};

    FunctionDeclaration fd = new FunctionDeclaration();
    fd.setSpecifiers(new DeclarationSpecifierList(typeSpecifiers));
    fd.setIdentifier("main");

    VariableDeclaration param1 = new VariableDeclaration();
    param1.setIdentifier("a");
    param1.setSpecifiers(new DeclarationSpecifierList(typeSpecifiers));
    param1.setParam(true);

    VariableDeclaration param2 = new VariableDeclaration();
    param2.setIdentifier("b");
    param2.setSpecifiers(new DeclarationSpecifierList(typeSpecifiers));
    param2.setIsArray(true);
    param2.setArraySize(new IntLiteral(5));
    param2.setParam(true);

    VariableDeclaration param3 = new VariableDeclaration();
    param3.setIdentifier("c");
    param3.setSpecifiers(new DeclarationSpecifierList(typeSpecifiers));
    param3.setPointer(true);
    param3.setParam(true);

    fd.add(param1);
    fd.add(param2);
    fd.add(param3);

    CompoundStatement body = new CompoundStatement();

    body.add(new ReturnStatement());

    fd.setBody(body);

    cg.visit(fd);

    String Expected =
        "int " + prefixer + "main(int " + prefixer + "a, int " + prefixer + "b[5], int *" + prefixer
            + "c);\n"
            + "int " + prefixer + "main(int " + prefixer + "a, int " + prefixer + "b[5], int *"
            + prefixer + "c)\n"
            + "{\n"
            + "LinkedList<int> goroutines__started;\n"
            + "{\n"
            + "while(goroutines__started.size()){\n"
            + "kill(goroutines__started.shift());\n"
            + "}return;\n"
            + "}\n"
            + "while(goroutines__started.size()){\n"
            + "kill(goroutines__started.shift());\n"
            + "}\n"
            + "}";

    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void GoRoutineFunctionDefinition() {
    List<String> typeSpecifiers = new ArrayList<String>() {{
      add("int");
    }};

    FunctionDeclaration fd = new FunctionDeclaration();
    fd.setSpecifiers(new DeclarationSpecifierList(new ArrayList<String>() {{
      add("void");
    }}));
    fd.setIdentifier("main");

    VariableDeclaration param1 = new VariableDeclaration();
    param1.setIdentifier("a");
    param1.setSpecifiers(new DeclarationSpecifierList(typeSpecifiers));
    param1.setParam(true);

    VariableDeclaration param2 = new VariableDeclaration();
    param2.setIdentifier("b");
    param2.setSpecifiers(new DeclarationSpecifierList(typeSpecifiers));
    param2.setIsArray(true);
    param2.setArraySize(new IntLiteral(5));
    param2.setParam(true);

    VariableDeclaration param3 = new VariableDeclaration();
    param3.setIdentifier("c");
    param3.setSpecifiers(new DeclarationSpecifierList(typeSpecifiers));
    param3.setPointer(true);
    param3.setParam(true);

    VariableDeclaration param4 = new VariableDeclaration();
    param4.setIdentifier("d");
    param4.setSpecifiers(new DeclarationSpecifierList(new ArrayList<String>() {{
      add("chan");
      add("int");
    }}));
    param4.setParam(true);

    fd.add(param1);
    fd.add(param2);
    fd.add(param3);
    fd.add(param4);

    CompoundStatement body = new CompoundStatement();
    body.add(new ReturnStatement());

    fd.setBody(body);
    fd.setCoroutined(true);

    cg.visit(fd);

    String Expected = "struct main__params__struct\n"
        + "{\n"
        + "int " + prefixer + "a;\n"
        + "int " + prefixer + "b[5];\n"
        + "int *" + prefixer + "c;\n"
        + "Channel<int > *" + prefixer + "d;\n"
        + "};\n"
        + "void " + prefixer + "main(void *main__param__pointer);\n"
        + "void " + prefixer + "main(void *main__param__pointer)\n"
        + "{\n"
        + "LinkedList<int> goroutines__started;\n"
        + "main__params__struct *main__params = (main__params__struct *)main__param__pointer;\n"
        + "int " + prefixer + "a = main__params->" + prefixer + "a;\n"
        + "int *" + prefixer + "b = main__params->" + prefixer + "b;\n"
        + "int *" + prefixer + "c = main__params->" + prefixer + "c;\n"
        + "Channel<int > *" + prefixer + "d = main__params->" + prefixer + "d;\n"
        + "\n"
        + "{\n"
        + "while(goroutines__started.size()){\n"
        + "kill(goroutines__started.shift());\n"
        + "}return;\n"
        + "}\n"
        + "while(goroutines__started.size()){\n"
        + "kill(goroutines__started.shift());\n"
        + "}"
        + "\n}";

    assertStringEqual(Expected, mockTarget.getOutput());
  }


  @Test
  public void declarationSpecifierList() {
    TestData data = new TestData();

    data.add("int", "int ");
    data.add("bool", "bool ");
    data.add("double", "double ");
    data.add("char", "char ");
    data.add("long int", "long int ");
    data.add("long bool", "long bool ");
    data.add("long double", "long double ");
    data.add("long char", "long char ");
    data.add("const int", "const int ");
    data.add("const bool", "const bool ");
    data.add("const double", "const double ");
    data.add("const char", "const char ");
    data.add("long const int", "long const int ");
    data.add("long const bool", "long const bool ");
    data.add("long const double", "long const double ");
    data.add("long const char", "long const char ");

    for (String input : data.getInputs()) {
      List<String> items = Arrays.asList(input.split("\\s* \\s*"));

      DeclarationSpecifierList dsl = new DeclarationSpecifierList(items);
      cg.visit(dsl);

      assertStringEqual(data.getExpected(input), mockTarget.getOutput());

      mockTarget.clear();
    }

  }

  @Test
  public void variableDefinition() {
    List<String> typeSpecifiers = new ArrayList<String>() {{
      add("int");
    }};
    VariableDeclaration var = new VariableDeclaration();
    var.setIdentifier("b");
    var.setSpecifiers(new DeclarationSpecifierList(typeSpecifiers));
    var.setIsArray(true);
    var.setArraySize(new IntLiteral(5));
    var.setParam(false);
    var.setPointer(true);
    var.setInitializer(new IntLiteral(15));

    String Expected = "int *" + prefixer + "b[5] = 15;\n";

    cg.visit(var);

    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void compoundStatement() {
    List<String> typeSpecifiers = new ArrayList<String>() {{
      add("int");
    }};

    VariableDeclaration var = new VariableDeclaration();
    var.setIdentifier("a");
    var.setSpecifiers(new DeclarationSpecifierList(typeSpecifiers));
    var.setInitializer(new IntLiteral(15));

    ReturnStatement ret = new ReturnStatement();
    ret.setReturnValue(new VariableExpression("a", var));

    CompoundStatement cs = new CompoundStatement();
    cs.add(var);
    cs.add(ret);

    String Expected = "{\nint " + prefixer + "a = 15;\n\n{\n"
        + "while(goroutines__started.size()){\n"
        + "kill(goroutines__started.shift());\n"
        + "}return " + prefixer + "a;\n"
        + "}\n"
        + "}";

    cg.visit(cs);

    assertStringEqual(Expected, mockTarget.getOutput());

  }

  @Test
  public void statement() {
    List<String> typeSpecifiers = new ArrayList<String>() {{
      add("int");
    }};
    Statement comp = new CompoundStatement();
    Statement coro = new CoroutineStatement();
    Statement estm = new ExpressionStatement();
    Statement sstm = new SendStatement();
    Statement vstm = new VariableDeclaration();
    Statement wstm = new WhileStatement();
    Statement bstm = new BreakStatement();
    Statement cstm = new ContinueStatement();
    Statement rstm = new ReturnStatement();
    Statement istm = new IfStatement();

    ((CoroutineStatement) coro).setExpression(new FunctionExpression("a"));

    ((ExpressionStatement) estm).setExpression(new IntLiteral(2));

    ((VariableDeclaration) vstm).setIdentifier("a");
    ((VariableDeclaration) vstm).setSpecifiers(new DeclarationSpecifierList(typeSpecifiers));

    ((WhileStatement) wstm).setCondition(new BoolLiteral(true));
    ((WhileStatement) wstm).setConsequence(new ReturnStatement());

    ((IfStatement) istm).setCondition(new BoolLiteral(false));
    ((IfStatement) istm).setConsequence(new ReturnStatement());

    thrown.expect(NullPointerException.class);

    cg.visit(comp);
    cg.visit(coro);
    cg.visit(estm);
    cg.visit(sstm);
    cg.visit(vstm);
    cg.visit(wstm);
    cg.visit(bstm);
    cg.visit(cstm);
    cg.visit(rstm);
    cg.visit(istm);

  }

  @Test
  public void statementThrowsException() {
    thrown.expect(StatementNotFoundException.class);
    cg.visit(new StatementMock());
  }

  @Test
  public void whileStatement() {
    WhileStatement wstm = new WhileStatement();
    wstm.setCondition(new BoolLiteral(true));
    wstm.setConsequence(new BreakStatement());

    cg.visit(wstm);

    String Expected = "while(true)break;";

    assertStringEqual(Expected, mockTarget.getOutput());

    mockTarget.clear();

    CompoundStatement stm = new CompoundStatement();
    stm.add(new BreakStatement());
    wstm.setConsequence(stm);

    cg.visit(wstm);

    Expected = "while(true){\nbreak;\n}";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void pipeExpression() {
    PipeExpression pexpr = new PipeExpression();
    pexpr.setFirstOperand(new VariableExpression() {{
      setIdentifier("id");
    }});

    cg.visit(pexpr);

    String Expected = prefixer + "id->recval()";

    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void pipeExpressionSecond() {
    VariableDeclaration def = new VariableDeclaration();
    def.setSpecifiers(new DeclarationSpecifierList(new ArrayList<String>() {{
      add("int");
    }}));
    ReturnType returnType = TypeRules.determineDeclarationSpecifier(def.getSpecifiers());
    def.setReturnType(returnType);
    PipeExpression pipe = new PipeExpression(new VariableExpression("a", def));

    cg.visit(pipe);
    String Expected = prefixer + "a->recval()";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void breakStatement() {
    BreakStatement bstm = new BreakStatement();

    cg.visit(bstm);

    String Expected = "break;";

    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void continueStatement() {
    ContinueStatement cstm = new ContinueStatement();

    cg.visit(cstm);

    String Expected = "continue;";

    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void returnStatement() {
    ReturnStatement rstm = new ReturnStatement();

    cg.visit(rstm);

    String Expected = "{\n"
        + "while(goroutines__started.size()){\n"
        + "kill(goroutines__started.shift());\n"
        + "}return;\n"
        + "}";

    assertStringEqual(Expected, mockTarget.getOutput());

    mockTarget.clear();

    rstm.setReturnValue(new IntLiteral(2));

    cg.visit(rstm);

    Expected = "{\n"
        + "while(goroutines__started.size()){\n"
        + "kill(goroutines__started.shift());\n"
        + "}return 2;\n"
        + "}";

    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void ifStatement() {
    IfStatement istm = new IfStatement();
    istm.setCondition(new BoolLiteral(true));
    istm.setConsequence(new BreakStatement());

    cg.visit(istm);
    String Expected = "if(true)break;";
    assertStringEqual(Expected, mockTarget.getOutput());

    mockTarget.clear();

    CompoundStatement stm = new CompoundStatement();
    stm.add(new BreakStatement());
    istm.setConsequence(stm);

    cg.visit(istm);
    Expected = "if(true){\nbreak;\n}";
    assertStringEqual(Expected, mockTarget.getOutput());

    mockTarget.clear();

    istm.setConsequence(new BreakStatement());
    istm.setAlternative(new BreakStatement());

    cg.visit(istm);
    Expected = "if(true)break;\n else break;\n";
    assertStringEqual(Expected, mockTarget.getOutput());

    mockTarget.clear();

    istm.setConsequence(stm);
    istm.setAlternative(stm);

    cg.visit(istm);
    Expected = "if(true){\nbreak;\n}\n else {\nbreak;\n}\n";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void expressionStatement() {
    ExpressionStatement estm = new ExpressionStatement();

    estm.setExpression(new IntLiteral(5));

    cg.visit(estm);

    String Expected = "5;";

    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void coroutineStatement() {
    CoroutineStatement coro = new CoroutineStatement();
    //GO a(23, true);

    //SETUP STUFF
    FunctionExpression func = new FunctionExpression("a");
    ArgumentExpressionList params = new ArgumentExpressionList();

    IntLiteral a = new IntLiteral(23);
    BoolLiteral b = new BoolLiteral(true);
    VariableExpression chan = new VariableExpression("channel", new VariableDeclaration());

    params.add(a);
    params.add(b);
    params.add(chan);

    func.setParameterList(params);

    coro.setExpression(func);

    cg.visit(coro);

    String Expected = "a__params__struct a__params__0 = {23, true, " + prefixer + "channel};\n"
        + "goroutines__started.add(start(" + prefixer + "a, (void*)(&a__params__0)));";
    assertStringEqual(Expected, mockTarget.getOutput());

  }

  @Test
  public void sendStatement() {

    SendStatement stm = new SendStatement();

    stm.setFirstOperand(new VariableExpression("channel", new VariableDeclaration()));

    stm.setSecondOperand(new IntLiteral(23));

    cg.visit(stm);

    String Expected = prefixer + "channel->sendval(23);";
    assertStringEqual(Expected, mockTarget.getOutput());


  }

  /*
   *
   *
   * END OF STATEMENTS TESTS
   *
   */


  /**
   * START OF EXPRESSIONS TESTS
   */

  @Test
  public void expression() {
    List<String> typeSpecifiers = new ArrayList<String>() {{
      add("int");
    }};

    // Literals
    Expression Bool = new BoolLiteral(true);
    Expression Int = new IntLiteral(1);
    Expression Char = new CharLiteral("a");
    Expression Float = new FloatLiteral(1.1);
    Expression String = new StringLiteral("sad");

    // Binary
    Expression add = new AdditiveExpression();
    ((AdditiveExpression) add).setFirstOperand(new IntLiteral(2));
    ((AdditiveExpression) add).setSecondOperand(new IntLiteral(2));
    ((AdditiveExpression) add).setOperator("+");

    Expression mul = new MultiplicativeExpression();
    ((MultiplicativeExpression) mul).setFirstOperand(new IntLiteral(2));
    ((MultiplicativeExpression) mul).setSecondOperand(new IntLiteral(2));
    ((MultiplicativeExpression) mul).setOperator("*");

    Expression and = new LogicalAndExpression();
    ((LogicalAndExpression) and).setFirstOperand(new IntLiteral(2));
    ((LogicalAndExpression) and).setSecondOperand(new IntLiteral(2));

    Expression equ = new LogicalEqualityExpression();
    ((LogicalEqualityExpression) equ).setFirstOperand(new IntLiteral(2));
    ((LogicalEqualityExpression) equ).setSecondOperand(new IntLiteral(2));

    Expression or = new LogicalOrExpression();
    ((LogicalOrExpression) or).setFirstOperand(new IntLiteral(2));
    ((LogicalOrExpression) or).setSecondOperand(new IntLiteral(2));

    Expression ass = new AssignmentExpression();
    ((AssignmentExpression) ass).setFirstOperand(new IntLiteral(2));
    ((AssignmentExpression) ass).setSecondOperand(new IntLiteral(2));

    Expression array = new ArrayExpression();
    ((ArrayExpression) array)
        .setFirstOperand(new VariableExpression("a", new VariableDeclaration()));
    ((ArrayExpression) array).setSecondOperand(new IntLiteral(2));

    // Unary
    Expression cast = new CastExpression();
    ((CastExpression) cast).setFirstOperand(new FloatLiteral(1.4));
    ((CastExpression) cast).setSpecifiers(new DeclarationSpecifierList(typeSpecifiers));

    Expression func = new FunctionExpression("main");
    Expression inc = new IncrementDecrementExpression();
    Expression size = new SizeofExpression(new VariableExpression("a", new VariableDeclaration()));
    Expression preAdd = new AdditivePrefixExpression(new IntLiteral(2), "+");
    Expression addr = new AddressOfExpression(
        new VariableExpression("a", new VariableDeclaration()));
    Expression deref = new DereferenceExpression(new IntLiteral(2));
    Expression make = new MakeExpression();
    Expression neg = new NegationExpression(new IntLiteral(2));
    Expression pip = new PipeExpression(new VariableExpression("a", new VariableDeclaration()));
    Expression size2 = new SizeofSpecifier(new DeclarationSpecifierList(typeSpecifiers));

    // LHS
    Expression var = new VariableExpression("a", new VariableDeclaration());

    thrown.expect(NullPointerException.class);
    cg.visit(Bool);
    cg.visit(Int);
    cg.visit(Char);
    cg.visit(Float);
    cg.visit(String);
    cg.visit(add);
    cg.visit(mul);
    cg.visit(and);
    cg.visit(equ);
    cg.visit(or);
    cg.visit(ass);
    cg.visit(array);
    cg.visit(cast);
    cg.visit(func);
    cg.visit(inc);
    cg.visit(size);
    cg.visit(preAdd);
    cg.visit(addr);
    cg.visit(deref);
    cg.visit(make);
    cg.visit(neg);
    cg.visit(pip);
    cg.visit(var);
    cg.visit(size2);

  }

  @Test
  public void expressionThrowsException() {
    thrown.expect(ExpressionNotFoundException.class);
    cg.visit(new ExpressionMock());
  }

  @Test
  public void boolLiteral() {
    BoolLiteral bool = new BoolLiteral(true);

    cg.visit(bool);
    String Expected = "true";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void intLiteral() {
    IntLiteral integer = new IntLiteral(1);

    cg.visit(integer);
    String Expected = "1";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void charLiteral() {
    CharLiteral character = new CharLiteral("a");

    cg.visit(character);
    String Expected = "a";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void floatLiteral() {
    FloatLiteral floatLiteral = new FloatLiteral(1.0);

    cg.visit(floatLiteral);
    String Expected = "1.0";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void stringLiteral() {
    StringLiteral stringLiteral = new StringLiteral("Hej");

    cg.visit(stringLiteral);
    String Expected = "Hej";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void additiveExpression() {
    AdditiveExpression add = new AdditiveExpression();
    add.setFirstOperand(new IntLiteral(2));
    add.setSecondOperand(new IntLiteral(2));
    add.setOperator("+");

    cg.visit(add);
    String Expected = "(2+2)";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void multiplicativeExpression() {
    MultiplicativeExpression mul = new MultiplicativeExpression();
    mul.setFirstOperand(new IntLiteral(2));
    mul.setSecondOperand(new IntLiteral(2));
    mul.setOperator("*");

    cg.visit(mul);
    String Expected = "(2*2)";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void logicalAndExpression() {
    LogicalAndExpression and = new LogicalAndExpression();
    and.setFirstOperand(new IntLiteral(2));
    and.setSecondOperand(new IntLiteral(2));

    cg.visit(and);
    String Expected = "(2&&2)";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void logicalOrExpression() {
    LogicalOrExpression or = new LogicalOrExpression();
    or.setFirstOperand(new IntLiteral(2));
    or.setSecondOperand(new IntLiteral(2));

    cg.visit(or);
    String Expected = "(2||2)";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void logicalEqualityExpression() {
    LogicalEqualityExpression equ = new LogicalEqualityExpression();
    equ.setFirstOperand(new IntLiteral(2));
    equ.setSecondOperand(new IntLiteral(2));

    cg.visit(equ);
    String Expected = "(2==2)";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void assignmentExpression() {
    AssignmentExpression ass = new AssignmentExpression();
    ass.setFirstOperand(new IntLiteral(2));
    ass.setSecondOperand(new IntLiteral(2));

    cg.visit(ass);
    String Expected = "(2=2)";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void arrayExpression() {
    ArrayExpression array = new ArrayExpression();
    array.setFirstOperand(new VariableExpression("a", new VariableDeclaration()));
    array.setSecondOperand(new IntLiteral(2));

    cg.visit(array);
    String Expected = prefixer + "a[2]";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void castExpression() {
    List<String> typeSpecifiers = new ArrayList<String>() {{
      add("int");
    }};

    CastExpression cast = new CastExpression();
    cast.setFirstOperand(new FloatLiteral(1.4));
    cast.setSpecifiers(new DeclarationSpecifierList(typeSpecifiers));

    cg.visit(cast);
    String Expected = "(int)1.4";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void functionExpression() {
    ArgumentExpressionList params = new ArgumentExpressionList();
    params.add(new IntLiteral(2));
    params.add(new VariableExpression("a", new VariableDeclaration()));
    params.add(new BoolLiteral(true));

    FunctionExpression func = new FunctionExpression("main");
    func.setParameterList(params);

    cg.visit(func);
    String Expected = prefixer + "main(2," + prefixer + "a,true)";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void incrementDecrementExpression() {
    IncrementDecrementExpression incr = new IncrementDecrementExpression();
    incr.setFirstOperand(new VariableExpression("a", new VariableDeclaration()));
    incr.setOperator("++");
    incr.setPrefix(false);

    cg.visit(incr);
    String Expected = prefixer + "a++";
    assertStringEqual(Expected, mockTarget.getOutput());

    mockTarget.clear();

    incr.setPrefix(true);
    cg.visit(incr);
    Expected = "++" + prefixer + "a";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void sizeofSpecifier() {
    List<String> typeSpecifiers = new ArrayList<String>() {{
      add("long");
      add("int");
    }};
    SizeofSpecifier size = new SizeofSpecifier(new DeclarationSpecifierList(typeSpecifiers));

    cg.visit(size);
    String Expected = "sizeof(long int)";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void sizeofExpression() {
    SizeofExpression size = new SizeofExpression(
        new VariableExpression("a", new VariableDeclaration()));
    cg.visit(size);
    String Expected = "sizeof(" + prefixer + "a)";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void additivePrefixExpression() {
    AdditivePrefixExpression addPre = new AdditivePrefixExpression(new IntLiteral(2), "-");

    cg.visit(addPre);
    String Expected = "-2";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void addressOfExpression() {
    AddressOfExpression adr = new AddressOfExpression(
        new VariableExpression("a", new VariableDeclaration()));

    cg.visit(adr);
    String Expected = "&" + prefixer + "a";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void dereferenceExpression() {
    DereferenceExpression adr = new DereferenceExpression(
        new VariableExpression("a", new VariableDeclaration()));

    cg.visit(adr);
    String Expected = "*" + prefixer + "a";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void makeExpression() {
    List<String> typeSpecifiers = new ArrayList<String>() {{
      add("chan");
      add("int");
    }};
    MakeExpression make = new MakeExpression();
    make.setSpecifiers(new DeclarationSpecifierList(typeSpecifiers));

    cg.visit(make);
    String Expected = "new Channel<int >()";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  @Test
  public void negationExpression() {
    NegationExpression neg = new NegationExpression(
        new VariableExpression("a", new VariableDeclaration()));

    cg.visit(neg);
    String Expected = "!" + prefixer + "a";
    assertStringEqual(Expected, mockTarget.getOutput());
  }

  /*
   *
   *
   * END OF EXPRESSIONS TESTS
   *
   */

  /**
   * START OF HELPERS
   */

  private void assertStringEqual(String Expected, String Actual) {
    assertEquals("\ndeclarationSpecifierList Failed:\nExpected: \""
        + Expected + "\" \nActual: \"" + Actual + "\"", Actual, Expected);
  }

  /*
   *
   *
   * END OF HELPERS
   *
   */

}


/**
 * This class is a helper class to clean up some tests.
 */
class TestData {

  private Hashtable<String, String> data;

  TestData() {
    this.data = new Hashtable<String, String>() {
    };
  }

  void add(String input, String result) {
    data.put(input, result);
  }

  String getExpected(String key) {
    return data.get(key);
  }

  Set<String> getInputs() {
    return this.data.keySet();
  }

}


