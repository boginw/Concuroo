package concuroo;

import concuroo.exceptions.ExpressionNotFoundException;
import concuroo.nodes.ArgumentExpressionList;
import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.Expression;
import concuroo.nodes.FunctionDeclaration;
import concuroo.nodes.Node;
import concuroo.nodes.Program;
import concuroo.nodes.Statement;
import concuroo.nodes.expression.InitializerList;
import concuroo.nodes.expression.SizeofSpecifier;
import concuroo.nodes.expression.UnaryExpression;
import concuroo.nodes.expression.binaryExpression.ArithmeticBinaryExpression;
import concuroo.nodes.expression.binaryExpression.AssignmentExpression;
import concuroo.nodes.expression.binaryExpression.BinaryExpression;
import concuroo.nodes.expression.binaryExpression.LogicalBinaryExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.MultiplicativeExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalAndExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalOrExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalRelantionalExpression;
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
import concuroo.nodes.statement.RawStatement;
import concuroo.nodes.statement.SendStatement;
import concuroo.nodes.statement.VariableDeclaration;
import concuroo.nodes.statement.jumpStatement.BreakStatement;
import concuroo.nodes.statement.jumpStatement.ContinueStatement;
import concuroo.nodes.statement.jumpStatement.ReturnStatement;
import concuroo.nodes.statement.selectionStatement.IfStatement;
import concuroo.nodes.statement.selectionStatement.WhileStatement;
import concuroo.symbol.SymbolTable;
import concuroo.types.ReturnType;
import concuroo.types.TypeModifier;
import concuroo.types.TypeRules;
import concuroo.types.Types;

public class ASTVisitor implements Visitor {

  private SymbolTable global;

  public ASTVisitor(SymbolTable st) {
    this.global = st;
  }

  /**
   * Visits the crux, namely the program
   *
   * @param program The program to check
   */
  @Override
  public void visit(Program program) {

    // We enforce the inclusion of both setup and loop
    Node setup = global.lookup("setup");
    Node loop = global.lookup("loop");

    if (!(setup instanceof FunctionDeclaration) || !(loop instanceof FunctionDeclaration)) {
      throw new RuntimeException("Could not locate setup-function and/ or loop-function.");
    }

    // Loop is a coroutine
    ((FunctionDeclaration) loop).setCoroutined(true);

    for (Node node : program) {
      if (node instanceof FunctionDeclaration) {
        visit((FunctionDeclaration) node);
      } else if (node instanceof VariableDeclaration) {
        visit((VariableDeclaration) node);
      }
    }
  }

  /**
   * Visits a function definition
   *
   * @param funcDef The function definition
   */
  @Override
  public void visit(FunctionDeclaration funcDef) {
    // Visit all the parameters
    for (VariableDeclaration vd : funcDef) {
      visit(vd);
    }

    visit(funcDef.getBody());

    ReturnType returnType = TypeRules.determineDeclarationSpecifier(funcDef.getSpecifiers());

    checkReturnValidityFunctionDefinition(funcDef.getBody(), returnType);
  }

  /**
   * Visits a statement
   *
   * @param stm The statement to visit
   */
  @Override
  public void visit(Statement stm) {
    if (stm instanceof ExpressionStatement) {
      visit((ExpressionStatement) stm);
    } else if (stm instanceof VariableDeclaration) {
      visit((VariableDeclaration) stm);
    } else if (stm instanceof IfStatement) {
      visit((IfStatement) stm);
    } else if (stm instanceof CoroutineStatement) {
      visit((CoroutineStatement) stm);
    } else if (stm instanceof DeleteStatement) {
      visit((DeleteStatement) stm);
    }
  }

  @Override
  public void visit(BreakStatement breakStatement) {
    // No type to decorate, hence empty
  }

  @Override
  public void visit(ContinueStatement continueStatement) {
    // No type to decorate, hence empty
  }

  @Override
  public void visit(ReturnStatement returnStatement) {
    // TODO: this
  }

  /**
   * Visits a compound statement
   *
   * @param cpmStm The compound statement
   */
  @Override
  public void visit(CompoundStatement cpmStm) {
    for (Statement stm : cpmStm) {
      visit(stm);
    }
  }

  /**
   * Visits a delete statement
   *
   * @param stm The delete statement to visit
   */
  @Override
  public void visit(DeleteStatement stm) {
    VariableExpression var = stm.getVariable();
    visit(var);

    if ((var.getReturnType().prefix.getLeft() != TypeModifier.CHAN) &&
        (var.getReturnType().prefix.getRight() != TypeModifier.CHAN)) {
      throw new RuntimeException(
          "Can only delete chan types. Deleted type is: " + var.getReturnType().toString());
    }
  }

  /**
   * Visits a coroutine statement
   *
   * @param stm The coroutine statement to visit
   */
  @Override
  public void visit(CoroutineStatement stm) {
    Expression expr = stm.getExpression();
    visit(stm.getExpression());

    if (!(expr instanceof FunctionExpression) || expr.getReturnType().type != Types.VOID) {
      throw new RuntimeException(
          "Only void functions can be called in go-routines " + stm.getClass().getName());
    }

    ((FunctionExpression) expr).getDefinition().setCoroutined(true);
  }

  /**
   * Visits an if statement
   *
   * @param stm The if statement to visit
   */
  @Override
  public void visit(IfStatement stm) {
    Expression condition = stm.getCondition();

    visit(stm.getCondition());
    visit(stm.getConsequence());

    if (stm.getAlternative() != null) {
      visit(stm.getAlternative());
    }

    if (condition.getReturnType().type != Types.BOOL) {
      throw new RuntimeException(
          "The condition was not a boolean expression in " + stm.getClass().getName());
    }
  }

  @Override
  public void visit(WhileStatement whileStatement) {
    Expression condition = whileStatement.getCondition();

    visit(whileStatement.getCondition());
    visit(whileStatement.getConsequence());

    if (condition.getReturnType().type != Types.BOOL) {
      throw new RuntimeException(
          "The condition was not a boolean expression in " + whileStatement.getClass().getName());
    }
  }

  /**
   * Visits an expression statement
   *
   * @param exprStm Expression statement
   */
  @Override
  public void visit(ExpressionStatement exprStm) {
    if (exprStm.getExpression() instanceof FunctionExpression) {
      visit((FunctionExpression) exprStm.getExpression());
    } else if (exprStm.getExpression() instanceof BinaryExpression) {
      visit((BinaryExpression) exprStm.getExpression());
    } else if (exprStm.getExpression() instanceof UnaryExpression) {
      visit((UnaryExpression) exprStm.getExpression());
    } else if (exprStm.getExpression() instanceof SizeofSpecifier) {
      visit((SizeofSpecifier) exprStm.getExpression());
    } else {
      throw new ExpressionNotFoundException("Expression was not found: " + exprStm.getLiteral());
    }
  }

  @Override
  public void visit(SendStatement sendStatement) {
    // TODO: this
  }

  /**
   * Visits a declaration statement
   *
   * @param stm The declaration statement to visit
   */
  @Override
  public void visit(VariableDeclaration stm) {
    ReturnType returnType = TypeRules.determineDeclarationSpecifier(stm.getSpecifiers());
    stm.setReturnType(returnType);

    if (stm.getInitializer() != null) {

      // Check if variable is array, and if so, visits the initializer if it exists
      if (stm.isArray()) {
        if (stm.getInitializer() instanceof InitializerList) {
          visit((InitializerList) stm.getInitializer(), returnType);
        } else {
          throw new RuntimeException("Trying to assign "
              + stm.getInitializer().getLiteral() + " which isn't an array.");
        }
      }

      // visit the initializer
      visit(stm.getInitializer());

      TypeRules.throwIfInvalidDeclaration(returnType, stm.getInitializer());

      // string assignment
      if (stm.getInitializer() instanceof StringLiteral && !stm.isPointer()) {
        throw new RuntimeException("Trying to assign a string to an illegal type.");
      }

      // TODO: explain this
      if (TypeRules.isPrecedenceCorrect(
          stm.getReturnType(), stm.getInitializer().getReturnType())) {
        return;
      } else {
        System.out.print("Trying to assign " +
            stm.getInitializer().getReturnType() + " to " + stm.getReturnType() +
            ", but this will lose precision.");
      }

      if (stm.getInitializer() instanceof MakeExpression
          && stm.getReturnType().prefix.getLeft() != TypeModifier.CHAN) {
        throw new RuntimeException(
            "Tried to assign a channel to a variable that isn't a channel.");
      }
    }
  }

  @Override
  public void visit(RawStatement rawStatement) {
    // Ignore this
  }

  @Override
  public void visit(ArgumentExpressionList expressionList) {
    // TODO: this
  }



  /**
   * Visits declartion specifiers
   *
   * @param specifiers The specifiers to visit
   */
  @Override
  public void visit(DeclarationSpecifierList specifiers) {
    TypeRules.determineDeclarationSpecifier(specifiers);
  }

  @Override
  public void visit(Node node) {
    Visitor.concreteify(node, this);
  }

  /**
   * Visits an expression
   *
   * @param expr The expression to visit
   */
  @Override
  public void visit(Expression expr) {
    Visitor.concreteify(expr, this);
  }

  @Override
  public void visit(BoolLiteral boolLiteral) {
    // Type already assigned, as it's static, thereby this is ignored
  }

  @Override
  public void visit(IntLiteral intLiteral) {
    // Type already assigned, as it's static, thereby this is ignored
  }

  @Override
  public void visit(CharLiteral charLiteral) {
    // Type already assigned, as it's static, thereby this is ignored
  }

  @Override
  public void visit(FloatLiteral floatLiteral) {
    // Type already assigned, as it's static, thereby this is ignored
  }

  @Override
  public void visit(StringLiteral stringLiteral) {
    // Type already assigned, as it's static, thereby this is ignored
  }

  /**
   * Visits a unary expression
   *
   * @param expr The unary expression
   */
  @Override
  public void visit(UnaryExpression expr) {
    if (expr instanceof AddressOfExpression) {
      visit((AddressOfExpression) expr);
    } else if (expr instanceof DereferenceExpression) {
      visit((DereferenceExpression) expr);
    } else if (expr instanceof NegationExpression) {
      visit((NegationExpression) expr);
    } else if (expr instanceof PipeExpression) {
      visit((PipeExpression) expr);
    } else if (expr instanceof CastExpression) {
      visit((CastExpression) expr);
    } else if (expr instanceof SizeofExpression) {
      visit((SizeofExpression) expr);
    } else if (expr instanceof AdditivePrefixExpression) {
      visit(expr.getFirstOperand());
      expr.setReturnType(expr.getFirstOperand().getReturnType());
    } else {
      throw new ExpressionNotFoundException("Unary expression was not found: " + expr.getLiteral());
    }
  }

  @Override
  public void visit(AdditivePrefixExpression expression) {
    visit(expression.getFirstOperand());
    expression.setReturnType(expression.getFirstOperand().getReturnType());
  }

  /**
   * Visits a binary expression
   *
   * @param expr The binary expression
   */
  @Override
  public void visit(BinaryExpression expr) {
    if (expr instanceof ArithmeticBinaryExpression) {
      visit((ArithmeticBinaryExpression) expr);
    } else if (expr instanceof LogicalBinaryExpression) {
      visit((LogicalBinaryExpression) expr);
    } else if (expr instanceof AssignmentExpression) {
      visit((AssignmentExpression) expr);
    } else if (expr instanceof ArrayExpression) {
      visit((ArrayExpression) expr);
    } else {
      throw new ExpressionNotFoundException(
          "Binary expression was not found: " + expr.getLiteral());
    }
  }

  @Override
  public void visit(AdditiveExpression additiveExpression) {
    visit((ArithmeticBinaryExpression) additiveExpression);
  }

  @Override
  public void visit(MultiplicativeExpression multiplicativeExpression) {
    visit((ArithmeticBinaryExpression) multiplicativeExpression);
  }

  @Override
  public void visit(LogicalRelantionalExpression logicalRelantionalExpression) {
    // TODO: this
  }

  @Override
  public void visit(LogicalAndExpression logicalAndExpression) {
    visit((LogicalBinaryExpression) logicalAndExpression);
  }

  @Override
  public void visit(LogicalEqualityExpression logicalEqualityExpression) {
    // TODO: this
  }

  @Override
  public void visit(LogicalOrExpression logicalOrExpression) {
    visit((LogicalBinaryExpression) logicalOrExpression);
  }

  /**
   * Visits a pipe expression
   *
   * @param expr The pipe expression
   */
  @Override
  public void visit(PipeExpression expr) {
    visit(expr.getFirstOperand());

    expr.setReturnType(expr.getFirstOperand().getReturnType());
  }

  /**
   * Visits a sizeof expression with specifiers
   *
   * @param expr Sizeof expression with specifiers
   */
  @Override
  public void visit(SizeofSpecifier expr) {
    expr.visit(this);
  }

  /**
   * Visits a sizeof expression without specifiers
   *
   * @param expr Sizeof expression without specifiers
   */
  @Override
  public void visit(SizeofExpression expr) {
    expr.visit(this);
  }

  /**
   * Visits a negation expression
   *
   * @param expr The negation expression
   */
  @Override
  public void visit(NegationExpression expr) {
    expr.visit(this);
    TypeRules.determineIfValidType(expr.getFirstOperand(), Types.BOOL);
  }

  /**
   * Visits a function call expression
   *
   * @param expr The function call
   */
  @Override
  public void visit(FunctionExpression expr) {
    String FuncID = expr.getIdentifier();
    FunctionDeclaration funcDef = (FunctionDeclaration) global.lookup(FuncID);

    // check if function is declared
    if (funcDef == null) {
      throw new RuntimeException("Function " + expr.getIdentifier() + " was not declared");
    }

    expr.setDefinition(funcDef);
    ReturnType returnType = TypeRules.determineDeclarationSpecifier(funcDef.getSpecifiers());
    expr.setReturnType(returnType);

    // match number of parameters
    if (funcDef.size() > expr.getParameterList().size()) {
      throw new RuntimeException("Too few parameters given in " + expr.getLiteral());
    } else if (funcDef.size() < expr.getParameterList().size()) {
      throw new RuntimeException("Too many parameters given in " + expr.getLiteral());
    }

    // verify that all parameters match types
    for (int i = 0; i < funcDef.size(); i++) {
      visit(funcDef.get(i));
      ReturnType declarationReturnType = funcDef.get(i).getReturnType();
      Expression parameter = expr.getParameterList().get(i);
      visit(parameter);
      TypeRules.throwIfInvalidDeclaration(declarationReturnType, parameter);
    }
  }

  @Override
  public void visit(IncrementDecrementExpression expression) {

  }

  /**
   * Visits an array expression
   *
   * @param expr The array expression
   */
  @Override
  public void visit(ArrayExpression expr) {
    visit(expr.getFirstOperand());
    visit(expr.getSecondOperand());

    // TODO: long int, long double and long char are also supported.
    TypeRules.determineIfValidType(expr.getSecondOperand(), Types.DOUBLE, Types.INT, Types.CHAR);

    expr.setReturnType(expr.getFirstOperand().getReturnType());
  }

  /**
   * Visits address of expression
   *
   * @param expr The address of expression
   */
  @Override
  public void visit(AddressOfExpression expr) {
    expr.visit(this);
    expr.setReturnType(expr.getFirstOperand().getReturnType());
  }

  /**
   * Visits a dereference expression
   *
   * @param expr The dereference expression
   */
  @Override
  public void visit(DereferenceExpression expr) {
    expr.visit(this);
    expr.setReturnType(expr.getFirstOperand().getReturnType());
  }

  /**
   * Visits an assignment expression
   *
   * @param expr Assignment expression
   */
  @Override
  public void visit(AssignmentExpression expr) {
    expr.visit(this);

    ReturnType returnType = TypeRules.throwIfInvalidDeclaration(
        expr.getFirstOperand().getReturnType(), expr.getSecondOperand()
    );

    expr.setReturnType(returnType);
  }

  /**
   * Visits a logical binary expression
   *
   * @param expr Logical binary expression
   */
  public void visit(LogicalBinaryExpression expr) {
    expr.visit(this);

    Node firstReturnType = expr.getFirstOperand();
    Node secondReturnType = expr.getSecondOperand();

    if (expr instanceof LogicalAndExpression || expr instanceof LogicalOrExpression) {
      TypeRules.determineBooleanReturnType(firstReturnType, secondReturnType);
    } else if (expr instanceof LogicalEqualityExpression) {
      TypeRules.throwIfInvalidDeclaration(expr.getFirstOperand().getReturnType(), secondReturnType);
    } else {
      TypeRules.determineArithmeticReturnType(firstReturnType, secondReturnType);
    }
  }

  /**
   * Visits an arithmetic binary expression
   *
   * @param expr The arithmetic binary expression
   */
  public void visit(ArithmeticBinaryExpression expr) {
    expr.visit(this);

    expr.setReturnType(TypeRules.determineArithmeticReturnType(
        expr.getFirstOperand(), expr.getSecondOperand())
    );

  }

  /**
   * Visits a variable expression
   *
   * @param expr The variable expression
   */
  @Override
  public void visit(VariableExpression expr) {
    expr.setReturnType(expr.getDefinition().getReturnType());
  }

  /**
   * Visits an initializer list
   *
   * @param expr The initializer list
   */
  @Override
  public void visit(InitializerList expr) {
    ReturnType ty = new ReturnType();

    if (expr.size() > 0) {
      ty.type = ((Expression) expr.get(0)).getReturnType().type;

      for (int i = 1; i < expr.size(); i++) {
        TypeRules.throwIfInvalidDeclaration(ty, expr.get(1));
      }
    }

    expr.setReturnType(ty);
  }

  /**
   * Visits a make expression
   *
   * @param expr The make expression
   */
  @Override
  public void visit(MakeExpression expr) {
    ReturnType returnType = TypeRules.determineDeclarationSpecifier(expr.getSpecifiers());
    if (returnType.prefix.getLeft() != TypeModifier.CHAN) {
      throw new RuntimeException(
          "The make function only supports the making of channels. The returnType " + returnType
              .toString()
              + " is not supported.");
    }

    expr.setReturnType(returnType);
  }

  /**
   * Visits a cast expression
   *
   * @param expr The cast expression
   */
  @Override
  public void visit(CastExpression expr) {
    ReturnType specifierReturnType = TypeRules
        .determineDeclarationSpecifier(expr.getSpecifiers());

    if (specifierReturnType.type == Types.BOOL) {
      TypeRules.determineIfValidType(expr.getFirstOperand(), Types.BOOL);
    } else {
      TypeRules.determineIfValidType(expr.getFirstOperand(), Types.DOUBLE, Types.INT, Types.CHAR);
    }

    expr.setReturnType(specifierReturnType);
  }

  /**
   * Helper method to check return type of a function
   *
   * @param body The function body
   * @param returnType The expected return type
   */
  private void checkReturnValidityFunctionDefinition(CompoundStatement body,
      ReturnType returnType) {
    if (body.size() == 0 && returnType.type != Types.VOID) {
      throw new RuntimeException("No returntype exists, expected " + returnType.toString());
    }

    if (returnType.type == Types.VOID) {
      return;
    }

    for (Statement stm : body) {

      if (stm instanceof IfStatement) {
        IfStatement iStm = (IfStatement) stm;
        if (iStm.getConsequence() != null) {
          checkReturnValidityFunctionDefinition((CompoundStatement) iStm.getConsequence(),
              returnType);
        }
        if (iStm.getAlternative() != null) {
          checkReturnValidityFunctionDefinition((CompoundStatement) iStm.getAlternative(),
              returnType);
        }
      }

      if (stm instanceof WhileStatement) {
        WhileStatement wStm = (WhileStatement) stm;
        checkReturnValidityFunctionDefinition((CompoundStatement) wStm.getConsequence(),
            returnType);
      }

      if (stm instanceof ReturnStatement) {
        visit(((ReturnStatement) stm).getReturnValue());
        if (((ReturnStatement) stm).getReturnValue().getReturnType().compareTo(returnType)
            != 0) {
          throw new RuntimeException(
              "Invalid type returned, expected " + returnType.toString());
        } else {
          return;
        }
      }
    }

    throw new RuntimeException("No returnvalue was given, expected " + returnType.toString());
  }

  /**
   * Visit an initializer list, and verify that it can be assigned to the parents type
   *
   * @param initializerList The initalizer list
   * @param parentReturnType The return type of the parent
   */
  private void visit(InitializerList initializerList, ReturnType parentReturnType) {
    for (Node initializer : initializerList) {
      visit((Expression) initializer);
      TypeRules.throwIfInvalidDeclaration(parentReturnType, initializer);
    }

    initializerList.setReturnType(parentReturnType);
  }
}