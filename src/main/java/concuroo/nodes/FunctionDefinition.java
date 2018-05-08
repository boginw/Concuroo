package concuroo.nodes;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.unaryExpression.FunctionExpression;
import concuroo.nodes.expression.unaryExpression.Identifier;
import concuroo.nodes.statement.CompoundStatement;
import concuroo.nodes.statement.VariableDefinition;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class FunctionDefinition implements Node, HasSpecifiers, Identifier {

  private DeclarationSpecifierList specifiers;
  private boolean pointer;
  private FunctionExpression identifier;
  private CompoundStatement body;
  private List<VariableDefinition> parameters = new ArrayList<>();

  @Override
  public String getLiteral() {
    List<String> params = new ArrayList<>();
    for (VariableDefinition param : parameters) {
      params.add(param.getLiteral());
    }

    return StringUtils.join(specifiers, " ") + ' ' +
        (pointer ? '*' : "") + identifier.getLiteral() + '(' + StringUtils.join(params, ", ") + ')'
        + body
        .getLiteral();
  }

  public void setSpecifiers(DeclarationSpecifierList specifiers) {
    this.specifiers = specifiers;
  }

  public DeclarationSpecifierList getSpecifiers() {
    return specifiers;
  }

  public void setPointer(boolean pointer) {
    this.pointer = pointer;
  }

  public boolean getPointer() {
    return pointer;
  }

  @Override
  public void setIdentifier(Expression identifier) {
    this.identifier = (FunctionExpression) identifier;
  }

  public FunctionExpression getIdentifier() {
    return identifier;
  }

  public void setBody(CompoundStatement body) {
    this.body = body;
  }

  public CompoundStatement getBody() {
    return body;
  }

  public void addParameter(VariableDefinition parameter) {
    this.parameters.add(parameter);
  }

  public int parameterCount() {
    return parameters.size();
  }

  public VariableDefinition getParameter(int index) {
    return parameters.get(index);
  }

  public List<VariableDefinition> getParameters() {
    return this.parameters;
  }

  public void setParameters(List<VariableDefinition> parameters) {
    this.parameters = parameters;
  }
}
