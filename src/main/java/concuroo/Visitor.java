package concuroo;

import concuroo.exceptions.ExpressionNotFoundException;
import concuroo.exceptions.StatementNotFoundException;
import concuroo.nodes.ArgumentExpressionList;
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

public interface Visitor {

  // Nodes
  void visit(Node node);

  // Expressions
  void visit(Expression expression);

  // Literals
  void visit(BoolLiteral boolLiteral);

  void visit(IntLiteral intLiteral);

  void visit(CharLiteral charLiteral);

  void visit(FloatLiteral floatLiteral);

  void visit(StringLiteral stringLiteral);

  // Unary
  void visit(UnaryExpression expression);

  void visit(AdditivePrefixExpression expression);

  void visit(AddressOfExpression addressOfExpression);

  void visit(CastExpression expression);

  void visit(FunctionExpression expression);

  void visit(IncrementDecrementExpression expression);

  void visit(MakeExpression expression);

  void visit(NegationExpression expression);

  void visit(PipeExpression expression);

  void visit(SizeofExpression expression);

  // Binary Expressions
  void visit(BinaryExpression binaryExpression);

  void visit(AdditiveExpression additiveExpression);

  void visit(MultiplicativeExpression multiplicativeExpression);

  void visit(LogicalRelantionalExpression logicalRelantionalExpression);

  void visit(LogicalAndExpression logicalAndExpression);

  void visit(LogicalEqualityExpression logicalEqualityExpression);

  void visit(LogicalOrExpression logicalOrExpression);

  void visit(AssignmentExpression assignmentExpression);

  // LHS Expressions
  void visit(ArrayExpression expression);

  void visit(DereferenceExpression expression);

  void visit(VariableExpression variableExpression);

  // Other expressions
  void visit(InitializerList initializerList);

  void visit(SizeofSpecifier expression);

  // Statements
  void visit(Statement statement);

  // Jump statements
  void visit(BreakStatement breakStatement);

  void visit(ContinueStatement continueStatement);

  void visit(ReturnStatement returnStatement);

  // Iteration / Selection Statements
  void visit(IfStatement ifStatement);

  void visit(WhileStatement whileStatement);

  // Other statements
  void visit(CompoundStatement compoundStatement);

  void visit(CoroutineStatement coroutineStatement);

  void visit(DeleteStatement deleteStatement);

  void visit(ExpressionStatement expressionStatement);

  void visit(SendStatement sendStatement);

  void visit(VariableDeclaration variableDeclaration);

  void visit(RawStatement rawStatement);

  // MISC
  void visit(ArgumentExpressionList expressionList);

  void visit(DeclarationSpecifierList declarationSpecifierList);

  void visit(FunctionDeclaration functionDeclaration);

  void visit(Program program);

  static void concreteify(Node node, Visitor visitor) {
    if (node instanceof Expression) {
      Visitor.concreteify(node, visitor);
    } else if (node instanceof Statement) {
      Visitor.concreteify(node, visitor);
    } else if (node instanceof ArgumentExpressionList) {
      visitor.visit((ArgumentExpressionList) node);
    } else if (node instanceof DeclarationSpecifierList) {
      visitor.visit((DeclarationSpecifierList) node);
    } else if (node instanceof FunctionDeclaration) {
      visitor.visit((FunctionDeclaration) node);
    } else if (node instanceof Program) {
      visitor.visit((Program) node);
    }
  }

  static void concreteify(Expression expression, Visitor visitor) {
    if (expression instanceof BoolLiteral) {
      visitor.visit((BoolLiteral) expression);
    } else if (expression instanceof IntLiteral) {
      visitor.visit((IntLiteral) expression);
    } else if (expression instanceof CharLiteral) {
      visitor.visit((CharLiteral) expression);
    } else if (expression instanceof FloatLiteral) {
      visitor.visit((FloatLiteral) expression);
    } else if (expression instanceof StringLiteral) {
      visitor.visit((StringLiteral) expression);
    } else if (expression instanceof AdditivePrefixExpression) {
      visitor.visit((AdditivePrefixExpression) expression);
    } else if (expression instanceof AddressOfExpression) {
      visitor.visit((AddressOfExpression) expression);
    } else if (expression instanceof CastExpression) {
      visitor.visit((CastExpression) expression);
    } else if (expression instanceof FunctionExpression) {
      visitor.visit((FunctionExpression) expression);
    } else if (expression instanceof IncrementDecrementExpression) {
      visitor.visit((IncrementDecrementExpression) expression);
    } else if (expression instanceof MakeExpression) {
      visitor.visit((MakeExpression) expression);
    } else if (expression instanceof NegationExpression) {
      visitor.visit((NegationExpression) expression);
    } else if (expression instanceof PipeExpression) {
      visitor.visit((PipeExpression) expression);
    } else if (expression instanceof SizeofExpression) {
      visitor.visit((SizeofExpression) expression);
    } else if (expression instanceof AdditiveExpression) {
      visitor.visit((AdditiveExpression) expression);
    } else if (expression instanceof MultiplicativeExpression) {
      visitor.visit((MultiplicativeExpression) expression);
    } else if (expression instanceof LogicalRelantionalExpression) {
      visitor.visit((LogicalRelantionalExpression) expression);
    } else if (expression instanceof LogicalAndExpression) {
      visitor.visit((LogicalAndExpression) expression);
    } else if (expression instanceof LogicalEqualityExpression) {
      visitor.visit((LogicalEqualityExpression) expression);
    } else if (expression instanceof LogicalOrExpression) {
      visitor.visit((LogicalOrExpression) expression);
    } else if (expression instanceof AssignmentExpression) {
      visitor.visit((AssignmentExpression) expression);
    } else if (expression instanceof ArrayExpression) {
      visitor.visit((ArrayExpression) expression);
    } else if (expression instanceof DereferenceExpression) {
      visitor.visit((DereferenceExpression) expression);
    } else if (expression instanceof VariableExpression) {
      visitor.visit((VariableExpression) expression);
    } else if (expression instanceof InitializerList) {
      visitor.visit((InitializerList) expression);
    } else if (expression instanceof SizeofSpecifier) {
      visitor.visit((SizeofSpecifier) expression);
    } else {
      throw new ExpressionNotFoundException(
          "Expression Not Found In ArduinoCodeGenerator visit(Expression expression) Expression: "
              + expression.getClass().getName());
    }
  }

  static void concreteify(Statement statement, Visitor visitor) {
    if (statement instanceof BreakStatement) {
      visitor.visit((BreakStatement) statement);
    } else if (statement instanceof ContinueStatement) {
      visitor.visit((ContinueStatement) statement);
    } else if (statement instanceof ReturnStatement) {
      visitor.visit((ReturnStatement) statement);
    } else if (statement instanceof IfStatement) {
      visitor.visit((IfStatement) statement);
    } else if (statement instanceof WhileStatement) {
      visitor.visit((WhileStatement) statement);
    } else if (statement instanceof CompoundStatement) {
      visitor.visit((CompoundStatement) statement);
    } else if (statement instanceof CoroutineStatement) {
      visitor.visit((CoroutineStatement) statement);
    } else if (statement instanceof DeleteStatement) {
      visitor.visit((DeleteStatement) statement);
    } else if (statement instanceof ExpressionStatement) {
      visitor.visit((ExpressionStatement) statement);
    } else if (statement instanceof SendStatement) {
      visitor.visit((SendStatement) statement);
    } else if (statement instanceof VariableDeclaration) {
      visitor.visit((VariableDeclaration) statement);
    } else if (statement instanceof RawStatement) {
      visitor.visit((RawStatement) statement);
    } else {
      throw new StatementNotFoundException(
          "Statement Not Found In ArduinoCodeGenerator visit(Statement statement) Statement: "
              + statement.getClass().getName());
    }
  }
}
