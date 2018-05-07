package concuroo.nodes.expression;

import concuroo.nodes.Node;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class ArgumentExpressionList implements Node {

  private List<Node> parameters = new ArrayList<>();

  public void addParam(Node param) {
    parameters.add(param);
  }

  public List<Node> getParams() {
    return parameters;
  }


  @Override
  public String getLiteral() {
    List<String> params = new ArrayList<>();
    for (Node param : parameters) {
      params.add(param.getLiteral());
    }

    return StringUtils.join(params, ", ");
  }
}