package concuroo.generators;

import concuroo.Builder;
import concuroo.CodeGenerator;
import concuroo.Stdlib;
import concuroo.Visitor;
import concuroo.nodes.ArgumentExpressionList;
import concuroo.nodes.Declaration;
import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.Expression;
import concuroo.nodes.FunctionDeclaration;
import concuroo.nodes.Node;
import concuroo.nodes.Program;
import concuroo.nodes.Statement;
import concuroo.nodes.expression.InitializerList;
import concuroo.nodes.expression.SizeofSpecifier;
import concuroo.nodes.expression.UnaryExpression;
import concuroo.nodes.expression.binaryExpression.AssignmentExpression;
import concuroo.nodes.expression.binaryExpression.BinaryExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.MultiplicativeExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalAndExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalOrExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalRelantionalExpression;
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
import concuroo.nodes.statement.DeleteStatement;
import concuroo.nodes.statement.ExpressionStatement;
import concuroo.nodes.statement.RawStatement;
import concuroo.nodes.statement.SendStatement;
import concuroo.nodes.statement.VariableDeclaration;
import concuroo.nodes.statement.jumpStatement.BreakStatement;
import concuroo.nodes.statement.jumpStatement.ContinueStatement;
import concuroo.nodes.statement.jumpStatement.ReturnStatement;
import concuroo.nodes.statement.selectionStatement.IfStatement;
import concuroo.nodes.statement.selectionStatement.WhileStatement;
import java.util.ArrayList;
import java.util.Iterator;

public class ArduinoCodeGenerator implements CodeGenerator {

  private String concu_prefix = "__concuroo__";
  private int numOfGoRoutinesCalled = 0;
  private final Builder targetBuilder;
  private RawStatement killwhile = new RawStatement(
      "while(goroutines__started.size()){\n"
          + "kill(goroutines__started.shift());\n"
          + "}");
  private RawStatement linklist = new RawStatement("LinkedList<int> goroutines__started;");


  public ArduinoCodeGenerator() {
    this(new Builder());
  }

  public ArduinoCodeGenerator(Builder targetBuilder) {
    this.targetBuilder = targetBuilder;
  }

  public void visit(Program program) {
    targetBuilder.addToPreCompiler("#include <Coroutine.h>\n"
        + "#include <Scheduler/Channel.h>\n"
        + "#include <Scheduler/LinkedList.h>\n\n");
    targetBuilder.add("\n\nvoid setup()\n"
        + "{\n"
        + "  " + concu_prefix + "setup();\n"
        + "  setupManager();\n"
        + "}\n"
        + "\n"
        + "void loop()\n"
        + "{\n"
        + "  if (threadAvailable(0))\n"
        + "  {\n"
        + "    start(" + concu_prefix + "loop, NULL);\n"
        + "  }\n"
        + "}\n\n");
    for (Declaration node : program) {
      node.accept(this);
    }
  }


  @Override
  public void visit(FunctionDeclaration functionDeclaration) {
    if (functionDeclaration.isCoroutined() && functionDeclaration.size() > 0) {
      generateGoParamStruct(functionDeclaration);
      injectGoParamStructDeref(functionDeclaration);
    }

    functionDeclaration.getBody().addToTop(linklist);
    functionDeclaration.getBody().add(killwhile);

    targetBuilder.startPrototype();

    visit(functionDeclaration.getSpecifiers());

    if (functionDeclaration.getPointer()) {
      targetBuilder.add("*");
    }

    targetBuilder.add(concu_prefix + functionDeclaration.getIdentifier() + "(");
    if (functionDeclaration.isCoroutined()) {
      targetBuilder.add("void *" + functionDeclaration.getIdentifier() + "__param__pointer");
    } else {

      for (Iterator<VariableDeclaration> vd = functionDeclaration.iterator();
          vd.hasNext(); ) {
        visit(vd.next());
        if (vd.hasNext()) {
          targetBuilder.add(", ");
        }
      }
    }
    targetBuilder.add(")");
    targetBuilder.endPrototype();
    targetBuilder.add("\n");

    visit(functionDeclaration.getBody());
  }

  @Override
  public void visit(DeclarationSpecifierList declarationSpecifierList) {

    if (declarationSpecifierList.contains("chan")) {
      DeclarationSpecifierList copy = new DeclarationSpecifierList(
          new ArrayList<>(declarationSpecifierList));
      copy.remove("chan");
      targetBuilder.add("Channel<");
      visit(copy);
      targetBuilder.add("> ");
      return;
    }
    for (String specifier : declarationSpecifierList) {
      targetBuilder.add(specifier + " ");
    }
  }

  @Override
  public void visit(RawStatement rawStatement) {
    targetBuilder.add(rawStatement.getRawCode());
  }

  @Override
  public void visit(VariableDeclaration variableDeclaration) {
    if (variableDeclaration.isGlobal()) {
      targetBuilder.startGlobalVariable();
    }
    visit(variableDeclaration.getSpecifiers());
    if (variableDeclaration.isPointer() || (targetBuilder.isGoParams() &&
        variableDeclaration.getSpecifiers().contains("chan")) ||
        variableDeclaration.getSpecifiers().contains("chan")) {

      targetBuilder.add("*");
    }
    targetBuilder.add(concu_prefix + variableDeclaration.getIdentifier());
    if (variableDeclaration.isArray()) {
      String size = null;

      if (variableDeclaration.getArraySize() != null) {
        size = variableDeclaration.getArraySize().getLiteral();
      }

      targetBuilder.add("[" + size + "]");
    }
    if (variableDeclaration.getInitializer() != null) {
      targetBuilder.add(" = ");
      visit(variableDeclaration.getInitializer());
    }
    if (!variableDeclaration.isParam()) {
      targetBuilder.add(";\n");
    }
    if (variableDeclaration.isGlobal()) {
      targetBuilder.stopGlobalVariable();
    }
  }

  @Override
  public void visit(CompoundStatement compoundStatement) {
    targetBuilder.add("{\n");
    for (Statement stm : compoundStatement) {
      visit(stm);
      targetBuilder.add("\n");
    }
    targetBuilder.add("}");
  }

  @Override
  public void visit(Statement statement) {
    Visitor.concreteify(statement, this);
  }

  // Todo: Fix This
  @Override
  public void visit(CoroutineStatement coroutineStatement) {
    FunctionExpression func = (FunctionExpression) coroutineStatement.getExpression();

    String ident = func.getIdentifier();

    targetBuilder
        .add(ident + "__params__struct " + ident + "__params__" + numOfGoRoutinesCalled + " = {");
    String prefix = "";

    for (Expression expr : func.getParameterList()) {
      targetBuilder.add(prefix);
      visit(expr);
      prefix = ", ";
    }
    targetBuilder.add("};\n");
    targetBuilder
        .add("goroutines__started.add(start(" + concu_prefix + ident + ", (void*)(&" + ident
            + "__params__" + numOfGoRoutinesCalled + ")));");
    numOfGoRoutinesCalled++;
  }

  @Override
  public void visit(WhileStatement whileStatement) {
    targetBuilder.add("while(");
    visit(whileStatement.getCondition());
    targetBuilder.add(")");
    visit(whileStatement.getConsequence());
  }

  @Override
  public void visit(BreakStatement breakStatement) {
    targetBuilder.add("break;");
  }

  @Override
  public void visit(ContinueStatement continueStatement) {
    targetBuilder.add("continue;");
  }

  @Override
  public void visit(ReturnStatement returnStatement) {
    // we ensure that we always kill all coroutines
    targetBuilder.add("{\n");
    visit(killwhile);

    targetBuilder.add("return ");
    if (returnStatement.getReturnValue() != null) {
      visit(returnStatement.getReturnValue());
    } else {
      targetBuilder.removeLastCharacter();
    }
    targetBuilder.add(";");
    targetBuilder.add("\n}");
  }

  @Override
  public void visit(IfStatement ifStatement) {
    targetBuilder.add("if(");
    visit(ifStatement.getCondition());
    targetBuilder.add(")");
    visit(ifStatement.getConsequence());
    if (ifStatement.getAlternative() != null) {
      targetBuilder.add("\n else ");
      visit(ifStatement.getAlternative());
      targetBuilder.add("\n");
    }
  }

  @Override
  public void visit(ExpressionStatement expressionStatement) {
    if (expressionStatement.getExpression() != null) {
      visit(expressionStatement.getExpression());
      targetBuilder.add(";");
    }
  }

  @Override
  public void visit(DeleteStatement deleteStatement) {
    targetBuilder.add("delete ");
    visit(deleteStatement.getVariable());
    targetBuilder.add(";");
  }

  @Override
  public void visit(SendStatement sendStatement) {
    targetBuilder.add(concu_prefix + sendStatement.getFirstOperand() + "->sendval(");

    visit(sendStatement.getSecondOperand());

    targetBuilder.add(");");
  }

  @Override
  public void visit(Node node) {
    Visitor.concreteify(node, this);
  }

  @Override
  public void visit(Expression expression) {
    Visitor.concreteify(expression, this);
  }

  @Override
  public void visit(InitializerList initializerList) {
    targetBuilder.add("{");
    String prefix = "";
    for (Node expr : initializerList) {
      targetBuilder.add(prefix);
      visit((Expression) expr);
      prefix = ", ";
    }

    targetBuilder.add("}");
  }

  @Override
  public void visit(BoolLiteral boolLiteral) {
    targetBuilder.add(boolLiteral.getValue().toString());
  }

  @Override
  public void visit(IntLiteral intLiteral) {
    targetBuilder.add(intLiteral.getValue().toString());
  }

  @Override
  public void visit(CharLiteral charLiteral) {
    targetBuilder.add(charLiteral.getValue());
  }

  @Override
  public void visit(FloatLiteral floatLiteral) {
    targetBuilder.add(floatLiteral.getValue().toString());
  }

  @Override
  public void visit(StringLiteral stringLiteral) {
    targetBuilder.add(stringLiteral.getValue().toString());
  }

  @Override
  public void visit(BinaryExpression binaryExpression) {
    targetBuilder.add("(");
    visit(binaryExpression.getFirstOperand());
    targetBuilder.add(binaryExpression.getOperator());
    visit(binaryExpression.getSecondOperand());
    targetBuilder.add(")");
  }

  @Override
  public void visit(AdditiveExpression additiveExpression) {
    visit((BinaryExpression) additiveExpression);
  }

  @Override
  public void visit(MultiplicativeExpression multiplicativeExpression) {
    visit((BinaryExpression) multiplicativeExpression);
  }

  @Override
  public void visit(LogicalRelantionalExpression logicalRelantionalExpression) {
    visit((BinaryExpression) logicalRelantionalExpression);
  }

  @Override
  public void visit(LogicalAndExpression logicalAndExpression) {
    visit((BinaryExpression) logicalAndExpression);
  }

  @Override
  public void visit(LogicalEqualityExpression logicalEqualityExpression) {
    visit((BinaryExpression) logicalEqualityExpression);
  }

  @Override
  public void visit(LogicalOrExpression logicalOrExpression) {
    visit((BinaryExpression) logicalOrExpression);
  }

  @Override
  public void visit(AssignmentExpression assignmentExpression) {
    visit((BinaryExpression) assignmentExpression);
  }

  @Override
  public void visit(VariableExpression variableExpression) {
    targetBuilder.add(concu_prefix + variableExpression.getIdentifier());
  }

  @Override
  public void visit(DereferenceExpression expression) {
    targetBuilder.add("*");
    visit(expression.getFirstOperand());
  }

  @Override
  public void visit(ArrayExpression expression) {
    visit(expression.getFirstOperand());
    targetBuilder.add("[");
    visit(expression.getSecondOperand());
    targetBuilder.add("]");
  }

  @Override
  public void visit(CastExpression expression) {
    targetBuilder.add("(");
    visit(expression.getSpecifiers());
    targetBuilder.removeLastCharacter();
    targetBuilder.add(")");
    visit(expression.getFirstOperand());
  }

  @Override
  public void visit(FunctionExpression expression) {
    if (!Stdlib.in(expression.getIdentifier())) {
      targetBuilder.add(concu_prefix);
    }
    targetBuilder.add(expression.getIdentifier() + "(");
    visit(expression.getParameterList());
    targetBuilder.add(")");
  }

  @Override
  public void visit(ArgumentExpressionList expression) {
    String prefix = "";
    for (Expression param : expression) {
      targetBuilder.add(prefix);
      visit(param);
      prefix = ",";
    }
  }


  @Override
  public void visit(IncrementDecrementExpression expression) {
    if (expression.isPrefix()) {
      targetBuilder.add(expression.getOperator());
      visit(expression.getFirstOperand());
    } else {
      visit(expression.getFirstOperand());
      targetBuilder.add(expression.getOperator());
    }
  }

  @Override
  public void visit(SizeofSpecifier expression) {
    targetBuilder.add("sizeof(");
    visit(expression.getSpecifiers());
    targetBuilder.removeLastCharacter();
    targetBuilder.add(")");
  }

  @Override
  public void visit(SizeofExpression expression) {
    targetBuilder.add("sizeof(");
    visit(expression.getFirstOperand());
    targetBuilder.add(")");
  }

  @Override
  public void visit(AddressOfExpression addressOfExpression) {
    targetBuilder.add("&");
    visit(addressOfExpression.getFirstOperand());
  }

  @Override
  public void visit(AdditivePrefixExpression expression) {
    targetBuilder.add(expression.getOperator());
    visit(expression.getFirstOperand());
  }

  @Override
  public void visit(UnaryExpression expression) {
    targetBuilder.add(expression.getOperator());
    visit(expression.getFirstOperand());
  }

  @Override
  public void visit(NegationExpression expression) {
    targetBuilder.add("!");
    visit(expression.getFirstOperand());
  }

  @Override
  public void visit(MakeExpression expression) {
    if (!expression.getSpecifiers().contains("chan")) {
      throw new RuntimeException(
          "Undefined behavior in Make command containing types: " + expression.getSpecifiers()
              .toString());
    }

    DeclarationSpecifierList copy = new DeclarationSpecifierList(
        new ArrayList<String>(expression.getSpecifiers()));
    copy.remove("chan");

    targetBuilder.add("new Channel<");
    visit(copy);
    targetBuilder.add(">()");
  }

  @Override
  public void visit(PipeExpression expression) {
    VariableExpression expr = (VariableExpression) expression.getFirstOperand();
    targetBuilder.add(concu_prefix + expr.getIdentifier() + "->recval()");
  }

  /**
   * This class generates the struct for params feeded to GoRoutines.
   */
  private void generateGoParamStruct(FunctionDeclaration func) {
    targetBuilder.startGoParamsStruct();
    targetBuilder.add("struct " + func.getIdentifier() + "__params__struct\n{\n");
    for (Iterator<VariableDeclaration> vd = func.iterator(); vd.hasNext(); ) {
      visit(vd.next());
      targetBuilder.add(";\n");
    }
    targetBuilder.add("};\n");
    targetBuilder.endGoParamsStruct();
  }


  private void injectGoParamStructDeref(FunctionDeclaration func) {

    // We cast the incoming pointer to the appropriate function struct
    RawStatement structCast = new RawStatement(
        func.getIdentifier() + "__params__struct *" +
            func.getIdentifier() + "__params = (" +
            func.getIdentifier() + "__params__struct *)" +
            func.getIdentifier() + "__param__pointer;"
    );
    // TODO: clean this
    ArduinoCodeGenerator derefs2 = new ArduinoCodeGenerator();

    for (VariableDeclaration vd : func) {
      derefs2.visit(vd.getSpecifiers());

      if (vd.isPointer() || vd.isArray() || vd.getSpecifiers().contains("chan")) {
        derefs2.visit(new RawStatement("*"));
      }

      derefs2.visit(new RawStatement(concu_prefix + vd.getIdentifier() + " = "));
      derefs2.visit(new RawStatement(func.getIdentifier() + "__params->" + concu_prefix + vd.getIdentifier() + ";\n"));
    }

    RawStatement deref = new RawStatement(derefs2.targetBuilder.getOutput());
    func.getBody().addToTop(deref);
    func.getBody().addToTop(structCast);
  }

  @Override
  public Builder getBuilder() {
    return this.targetBuilder;
  }
}


