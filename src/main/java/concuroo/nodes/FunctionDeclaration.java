package concuroo.nodes;

import concuroo.CodeGenerator;
import concuroo.nodes.expression.Identifier;
import concuroo.nodes.statement.CompoundStatement;
import concuroo.nodes.statement.VariableDeclaration;
import concuroo.symbol.SymbolTable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * This class represents the declaration of a function
 */
public class FunctionDeclaration extends ArrayList<VariableDeclaration> implements Node, HasScope,
    HasSpecifiers, Identifier, Declaration {

  private DeclarationSpecifierList specifiers;
  private boolean pointer;
  private String identifier;
  private CompoundStatement body;
  private boolean isCoroutined = false;
  private SymbolTable scope = new SymbolTable();

  /**
   * Sets whether or not this function returns a pointer
   *
   * @param pointer whether or not this function returns a pointer
   */
  public void setPointer(boolean pointer) {
    this.pointer = pointer;
  }

  /**
   * Gets whether or not this function returns a pointer
   *
   * @return whether or not this function returns a pointer
   */
  public boolean getPointer() {
    return pointer;
  }

  /**
   * Sets the body of the function
   *
   * @param body The body of the function
   */
  public void setBody(CompoundStatement body) {
    this.body = body;
  }

  /**
   * Gets the body of the function
   *
   * @return The body of the function
   */
  public CompoundStatement getBody() {
    return body;
  }

  /**
   * Appends the variable definition to the end of this functions parameters list.
   *
   * @param parameter Variable definition to be insterted
   * @return true, (as specified by Collection.add(E))
   */
  @Override
  public boolean add(VariableDeclaration parameter) {
    scope.insert(parameter);
    return super.add(parameter);
  }

  /**
   * Gets whether this function is called as a coroutine or not
   *
   * @return Whether or not this function is called as a coroutine
   */
  public boolean isCoroutined() {
    return isCoroutined;
  }

  /**
   * Sets whether this function is called as a coroutine or not
   *
   * @param coroutine Whether or not this function is called as a coroutine
   */
  public void setCoroutined(boolean coroutine) {
    this.isCoroutined = coroutine;
  }


  @Override
  public void setSpecifiers(DeclarationSpecifierList specifiers) {
    this.specifiers = specifiers;
  }

  @Override
  public DeclarationSpecifierList getSpecifiers() {
    return specifiers;
  }

  @Override
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }

  @Override
  public void accept(CodeGenerator cg) {
    cg.visit(this);
  }

  @Override
  public SymbolTable getScope() {
    return scope;
  }

  @Override
  public void setScope(SymbolTable scope) {
    this.scope = scope;
  }

  @Override
  public String getLiteral() {
    List<String> params = new ArrayList<>();
    for (VariableDeclaration param : this) {
      params.add(param.getLiteral());
    }

    return StringUtils.join(specifiers, " ") + ' ' +
        (pointer ? '*' : "") + identifier + '(' + StringUtils.join(params, ", ") + ')'
        + body
        // TODO: why not get literal of body?
//        .getLiteral();
        ;
  }
}
