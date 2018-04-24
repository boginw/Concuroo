package concuroo.nodes;

public class pointer implements Node {

  private TypeQualifierList TypeQualifierList;

  @Override
  public String getLiteral() {
    return null;
  }

  public TypeQualifierList getTypeQualifierList() {
    return TypeQualifierList;
  }

  public void setTypeQualifierList(TypeQualifierList TypeQualifierList) {
    this.TypeQualifierList = TypeQualifierList;
  }
}