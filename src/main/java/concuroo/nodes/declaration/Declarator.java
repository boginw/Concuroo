package concuroo.nodes.declaration;

import concuroo.nodes.Node;
import concuroo.nodes.Pointer;

public class Declarator implements Node {

  private Pointer pointer;
  private DirectDeclarator directDeclarator;

  public void setPointer(Pointer pointer) {
    this.pointer = pointer;
  }

  @Override
  public String getLiteral() {
    return null;
  }

  public Pointer getPointer() {
    return this.pointer;
  }

  public void setDirectDeclarator(DirectDeclarator directDeclarator) {
    this.directDeclarator = directDeclarator;
  }

  public DirectDeclarator getDirectDeclarator() {
    return this.directDeclarator;
  }
}