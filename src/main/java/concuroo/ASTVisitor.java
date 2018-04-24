package concuroo;

import ConcurooParser.ConcurooBaseVisitor;
import ConcurooParser.ConcurooParser;
import concuroo.nodes.Node;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.PrimaryExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.MultiplicativeExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalAndExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalOrExpression;
import concuroo.nodes.expression.literalExpression.*;
import org.antlr.v4.runtime.tree.TerminalNode;


public class ASTVisitor extends ConcurooBaseVisitor<Node> {

  @Override
  public Node visitPrimaryExpression(ConcurooParser.PrimaryExpressionContext ctx) {
    if (ctx.children.size() != 0) {
      if (ctx.children.size() == 3) {
        return visit(ctx.getChild(0));
      }

      TerminalNode n;
      if ((ctx.StringLiteral().size()) != 0) {
        return new StringLiteral(ctx.StringLiteral().toString());
      } else if ((n = ctx.CharLiteral()) != null) {
        return new CharLiteral(ctx.CharLiteral().getSymbol().getText());
      } else if ((n = ctx.DoubleLiteral()) != null) {
        return new FloatLiteral(Double.valueOf(ctx.DoubleLiteral().getSymbol().getText()));
      } else if ((n = ctx.Number()) != null) {
        return new IntLiteral(Integer.valueOf(ctx.Number().getSymbol().getText()));
      } else if (!ctx.boolLiteral().isEmpty()) {
        if (ctx.boolLiteral().getChild(0).toString().equals("true")) {
          return new BoolLiteral(true);
        }
        return new BoolLiteral(false);
      }
    }
    throw new RuntimeException("No recognized primary expression");
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
    if (ctx.getChild(0) instanceof ConcurooParser.EqualityExpressionContext) {
      return visitEqualityExpression(ctx.equalityExpression());
    }

    LogicalAndExpression expr = new LogicalAndExpression();
    expr.setFirstOperand((Expression) visit(ctx.getChild(0)));
    expr.setSecondOperand((Expression) visit(ctx.getChild(2)));

    return visitChildren(ctx);
  }

  @Override
  public Node visitLogicalOrExpression(ConcurooParser.LogicalOrExpressionContext ctx) {
    if (ctx.getChild(0) instanceof ConcurooParser.LogicalAndExpressionContext) {
      return visitLogicalAndExpression(ctx.logicalAndExpression());
    }

    LogicalOrExpression expr = new LogicalOrExpression();
    expr.setFirstOperand((Expression) visit(ctx.getChild(0)));
    expr.setSecondOperand((Expression) visit(ctx.getChild(2)));

    return visitChildren(ctx);
  }

  @Override
  public Node visitEqualityExpression(ConcurooParser.EqualityExpressionContext ctx) {
    if (ctx.getChild(0) instanceof ConcurooParser.RelationalExpressionContext) {
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
