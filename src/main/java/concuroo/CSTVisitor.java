package concuroo;

import ConcurooParser.ConcurooBaseVisitor;
import ConcurooParser.ConcurooParser;
import ConcurooParser.ConcurooParser.ArgumentExpressionListContext;
import ConcurooParser.ConcurooParser.AssignmentExpressionContext;
import ConcurooParser.ConcurooParser.CoroutineStatementContext;
import ConcurooParser.ConcurooParser.DeclarationSpecifiersContext;
import ConcurooParser.ConcurooParser.DeclaratorContext;
import ConcurooParser.ConcurooParser.DirectDeclaratorContext;
import ConcurooParser.ConcurooParser.InitDeclaratorContext;
import ConcurooParser.ConcurooParser.ParameterListContext;
import ConcurooParser.ConcurooParser.ParameterTypeListContext;
import ConcurooParser.ConcurooParser.PostfixExpressionContext;
import ConcurooParser.ConcurooParser.RelationalExpressionContext;
import ConcurooParser.ConcurooParser.SendStatementContext;
import ConcurooParser.ConcurooParser.StartContext;
import ConcurooParser.ConcurooParser.StatementListContext;
import ConcurooParser.ConcurooParser.TranslationUnitContext;
import ConcurooParser.ConcurooParser.UnaryExpressionContext;
import concuroo.nodes.Declaration;
import concuroo.nodes.ArgumentExpressionList;
import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.FunctionDeclaration;
import concuroo.nodes.Node;
import concuroo.nodes.Program;
import concuroo.nodes.Statement;
import concuroo.nodes.Expression;
import concuroo.nodes.expression.InitializerList;
import concuroo.nodes.expression.LHSExpression;
import concuroo.nodes.expression.SizeofSpecifier;
import concuroo.nodes.expression.UnaryExpression;
import concuroo.nodes.expression.binaryExpression.AssignmentExpression;
import concuroo.nodes.expression.binaryExpression.LogicalBinaryExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.MultiplicativeExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalAndExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalGreaterEqualsExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalGreaterExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalLessEqualsExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalLessExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalOrExpression;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class CSTVisitor extends ConcurooBaseVisitor<Node> {

  private SymbolTable global;

  public CSTVisitor(SymbolTable st) {
    global = st;
  }

  @Override
  public Node visitStart(StartContext ctx) {
    Program program = new Program();

    TranslationUnitContext tuc = ctx.translationUnit();
    Stack<TranslationUnitContext> tucList = new Stack<>();
    while (tuc != null) {
      tucList.push(tuc);
      tuc = tuc.translationUnit();
    }
    while (!tucList.empty()) {
      program.add((Declaration) visit(tucList.pop().externalDeclaration()));
    }
    return program;
  }

  @Override
  public Node visitDeleteStatement(ConcurooParser.DeleteStatementContext ctx) {
    String identifier = ctx.Identifier().getText();

    VariableDeclaration def = (VariableDeclaration) global.lookup(identifier);
    if (def == null) {
      throw new RuntimeException(
          "Variable Definition was not found in Variable " + identifier);
    }

    return new DeleteStatement(new VariableExpression(identifier, def));
  }

  @Override
  public Node visitParameterDeclaration(ConcurooParser.ParameterDeclarationContext ctx) {
    VariableDeclaration declarationStatement = new VariableDeclaration();

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
            .setIdentifier(directDeclarator.getChild(0).getText());

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
    VariableDeclaration declarationStatement = new VariableDeclaration();

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
            .setIdentifier(directDeclarator.getChild(0).getText());

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

    if(global.isGlobalScope()){
      declarationStatement.setGlobal(true);
    }

    global.insert(declarationStatement);

    // Step 7: Profit?
    return declarationStatement;
  }

  @Override
  public Node visitInitializer(ConcurooParser.InitializerContext ctx) {
    if (ctx.declarationSpecifiers() != null) {
      MakeExpression exp = new MakeExpression();
      exp.setSpecifiers(parseDeclarationSpecifiers(ctx.declarationSpecifiers()));
      return exp;
    } else if (ctx.initializerList() != null) {
      return visitInitializerList(ctx.initializerList());
    } else {
      return visitChildren(ctx);
    }
  }

  @Override
  public Node visitInitializerList(ConcurooParser.InitializerListContext ctx) {
    InitializerList initList = new InitializerList();
    ConcurooParser.InitializerListContext initCtx = ctx;
    Stack<ParseTree> parseTreeStack = new Stack<>();

    while (initCtx.initializerList() != null) {
      // If there are more siblings
      parseTreeStack.push(initCtx.initializer());

      // Go down
      initCtx = initCtx.initializerList();
    }
    parseTreeStack.push(initCtx.initializer());

    while (!parseTreeStack.empty()) {
      initList.add((Expression) visit(parseTreeStack.pop()));
    }
    return initList;
  }

  @Override
  public Node visitFunctionDeclaration(ConcurooParser.FunctionDeclarationContext ctx) {
    FunctionDeclaration funcDef = new FunctionDeclaration();
    scopeIn(funcDef.getScope());
    // Step 1: Fetch all type specifiers
    if (ctx.declarationSpecifiers() != null) {
      funcDef.setSpecifiers(parseDeclarationSpecifiers(ctx.getChild(0)));
    }

    // Step 2: Check if pointer
    if (ctx.pointer() != null) {
      funcDef.setPointer(true);
    }

    // Step 3: Fetch identifier name
    funcDef.setIdentifier(ctx.Identifier().getText());
    // Step 4: Get parameters
    if (ctx.parameterTypeList() != null) {
      ParameterTypeListContext ptlc = ctx.parameterTypeList();

      ParseTree child = ptlc.getChild(0);

      // Go down the rabbit hole
      while (child instanceof ParameterListContext) {
        ParameterListContext castedChild = (ParameterListContext) child;
        // If there are more siblings
        if (castedChild.children.size() == 3) {
          funcDef.add((VariableDeclaration) visit(castedChild.getChild(2)));
        }

        // Go down
        child = castedChild.getChild(0);
      }

      // Add the last parameter (or first, if we never went down the rabbit hole)
      funcDef.add((VariableDeclaration) visit(child));
      Collections.reverse(funcDef);
    }

    funcDef.setBody((CompoundStatement) visitCompoundStatement(ctx.compoundStatement()));

    scopeOut();
    global.insert(funcDef);
    return funcDef;
  }

  @Override
  public Node visitPrimaryExpression(ConcurooParser.PrimaryExpressionContext ctx) {
    if (ctx.children.size() != 0) {
      // There's 3 children when it's a parenthesized expression.
      if (ctx.children.size() == 3) {
        // The middle child is the expression, between the opening and closing parenthesis.
        return visit(ctx.getChild(1));
      }

      TerminalNode n;
      List<TerminalNode> str;

      // If it is string
      if (((str = ctx.StringLiteral()).size()) != 0) {
        return new StringLiteral(str.get(0).getText());
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
        VariableDeclaration def = (VariableDeclaration) global.lookup(n.getText());
        if (def == null) {
          throw new RuntimeException(
              "Variable Definition was not found in Variable " + n.getText());
        }
        return new VariableExpression(n.getText(), def);
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
        cs.add((Statement) visit(parseTreeStack.pop()));
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
    LHSExpression ld = (LHSExpression) visit(ctx.unaryExpression());
    if (ld == null) {
      throw new RuntimeException("Left side of assignment is not LHS expression");
    }

    AssignmentExpression expr = new AssignmentExpression();
    expr.setFirstOperand(ld);
    expr.setSecondOperand((Expression) visit(ctx.getChild(2)));

    return expr;
  }

  @Override
  public Node visitExpressionStatement(ConcurooParser.ExpressionStatementContext ctx) {
    ExpressionStatement exprStat = new ExpressionStatement();
    exprStat.setExpression((Expression) visit(ctx.getChild(0)));
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
  public Node visitRelationalExpression(RelationalExpressionContext ctx) {
    if (ctx.children.size() == 1) {
      return visitChildren(ctx);
    }

    LogicalBinaryExpression expr;

    switch (ctx.children.get(1).getText()) {
      case ">":
        expr = new LogicalGreaterExpression();
        break;
      case "<":
        expr = new LogicalLessExpression();
        break;
      case ">=":
        expr = new LogicalGreaterEqualsExpression();
        break;
      default:
        expr = new LogicalLessEqualsExpression();
        break;
    }

    expr.setFirstOperand((Expression) visit(ctx.getChild(0)));
    expr.setSecondOperand((Expression) visit(ctx.getChild(2)));

    return expr;
  }

  @Override
  public Node visitArgumentExpressionList(ArgumentExpressionListContext ctx) {
    ArgumentExpressionList arg = new ArgumentExpressionList();
    ArgumentExpressionListContext argCtx = ctx;
    Stack<AssignmentExpressionContext> expList = new Stack<>();
    while (argCtx != null) {
      if (argCtx.assignmentExpression() != null) {
        expList.push(argCtx.assignmentExpression());
        argCtx = argCtx.argumentExpressionList();
      }
    }
    while (!expList.empty()) {
      arg.add((Expression) visit(expList.pop()));
    }
    return arg;
  }

  @Override
  public Node visitPostfixExpression(PostfixExpressionContext ctx) {

    if (ctx.children.size() > 1 && ctx.getChild(1).getText().equals("(")) {
      FunctionExpression functionExpression = new FunctionExpression(ctx.getChild(0).getText());
      //((FunctionDeclaration)global.lookup(ctx.getChild(0).getText())).getIdentifier());
      if (ctx.argumentExpressionList() != null) {
        functionExpression
                .setParameterList((ArgumentExpressionList) visit(ctx.argumentExpressionList()));
      }
      return functionExpression;
    } else if (ctx.children.size() > 1 && ctx.getChild(1).getText().equals("[")) {
      ArrayExpression arrExp = new ArrayExpression();

      //This is flawed, since it doesn't support (*a)[2]
      arrExp.setFirstOperand(
              (Expression) visitPostfixExpression(ctx.postfixExpression())
      );

      arrExp.setSecondOperand((Expression) visitExpression(ctx.expression()));

      return arrExp;
    } else if (ctx.children.size() > 1 && ctx.getChild(1).getText().equals("++")) {
      IncrementDecrementExpression incExp = new IncrementDecrementExpression();
      incExp.setFirstOperand((Expression) visit(ctx.getChild(0)));
      incExp.setOperator("++");
      incExp.setPrefix(false);
      return incExp;
    } else if (ctx.children.size() > 1 && ctx.getChild(1).getText().equals("--")) {
      IncrementDecrementExpression decExp = new IncrementDecrementExpression();
      decExp.setFirstOperand((Expression) visit(ctx.getChild(0)));
      decExp.setOperator("--");
      decExp.setPrefix(false);

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
    List<String> stringList = new ArrayList<>();
    stringList.add(ctx.getChild(1).getText());
    DeclarationSpecifierList decList = new DeclarationSpecifierList(stringList);
    expr.setSpecifiers(decList);
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
  public Node visitUnaryExpression(UnaryExpressionContext ctx) {
    if (ctx.postfixExpression() != null) {
      return visitPostfixExpression(ctx.postfixExpression());
    }

    if (ctx.unaryOperator() != null) {
      String operator = ctx.unaryOperator().getText();
      Expression expr = (Expression) visit(ctx.getChild(1));
      UnaryExpression n;

      switch (operator) {
        case "--":
        case "++":
          n = new IncrementDecrementExpression();
          ((IncrementDecrementExpression) n).setPrefix(true);
          n.setFirstOperand(expr);
          ((IncrementDecrementExpression) n).setOperator(operator);
          break;
        case "+":
        case "-":
          n = new AdditivePrefixExpression(expr, operator);
          break;
        case "*":
          n = new DereferenceExpression(expr);
          break;
        case "&":
          n = new AddressOfExpression(expr);
          break;
        case "!":
          n = new NegationExpression(expr);
          break;
        case "<-":
          n = new PipeExpression(expr);
          break;
        default:
          throw new RuntimeException("The operator for the unaryexpression wasn't known.");
      }
      return n;
    }

    if (ctx.declarationSpecifiers() != null) {
      List<ParseTree> operators = ctx.declarationSpecifiers().children;
      List<String> list = new ArrayList<>();
      for (ParseTree operator : operators) {
        list.add(operator.getText());
      }

      DeclarationSpecifierList decList = new DeclarationSpecifierList(list);
      return new SizeofSpecifier(decList);
    }

    if (ctx.getChild(0).getText().equals("sizeof")) {
      Expression expr = (Expression) visitUnaryExpression(ctx.unaryExpression());
      return new SizeofExpression(expr);
    }

    throw new RuntimeException("UnaryExpression did not get a valid input");
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
