// Generated from /home/scarress/Documents/p4-code/cfg/Concuroo.g4 by ANTLR 4.7
package ConcurooParser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ConcurooParser}.
 */
public interface ConcurooListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(ConcurooParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(ConcurooParser.PrimaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(ConcurooParser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(ConcurooParser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#argumentExpressionList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentExpressionList(ConcurooParser.ArgumentExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#argumentExpressionList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentExpressionList(ConcurooParser.ArgumentExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(ConcurooParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(ConcurooParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperator(ConcurooParser.UnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperator(ConcurooParser.UnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#castExpression}.
	 * @param ctx the parse tree
	 */
	void enterCastExpression(ConcurooParser.CastExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#castExpression}.
	 * @param ctx the parse tree
	 */
	void exitCastExpression(ConcurooParser.CastExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(ConcurooParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(ConcurooParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(ConcurooParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(ConcurooParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(ConcurooParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(ConcurooParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(ConcurooParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(ConcurooParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpression(ConcurooParser.LogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpression(ConcurooParser.LogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpression(ConcurooParser.LogicalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpression(ConcurooParser.LogicalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(ConcurooParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(ConcurooParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ConcurooParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ConcurooParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#declarationStatement}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationStatement(ConcurooParser.DeclarationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#declarationStatement}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationStatement(ConcurooParser.DeclarationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(ConcurooParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(ConcurooParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#declarationList}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationList(ConcurooParser.DeclarationListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#declarationList}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationList(ConcurooParser.DeclarationListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#initDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterInitDeclarator(ConcurooParser.InitDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#initDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitInitDeclarator(ConcurooParser.InitDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#declarator}.
	 * @param ctx the parse tree
	 */
	void enterDeclarator(ConcurooParser.DeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#declarator}.
	 * @param ctx the parse tree
	 */
	void exitDeclarator(ConcurooParser.DeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#directDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterDirectDeclarator(ConcurooParser.DirectDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#directDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitDirectDeclarator(ConcurooParser.DirectDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#parameterTypeList}.
	 * @param ctx the parse tree
	 */
	void enterParameterTypeList(ConcurooParser.ParameterTypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#parameterTypeList}.
	 * @param ctx the parse tree
	 */
	void exitParameterTypeList(ConcurooParser.ParameterTypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(ConcurooParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(ConcurooParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclaration(ConcurooParser.ParameterDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclaration(ConcurooParser.ParameterDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierList(ConcurooParser.IdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierList(ConcurooParser.IdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#declarationSpecifiers}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationSpecifiers(ConcurooParser.DeclarationSpecifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#declarationSpecifiers}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationSpecifiers(ConcurooParser.DeclarationSpecifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#declarationSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationSpecifier(ConcurooParser.DeclarationSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#declarationSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationSpecifier(ConcurooParser.DeclarationSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#initializer}.
	 * @param ctx the parse tree
	 */
	void enterInitializer(ConcurooParser.InitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#initializer}.
	 * @param ctx the parse tree
	 */
	void exitInitializer(ConcurooParser.InitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#initializerList}.
	 * @param ctx the parse tree
	 */
	void enterInitializerList(ConcurooParser.InitializerListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#initializerList}.
	 * @param ctx the parse tree
	 */
	void exitInitializerList(ConcurooParser.InitializerListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ConcurooParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ConcurooParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStatement(ConcurooParser.CompoundStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStatement(ConcurooParser.CompoundStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#statementList}.
	 * @param ctx the parse tree
	 */
	void enterStatementList(ConcurooParser.StatementListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#statementList}.
	 * @param ctx the parse tree
	 */
	void exitStatementList(ConcurooParser.StatementListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(ConcurooParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(ConcurooParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void enterSelectionStatement(ConcurooParser.SelectionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void exitSelectionStatement(ConcurooParser.SelectionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ConcurooParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ConcurooParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterIterationStatement(ConcurooParser.IterationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitIterationStatement(ConcurooParser.IterationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpStatement(ConcurooParser.JumpStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpStatement(ConcurooParser.JumpStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#typeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeSpecifier(ConcurooParser.TypeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#typeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeSpecifier(ConcurooParser.TypeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#pointer}.
	 * @param ctx the parse tree
	 */
	void enterPointer(ConcurooParser.PointerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#pointer}.
	 * @param ctx the parse tree
	 */
	void exitPointer(ConcurooParser.PointerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#typeModifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeModifier(ConcurooParser.TypeModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#typeModifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeModifier(ConcurooParser.TypeModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#typeQualifierList}.
	 * @param ctx the parse tree
	 */
	void enterTypeQualifierList(ConcurooParser.TypeQualifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#typeQualifierList}.
	 * @param ctx the parse tree
	 */
	void exitTypeQualifierList(ConcurooParser.TypeQualifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#typeQualifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeQualifier(ConcurooParser.TypeQualifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#typeQualifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeQualifier(ConcurooParser.TypeQualifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#boolLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBoolLiteral(ConcurooParser.BoolLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#boolLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBoolLiteral(ConcurooParser.BoolLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(ConcurooParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(ConcurooParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void enterTranslationUnit(ConcurooParser.TranslationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void exitTranslationUnit(ConcurooParser.TranslationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConcurooParser#externalDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterExternalDeclaration(ConcurooParser.ExternalDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConcurooParser#externalDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitExternalDeclaration(ConcurooParser.ExternalDeclarationContext ctx);
}