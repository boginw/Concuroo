package concuroo.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class DeclarationSpecifierList implements Node {

  private List<String> specifiers = new ArrayList<>();

  public DeclarationSpecifierList(List<String> specifiers) {
    this.specifiers = specifiers;
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

  @Override
  public String getLiteral() {
    return StringUtils.join(specifiers, " ");
  }
}
