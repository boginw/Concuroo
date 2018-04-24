package concuroo.nodes;

public class TypeSpecifier implements Node {

  private String specifier;

  public String getSpecifier() {
    return specifier;
  }

  public void setSpecifier(String specifier) {
    this.specifier = specifier;
  }

  @Override
  public String getLiteral() {
    return null;
  }
}