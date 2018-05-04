package concuroo.nodes.expression;

import concuroo.nodes.Node;
import java.util.ArrayList;
import java.util.List;

public class ArgumentExpressionList implements Node {

  private List<Node> parameters = new ArrayList<>();

  public void addParam(Node param){
    parameters.add(param);
  }

  public List<Node> getParams(){
    return parameters;
  }

  @Override
  public String getLiteral() {
    StringBuilder sb = new StringBuilder();
    for(Node n: parameters) {
      sb.append(n.getLiteral() + ", ");
    }
    sb.delete(sb.length()-2, sb.length());
    return sb.toString();
  }
}