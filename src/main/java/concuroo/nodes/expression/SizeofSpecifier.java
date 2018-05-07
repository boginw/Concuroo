package concuroo.nodes.expression;

import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.HasSpecifiers;

public class SizeofSpecifier implements Expression, HasSpecifiers {
  private DeclarationSpecifierList specifiers;

  public SizeofSpecifier(DeclarationSpecifierList specifiers) {
    setSpecifiers(specifiers);
  }

  public String getOperator() {
    return "sizeof";
  }

  public String getLiteral() {
    return getOperator() + "(" + getSpecifiers().getLiteral() + ")";
  }

  @Override
  public DeclarationSpecifierList getSpecifiers() {
    return specifiers;
  }

  @Override
  public void setSpecifiers(DeclarationSpecifierList specifiers) {
    this.specifiers = specifiers;
  }
}
