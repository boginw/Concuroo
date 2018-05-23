package concuroo.nodes.expression;

import concuroo.ReturnType;
import concuroo.Types;
import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.Expression;
import concuroo.nodes.HasSpecifiers;

/**
 * This class represents an expression of the form sizeof(long int)
 */
public class SizeofSpecifier implements Expression, HasSpecifiers {

  private ReturnType returnReturnType;
  private DeclarationSpecifierList specifiers;

  /**
   * Default constructor
   *
   * @param specifiers The specifiers to take the size of
   */
  public SizeofSpecifier(DeclarationSpecifierList specifiers) {
    setSpecifiers(specifiers);
    returnReturnType = new ReturnType();
    returnReturnType.type = Types.INT;
  }

  @Override
  public String getLiteral() {
    return "sizeof" + "(" + getSpecifiers().getLiteral() + ")";
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
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    throw new RuntimeException("Cannot set return type of a sizeof operation");
  }
}
