package concuroo;

import ConcurooParser.ConcurooBaseVisitor;
import ConcurooParser.ConcurooParser;
import ConcurooParser.ConcurooParser.CoroutineStatementContext;
import ConcurooParser.ConcurooParser.DeclarationSpecifiersContext;
import ConcurooParser.ConcurooParser.DeclaratorContext;
import ConcurooParser.ConcurooParser.DirectDeclaratorContext;
import ConcurooParser.ConcurooParser.InitDeclaratorContext;
import ConcurooParser.ConcurooParser.ParameterListContext;
import ConcurooParser.ConcurooParser.ParameterTypeListContext;
import ConcurooParser.ConcurooParser.PostfixExpressionContext;
import ConcurooParser.ConcurooParser.StartContext;
import concuroo.nodes.FunctionDefinition;
import ConcurooParser.ConcurooParser.StatementListContext;
import ConcurooParser.ConcurooParser.TranslationUnitContext;
import ConcurooParser.ConcurooParser.ArgumentExpressionListContext;
import concuroo.nodes.Node;
import concuroo.nodes.Program;
import concuroo.nodes.Statement;
import concuroo.nodes.TranslationUnit;
import concuroo.nodes.expression.ArgumentExpressionList;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.binaryExpression.AssignmentExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.MultiplicativeExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalAndExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalOrExpression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;
import concuroo.nodes.expression.literalExpression.BoolLiteral;
import concuroo.nodes.expression.literalExpression.CharLiteral;
import concuroo.nodes.expression.literalExpression.FloatLiteral;
import concuroo.nodes.expression.literalExpression.IntLiteral;
import concuroo.nodes.expression.literalExpression.StringLiteral;
import concuroo.nodes.expression.unaryExpression.ArrayExpression;
import concuroo.nodes.expression.unaryExpression.CastExpression;
import concuroo.nodes.expression.unaryExpression.FunctionExpression;
import concuroo.nodes.expression.unaryExpression.IncrementDecrementExpression;
import concuroo.nodes.statement.CompoundStatement;
import concuroo.nodes.statement.ExpressionStatement;
import concuroo.nodes.statement.VariableDefinition;
import concuroo.nodes.statement.iterationStatement.WhileStatement;
import concuroo.nodes.statement.jumpStatement.BreakStatement;
import concuroo.nodes.statement.jumpStatement.ContinueStatement;
import concuroo.nodes.statement.jumpStatement.ReturnStatement;
import concuroo.nodes.statement.selectionStatement.IfStatement;
import concuroo.symbol.SymbolTable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;


public class ASTVisitor extends ConcurooBaseVisitor<Node> {

  private SymbolTable global;

  public ASTVisitor(SymbolTable st) {
    global = st;
  }

  @Override
  public Node visitStart(StartContext ctx) {
    Program program = new Program();
    program.setTranslationUnit((TranslationUnit) visit(ctx.translationUnit()));
    return program;
  }

  @Override
  public Node visitTranslationUnit(TranslationUnitContext ctx) {
    TranslationUnitContext tuc = ctx;
    Stack<TranslationUnitContext> tucList = new Stack<>() ;
    TranslationUnit translationUnit = new TranslationUnit();
    while(tuc != null)
    {
      tucList.push(tuc);
      tuc = tuc.translationUnit();
    }
    while (!tucList.empty()){
      translationUnit.addUnit(visit(tucList.pop().externalDeclaration()));
    }
    return translationUnit;
  }

  @Override
  public Node visitParameterDeclaration(ConcurooParser.ParameterDeclarationContext ctx) {
    VariableDefinition declarationStatement = new VariableDefinition();

    // Step 1: Down the rabbit hole
    DeclaratorContext declarator = ctx.declarator();
    DirectDeclaratorContext directDeclarator = declarator.directDeclarator();

    // Step 2: Fetch all type specifiers
    if (ctx.getChild(0) instanceof DeclarationSpecifiersContext) {
      declarationStatement.setSpecifiers(parseDeclarationSpecifiers(ctx.getChild(0)));
    }

    // Step 3: Check if it is a pointer
    if (declarator.children.size() > 1) {
      declarationStatement.setPointer(true);
    }

    // Step 4: Find the identifier
    declarationStatement
        .setIdentifier(new VariableExpression(directDeclarator.getChild(0).getText()));

    // Step 5: Check if it is an array declaration
    if (directDeclarator.children.size() > 1) {
      declarationStatement.setArraySize(
          (Expression) visitAssignmentExpression(directDeclarator.assignmentExpression())
      );
    }

    declarationStatement.setParam(true);

    return declarationStatement;
  }

  @Override
  public Node visitDeclarationStatement(ConcurooParser.DeclarationStatementContext ctx) {
    VariableDefinition declarationStatement = new VariableDefinition();

    // Step 1: Down the rabbit hole
    InitDeclaratorContext initDeclarator = (InitDeclaratorContext) ctx.getChild(1);
    DeclaratorContext declarator = initDeclarator.declarator();
    DirectDeclaratorContext directDeclarator = declarator.directDeclarator();

    // Step 2: Fetch all type specifiers
    if (ctx.getChild(0) instanceof DeclarationSpecifiersContext) {
      declarationStatement.setSpecifiers(parseDeclarationSpecifiers(ctx.getChild(0)));
    }

    // Step 3: Check if it is a pointer
    if (declarator.children.size() > 1) {
      declarationStatement.setPointer(true);
    }

    // Step 4: Find the identifier
    declarationStatement
        .setIdentifier(new VariableExpression(directDeclarator.getChild(0).getText()));

    // Step 5: Check if it is an array declaration
    if (directDeclarator.children.size() > 1) {
      declarationStatement.setArraySize(
          (Expression) visitAssignmentExpression(directDeclarator.assignmentExpression())
      );
    }

    // Step 6: Check if it has initializer?
    if (initDeclarator.children.size() > 1) {
      declarationStatement.setInitializer(
          (Expression) visitInitializer(initDeclarator.initializer())
      );
    }

    global.insert(declarationStatement);

    // Step 7: Profit?
    return declarationStatement;
  }

  @Override
  public Node visitFunctionDefinition(ConcurooParser.FunctionDefinitionContext ctx) {
    FunctionDefinition funcDef = new FunctionDefinition();

    // Step 1: Fetch all type specifiers
    if (ctx.declarationSpecifiers() != null) {
      funcDef.setSpecifiers(parseDeclarationSpecifiers(ctx.getChild(0)));
    }

    // Step 2: Check if pointer
    if (ctx.pointer() != null) {
      funcDef.setPointer(true);
    }

    // Step 3: Fetch identifier name
    funcDef.setIdentifier(new FunctionExpression(ctx.Identifier().getText()));
    // Step 4: Get parameters
    if (ctx.parameterTypeList() != null) {
      ParameterTypeListContext ptlc = ctx.parameterTypeList();

      ParseTree child = ptlc.getChild(0);

      // Go down the rabbit hole
      while (child instanceof ParameterListContext) {
        ParameterListContext castedChild = (ParameterListContext) child;
        // If there are more siblings
        if (castedChild.children.size() == 3) {
          funcDef.addParameter((VariableDefinition) visit(castedChild.getChild(2)));
        }

        // Go down
        child = castedChild.getChild(0);
      }

      // Add the last parameter (or first, if we never went down the rabbit hole)
      funcDef.addParameter((VariableDefinition) visit(child));
      Collections.reverse(funcDef.getParameters());
    }

    funcDef.setBody((CompoundStatement) visitCompoundStatement(ctx.compoundStatement()));
    global.insert(funcDef);
    return funcDef;
  }

  @Override
  public Node visitPrimaryExpression(ConcurooParser.PrimaryExpressionContext ctx) {
    if (ctx.children.size() != 0) {
      if (ctx.children.size() == 3) {
        return visit(ctx.getChild(0));
      }

      TerminalNode n;
      List<TerminalNode> str;

      // If it is string
      if (((str = ctx.StringLiteral()).size()) != 0) {
        return new StringLiteral(str.toString());
      }
      // If it is char
      else if ((n = ctx.CharLiteral()) != null) {
        return new CharLiteral(n.getSymbol().getText());
      }
      // If it is double
      else if ((n = ctx.DoubleLiteral()) != null) {
        return new FloatLiteral(Double.valueOf(n.getSymbol().getText()));
      }
      // If it is int
      else if ((n = ctx.Number()) != null) {
        return new IntLiteral(Integer.valueOf(n.getSymbol().getText()));
      }
      // If it is bool
      else if (ctx.boolLiteral() != null) {
        if (ctx.boolLiteral().getChild(0).toString().equals("true")) {
          return new BoolLiteral(true);
        }
        return new BoolLiteral(false);
      }
      // If it is identifier
      else if ((n = ctx.Identifier()) != null) {
        return global.lookup(n.getText());
      }
    }
    throw new RuntimeException("No recognized primary expression");
  }

  public Node visitCompoundStatement(ConcurooParser.CompoundStatementContext ctx) {
    CompoundStatement cs = new CompoundStatement();

    // scope in
    scopeIn(cs.getScope());

    // Compound statement contains statements
    if (ctx.children.size() == 3) {
      Stack<ParseTree> parseTreeStack = new Stack<>();
      ParseTree child = ctx.getChild(1);

      // Go down the rabbit hole
      while (child instanceof StatementListContext) {
        StatementListContext castedChild = (StatementListContext) child;

        // If there are more siblings
        if (castedChild.children.size() == 2) {
          parseTreeStack.push(castedChild.getChild(1));
        }

        // Go down
        child = castedChild.getChild(0);
      }

      // Add the last statement (or first, if we never went down the rabbit hole)
      parseTreeStack.push(child);

      // empty the stack
      while (!parseTreeStack.empty()) {
        cs.addStatement((Statement) visit(parseTreeStack.pop()));
      }
    }

    // scope out
    scopeOut();

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
          ((ReturnStatement) node).setReturnValue((Expression) visitExpression(ctx.expression()));
        } else if (ctx.children.size() == 1) {
          throw new RuntimeException("Missing semicolon");
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

    VariableExpression left = (VariableExpression) visit(ctx.getChild(0));

    if (left == null) {
      throw new RuntimeException("Left side of assignment is not LHS expression");
    }

    AssignmentExpression expr = new AssignmentExpression();
    expr.setFirstOperand(left);
    expr.setSecondOperand((Expression) visit(ctx.getChild(2)));

    return expr;
  }

  @Override
  public Node visitExpressionStatement(ConcurooParser.ExpressionStatementContext ctx) {
    ExpressionStatement exprStat = new ExpressionStatement();
    exprStat.setExpr((Expression) visit(ctx.getChild(0)));
    return exprStat;
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

    return expr;
  }

  @Override
  public Node visitLogicalOrExpression(ConcurooParser.LogicalOrExpressionContext ctx) {
    if (ctx.getChild(0) instanceof ConcurooParser.LogicalAndExpressionContext) {
      return visitLogicalAndExpression(ctx.logicalAndExpression());
    }

    LogicalOrExpression expr = new LogicalOrExpression();
    expr.setFirstOperand((Expression) visit(ctx.getChild(0)));
    expr.setSecondOperand((Expression) visit(ctx.getChild(2)));

    return expr;
  }

  @Override
  public Node visitEqualityExpression(ConcurooParser.EqualityExpressionContext ctx) {
    if (ctx.getChild(0) instanceof ConcurooParser.RelationalExpressionContext) {
      return visitRelationalExpression(ctx.relationalExpression());
    }

    LogicalEqualityExpression expr = new LogicalEqualityExpression();
    expr.setFirstOperand((Expression) visit(ctx.getChild(0)));
    expr.setSecondOperand((Expression) visit(ctx.getChild(2)));

    return expr;
  }

  @Override
  public Node visitArgumentExpressionList(ArgumentExpressionListContext ctx) {
    ArgumentExpressionList arg = new ArgumentExpressionList();
    ArgumentExpressionListContext argCtx = ctx;
    while (argCtx != null) {
      if (argCtx.assignmentExpression() != null) {
        arg.addParam(visit(argCtx.assignmentExpression()));
      }
      argCtx = argCtx.argumentExpressionList();
      }
    return arg;
  }

  @Override
  public Node visitPostfixExpression(PostfixExpressionContext ctx) {

    if (ctx.children.size() > 1 && ctx.getChild(1).getText().equals("(")) {
      FunctionExpression functionExpression = new FunctionExpression(global.lookup(ctx.getChild(0).getText()).getLiteral());
      if (ctx.argumentExpressionList() != null) {
        functionExpression
            .setParameterList((ArgumentExpressionList) visit(ctx.argumentExpressionList()));
      }
      return functionExpression;
    } else if (ctx.children.size() > 1 && ctx.getChild(1).getText().equals("[")) {
      ArrayExpression arrExp = new ArrayExpression();
      arrExp.setFirstOperand(new VariableExpression(ctx.getChild(0).getText()));
      arrExp.setOperator((Expression) visitExpression(ctx.expression()));
      return arrExp;
    } else if (ctx.children.size() > 1 && ctx.getChild(1).getText().equals("++")) {
      IncrementDecrementExpression incExp = new IncrementDecrementExpression();
      incExp.setFirstOperand((Expression) visit(ctx.getChild(0)));
      incExp.setOperator("++");
      return incExp;
    } else if (ctx.children.size() > 1 && ctx.getChild(1).getText().equals("--")) {
      IncrementDecrementExpression decExp = new IncrementDecrementExpression();
      decExp.setFirstOperand((Expression) visit(ctx.getChild(0)));
      decExp.setOperator("--");
      return decExp;
    } else if (ctx.primaryExpression() != null) {
      return visitPrimaryExpression(ctx.primaryExpression());
    }
    return visitChildren(ctx);
  }

  @Override
  public Node visitCastExpression(ConcurooParser.CastExpressionContext ctx) {
    if (ctx.getChild(0) instanceof ConcurooParser.UnaryExpressionContext) {
      return visitUnaryExpression(ctx.unaryExpression());
    }

    CastExpression expr = new CastExpression();
    expr.setOperator(ctx.getChild(1).getText());
    expr.setFirstOperand((Expression) visit(ctx.getChild(3)));

    return expr;
  }

  @Override
  public Node visitCoroutineStatement(CoroutineStatementContext ctx) {
    CoroutineStatement stmt = new CoroutineStatement();
    stmt.setExpression((Expression) visitExpression(ctx.expression()));
    return stmt;
  }

  @Override
  public Node visitSendStatement(SendStatementContext ctx) {
    SendStatement stmt = new SendStatement();
    stmt.setFirstOperand((Expression) visit(ctx.getChild(0)));
    stmt.setSecondOperand((Expression) visit(ctx.getChild(2)));
    return stmt;
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

  private DeclarationSpecifierList parseDeclarationSpecifiers(ParseTree parseTree) {
    List<String> specifiers = new ArrayList<>();
    if (parseTree instanceof DeclarationSpecifiersContext) {
      DeclarationSpecifiersContext child = (DeclarationSpecifiersContext) parseTree;

      for (ParseTree specifier : child.children) {
        specifiers.add(specifier.getText());
      }

      return new DeclarationSpecifierList(specifiers);
    }
    return null;
  }

  private void scopeIn(SymbolTable scope) {
    scope.setParent(global);
    global = scope;
  }

  private void scopeOut() {
    global = global.getParent();
  }
}
