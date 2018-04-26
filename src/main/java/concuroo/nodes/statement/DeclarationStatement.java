package concuroo.nodes.statement;

import concuroo.nodes.Statement;
import concuroo.nodes.TypeSpecifier;
import concuroo.nodes.declaration.InitDeclarator;
import java.util.ArrayList;
import java.util.List;

public class DeclarationStatement implements Statement {

  private List<TypeSpecifier> specifiers;
  private InitDeclarator initDeclarator;

  public DeclarationStatement(){
    specifiers = new ArrayList<>();
  }

  public DeclarationStatement(List<TypeSpecifier> prePopulatedList) {
    specifiers = prePopulatedList;
  }

  @Override
  public String getLiteral() {
    return null;
  }

  public int getSpecifiersCount(){
    return specifiers.size();
  }

  public void addSpecifier(TypeSpecifier typeSpecifier) {
    specifiers.add(typeSpecifier);
  }

  public List<TypeSpecifier> getSpecifiers() {
    return specifiers;
  }

  public void setInitDeclarator(InitDeclarator initDeclarator) {
    this.initDeclarator = initDeclarator;
  }

  public InitDeclarator getInitDeclarator() {
    return this.initDeclarator;
  }
}
