package concuroo;

import ConcurooParser.ConcurooBaseVisitor;
import ConcurooParser.ConcurooParser;

import concuroo.nodes.Node;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.LiteralExpression;
import concuroo.nodes.expression.PrimaryExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.MultiplicativeExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalAndExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalOrExpression;
import concuroo.nodes.expression.literalExpression.*;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

public class ASTVisitor extends ConcurooBaseVisitor<Node> {

  @Override
  public Node visitPrimaryExpression(ConcurooParser.PrimaryExpressionContext ctx) {
  if (ctx.children.size() != 0) {
    if (ctx.children.size() == 3) {
      return null;
    }


    if (!ctx.StringLiteral().isEmpty()){
      return new StringLiteral(ctx.StringLiteral().toString());
    }

    else if (ctx.ConstantLiteral().getSymbol().getType() == ConcurooParser.ConstantLiteral) {
      String token = ctx.ConstantLiteral().toString();
      if (token.charAt(0) == '\'')
      {
        if (token.length() > 2) {
          return new CharLiteral(token.charAt(1));
        }
      }
      else if (Character.isDigit(token.charAt(0))) {
        if (token.contains(".")) {
          return new FloatLiteral(Double.valueOf(token));
        }
        return new IntLiteral(Integer.valueOf(token));
      }
      else if (token.equals("false")){
        return new BoolLiteral(false);
      }
      else return new BoolLiteral(true);


    }


//    else if ( == ConcurooParser.T__38) {
//    }

    //ParseTree node = visitTerminal(ctx.ConstantLiteral());

//  if (visit(ctx.getChild(0)).getLiteral().indexOf(0) == '"'){
//    StringLiteral lit = new StringLiteral(ctx.getChild(0).getText());
//    return lit;
//  }
//  else if (Character.isDigit(visit(ctx.getChild(0)).toString().indexOf(0))){
//    IntLiteral lit = new IntLiteral(Integer.parseInt(ctx.getChild(0).getText()));
//    return lit;
//  }


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
