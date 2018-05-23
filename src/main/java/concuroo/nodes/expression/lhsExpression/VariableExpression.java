package concuroo.nodes.expression.lhsExpression;

import concuroo.ReturnType;
import concuroo.nodes.expression.HasDefinition;
import concuroo.nodes.expression.LHSExpression;
import concuroo.nodes.expression.Identifier;
import concuroo.nodes.statement.VariableDeclaration;

/**
 * This class represents an variable used in an expression.
 */
public class VariableExpression implements LHSExpression, Identifier,
    HasDefinition<VariableDeclaration> {

  private String identifier;
  private VariableDeclaration definition;

  /**
   * Default constructor
   *
   * @param identifier Identifier of the variable
   * @param definition The definition of the variable
   */
  public VariableExpression(String identifier, VariableDeclaration definition) {
    this.identifier = identifier;
    this.definition = definition;
  }

  @Override
  public String getLiteral() {
    return identifier;
  }

  @Override
  public String toString() {
    return identifier;
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }

  @Override
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public VariableDeclaration getDefinition() {
    return this.definition;
  }

  @Override
  public void setDefinition(VariableDeclaration definition) {
    this.definition = definition;
  }

  @Override
  public ReturnType getReturnType() {
    return definition.getReturnType();
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    definition.setReturnType(returnReturnType);
  }
}
