package concuroo.generators;

import concuroo.Builder;
import concuroo.CodeGenerator;
import concuroo.Stdlib;
import concuroo.exceptions.ExpressionNotFoundException;
import concuroo.exceptions.StatementNotFoundException;
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
import concuroo.nodes.expression.binaryExpression.BinaryExpression;
import concuroo.nodes.expression.lhsExpression.ArrayExpression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;
import concuroo.nodes.expression.literalExpression.BoolLiteral;
import concuroo.nodes.expression.literalExpression.CharLiteral;
import concuroo.nodes.expression.literalExpression.FloatLiteral;
import concuroo.nodes.expression.literalExpression.IntLiteral;
import concuroo.nodes.expression.literalExpression.StringLiteral;
import concuroo.nodes.expression.unaryExpression.AdditivePrefixExpression;
import concuroo.nodes.expression.unaryExpression.CastExpression;
import concuroo.nodes.expression.unaryExpression.FunctionExpression;
import concuroo.nodes.expression.unaryExpression.IncrementDecrementExpression;
import concuroo.nodes.expression.unaryExpression.MakeExpression;
import concuroo.nodes.expression.unaryExpression.PipeExpression;
import concuroo.nodes.expression.unaryExpression.SizeofExpression;
import concuroo.nodes.statement.CompoundStatement;
import concuroo.nodes.statement.CoroutineStatement;
import concuroo.nodes.statement.DeleteStatement;
import concuroo.nodes.statement.ExpressionStatement;
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


  public ArduinoCodeGenerator() {
    this(new Builder());
  }

  public ArduinoCodeGenerator(Builder targetBuilder) {
    this.targetBuilder = targetBuilder;
  }

  public Builder generate(Program program) {
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

    return targetBuilder;
  }


  @Override
  public void visit(FunctionDeclaration functionDeclaration) {
    visit(functionDeclaration, this.targetBuilder);
  }

  public void visit(FunctionDeclaration functionDeclaration, Builder targetBuilder) {
    if (functionDeclaration.isCoroutined() && functionDeclaration.size() > 0) {
      generateGoParamStruct(functionDeclaration, targetBuilder);
      injectGoParamStructDeref(functionDeclaration, targetBuilder);
    }

    RawStatement linklist = new RawStatement("LinkedList<int> goroutines__started;");

    functionDeclaration.getBody().addToTop(linklist);

    /*RawStatement killwhile = new RawStatement(
        "while(goroutines__started.size()){\n"
        + "kill(goroutines__started.shift());\n"
        + "}");*/

    functionDeclaration.getBody().add(killwhile);

    targetBuilder.startPrototype();

    visit(functionDeclaration.getSpecifiers(), targetBuilder);

    if (functionDeclaration.getPointer()) {
      targetBuilder.add("*");
    }

    targetBuilder.add(concu_prefix + functionDeclaration.getIdentifier() + "(");
    if (functionDeclaration.isCoroutined()) {
      targetBuilder.add("void *" + functionDeclaration.getIdentifier() + "__param__pointer");
    } else {

      for (Iterator<VariableDeclaration> vd = functionDeclaration.iterator();
          vd.hasNext(); ) {
        visit(vd.next(), targetBuilder);
        if (vd.hasNext()) {
          targetBuilder.add(", ");
        }
      }
    }
    targetBuilder.add(")");
    targetBuilder.endPrototype();
    targetBuilder.add("\n");

    visit(functionDeclaration.getBody(), targetBuilder);
  }

  @Override
  public void visit(DeclarationSpecifierList declarationSpecifierList) {
    visit(declarationSpecifierList, this.targetBuilder);
  }

  public void visit(DeclarationSpecifierList declarationSpecifierList, Builder targetBuilder) {

    if (declarationSpecifierList.contains("chan")) {
      DeclarationSpecifierList copy = new DeclarationSpecifierList(
          new ArrayList<>(declarationSpecifierList));
      copy.remove("chan");
      targetBuilder.add("Channel<");
      visit(copy, targetBuilder);
      targetBuilder.add("> ");
      return;
    }
    for (String specifier : declarationSpecifierList) {
      targetBuilder.add(specifier + " ");
    }
  }

  @Override
  public void visit(VariableDeclaration variableDeclaration) {
    visit(variableDeclaration, targetBuilder);
  }

  public void visit(VariableDeclaration variableDeclaration, Builder targetBuilder) {
    if (variableDeclaration.isGlobal()) {
      targetBuilder.startGlobalVariable();
    }
    visit(variableDeclaration.getSpecifiers(), targetBuilder);
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
      visit(variableDeclaration.getInitializer(), targetBuilder);
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
    visit(compoundStatement, targetBuilder);
  }

  public void visit(CompoundStatement compoundStatement, Builder targetBuilder) {
    targetBuilder.add("{\n");
    for (Statement stm : compoundStatement) {
      visit(stm, targetBuilder);
      targetBuilder.add("\n");
    }
    targetBuilder.add("}");
  }

  @Override
  public void visit(Statement statement) {
    visit(statement, targetBuilder);
  }

  public void visit(Statement statement, Builder targetBuilder) {
    if (statement instanceof CompoundStatement) {
      visit((CompoundStatement) statement, targetBuilder);
    } else if (statement instanceof CoroutineStatement) {
      visit((CoroutineStatement) statement, targetBuilder);
    } else if (statement instanceof WhileStatement) {
      visit((WhileStatement) statement, targetBuilder);
    } else if (statement instanceof BreakStatement) {
      visit((BreakStatement) statement, targetBuilder);
    } else if (statement instanceof ContinueStatement) {
      visit((ContinueStatement) statement, targetBuilder);
    } else if (statement instanceof ReturnStatement) {
      visit((ReturnStatement) statement, targetBuilder);
    } else if (statement instanceof IfStatement) {
      visit((IfStatement) statement, targetBuilder);
    } else if (statement instanceof ExpressionStatement) {
      visit((ExpressionStatement) statement, targetBuilder);
    } else if (statement instanceof SendStatement) {
      visit((SendStatement) statement, targetBuilder);
    } else if (statement instanceof DeleteStatement) {
      visit((DeleteStatement) statement);
    } else if (statement instanceof VariableDeclaration) {
      visit((VariableDeclaration) statement, targetBuilder);
    } else if (statement instanceof RawStatement) {
      visit((RawStatement) statement, targetBuilder);
    } else {
      throw new StatementNotFoundException(
          "Statement Not Found In ArduinoCodeGenerator visit(Statement statement) Statement: "
              + statement.getClass().getName());
    }
  }

  // Todo: Fix This
  @Override
  public void visit(CoroutineStatement coroutineStatement) {
    visit(coroutineStatement, this.targetBuilder);
  }


  public void visit(CoroutineStatement coroutineStatement, Builder targetBuilder) {
    FunctionExpression func = (FunctionExpression) coroutineStatement.getExpression();

    String ident = func.getIdentifier();

    Builder stuff = new Builder();

    stuff.add(ident + "__params__struct " + ident + "__params__" + numOfGoRoutinesCalled + " = {");
    String prefix = "";

    for (Expression expr : func.getParameterList()) {
      stuff.add(prefix);
      visit(expr, stuff);
      prefix = ", ";
    }
    stuff.add("};\n");
    stuff.add("goroutines__started.add(start(" + concu_prefix + ident + ", (void*)(&" + ident
        + "__params__" + numOfGoRoutinesCalled + ")));");
    numOfGoRoutinesCalled++;
    targetBuilder.add(stuff.getOutput());
  }

  @Override
  public void visit(WhileStatement whileStatement) {
    visit(whileStatement, this.targetBuilder);
  }

  public void visit(WhileStatement whileStatement, Builder targetBuilder) {
    targetBuilder.add("while(");
    visit(whileStatement.getCondition(), targetBuilder);
    targetBuilder.add(")");
    visit(whileStatement.getConsequence(), targetBuilder);
  }


  @Override
  public void visit(BreakStatement breakStatement) {
    visit(breakStatement, this.targetBuilder);
  }

  public void visit(BreakStatement breakStatement, Builder targetBuilder) {
    targetBuilder.add("break;");
  }

  @Override
  public void visit(ContinueStatement continueStatement) {
    visit(continueStatement, this.targetBuilder);
  }

  public void visit(ContinueStatement continueStatement, Builder targetBuilder) {
    targetBuilder.add("continue;");
  }

  @Override
  public void visit(ReturnStatement returnStatement) {
    visit(returnStatement, this.targetBuilder);
  }

  public void visit(ReturnStatement returnStatement, Builder targetBuilder) {
    // we ensure that we always kill all coroutines
    targetBuilder.add("{\n");
    visit(killwhile, targetBuilder);

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
    visit(ifStatement, this.targetBuilder);
  }

  public void visit(IfStatement ifStatement, Builder targetBuilder) {
    targetBuilder.add("if(");
    visit(ifStatement.getCondition(), targetBuilder);
    targetBuilder.add(")");
    visit(ifStatement.getConsequence(), targetBuilder);
    if (ifStatement.getAlternative() != null) {
      targetBuilder.add("\n else ");
      visit(ifStatement.getAlternative(), targetBuilder);
      targetBuilder.add("\n");
    }
  }

  @Override
  public void visit(ExpressionStatement expressionStatement) {
    visit(expressionStatement, this.targetBuilder);
  }

  public void visit(ExpressionStatement expressionStatement, Builder targetBuilder) {
    if(expressionStatement.getExpression() != null) {
      visit(expressionStatement.getExpression(), targetBuilder);
      targetBuilder.add(";");
    }
  }

  @Override
  public void visit(SendStatement sendStatement) {
    visit(sendStatement, this.targetBuilder);
  }

  @Override
  public void visit(DeleteStatement deleteStatement) {
    visit(deleteStatement, this.targetBuilder);
  }

  public void visit(DeleteStatement deleteStatement, Builder targetBuilder) {
    targetBuilder.add("delete ");
    visit(deleteStatement.getVariable(), targetBuilder);
    targetBuilder.add(";");
  }

  public void visit(SendStatement sendStatement, Builder targetBuilder) {
    targetBuilder.add(concu_prefix + sendStatement.getFirstOperand() + "->sendval(");

    visit(sendStatement.getSecondOperand(), targetBuilder);

    targetBuilder.add(");");
  }

  @Override
  public void visit(Expression expression) {
    visit(expression, this.targetBuilder);
  }

  public void visit(Expression expression, Builder targetBuilder) {
    if (expression instanceof BoolLiteral) {
      visit((BoolLiteral) expression, targetBuilder);
    } else if (expression instanceof IntLiteral) {
      visit((IntLiteral) expression, targetBuilder);
    } else if (expression instanceof CharLiteral) {
      visit((CharLiteral) expression, targetBuilder);
    } else if (expression instanceof FloatLiteral) {
      visit((FloatLiteral) expression, targetBuilder);
    } else if (expression instanceof StringLiteral) {
      visit((StringLiteral) expression, targetBuilder);
    } else if (expression instanceof ArrayExpression) {
      visit((ArrayExpression) expression, targetBuilder);
    } else if (expression instanceof BinaryExpression) {
      visit((BinaryExpression) expression, targetBuilder);
    } else if (expression instanceof VariableExpression) {
      visit((VariableExpression) expression, targetBuilder);
    } else if (expression instanceof CastExpression) {
      visit((CastExpression) expression, targetBuilder);
    } else if (expression instanceof SizeofSpecifier) {
      visit((SizeofSpecifier) expression, targetBuilder);
    } else if (expression instanceof SizeofExpression) {
      visit((SizeofExpression) expression, targetBuilder);
    } else if (expression instanceof AdditivePrefixExpression) {
      visit((AdditivePrefixExpression) expression, targetBuilder);
    } else if (expression instanceof IncrementDecrementExpression) {
      visit((IncrementDecrementExpression) expression, targetBuilder);
    } else if (expression instanceof FunctionExpression) {
      visit((FunctionExpression) expression, targetBuilder);
    } else if (expression instanceof MakeExpression) {
      visit((MakeExpression) expression, targetBuilder);
    } else if (expression instanceof PipeExpression) {
      visit((PipeExpression) expression, targetBuilder);
    } else if (expression instanceof InitializerList) {
      visit((InitializerList) expression, targetBuilder);
    } else if (expression instanceof UnaryExpression) {
      visit((UnaryExpression) expression, targetBuilder);
    } else {
      throw new ExpressionNotFoundException(
          "Expression Not Found In ArduinoCodeGenerator visit(Expression expression) Expression: "
              + expression.getClass().getName());
    }
  }

  @Override
  public void visit(InitializerList initializerList) {
    visit(initializerList, this.targetBuilder);
  }

  public void visit(InitializerList initializerList, Builder targetBuilder) {
    targetBuilder.add("{");
    String prefix = "";
    for (Node expr : initializerList) {
      targetBuilder.add(prefix);
      visit((Expression) expr, targetBuilder);
      prefix = ", ";
    }

    targetBuilder.add("}");
  }

  @Override
  public void visit(BoolLiteral boolLiteral) {
    visit(boolLiteral, this.targetBuilder);
  }

  public void visit(BoolLiteral boolLiteral, Builder targetBuilder) {
    targetBuilder.add(boolLiteral.getValue().toString());
  }

  @Override
  public void visit(IntLiteral intLiteral) {
    visit(intLiteral, this.targetBuilder);
  }

  public void visit(IntLiteral intLiteral, Builder targetBuilder) {
    targetBuilder.add(intLiteral.getValue().toString());
  }

  @Override
  public void visit(CharLiteral charLiteral) {
    visit(charLiteral, this.targetBuilder);
  }

  public void visit(CharLiteral charLiteral, Builder targetBuilder) {
    targetBuilder.add(charLiteral.getValue());
  }

  @Override
  public void visit(FloatLiteral floatLiteral) {
    visit(floatLiteral, this.targetBuilder);
  }


  public void visit(FloatLiteral floatLiteral, Builder targetBuilder) {
    targetBuilder.add(floatLiteral.getValue().toString());
  }

  @Override
  public void visit(StringLiteral stringLiteral) {
    visit(stringLiteral, this.targetBuilder);
  }

  public void visit(StringLiteral stringLiteral, Builder targetBuilder) {
    targetBuilder.add(stringLiteral.getValue().toString());
  }

  @Override
  public void visit(BinaryExpression binaryExpression) {
    visit(binaryExpression, this.targetBuilder);
  }

  public void visit(BinaryExpression binaryExpression, Builder targetBuilder) {
    targetBuilder.add("(");
    visit(binaryExpression.getFirstOperand(), targetBuilder);
    targetBuilder.add(binaryExpression.getOperator());
    visit(binaryExpression.getSecondOperand(), targetBuilder);
    targetBuilder.add(")");
  }

  @Override
  public void visit(VariableExpression variableExpression) {
    visit(variableExpression, this.targetBuilder);
  }

  public void visit(VariableExpression variableExpression, Builder targetBuilder) {
    targetBuilder.add(concu_prefix + variableExpression.getIdentifier());
  }

  @Override
  public void visit(ArrayExpression expression) {
    visit(expression, this.targetBuilder);
  }

  public void visit(ArrayExpression expression, Builder targetBuilder) {
    visit(expression.getFirstOperand(), targetBuilder);
    targetBuilder.add("[");
    visit(expression.getSecondOperand(), targetBuilder);
    targetBuilder.add("]");
  }

  @Override
  public void visit(CastExpression expression) {
    visit(expression, this.targetBuilder);
  }

  public void visit(CastExpression expression, Builder targetBuilder) {
    targetBuilder.add("(");
    visit(expression.getSpecifiers(), targetBuilder);
    targetBuilder.removeLastCharacter();
    targetBuilder.add(")");
    visit(expression.getFirstOperand(), targetBuilder);
  }

  @Override
  public void visit(FunctionExpression expression) {
    visit(expression, this.targetBuilder);
  }

  public void visit(FunctionExpression expression, Builder targetBuilder) {
    if (!Stdlib.in(expression.getIdentifier())) {
      targetBuilder.add(concu_prefix);
    }
    targetBuilder.add(expression.getIdentifier() + "(");
    visit(expression.getParameterList(), targetBuilder);
    targetBuilder.add(")");
  }

  @Override
  public void visit(ArgumentExpressionList expression) {
    visit(expression, this.targetBuilder);
  }

  public void visit(ArgumentExpressionList expression, Builder targetBuilder) {
    String prefix = "";
    for (Expression param : expression) {
      targetBuilder.add(prefix);
      visit(param, targetBuilder);
      prefix = ",";
    }
  }

  @Override
  public void visit(IncrementDecrementExpression expression) {
    visit(expression, this.targetBuilder);
  }

  public void visit(IncrementDecrementExpression expression, Builder targetBuilder) {
    if (expression.isPrefix()) {
      targetBuilder.add(expression.getOperator());
      visit(expression.getFirstOperand(), targetBuilder);
    } else {
      visit(expression.getFirstOperand(), targetBuilder);
      targetBuilder.add(expression.getOperator());
    }
  }

  @Override
  public void visit(SizeofSpecifier expression) {
    visit(expression, this.targetBuilder);
  }

  public void visit(SizeofSpecifier expression, Builder targetBuilder) {
    targetBuilder.add("sizeof(");
    visit(expression.getSpecifiers(), targetBuilder);
    targetBuilder.removeLastCharacter();
    targetBuilder.add(")");
  }

  @Override
  public void visit(SizeofExpression expression) {
    visit(expression, this.targetBuilder);
  }

  public void visit(SizeofExpression expression, Builder targetBuilder) {
    targetBuilder.add("sizeof(");
    visit(expression.getFirstOperand(), targetBuilder);
    targetBuilder.add(")");
  }

  @Override
  public void visit(AdditivePrefixExpression expression) {
    visit(expression, this.targetBuilder);
  }

  public void visit(AdditivePrefixExpression expression, Builder targetBuilder) {
    targetBuilder.add(expression.getOperator());
    visit(expression.getFirstOperand(), targetBuilder);
  }

  @Override
  public void visit(UnaryExpression expression) {
    visit(expression, this.targetBuilder);
  }

  public void visit(UnaryExpression expression, Builder targetBuilder) {
    targetBuilder.add(expression.getOperator());
    visit(expression.getFirstOperand(), targetBuilder);
  }

  @Override
  public void visit(MakeExpression expression) {
    visit(expression, this.targetBuilder);
  }

  public void visit(MakeExpression expression, Builder targetBuilder) {
    if (!expression.getSpecifiers().contains("chan")) {
      throw new RuntimeException(
          "Undefined behavior in Make command containing types: " + expression.getSpecifiers()
              .toString());
    }

    DeclarationSpecifierList copy = new DeclarationSpecifierList(
        new ArrayList<String>(expression.getSpecifiers()));
    copy.remove("chan");

    targetBuilder.add("new Channel<");
    visit(copy, targetBuilder);
    targetBuilder.add(">()");
  }

  @Override
  public void visit(PipeExpression expression) {
    visit(expression, this.targetBuilder);
  }

  public void visit(PipeExpression expression, Builder targetBuilder) {
    VariableExpression expr = (VariableExpression) expression.getFirstOperand();
    targetBuilder.add(concu_prefix + expr.getIdentifier() + "->recval()");
  }

  /**
   * This class generates the struct for params feeded to GoRoutines.
   */
  private void generateGoParamStruct(FunctionDeclaration func, Builder targetBuilder) {
    targetBuilder.startGoParamsStruct();
    targetBuilder.add("struct " + func.getIdentifier() + "__params__struct\n{\n");
    for (Iterator<VariableDeclaration> vd = func.iterator(); vd.hasNext(); ) {
      visit(vd.next(), targetBuilder);
      targetBuilder.add(";\n");
    }
    targetBuilder.add("};\n");
    targetBuilder.endGoParamsStruct();
  }

  private void injectGoParamStructDeref(FunctionDeclaration func, Builder targetBuilder) {

    RawStatement structcast = new RawStatement(
        func.getIdentifier() + "__params__struct *" + func.getIdentifier() + "__params = (" + func
            .getIdentifier() + "__params__struct *)" + func.getIdentifier() + "__param__pointer;");
    Builder derefs = new Builder();
    for (VariableDeclaration vd : func) {
      visit(vd.getSpecifiers(), derefs);
      if (vd.isPointer() || vd.isArray() || vd.getSpecifiers().contains("chan")) {
        derefs.add("*");
      }
      derefs.add(concu_prefix + vd.getIdentifier() + " = ");
      derefs.add(func.getIdentifier() + "__params->" + concu_prefix + vd.getIdentifier() + ";\n");
    }
    RawStatement deref = new RawStatement(derefs.getOutput());
    func.getBody().addToTop(deref);

    func.getBody().addToTop(structcast);
  }

  private void visit(RawStatement stm, Builder targetBuilder) {
    targetBuilder.add(stm.getRawCode());
  }

  private class RawStatement implements Statement {

    private String rawCode;

    public RawStatement(String code) {
      this.rawCode = code;
    }

    public String getRawCode() {
      return rawCode;
    }

    @Override
    public String getLiteral() {
      return null;
    }
  }

}


