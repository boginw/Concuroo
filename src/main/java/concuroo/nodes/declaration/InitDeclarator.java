package concuroo.nodes.declaration;

import concuroo.nodes.Node;

public class InitDeclarator implements Node {

  private Declarator declarator;
  private Initializer initializer;

  @Override
  public String getLiteral() {
    return null;
  }

  public void setDeclarator(Declarator declarator) {
    this.declarator = declarator;
  }

  public Declarator getDeclarator() {
    return this.declarator;
  }

  public void setInitializer(Initializer initializer) {
    this.initializer = initializer;
  }

  public Initializer getInitializer() {
    return this.initializer;
  }
}