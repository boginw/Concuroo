package concuroo.nodes;

import concuroo.nodes.statement.VariableDefinition;

public class ExternalDeclaration implements Node{

  private FunctionDefinition functionDefinition;
  private VariableDefinition declarationStatement;

  public void setType(Node type) {
    if(type instanceof FunctionDefinition) {
      this.functionDefinition = (FunctionDefinition) type;
    }
    else if (type instanceof VariableDefinition){
      this.declarationStatement = (VariableDefinition) type;
    }
  }

  public Node getType(){
    if (functionDefinition != null){
      return functionDefinition;
    }
    return declarationStatement;
  }

  @Override
  public String getLiteral() {
    if (functionDefinition != null){
      return functionDefinition.getLiteral() + '\n';
    }
    return declarationStatement.getLiteral() + '\n';
  }
}
