package concuroo;

import ConcurooParser.ConcurooBaseVisitor;
import ConcurooParser.ConcurooParser;
import concuroo.nodes.Node;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.LiteralExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.MultiplicativeExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalAndExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalOrExpression;
import concuroo.nodes.expression.literalExpression.IntLiteral;
import concuroo.nodes.expression.literalExpression.StringLiteral;
import org.apache.commons.lang3.StringUtils;

public class ASTVisitor extends ConcurooBaseVisitor<Node> {

  @Override
  public Node visitPrimaryExpression(ConcurooParser.PrimaryExpressionContext ctx) {
  if(ctx.StringLiteral().toString().indexOf(0) == '"') {
    LiteralExpression exp = new StringLiteral(ctx.StringLiteral().toString());

  }
  else if(StringUtils.isNumeric(ctx.StringLiteral().toString())) {
    LiteralExpression exp = new IntLiteral(Integer.parseInt(ctx.StringLiteral().toString()));
  }
    return super.visitPrimaryExpression(ctx);


  }

  @Override
  public Node visitAdditiveExpression(ConcurooParser.AdditiveExpressionContext ctx) {
    if (ctx.children.size() == 1) {
      return visitMultiplicativeExpression(ctx.multiplicativeExpression());
    }

    AdditiveExpression expr = new AdditiveExpression();
    expr.setFirstOperand((Expression) visit(ctx.getChild(0)));
    expr.setOperator(ctx.children.get(1).toString());
    expr.setSecondOperand((Expression) visit(ctx.getChild(2)));

    return expr;
  }

  @Override
  public Node visitMultiplicativeExpression(ConcurooParser.MultiplicativeExpressionContext ctx) {
    if (ctx.children.size() == 1) {
      return visitCastExpression(ctx.castExpression());
    }

    MultiplicativeExpression expr = new MultiplicativeExpression();
    expr.setFirstOperand((Expression) visit(ctx.getChild(0)));
    expr.setOperator(ctx.children.get(1).toString());
    expr.setSecondOperand((Expression) visit(ctx.getChild(2)));

    return expr;
  }

  @Override
  public Node visitLogicalAndExpression(ConcurooParser.LogicalAndExpressionContext ctx) {
    if(ctx.getChild(0) instanceof ConcurooParser.EqualityExpressionContext) {
      return visitEqualityExpression(ctx.equalityExpression());
    }

    LogicalAndExpression expr = new LogicalAndExpression();
    expr.setFirstOperand((Expression) visit(ctx.getChild(0)));
    expr.setSecondOperand((Expression) visit(ctx.getChild(2)));

    return visitChildren(ctx);
  }

  @Override
  public Node visitLogicalOrExpression(ConcurooParser.LogicalOrExpressionContext ctx) {
    if(ctx.getChild(0) instanceof ConcurooParser.LogicalAndExpressionContext) {
      return visitLogicalAndExpression(ctx.logicalAndExpression());
    }

    LogicalOrExpression expr = new LogicalOrExpression();
    expr.setFirstOperand((Expression) visit(ctx.getChild(0)));
    expr.setSecondOperand((Expression) visit(ctx.getChild(2)));

    return visitChildren(ctx);
  }

  @Override
  public Node visitEqualityExpression(ConcurooParser.EqualityExpressionContext ctx) {
    if(ctx.getChild(0) instanceof ConcurooParser.RelationalExpressionContext) {
      return visitRelationalExpression(ctx.relationalExpression());
    }

    LogicalEqualityExpression expr = new LogicalEqualityExpression();
    expr.setFirstOperand((Expression) visit(ctx.getChild(0)));
    expr.setSecondOperand((Expression) visit(ctx.getChild(2)));

    return visitChildren(ctx);
  }

  @Override
  public Node aggregateResult(Node aggregate, Node nextResult) {
    if (aggregate == null) {
      return nextResult;
    }

    if (nextResult == null) {
      return aggregate;
    }

    return aggregate;
  }
}
