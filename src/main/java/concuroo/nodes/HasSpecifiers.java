package concuroo.nodes;

public interface HasSpecifiers {

  /**
   * Gets a list of specifiers for the declaration.
   *
   * @return list of specifiers.
   */
  DeclarationSpecifierList getSpecifiers();

  /**
   * Sets the list of specifiers used for declaration.
   *
   * @param specifiers is a list of specifiers.
   */
  void setSpecifiers(DeclarationSpecifierList specifiers);
}
