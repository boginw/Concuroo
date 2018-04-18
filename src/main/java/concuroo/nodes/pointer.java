package concuroo.nodes;

public class pointer implements Node {

  private typeQualifierList typeQualifierList;

  @Override
  public String getLiteral() {
    return null;
  }

  public concuroo.nodes.typeQualifierList getTypeQualifierList() {
    return typeQualifierList;
  }

  public void setTypeQualifierList(concuroo.nodes.typeQualifierList typeQualifierList) {
    this.typeQualifierList = typeQualifierList;
  }
}