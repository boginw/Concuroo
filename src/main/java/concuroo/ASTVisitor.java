package concuroo;

import concuroo.exceptions.ExpressionNotFoundException;
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
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalAndExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalEqualityExpression;
import concuroo.nodes.expression.binaryExpression.logicalBinaryExpression.LogicalOrExpression;
import concuroo.nodes.expression.lhsExpression.ArrayExpression;
import concuroo.nodes.expression.lhsExpression.DereferenceExpression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;
import concuroo.nodes.expression.literalExpression.StringLiteral;
import concuroo.nodes.expression.unaryExpression.AdditivePrefixExpression;
import concuroo.nodes.expression.unaryExpression.AddressOfExpression;
import concuroo.nodes.expression.unaryExpression.CastExpression;
import concuroo.nodes.expression.unaryExpression.FunctionExpression;
import concuroo.nodes.expression.unaryExpression.MakeExpression;
import concuroo.nodes.expression.unaryExpression.NegationExpression;
import concuroo.nodes.expression.unaryExpression.PipeExpression;
import concuroo.nodes.expression.unaryExpression.SizeofExpression;
import concuroo.nodes.statement.CompoundStatement;
import concuroo.nodes.statement.CoroutineStatement;
import concuroo.nodes.statement.DeleteStatement;
import concuroo.nodes.statement.ExpressionStatement;
import concuroo.nodes.statement.VariableDeclaration;
import concuroo.nodes.statement.jumpStatement.ReturnStatement;
import concuroo.nodes.statement.selectionStatement.IfStatement;
import concuroo.nodes.statement.selectionStatement.WhileStatement;
import concuroo.symbol.SymbolTable;

public class ASTVisitor {

  private SymbolTable global;

  public ASTVisitor(SymbolTable st) {
    this.global = st;
  }

  /**
   * Visits the crux, namely the program
   *
   * @param program The program to check
   */
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

  /**
   * Visits a compound statement
   *
   * @param cpmStm The compound statement
   */
  private void visit(CompoundStatement cpmStm) {
    for (Statement stm : cpmStm) {
      visit(stm);
    }
  }

  /**
   * Visits a delete statement
   *
   * @param stm The delete statement to visit
   */
  private void visit(DeleteStatement stm) {
    Expression expr = (Expression) visit(stm.getVariable());

    if (!(expr instanceof VariableExpression) || (
        (expr.getReturnType().prefix.getLeft() != TypeModifier.CHAN)
            && (expr.getReturnType().prefix.getRight() != TypeModifier.CHAN))) {
      throw new RuntimeException(
          "Can only delete chan types. Deleted type is: " + expr.getReturnType().toString());
    }
  }

  /**
   * Visits a coroutine statement
   *
   * @param stm The coroutine statement to visit
   */
  private void visit(CoroutineStatement stm) {
    Expression expr = (Expression) visit(stm.getExpression());

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
  private void visit(IfStatement stm) {
    Expression condition = (Expression) visit(stm.getCondition());

    visit(stm.getConsequence());

    if (stm.getAlternative() != null) {
      visit(stm.getAlternative());
    }

    if (condition.getReturnType().type != Types.BOOL) {
      throw new RuntimeException(
          "The condition was not a boolean expression in " + stm.getClass().getName());
    }
  }

  /**
   * Visits an expression statement
   *
   * @param exprStm Expression statement
   * @return The visited statement
   */
  public Node visit(ExpressionStatement exprStm) {
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

    return exprStm;
  }

  /**
   * Visits a declaration statement
   *
   * @param stm The declaration statement to visit
   */
  private void visit(VariableDeclaration stm) {
    ReturnType returnType = visit(stm.getSpecifiers());
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

  /**
   * Visits declartion specifiers
   *
   * @param specifiers The specifiers to visit
   * @return The final type, given the specifiers
   */
  private ReturnType visit(DeclarationSpecifierList specifiers) {
    return TypeRules.determineDeclarationSpecifier(specifiers);
  }

  /**
   * Visits an expression
   *
   * @param expr The expression to visit
   * @return The visited expression
   */
  private Node visit(Expression expr) {
    if (expr.getReturnType() != null) {
      return expr;
    } else if (expr instanceof BinaryExpression) {
      return visit((BinaryExpression) expr);
    } else if (expr instanceof UnaryExpression) {
      return visit((UnaryExpression) expr);
    } else if (expr instanceof FunctionExpression) {
      return visit((FunctionExpression) expr);
    } else if (expr instanceof MakeExpression) {
      return visit((MakeExpression) expr);
    } else if (expr instanceof InitializerList) {
      return visit((InitializerList) expr);
    } else if (expr instanceof VariableExpression) {
      return visit((VariableExpression) expr);
    } else {
      throw new RuntimeException("The expression wasn't a valid type.");
    }
  }

  /**
   * Visits a unary expression
   *
   * @param expr The unary expression
   * @return The visited unary expression
   */
  private Node visit(UnaryExpression expr) {
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

    return expr;
  }

  /**
   * Visits a binary expression
   *
   * @param expr The binary expression
   * @return The visited binary expression
   */
  private Node visit(BinaryExpression expr) {
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

    return expr;
  }

  /**
   * Visits a pipe expression
   *
   * @param expr The pipe expression
   */
  private void visit(PipeExpression expr) {
    visit(expr.getFirstOperand());

    expr.setReturnType(expr.getFirstOperand().getReturnType());
  }

  /**
   * Visits a sizeof expression with specifiers
   *
   * @param expr Sizeof expression with specifiers
   */
  private void visit(SizeofSpecifier expr) {
    visit(expr.getSpecifiers());
  }

  /**
   * Visits a sizeof expression without specifiers
   *
   * @param expr Sizeof expression without specifiers
   */
  private void visit(SizeofExpression expr) {
    visit(expr.getFirstOperand());
  }

  /**
   * Visits a negation expression
   *
   * @param expr The negation expression
   */
  private void visit(NegationExpression expr) {
    TypeRules.determineIfValidType(expr.getFirstOperand(), Types.BOOL);
  }

  /**
   * Visits a function call expression
   *
   * @param expr The function call
   * @return The visited function call expression
   */
  private Node visit(FunctionExpression expr) {
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

    return expr;
  }

  /**
   * Visits an array expression
   *
   * @param expr The array expression
   */
  private void visit(ArrayExpression expr) {
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
  private void visit(AddressOfExpression expr) {
    visit(expr.getFirstOperand());
    expr.setReturnType(expr.getFirstOperand().getReturnType());
  }

  /**
   * Visits a dereference expression
   *
   * @param expr The dereference expression
   */
  private void visit(DereferenceExpression expr) {
    visit(expr.getFirstOperand());
    expr.setReturnType(expr.getFirstOperand().getReturnType());
  }

  /**
   * Visits an assignment expression
   *
   * @param expr Assignment expression
   */
  private void visit(AssignmentExpression expr) {
    Expression firstReturnType = (Expression) visit(expr.getFirstOperand());
    expr.setSecondOperand((Expression) visit(expr.getSecondOperand()));

    ReturnType returnType = TypeRules.throwIfInvalidDeclaration(
        firstReturnType.getReturnType(), expr.getSecondOperand()
    );

    expr.setReturnType(returnType);
  }

  /**
   * Visits a logical binary expression
   *
   * @param expr Logical binary expression
   */
  private void visit(LogicalBinaryExpression expr) {
    Node firstReturnType = visit(expr.getFirstOperand());
    Node secondReturnType = visit(expr.getSecondOperand());

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
  private void visit(ArithmeticBinaryExpression expr) {
    Node firstReturnType = visit(expr.getFirstOperand());
    Node secondReturnType = visit(expr.getSecondOperand());

    expr.setReturnType(TypeRules.determineArithmeticReturnType(firstReturnType, secondReturnType));

  }

  /**
   * Visits a variable expression
   *
   * @param expr The variable expression
   */
  private Node visit(VariableExpression expr) {
    return expr;
  }

  /**
   * Visits an initializer list
   *
   * @param expr The initializer list
   * @return The visited initializer list
   */
  private Node visit(InitializerList expr) {
    ReturnType ty = new ReturnType();

    if (expr.size() > 0) {
      ty.type = ((Expression) expr.get(0)).getReturnType().type;

      for (int i = 1; i < expr.size(); i++) {
        TypeRules.throwIfInvalidDeclaration(ty, expr.get(1));
      }
    }

    expr.setReturnType(ty);
    return expr;
  }

  /**
   * Visits a make expression
   *
   * @param expr The make expression
   * @return The visited make expression
   */
  private Node visit(MakeExpression expr) {
    ReturnType returnType = TypeRules.determineDeclarationSpecifier(expr.getSpecifiers());
    if (returnType.prefix.getLeft() != TypeModifier.CHAN) {
      throw new RuntimeException(
          "The make function only supports the making of channels. The returnType " + returnType
              .toString()
              + " is not supported.");
    }

    expr.setReturnType(returnType);
    return expr;
  }

  /**
   * Visits a cast expression
   *
   * @param expr The cast expression
   */
  private void visit(CastExpression expr) {
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
}