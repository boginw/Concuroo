package concuroo;

import ConcurooParser.ConcurooBaseVisitor;
import ConcurooParser.ConcurooParser;
import ConcurooParser.ConcurooParser.ArgumentExpressionListContext;
import ConcurooParser.ConcurooParser.CoroutineStatementContext;
import ConcurooParser.ConcurooParser.PostfixExpressionContext;
import ConcurooParser.ConcurooParser.RelationalExpressionContext;
import ConcurooParser.ConcurooParser.SendStatementContext;
import ConcurooParser.ConcurooParser.StartContext;
import ConcurooParser.ConcurooParser.UnaryExpressionContext;
import concuroo.nodes.ArgumentExpressionList;
import concuroo.nodes.FunctionDeclaration;
import concuroo.nodes.Node;
import concuroo.nodes.Program;
import concuroo.nodes.expression.InitializerList;
import concuroo.nodes.expression.SizeofSpecifier;
import concuroo.nodes.expression.binaryExpression.AssignmentExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.MultiplicativeExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalAndExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalOrExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.logicalRelationalExpression.LogicalGreaterEqualsExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.logicalRelationalExpression.LogicalGreaterExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.logicalRelationalExpression.LogicalLessEqualsExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.logicalRelationalExpression.LogicalLessExpression;
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
import concuroo.nodes.statement.SendStatement;
import concuroo.nodes.statement.VariableDeclaration;
import concuroo.nodes.statement.jumpStatement.BreakStatement;
import concuroo.nodes.statement.jumpStatement.ContinueStatement;
import concuroo.nodes.statement.jumpStatement.ReturnStatement;
import concuroo.nodes.statement.selectionStatement.IfStatement;
import concuroo.nodes.statement.selectionStatement.WhileStatement;
import concuroo.symbol.SymbolTable;

public class CSTVisitor extends ConcurooBaseVisitor<Node> {

  private SymbolTable global;

  public CSTVisitor(SymbolTable st) {
    global = st;
  }

  public SymbolTable getGlobal() {
    return global;
  }

  public void scopeIn(SymbolTable scope) {
    scope.setParent(global);
    global = scope;
  }

  public void scopeOut() {
    global = global.getParent();
  }

  @Override
  public Node visitStart(StartContext ctx) {
    return new Program().parse(ctx, this);
  }

  @Override
  public Node visitDeleteStatement(ConcurooParser.DeleteStatementContext ctx) {
    return new DeleteStatement().parse(ctx, this);
  }

  @Override
  public Node visitParameterDeclaration(ConcurooParser.ParameterDeclarationContext ctx) {
    return new VariableDeclaration().parse(ctx, this);
  }


  @Override
  public Node visitDeclarationStatement(ConcurooParser.DeclarationStatementContext ctx) {
    return new VariableDeclaration().parse(ctx, this);
  }

  @Override
  public Node visitInitializer(ConcurooParser.InitializerContext ctx) {
    if (ctx.declarationSpecifiers() != null) {
      return new MakeExpression().parse(ctx, this);
    } else if (ctx.initializerList() != null) {
      return visitInitializerList(ctx.initializerList());
    } else {
      return visitChildren(ctx);
    }
  }

  @Override
  public Node visitInitializerList(ConcurooParser.InitializerListContext ctx) {
    return new InitializerList().parse(ctx, this);
  }

  @Override
  public Node visitFunctionDeclaration(ConcurooParser.FunctionDeclarationContext ctx) {
    return new FunctionDeclaration().parse(ctx, this);
  }

  @Override
  public Node visitPrimaryExpression(ConcurooParser.PrimaryExpressionContext ctx) {
    if (ctx.children.size() != 0) {
      // There's 3 children when it's a parenthesized expression.
      if (ctx.children.size() == 3) {
        // The middle child is the expression, between the opening and closing parenthesis.
        return visit(ctx.getChild(1));
      }
      // If it is string
      else if ((ctx.StringLiteral().size()) != 0) {
        return new StringLiteral().parse(ctx, this);
      }
      // If it is char
      else if (ctx.CharLiteral() != null) {
        return new CharLiteral().parse(ctx, this);
      }
      // If it is double
      else if (ctx.DoubleLiteral() != null) {
        return new FloatLiteral().parse(ctx, this);
      }
      // If it is int
      else if (ctx.Number() != null) {
        return new IntLiteral().parse(ctx, this);
      }
      // If it is bool
      else if (ctx.boolLiteral() != null) {
        return new BoolLiteral().parse(ctx, this);
      }
      // If it is identifier
      else if (ctx.Identifier() != null) {
        return new VariableExpression().parse(ctx, this);
      }
    }
    throw new RuntimeException("No recognized primary expression");
  }

  public Node visitCompoundStatement(ConcurooParser.CompoundStatementContext ctx) {
    return new CompoundStatement().parse(ctx, this);
  }

  @Override
  public Node visitIterationStatement(ConcurooParser.IterationStatementContext ctx) {
    return new WhileStatement().parse(ctx, this);
  }

  @Override
  public Node visitIfStatement(ConcurooParser.IfStatementContext ctx) {
    return new IfStatement().parse(ctx, this);
  }

  @Override
  public Node visitJumpStatement(ConcurooParser.JumpStatementContext ctx) {
    String terminal = ctx.getChild(0).getText();

    switch (terminal) {
      case "return":
        return new ReturnStatement().parse(ctx, this);
      case "continue":
        return new ContinueStatement().parse(ctx, this);
      case "break":
        return new BreakStatement().parse(ctx, this);
      default:
        throw new RuntimeException("Such jump statement does NOT exist");
    }
  }

  public Node visitAssignmentExpression(ConcurooParser.AssignmentExpressionContext ctx) {
    return new AssignmentExpression().parse(ctx, this);
  }

  @Override
  public Node visitExpressionStatement(ConcurooParser.ExpressionStatementContext ctx) {
    return new ExpressionStatement().parse(ctx, this);
  }


  @Override
  public Node visitAdditiveExpression(ConcurooParser.AdditiveExpressionContext ctx) {
    return new AdditiveExpression().parse(ctx, this);
  }

  @Override
  public Node visitMultiplicativeExpression(ConcurooParser.MultiplicativeExpressionContext ctx) {
    return new MultiplicativeExpression().parse(ctx, this);
  }

  @Override
  public Node visitLogicalAndExpression(ConcurooParser.LogicalAndExpressionContext ctx) {
    return new LogicalAndExpression().parse(ctx, this);
  }

  @Override
  public Node visitLogicalOrExpression(ConcurooParser.LogicalOrExpressionContext ctx) {
    return new LogicalOrExpression().parse(ctx, this);
  }

  @Override
  public Node visitEqualityExpression(ConcurooParser.EqualityExpressionContext ctx) {
    return new LogicalEqualityExpression().parse(ctx, this);
  }

  @Override
  public Node visitRelationalExpression(RelationalExpressionContext ctx) {
    if (ctx.children.size() == 1) {
      return visitChildren(ctx);
    }

    switch (ctx.children.get(1).getText()) {
      case ">":
        return new LogicalGreaterExpression().parse(ctx, this);
      case "<":
        return new LogicalLessExpression().parse(ctx, this);
      case ">=":
        return new LogicalGreaterEqualsExpression().parse(ctx, this);
      case "<=":
        return new LogicalLessEqualsExpression().parse(ctx, this);
      default:
        throw new RuntimeException("No logical expression of that form");
    }
  }

  @Override
  public Node visitArgumentExpressionList(ArgumentExpressionListContext ctx) {
    return new ArgumentExpressionList().parse(ctx, this);
  }

  @Override
  public Node visitPostfixExpression(PostfixExpressionContext ctx) {

    if (ctx.children.size() > 1) {

      String firstToken = ctx.getChild(1).getText();

      switch (firstToken) {
        case "(":
          return new FunctionExpression().parse(ctx, this);
        case "[":
          return new ArrayExpression().parse(ctx, this);
        case "++":
        case "--":
          return new IncrementDecrementExpression().parse(ctx, this);
      }

    } else if (ctx.primaryExpression() != null) {
      return visitPrimaryExpression(ctx.primaryExpression());
    }

    return visitChildren(ctx);
  }

  @Override
  public Node visitCastExpression(ConcurooParser.CastExpressionContext ctx) {
    return new CastExpression().parse(ctx, this);
  }

  @Override
  public Node visitCoroutineStatement(CoroutineStatementContext ctx) {
    return new CoroutineStatement().parse(ctx, this);
  }

  @Override
  public Node visitUnaryExpression(UnaryExpressionContext ctx) {

    // Check if actually postfix expression
    if (ctx.postfixExpression() != null) {

      return visitPostfixExpression(ctx.postfixExpression());

    } else if (ctx.unaryOperator() != null) {

      // Prefix expression confirmed
      switch (ctx.unaryOperator().getText()) {
        case "--":
        case "++":
          return new IncrementDecrementExpression().parse(ctx, this);
        case "+":
        case "-":
          return new AdditivePrefixExpression().parse(ctx, this);
        case "*":
          return new DereferenceExpression().parse(ctx, this);
        case "&":
          return new AddressOfExpression().parse(ctx, this);
        case "!":
          return new NegationExpression().parse(ctx, this);
        case "<-":
          return new PipeExpression().parse(ctx, this);
        default:
          throw new RuntimeException("The operator for the unary expression wasn't known.");
      }

    } else if (ctx.declarationSpecifiers() != null) {

      // It's actually a sizeof operator with specifiers
      return new SizeofSpecifier().parse(ctx, this);

    } else if (ctx.getChild(0).getText().equals("sizeof")) {

      // It's actually a sizeof operator with variables
      return new SizeofExpression().parse(ctx, this);

    } else {

      // We don't know what it is
      throw new RuntimeException("UnaryExpression did not get a valid input");

    }
  }

  @Override
  public Node visitSendStatement(SendStatementContext ctx) {
    return new SendStatement().parse(ctx, this);
  }

}
