// Generated from C:/repos/p4-code/cfg\Concuroo.g4 by ANTLR 4.7
package ConcurooParser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ConcurooParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ConcurooVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpression(ConcurooParser.PrimaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#postfixExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpression(ConcurooParser.PostfixExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#argumentExpressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentExpressionList(ConcurooParser.ArgumentExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(ConcurooParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#unaryOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperator(ConcurooParser.UnaryOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#castExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpression(ConcurooParser.CastExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(ConcurooParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(ConcurooParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(ConcurooParser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(ConcurooParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpression(ConcurooParser.LogicalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOrExpression(ConcurooParser.LogicalOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#assignmentExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExpression(ConcurooParser.AssignmentExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ConcurooParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#declarationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationStatement(ConcurooParser.DeclarationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(ConcurooParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#initDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitDeclarator(ConcurooParser.InitDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarator(ConcurooParser.DeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#directDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDirectDeclarator(ConcurooParser.DirectDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#parameterTypeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterTypeList(ConcurooParser.ParameterTypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(ConcurooParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterDeclaration(ConcurooParser.ParameterDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#declarationSpecifiers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationSpecifiers(ConcurooParser.DeclarationSpecifiersContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#declarationSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationSpecifier(ConcurooParser.DeclarationSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#initializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitializer(ConcurooParser.InitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#initializerList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitializerList(ConcurooParser.InitializerListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ConcurooParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#sendStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSendStatement(ConcurooParser.SendStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#coroutineStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCoroutineStatement(ConcurooParser.CoroutineStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#compoundStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundStatement(ConcurooParser.CompoundStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#statementList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementList(ConcurooParser.StatementListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(ConcurooParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#selectionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectionStatement(ConcurooParser.SelectionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ConcurooParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#iterationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIterationStatement(ConcurooParser.IterationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpStatement(ConcurooParser.JumpStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#typeSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeSpecifier(ConcurooParser.TypeSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#pointer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPointer(ConcurooParser.PointerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#typeModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeModifier(ConcurooParser.TypeModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#typeQualifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeQualifier(ConcurooParser.TypeQualifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#boolLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLiteral(ConcurooParser.BoolLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(ConcurooParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#translationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranslationUnit(ConcurooParser.TranslationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurooParser#externalDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExternalDeclaration(ConcurooParser.ExternalDeclarationContext ctx);
}