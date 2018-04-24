package concuroo;

import ConcurooParser.ConcurooBaseVisitor;
import ConcurooParser.ConcurooParser;
import ConcurooParser.ConcurooParser.StatementListContext;
import concuroo.nodes.Node;
import concuroo.nodes.Statement;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.binaryExpression.AssignmentExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.MultiplicativeExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalAndExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalOrExpression;
import concuroo.nodes.expression.lhsExpression.LHSExpression;
import concuroo.nodes.statement.CompoundStatement;
import concuroo.nodes.statement.iterationStatement.WhileStatement;
import concuroo.nodes.statement.jumpStatement.BreakStatement;
import concuroo.nodes.statement.jumpStatement.ContinueStatement;
import concuroo.nodes.statement.jumpStatement.ReturnStatement;
import concuroo.nodes.statement.selectionStatement.IfStatement;
import org.antlr.v4.runtime.tree.ParseTree;

public class ASTVisitor extends ConcurooBaseVisitor<Node> {

  @Override
  public Node visitCompoundStatement(ConcurooParser.CompoundStatementContext ctx) {
    CompoundStatement cs = new CompoundStatement();

    // Compound statement contains statements
    if (ctx.children.size() == 3) {
      ParseTree child = ctx.getChild(1);

      // Go down the rabbit hole
      while (child instanceof StatementListContext) {
        StatementListContext castedChild = (StatementListContext) child;

        // If there are more siblings
        if (castedChild.children.size() == 2) {
          cs.addStatement((Statement) visit(castedChild.getChild(1)));
        }

        // Go down
        child = castedChild.getChild(0);
      }

      // Add the last statement (or first, if we never went down the rabbit hole)
      cs.addStatement((Statement) visit(child));
    }

    return cs;
  }

  @Override
  public Node visitIterationStatement(ConcurooParser.IterationStatementContext ctx) {
    WhileStatement wh = new WhileStatement();
    wh.setCondition((Expression) visit(ctx.getChild(2)));
    wh.setConsequence((Statement) visit(ctx.getChild(4)));
    return wh;
  }

  @Override
  public Node visitIfStatement(ConcurooParser.IfStatementContext ctx) {
    if (ctx.children.size() == 3) {
      IfStatement st = (IfStatement) visit(ctx.getChild(0));
      st.setAlternative((Statement) visit(ctx.getChild(2)));
      return st;
    }

    IfStatement st = new IfStatement();
    st.setCondition((Expression) visit(ctx.getChild(2)));
    st.setConsequence((Statement) visit(ctx.getChild(4)));

    return st;
  }

  @Override
  public Node visitJumpStatement(ConcurooParser.JumpStatementContext ctx) {
    String terminal = ctx.getChild(0).getText();
    Node node;

    switch (terminal) {
      case "return":
        node = new ReturnStatement();
        // We expect expr to be second argument, and semicolon to be third.
        if (ctx.children.size() == 3) {
          ((ReturnStatement) node).setReturnValue((Expression) visit(ctx.getChild(2)));
        }
        break;

      case "continue":
        node = new ContinueStatement();
        break;

      case "break":
        node = new BreakStatement();
        break;

      default:
        throw new RuntimeException("Such jump statement does NOT exist");
    }
    return node;
  }

  public Node visitAssignmentExpression(ConcurooParser.AssignmentExpressionContext ctx) {
    if (ctx.getChild(0) instanceof ConcurooParser.LogicalOrExpressionContext) {
      return visitLogicalOrExpression(ctx.logicalOrExpression());
    }

    LHSExpression left = (LHSExpression) visit(ctx.getChild(0));

    if (left == null) {
      throw new RuntimeException("Left side of assignment is not LHS expression");
    }

    AssignmentExpression expr = new AssignmentExpression();
    expr.setFirstOperand(left);
    expr.setSecondOperand((Expression) visit(ctx.getChild(2)));

    return expr;
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
