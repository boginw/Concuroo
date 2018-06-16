package concuroo.nodes.statement;

import ConcurooParser.ConcurooParser.DeclarationSpecifiersContext;
import ConcurooParser.ConcurooParser.DeclarationStatementContext;
import ConcurooParser.ConcurooParser.DeclaratorContext;
import ConcurooParser.ConcurooParser.DirectDeclaratorContext;
import ConcurooParser.ConcurooParser.InitDeclaratorContext;
import ConcurooParser.ConcurooParser.ParameterDeclarationContext;
import concuroo.CSTVisitor;
import concuroo.CodeGenerator;
import concuroo.types.ReturnType;
import concuroo.Visitor;
import concuroo.nodes.Declaration;
import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.Expression;
import concuroo.nodes.HasSpecifiers;
import concuroo.nodes.Node;
import concuroo.nodes.Statement;
import concuroo.nodes.expression.Identifier;
import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.commons.lang3.StringUtils;

public class VariableDeclaration implements Statement, Expression, Identifier, HasSpecifiers,
    Declaration {

  private DeclarationSpecifierList specifiers = new DeclarationSpecifierList();

  private ReturnType returnReturnType;
  private boolean pointer = false;
  private String identifier;
  private Expression arraySize = null;
  private boolean isArray = false;
  private Expression initializer;
  private boolean param;
  private boolean global = false;

  /**
   * Default constructor
   */
  public VariableDeclaration() {

  }

  /**
   * Constructs a VariableDeclaration
   *
   * @param specifiers A populated list of type specifiers
   */
  public VariableDeclaration(DeclarationSpecifierList specifiers) {
    this.specifiers = specifiers;
  }

  /**
   * Returns if the variable declaration is in global scope. Usefull in Code Generation.
   *
   * @return boolean
   */
  public boolean isGlobal() {
    return global;
  }

  /**
   * Sets global
   *
   * @param global the boolean value
   */
  public void setGlobal(boolean global) {
    this.global = global;
  }

  /**
   * Gets the array size of this declaration
   *
   * @return Expression representing this declaration's array size
   */
  public Expression getArraySize() {
    return arraySize;
  }

  /**
   * Sets this declaration's array size. Also sets this declaration to be an array declaration
   *
   * @param arraySize Expression representing this declaration's array size
   */
  public void setArraySize(Expression arraySize) {
    isArray = true;
    this.arraySize = arraySize;
  }

  /**
   * Checks whether this declaration is an array declaration
   *
   * @return Whether or not this declaration is an array declaration
   */
  public boolean isArray() {
    return isArray;
  }

  /**
   * Sets whether this declaration is an array declaration
   *
   * @param isArray Whether or not this declaration should be an array declaration
   */
  public void setIsArray(boolean isArray) {
    this.isArray = isArray;
  }

  /**
   * Gets the initial value for the declaration
   *
   * @return Initial value for declaration
   */
  public Expression getInitializer() {
    return initializer;
  }

  /**
   * Sets the inital value for the declaration
   *
   * @param initializer Initial value for declaration
   */
  public void setInitializer(Expression initializer) {
    this.initializer = initializer;
  }

  /**
   * Sets whether this declaration is a pointer
   *
   * @param pointer Whether this should be a pointer or not
   */
  public void setPointer(boolean pointer) {
    this.pointer = pointer;
  }

  /**
   * Checks if this declaration is a pointer
   *
   * @return Whether this is a pointer or not
   */
  public boolean isPointer() {
    return pointer;
  }

  /**
   * Checks if this declaration actually is a parameter
   *
   * @return Whether or not this declaration is a parameter
   */
  public boolean isParam() {
    return param;
  }

  /**
   * Sets whether this is a parameter or not
   *
   * @param param Whether or not this declaration should be a parameter or not
   */
  public void setParam(boolean param) {
    this.param = param;
  }

  /**
   * Gets the declaration's identifier
   *
   * @return The declaration's identifier
   */
  @Override
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Sets the declaration's identifier
   *
   * @param identifier A string that'll be the new identifier for this declaration
   */
  @Override
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public DeclarationSpecifierList getSpecifiers() {
    return specifiers;
  }

  @Override
  public void setSpecifiers(DeclarationSpecifierList specifiers) {
    this.specifiers = specifiers;
  }

  @Override
  public String getLiteral() {
    return StringUtils.join(specifiers, " ") + ' ' +
        (pointer ? '*' : "") + identifier + (isArray ? '[' + arraySize.getLiteral()
        + ']' : "") + (initializer != null ? " = " + initializer.getLiteral() : "") +
        (isParam() ? "" : ";");
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    // Step 1: Down the rabbit hole
    InitDeclaratorContext initDeclarator;
    DeclaratorContext declarator;
    DirectDeclaratorContext directDeclarator;

    if (ctx instanceof ParameterDeclarationContext) {
      declarator = ((ParameterDeclarationContext) ctx).declarator();
      directDeclarator = declarator.directDeclarator();
      setParam(true);
    } else if (ctx instanceof DeclarationStatementContext) {
      initDeclarator = (InitDeclaratorContext) ctx.getChild(1);
      declarator = initDeclarator.declarator();
      directDeclarator = declarator.directDeclarator();

      // Step 6: Check if it has initializer?
      if (initDeclarator.children.size() > 1) {
        setInitializer((Expression) visitor.visitInitializer(initDeclarator.initializer())
        );
      }

      if (visitor.getGlobal().isGlobalScope()) {
        setGlobal(true);
      }

    } else {
      throw new RuntimeException("Invalid context");
    }

    // Step 2: Fetch all type specifiers
    if (ctx.getChild(0) instanceof DeclarationSpecifiersContext) {
      setSpecifiers(Node.parseDeclarationSpecifiers(ctx.getChild(0)));
    }

    // Step 3: Check if it is a pointer
    if (declarator.children.size() > 1) {
      setPointer(true);
    }

    // Step 4: Find the identifier
    setIdentifier(directDeclarator.getChild(0).getText());

    // Step 5: Check if it is an array declaration
    if (directDeclarator.children.size() > 1) {
      setArraySize(
          (Expression) visitor.visitAssignmentExpression(directDeclarator.assignmentExpression())
      );
    }

    if (!isParam()) {
      visitor.getGlobal().insert(this);
    }

    // Step 7: Profit?
    return this;
  }

  @Override
  public void visit(Visitor visitor) {
    if (initializer != null) {
      visitor.visit(initializer);
    }

    visitor.visit(this);
  }

  @Override
  public void accept(CodeGenerator cg) {
    cg.visit(this);
  }

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    this.returnReturnType = returnReturnType;
  }


}
