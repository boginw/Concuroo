// Generated from /home/hamburger/projects/p4-code/cfg/Concurro.g4 by ANTLR 4.7
package CuncurroParser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ConcurroParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ConcurroVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpression(ConcurroParser.PrimaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#postfixExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpression(ConcurroParser.PostfixExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#argumentExpressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentExpressionList(ConcurroParser.ArgumentExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(ConcurroParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#unaryOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperator(ConcurroParser.UnaryOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#castExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpression(ConcurroParser.CastExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(ConcurroParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(ConcurroParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(ConcurroParser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(ConcurroParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpression(ConcurroParser.LogicalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOrExpression(ConcurroParser.LogicalOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#assignmentExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExpression(ConcurroParser.AssignmentExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ConcurroParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#declarationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationStatement(ConcurroParser.DeclarationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(ConcurroParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#declarationList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationList(ConcurroParser.DeclarationListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#initDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitDeclarator(ConcurroParser.InitDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarator(ConcurroParser.DeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#directDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDirectDeclarator(ConcurroParser.DirectDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#parameterTypeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterTypeList(ConcurroParser.ParameterTypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(ConcurroParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterDeclaration(ConcurroParser.ParameterDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#identifierList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierList(ConcurroParser.IdentifierListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#declarationSpecifiers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationSpecifiers(ConcurroParser.DeclarationSpecifiersContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#declarationSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationSpecifier(ConcurroParser.DeclarationSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#initializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitializer(ConcurroParser.InitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#initializerList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitializerList(ConcurroParser.InitializerListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ConcurroParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#compoundStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundStatement(ConcurroParser.CompoundStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#blockItemList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockItemList(ConcurroParser.BlockItemListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#blockItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockItem(ConcurroParser.BlockItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(ConcurroParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#selectionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectionStatement(ConcurroParser.SelectionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ConcurroParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#iterationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIterationStatement(ConcurroParser.IterationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpStatement(ConcurroParser.JumpStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#typeSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeSpecifier(ConcurroParser.TypeSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#pointer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPointer(ConcurroParser.PointerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#typeModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeModifier(ConcurroParser.TypeModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#typeQualifierList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeQualifierList(ConcurroParser.TypeQualifierListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#typeQualifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeQualifier(ConcurroParser.TypeQualifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(ConcurroParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#translationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranslationUnit(ConcurroParser.TranslationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConcurroParser#externalDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExternalDeclaration(ConcurroParser.ExternalDeclarationContext ctx);
}