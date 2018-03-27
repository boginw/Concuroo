// Generated from /home/hamburger/projects/p4-code/cfg/Concurro.g4 by ANTLR 4.7
package CuncurroParser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ConcurroParser}.
 */
public interface ConcurroListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(ConcurroParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(ConcurroParser.PrimaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(ConcurroParser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(ConcurroParser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#argumentExpressionList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentExpressionList(ConcurroParser.ArgumentExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#argumentExpressionList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentExpressionList(ConcurroParser.ArgumentExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(ConcurroParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(ConcurroParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperator(ConcurroParser.UnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperator(ConcurroParser.UnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#castExpression}.
	 * @param ctx the parse tree
	 */
	void enterCastExpression(ConcurroParser.CastExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#castExpression}.
	 * @param ctx the parse tree
	 */
	void exitCastExpression(ConcurroParser.CastExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(ConcurroParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(ConcurroParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(ConcurroParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(ConcurroParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(ConcurroParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(ConcurroParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(ConcurroParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(ConcurroParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpression(ConcurroParser.LogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpression(ConcurroParser.LogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpression(ConcurroParser.LogicalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpression(ConcurroParser.LogicalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(ConcurroParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(ConcurroParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ConcurroParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ConcurroParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#declarationStatement}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationStatement(ConcurroParser.DeclarationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#declarationStatement}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationStatement(ConcurroParser.DeclarationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(ConcurroParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(ConcurroParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#declarationList}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationList(ConcurroParser.DeclarationListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#declarationList}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationList(ConcurroParser.DeclarationListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#initDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterInitDeclarator(ConcurroParser.InitDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#initDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitInitDeclarator(ConcurroParser.InitDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#declarator}.
	 * @param ctx the parse tree
	 */
	void enterDeclarator(ConcurroParser.DeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#declarator}.
	 * @param ctx the parse tree
	 */
	void exitDeclarator(ConcurroParser.DeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#directDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterDirectDeclarator(ConcurroParser.DirectDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#directDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitDirectDeclarator(ConcurroParser.DirectDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#parameterTypeList}.
	 * @param ctx the parse tree
	 */
	void enterParameterTypeList(ConcurroParser.ParameterTypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#parameterTypeList}.
	 * @param ctx the parse tree
	 */
	void exitParameterTypeList(ConcurroParser.ParameterTypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(ConcurroParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(ConcurroParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclaration(ConcurroParser.ParameterDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclaration(ConcurroParser.ParameterDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierList(ConcurroParser.IdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierList(ConcurroParser.IdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#declarationSpecifiers}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationSpecifiers(ConcurroParser.DeclarationSpecifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#declarationSpecifiers}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationSpecifiers(ConcurroParser.DeclarationSpecifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#declarationSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationSpecifier(ConcurroParser.DeclarationSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#declarationSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationSpecifier(ConcurroParser.DeclarationSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#initializer}.
	 * @param ctx the parse tree
	 */
	void enterInitializer(ConcurroParser.InitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#initializer}.
	 * @param ctx the parse tree
	 */
	void exitInitializer(ConcurroParser.InitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#initializerList}.
	 * @param ctx the parse tree
	 */
	void enterInitializerList(ConcurroParser.InitializerListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#initializerList}.
	 * @param ctx the parse tree
	 */
	void exitInitializerList(ConcurroParser.InitializerListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ConcurroParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ConcurroParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStatement(ConcurroParser.CompoundStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStatement(ConcurroParser.CompoundStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#blockItemList}.
	 * @param ctx the parse tree
	 */
	void enterBlockItemList(ConcurroParser.BlockItemListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#blockItemList}.
	 * @param ctx the parse tree
	 */
	void exitBlockItemList(ConcurroParser.BlockItemListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#blockItem}.
	 * @param ctx the parse tree
	 */
	void enterBlockItem(ConcurroParser.BlockItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#blockItem}.
	 * @param ctx the parse tree
	 */
	void exitBlockItem(ConcurroParser.BlockItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(ConcurroParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(ConcurroParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void enterSelectionStatement(ConcurroParser.SelectionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void exitSelectionStatement(ConcurroParser.SelectionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ConcurroParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ConcurroParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterIterationStatement(ConcurroParser.IterationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitIterationStatement(ConcurroParser.IterationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpStatement(ConcurroParser.JumpStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpStatement(ConcurroParser.JumpStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#typeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeSpecifier(ConcurroParser.TypeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#typeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeSpecifier(ConcurroParser.TypeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#pointer}.
	 * @param ctx the parse tree
	 */
	void enterPointer(ConcurroParser.PointerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#pointer}.
	 * @param ctx the parse tree
	 */
	void exitPointer(ConcurroParser.PointerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#typeModifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeModifier(ConcurroParser.TypeModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#typeModifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeModifier(ConcurroParser.TypeModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#typeQualifierList}.
	 * @param ctx the parse tree
	 */
	void enterTypeQualifierList(ConcurroParser.TypeQualifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#typeQualifierList}.
	 * @param ctx the parse tree
	 */
	void exitTypeQualifierList(ConcurroParser.TypeQualifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#typeQualifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeQualifier(ConcurroParser.TypeQualifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#typeQualifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeQualifier(ConcurroParser.TypeQualifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(ConcurroParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(ConcurroParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void enterTranslationUnit(ConcurroParser.TranslationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void exitTranslationUnit(ConcurroParser.TranslationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurroParser#externalDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterExternalDeclaration(ConcurroParser.ExternalDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurroParser#externalDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitExternalDeclaration(ConcurroParser.ExternalDeclarationContext ctx);
}