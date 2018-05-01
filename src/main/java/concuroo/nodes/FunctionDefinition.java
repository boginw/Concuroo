package concuroo.nodes;

import concuroo.nodes.statement.CompoundStatement;
import concuroo.nodes.statement.VariableDefinition;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class FunctionDefinition implements Node {

  private List<String> specifiers;
  private boolean pointer;
  private String identifier;
  private CompoundStatement body;
  private List<VariableDefinition> parameters = new ArrayList<>();

  @Override
  public String getLiteral() {
    List<String> params = new ArrayList<>();
    for (VariableDefinition param : parameters) {
      params.add(param.getLiteral());
    }

    return StringUtils.join(specifiers, " ") + ' ' +
        (pointer ? '*' : "") + identifier + '(' + StringUtils.join(params, ", ") + ')' + body
        .getLiteral();
  }

  public void setSpecifiers(List<String> specifiers) {
    this.specifiers = specifiers;
  }

  public List<String> getSpecifiers() {
    return specifiers;
  }

  public void setPointer(boolean pointer) {
    this.pointer = pointer;
  }

  public boolean getPointer() {
    return pointer;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getIdentifier() {
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
