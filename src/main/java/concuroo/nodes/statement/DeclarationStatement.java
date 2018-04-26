package concuroo.nodes.statement;

import concuroo.nodes.Statement;
import concuroo.nodes.expression.Expression;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class DeclarationStatement implements Statement {

  private List<String> specifiers;
  private boolean pointer = false;
  private String identifier;
  private Expression arraySize = null;
  private boolean isArray = false;
  private Expression initializer;
  private boolean param;

  /**
   * Gets the declaration's identifier
   *
   * @return The declaration's identifier
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Sets the declaration's identifier
   *
   * @param identifier A string that'll be the new identifier for this declaration
   */
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
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
   * Default constructor
   */
  public DeclarationStatement() {
    specifiers = new ArrayList<>();
  }

  /**
   * Constructs a DeclarationStatement
   *
   * @param populated A populated list of type specifiers
   */
  public DeclarationStatement(List<String> populated) {
    specifiers = populated;
  }

  /**
   * Gets the count of specifiers in the declaration
   *
   * @return Size of the specifiers list
   */
  public int getSpecifiersCount() {
    return specifiers.size();
  }

  /**
   * Adds one or more specifier to the list of specifiers
   *
   * @param args One or more specifier
   */
  public void addSpecifier(String... args) {
    Collections.addAll(specifiers, args);
  }

  /**
   * Gets all specifiers
   *
   * @return List of all specifiers
   */
  public List<String> getSpecifiers() {
    return specifiers;
  }

  /**
   * Sets the declaration's specifiers
   *
   * @param specifiers List of specifiers
   */
  public void setSpecifiers(List<String> specifiers) {
    this.specifiers = specifiers;
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

  @Override
  public String getLiteral() {
    return StringUtils.join(specifiers, " ") + ' ' +
        (pointer ? '*' : "") + identifier + (isArray ? '[' + arraySize.getLiteral() + ']' : "") +
        (initializer != null ? " = " + initializer.getLiteral() : "") + (isParam() ? "" : ";");
  }

}
