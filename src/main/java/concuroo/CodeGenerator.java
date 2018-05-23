package concuroo;

import concuroo.nodes.ArgumentExpressionList;
import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.FunctionDeclaration;
import concuroo.nodes.Program;
import concuroo.nodes.Statement;
import concuroo.nodes.Expression;
import concuroo.nodes.expression.InitializerList;
import concuroo.nodes.expression.SizeofSpecifier;
import concuroo.nodes.expression.binaryExpression.BinaryExpression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;
import concuroo.nodes.expression.literalExpression.BoolLiteral;
import concuroo.nodes.expression.literalExpression.CharLiteral;
import concuroo.nodes.expression.literalExpression.FloatLiteral;
import concuroo.nodes.expression.literalExpression.IntLiteral;
import concuroo.nodes.expression.literalExpression.StringLiteral;
import concuroo.nodes.expression.lhsExpression.ArrayExpression;
import concuroo.nodes.expression.unaryExpression.CastExpression;
import concuroo.nodes.expression.unaryExpression.FunctionExpression;
import concuroo.nodes.expression.unaryExpression.IncrementDecrementExpression;
import concuroo.nodes.expression.unaryExpression.SizeofExpression;
import concuroo.nodes.expression.UnaryExpression;
import concuroo.nodes.expression.unaryExpression.AdditivePrefixExpression;
import concuroo.nodes.expression.unaryExpression.MakeExpression;
import concuroo.nodes.expression.unaryExpression.PipeExpression;
import concuroo.nodes.statement.CompoundStatement;
import concuroo.nodes.statement.CoroutineStatement;
import concuroo.nodes.statement.DeleteStatement;
import concuroo.nodes.statement.ExpressionStatement;
import concuroo.nodes.statement.SendStatement;
import concuroo.nodes.statement.VariableDeclaration;
import concuroo.nodes.statement.selectionStatement.WhileStatement;
import concuroo.nodes.statement.jumpStatement.BreakStatement;
import concuroo.nodes.statement.jumpStatement.ContinueStatement;
import concuroo.nodes.statement.jumpStatement.ReturnStatement;
import concuroo.nodes.statement.selectionStatement.IfStatement;

public interface CodeGenerator {

  Builder generate(Program program);

  void visit(FunctionDeclaration functionDeclaration);
  void visit(DeclarationSpecifierList declarationSpecifierList);
  void visit(VariableDeclaration variableDeclaration);
  void visit(CompoundStatement compoundStatement);
  void visit(Statement statement);
  void visit(CoroutineStatement coroutineStatement);
  void visit(WhileStatement whileStatement);
  void visit(BreakStatement breakStatement);
  void visit(ContinueStatement continueStatement);
  void visit(ReturnStatement returnStatement);
  void visit(IfStatement ifStatement);
  void visit(ExpressionStatement expressionStatement);
  void visit(SendStatement sendStatement);
  void visit(DeleteStatement deleteStatement);

  void visit(BoolLiteral boolLiteral);
  void visit(IntLiteral intLiteral);
  void visit(CharLiteral charLiteral);
  void visit(FloatLiteral floatLiteral);
  void visit(StringLiteral stringLiteral);

  void visit(InitializerList initializerList);
  void visit(Expression expression);
  void visit(BinaryExpression binaryExpression);
  void visit(VariableExpression variableExpression);
  void visit(ArrayExpression expression);
  void visit(CastExpression expression);
  void visit(FunctionExpression expression);
  void visit(ArgumentExpressionList expression);
  void visit(IncrementDecrementExpression expression);
  void visit(SizeofSpecifier expression);
  void visit(SizeofExpression expression);
  void visit(AdditivePrefixExpression expression);
  void visit(UnaryExpression expression);
  void visit(MakeExpression expression);
  void visit(PipeExpression expression);

}
